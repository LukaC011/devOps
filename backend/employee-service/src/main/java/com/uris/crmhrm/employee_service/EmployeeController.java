package com.uris.crmhrm.employee_service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @GetMapping
    public String getEmployees() {
        return "Employee microservice is working.";
    }
}