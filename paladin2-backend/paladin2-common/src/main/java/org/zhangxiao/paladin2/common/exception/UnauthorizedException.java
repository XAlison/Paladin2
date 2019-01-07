package org.zhangxiao.paladin2.common.exception;

/**
 *  用于报401
 */
public class UnauthorizedException extends Exception{

    private static final long serialVersionUID = 151772371978507402L;

    public UnauthorizedException(String message) {
        super(message);
    }
}
