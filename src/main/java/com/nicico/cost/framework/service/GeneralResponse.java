package com.nicico.cost.framework.service;

import com.nicico.cost.framework.domain.dto.BaseDTO;
import com.nicico.cost.framework.enums.ResultStatus;
import com.nicico.cost.framework.utility.impl.ApplicationResourceImpl;

import java.util.List;

public interface GeneralResponse<R> {


    static <G> BaseDTO<G> successCustomResponse(G o) {
        return BaseDTO.<G>builder().data(o)
                .resultCode(ApplicationResourceImpl.successResource().getResultCode())
                .resultMessage(ApplicationResourceImpl.successResource().getResultMessage())
                .status(ResultStatus.SUCCESS).build();
    }

    static <G> BaseDTO<List<G>> successCustomListResponse(List<G> o) {
        return BaseDTO.<List<G>>builder().data(o)
                .resultCode(ApplicationResourceImpl.successResource().getResultCode())
                .resultMessage(ApplicationResourceImpl.successResource().getResultMessage())
                .status(ResultStatus.SUCCESS).build();
    }

    BaseDTO<R> successResponse(R o);

    BaseDTO<List<R>> successListResponse(List<R> o);

}
