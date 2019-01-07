package org.zhangxiao.paladin2.common.exception;

/**
 *  用于报401
 */
public class ForbiddenException extends Exception{

    private static final long serialVersionUID = 6782979314438169018L;
    public ForbiddenException(String message) {
        super(message);
    }
}
