package org.zhangxiao.paladin2.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class BizExceptionHandler {

    @ExceptionHandler(BizException.class)
    public ResponseEntity<HashMap> bizExceptionHandler(BizException ex) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", ex.getError().getCode());
        map.put("message", ex.getError().getMessage());
        map.put("status", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<HashMap> unauthorizedExceptionHandler(UnauthorizedException ex) {
        return errorEntity(HttpStatus.UNAUTHORIZED,ex.getMessage());
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<HashMap> forbiddenExceptionHandler(UnauthorizedException ex) {
        return errorEntity(HttpStatus.FORBIDDEN,ex.getMessage());
    }

    private ResponseEntity<HashMap> errorEntity(HttpStatus httpStatus, String msg) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", httpStatus.value());
        map.put("msg", msg);
        return new ResponseEntity<>(map, httpStatus);
    }
}
