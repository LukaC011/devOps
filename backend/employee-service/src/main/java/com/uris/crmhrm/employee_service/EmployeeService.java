package com.uris.crmhrm.employee_service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> findById(Long id) {
        return employeeRepository.findById(id);
    }

    @Transactional
    public Employee create(EmployeeRequest request) {
        return employeeRepository.save(new Employee(request.firstName(), request.lastName(),
                request.position(), request.email()));
    }

    @Transactional
    public Optional<Employee> update(Long id, EmployeeRequest request) {
        return employeeRepository.findById(id).map(employee -> {
            employee.setFirstName(request.firstName());
            employee.setLastName(request.lastName());
            employee.setPosition(request.position());
            employee.setEmail(request.email());
            return employeeRepository.save(employee);
        });
    }

    @Transactional
    public boolean deleteById(Long id) {
        if (!employeeRepository.existsById(id)) {
            return false;
        }
        employeeRepository.deleteById(id);
        return true;
    }
}
