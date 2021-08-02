package com.clane.wallet.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;

/**
 * @author timolor
 * @created 27/07/2021
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Response<T> {
    @Getter
    private String code;
    private String message;
    private T data;

    public static  <T>Response<T>  build(T data){
        Response<T> response = new Response<>();
        response.setCode(ResponseCodes.SUCCESS.getCode());
        response.setMessage(ResponseCodes.SUCCESS.getMessage());
        response.setData(data);
        return response;
    }
    public static  <T>Response<T>  build(String responseCode, String message, T data){
        Response<T> response = new Response<>();
        response.setCode(responseCode);
        response.setMessage(message);
        response.setData(data);
        return response;
    }
}
