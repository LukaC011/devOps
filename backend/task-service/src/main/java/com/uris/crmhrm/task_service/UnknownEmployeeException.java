package com.uris.crmhrm.task_service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UnknownEmployeeException extends RuntimeException {

    public UnknownEmployeeException(Long employeeId) {
        super("Employee " + employeeId + " does not exist");
    }
}
