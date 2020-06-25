package com.yenroc.ho.common.dao;

import org.springframework.dao.DataAccessException;

public class IllegalClassTypeException extends DataAccessException {
    private static final long serialVersionUID = -3147888263699426883L;
    public static final String ERROR_ILLEGAL_CLASS_TYPE = "The illegal Class Type of the argument.";

    public IllegalClassTypeException() {
        super("The illegal Class Type of the argument.");
    }

    public IllegalClassTypeException(String message) {
        super(message);
    }

    public IllegalClassTypeException(Throwable cause) {
        super("The illegal Class Type of the argument.", cause);
    }

    public IllegalClassTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
