package com.rumakin.universityschedule.exceptions;

public class InvalidEntityException extends RuntimeException {

    public InvalidEntityException(String text) {
        super("Can't get " + text + " fields list");
    }

}
