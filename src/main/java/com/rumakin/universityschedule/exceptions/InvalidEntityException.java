package com.rumakin.universityschedule.exceptions;

public class InvalidEntityException extends RuntimeException {

    public InvalidEntityException(String text) {
        super("Model " + text + " error.");
    }

}
