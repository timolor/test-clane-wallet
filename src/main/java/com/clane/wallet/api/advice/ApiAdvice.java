package com.clane.wallet.api.advice;

import com.clane.wallet.exception.ClaneBadRequestException;
import com.clane.wallet.exception.ClaneResourceExistsException;
import com.clane.wallet.exception.ClaneResourceNotFoundException;
import com.clane.wallet.model.response.Response;
import com.clane.wallet.model.response.ResponseCodes;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author timolor
 * @created 27/07/2021
 */
@ControllerAdvice(annotations = RestController.class)
@ResponseBody
public class ApiAdvice{

    @ExceptionHandler(ClaneResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response handleResourceNotFoundException(ClaneResourceNotFoundException e){
        Response response = new Response();
        response.setCode(e.getCode());
        response.setMessage(e.getMessage());
        return response;
    }

    @ExceptionHandler(ClaneResourceExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Response handleResourceExistsException(ClaneResourceExistsException e){
        Response response = new Response();
        response.setCode(e.getCode());
        response.setMessage(e.getMessage());
        return response;
    }

    @ExceptionHandler(ClaneBadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response handleBadRequestException(ClaneBadRequestException e){
        Response response = new Response();
        response.setCode(e.getCode());
        response.setMessage(e.getMessage());
        return response;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected Response handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        Response<List<String>> response = new Response<>();
        response.setCode(ResponseCodes.BAD_REQUEST.getCode());
        response.setMessage(fieldErrors.get(0).getDefaultMessage());

        return response;
    }

    private Response processFieldErrors(List<FieldError> fieldErrors) {
        Response<List<String>> response = new Response<>();
        response.setCode(ResponseCodes.BAD_REQUEST.getCode());
        response.setMessage(ResponseCodes.BAD_REQUEST.getMessage());
        List<String> errors = new ArrayList<>();
        for (FieldError fieldError: fieldErrors) {
            errors.add(fieldError.getDefaultMessage());
        }
        response.setData(errors);
        return response;
    }
}
