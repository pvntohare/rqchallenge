package com.example.rqchallenge.employees;

import com.example.rqchallenge.employees.model.Employee;
import com.example.rqchallenge.employees.model.EmployeeResponse;
import com.example.rqchallenge.employees.model.EmployeesResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class EmployeeClient {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeClient.class);
    private final RestTemplate restTemplate;
    private static final String BASE_URL = "https://dummy.restapiexample.com/api/v1";

    public EmployeeClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Employee> getAllEmployees() {
        logger.info("Fetching all employees from {}", BASE_URL);
        ResponseEntity<EmployeesResponse> response = restTemplate.getForEntity(BASE_URL + "/employees",
                EmployeesResponse.class);
        return Objects.requireNonNull(response.getBody()).getData();
    }

    public Employee getEmployeeById(String id) {
        logger.info("Fetching employee with ID: {}", id);
        ResponseEntity<EmployeeResponse> response = restTemplate.getForEntity(BASE_URL + "/employees/" + id,
                EmployeeResponse.class);
        return Objects.requireNonNull(response.getBody()).getData();
    }

    public Employee createEmployee(String name, String salary, String age) {
        logger.info("Creating employee: {} with salary: {} and age: {}", name, salary, age);
        ResponseEntity<Employee> response = restTemplate.postForEntity(BASE_URL + "/employees",
                new Employee(UUID.randomUUID().toString(), name, salary, age, ""), Employee.class);
        return response.getBody();
    }

    public String deleteEmployee(String id) {
        logger.info("Deleting employee with ID: {}", id);
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL + "/employees/" + id, HttpMethod.DELETE, null,
                String.class);
        return response.getBody();
    }
}
