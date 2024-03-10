package com.david.controller.advice;

import com.david.common.BasicOut;
import com.david.common.HttpStatusEnum;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

//@ControllerAdvice
//@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BasicOut<?> handleInputValidate(MethodArgumentNotValidException e) {
        BasicOut<List<String>> result = new BasicOut<>();
        result.setMessage(HttpStatusEnum.INPUT_VALUE_VALIDATE_ERROR.getMessage());
        result.setRetCode(HttpStatusEnum.INPUT_VALUE_VALIDATE_ERROR.getCode());
        BindingResult bindingResult = e.getBindingResult();
        List<String> errors = bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
        result.setBody(errors);
        return result;
    }
}
