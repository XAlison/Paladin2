package org.zhangxiao.paladin2.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.zhangxiao.paladin2.common.exception.BizException;
import org.zhangxiao.paladin2.common.exception.ForbiddenException;
import org.zhangxiao.paladin2.common.exception.UnauthorizedException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RestControllerAdvice
public class BizExceptionHandler {
    @ExceptionHandler(BizException.class)
    public ResponseEntity<HashMap> bizExceptionHandler(BizException ex, HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("scope", ex.getError().getScope());
        map.put("code", ex.getError().getCode());
        map.put("message", ex.getError().getMessage());
        map.put("path", request.getRequestURI());
        map.put("status", HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<HashMap> unauthorizedExceptionHandler(UnauthorizedException ex, HttpServletRequest request) {
        return errorEntity(HttpStatus.UNAUTHORIZED, ex.getMessage(), request);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<HashMap> forbiddenExceptionHandler(UnauthorizedException ex, HttpServletRequest request) {
        return errorEntity(HttpStatus.FORBIDDEN, ex.getMessage(), request);
    }

    private ResponseEntity<HashMap> errorEntity(HttpStatus httpStatus, String msg, HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("message", msg);
        map.put("path", request.getRequestURI());
        map.put("status", HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(map, httpStatus);
    }
}
