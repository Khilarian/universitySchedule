package com.rumakin.universityschedule.exceptions;

public class InvalidEntityException extends RuntimeException {

    public InvalidEntityException() {
        super("No such model exist.");
    }

}
