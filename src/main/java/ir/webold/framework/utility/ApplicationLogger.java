package ir.webold.framework.utility;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ir.webold.framework.domain.viewmodel.AuditReqVM;
import ir.webold.framework.enums.audit.AuditLocation;
import ir.webold.framework.enums.audit.AuditType;
import ir.webold.framework.enums.audit.LogLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import static ir.webold.framework.aop.AuditService.DATE_PATTERN;
import static ir.webold.framework.config.general.GeneralStatic.RRN;
import static ir.webold.framework.service.GeneralService.successCustomResponse;

@Component
public class ApplicationLogger {

    private static final Logger APP_LOG = LoggerFactory.getLogger("APP_LOG");

    private final ObjectMapper objectMapper;
    private final ApplicationKafka applicationKafka;
    private final ApplicationRequest applicationRequest;

    @Autowired
    public ApplicationLogger(ObjectMapper objectMapper, ApplicationKafka applicationKafka, ApplicationRequest applicationRequest) {
        this.objectMapper = objectMapper;
        this.applicationKafka = applicationKafka;
        this.applicationRequest = applicationRequest;
    }

    @Value("${kafka.audit.topic}")
    private String logTopic;

    @Value("${spring.application.name}")
    private String microserviceName;

    @Value("${log.location}")
    private String logLocation;


    @Async("treadPoolAsync")
    public void around(LogLevel level, String clazz, String method, String message) {
        String rrn = applicationRequest.getHeader(RRN);
        AuditReqVM auditReqVM = AuditReqVM.builder()
                .method(method)
                .clazz(clazz)
                .microServiceName(microserviceName)
                .rrn(rrn)
                .type(AuditType.AROUND)
                .level(level.name())
                .result(successCustomResponse(message))
                .time(new SimpleDateFormat(DATE_PATTERN).format(new Timestamp(System.currentTimeMillis())))
                .build();
        log(auditReqVM, LogLevel.INFO);

    }


    private String convertToJson(Object o) {
        try {
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            return null;
        }
    }


    @Async("treadPoolAsync")
    public void log(Object o, LogLevel level) {
        if (Boolean.TRUE.equals(logLocation.equals(AuditLocation.KAFKA.name()))) {
            logInKafka(convertToJson(o));
        } else if (Boolean.TRUE.equals(logLocation.equals(AuditLocation.FILE.name()))) {
            logInfile(convertToJson(o), level);
        } else if (Boolean.TRUE.equals(logLocation.equals(AuditLocation.BOTH.name()))) {
            logInKafka(convertToJson(o));
            logInfile(convertToJson(o), level);
        }
    }


    private void logInKafka(String text) {
        applicationKafka.sendMessage(logTopic, text);
    }


    private void logInfile(String text, LogLevel level) {
        if (Boolean.TRUE.equals(level.name().equals(LogLevel.INFO.name())))
            APP_LOG.info(text);
        if (Boolean.TRUE.equals(level.name().equals(LogLevel.ERROR.name())))
            APP_LOG.error(text);
        if (Boolean.TRUE.equals(level.name().equals(LogLevel.DEBUG.name())))
            APP_LOG.debug(text);
        if (Boolean.TRUE.equals(level.name().equals(LogLevel.WARN.name())))
            APP_LOG.warn(text);
        if (Boolean.TRUE.equals(level.name().equals(LogLevel.TRACE.name())))
            APP_LOG.trace(text);
    }

}
