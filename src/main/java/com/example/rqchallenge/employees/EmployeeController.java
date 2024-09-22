package com.example.rqchallenge.employees;

import com.example.rqchallenge.employees.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * REST controller for Employee operations.
 */
@RestController
@RequestMapping("/api/employees")
public class EmployeeController implements IEmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public ResponseEntity<List<Employee>> getAllEmployees() throws IOException {
        logger.info("Fetching all employees.");
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @Override
    public ResponseEntity<List<Employee>> getEmployeesByNameSearch(@PathVariable String searchString) {
        logger.info("Searching employees by name: {}", searchString);
        return ResponseEntity.ok(employeeService.getEmployeesByNameSearch(searchString));
    }

    @Override
    public ResponseEntity<Employee> getEmployeeById(@PathVariable String id) {
        logger.info("Fetching employee by ID: {}", id);
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @Override
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        logger.info("Fetching the highest salary of employees.");
        return ResponseEntity.ok(employeeService.getHighestSalaryOfEmployees());
    }

    @Override
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        logger.info("Fetching top 10 highest earning employee names.");
        return ResponseEntity.ok(employeeService.getTopTenHighestEarningEmployeeNames());
    }

    @Override
    public ResponseEntity<Employee> createEmployee(@RequestBody Map<String, Object> employeeInput) {
        String name = (String) employeeInput.get("name");
        String salary = (String) employeeInput.get("salary");
        String age = (String) employeeInput.get("age");
        logger.info("Creating employee: {}, {}, {}", name, salary, age);
        return ResponseEntity.ok(employeeService.createEmployee(name, salary, age));
    }

    @Override
    public ResponseEntity<String> deleteEmployeeById(@PathVariable String id) {
        logger.info("Deleting employee with ID: {}", id);
        return ResponseEntity.ok(employeeService.deleteEmployee(id));
    }
}
