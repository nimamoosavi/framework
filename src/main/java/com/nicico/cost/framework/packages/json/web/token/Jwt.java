package com.nicico.cost.framework.packages.json.web.token;

import com.nicico.cost.framework.domain.dto.BaseDTO;
import com.nicico.cost.framework.packages.json.web.token.view.JwtObjReqVM;
import io.jsonwebtoken.Claims;

/**
 * @version 1.0.1
 * @implNote you must impl the json web token service in java
 * @author nima mousavi
 */
public interface Jwt {
    /**
     * @param jwtObjReqVM is an object that have some main field for create jwt and a map for custom fields
     * @param secretKey   is your own secret key for generate jwt
     * @return a String of jwt
     */
    BaseDTO<String> generateJwt(JwtObjReqVM jwtObjReqVM, String secretKey);

    /**
     * @param jwtObjReqVM is an object that have some main field for create jwt and a map for custom fields
     * @return a String of jwt
     * @implNote secret key is a default field in your application properties
     */
    BaseDTO<String> generateJwt(JwtObjReqVM jwtObjReqVM);

    /**
     * @param jwt       is your input jwt
     * @param secretKey is your own secret key for valid jwt
     * @return Boolean for process
     * @apiNote method is for valid jwt without check expire time .. its important expire time not important in this method
     */

    BaseDTO<Boolean> isTokenWithoutCheckExpireTime(String jwt, String secretKey);

    /**
     * @param jwt       is your input jwt
     * @param secretKey is your own secret key for valid jwt
     * @return Boolean for process
     */
    BaseDTO<Boolean> isValid(String jwt, String secretKey);

    /**
     * @param jwt is your input jwt
     * @return Boolean for process
     * @apiNote secret key is a default field in your application properties
     * @apiNote method is for valid jwt without check expire time .. its important expire time not important in this method
     */
    BaseDTO<Boolean> isValidWithoutCheckExpireTime(String jwt);

    /**
     * @param jwt is your input jwt
     * @return Boolean for process
     * @apiNote secret key is a default field in your application properties
     */
    BaseDTO<Boolean> isValid(String jwt);

    /**
     * @param jwt is your input jwt
     * @return object of claims that is your json body
     * @apiNote secret key is a default field in your application properties
     * @apiNote this method not validate expireTime
     */
    BaseDTO<Claims> getJwtBodyWithoutCheckExpireTime(String jwt);

    /**
     * @param jwt is your input jwt
     * @return object of claims that is your json body
     * @apiNote secret key is a default field in your application properties
     */
    BaseDTO<Claims> getJwtBody(String jwt);

    /**
     * @param jwt is your input jwt
     * @return object of claims that is your json body
     * @param secretKey is your own secret key for valid jwt
     * @apiNote this method not validate expireTime
     */
    BaseDTO<Claims> getJwtBodyWithoutCheckExpireTime(String jwt, String secretKey);

    /**
     * @param jwt is your input jwt
     * @return object of claims that is your json body
     * @param secretKey is your own secret key for valid jwt
     */
    BaseDTO<Claims> getJwtBody(String jwt, String secretKey);

    /**
     *
     * @param jwt is your input jwt
     * @param paramName is your key that you want fetch from claims
     * @param tClass is object that you want cast to it
     * @param <R> the class type
     * @return a object of <T>
     */
    <R> BaseDTO<R> getJwtParam(String jwt, String paramName, Class<R> tClass);

    /**
     *
     * @param claims is your input jwt body
     * @param paramName is your key that you want fetch from claims
     * @param tClass is object that you want cast to it
     * @param <R> the class type
     * @return a object of <T>
     */
    <R> BaseDTO<R> getJwtParam(Claims claims, String paramName, Class<R> tClass);

    /**
     *
     * @param jwt is your input jwt
     * @return your claims body object
     */
    BaseDTO<Object> getJwtParam(String jwt);

    /**
     *
     * @param jwt is your input jwt
     * @param secretKey is your own secret key for valid jwt
     * @param paramName is your key that you want fetch from claims
     * @param tClass is object that you want cast to it
     * @param <R> the class type
     * @return your claims body object
     */
    <R> BaseDTO<R> getJwtParamWithoutCheckExpireTime(String jwt, String secretKey, String paramName, Class<R> tClass);

    /**
     *
     * @param jwt is your input jwt
     * @param secretKey is your own secret key for valid jwt
     * @param paramName is your key that you want fetch from claims
     * @param tClass is object that you want cast to it
     * @param <R> the class type
     * @return your claims body object
     */
    <R> BaseDTO<R> getJwtParam(String jwt, String secretKey, String paramName, Class<R> tClass);

    /**
     *
     * @param text input text for Hash
     * @return hash of text
     */
    BaseDTO<String> hash(String text);
}
