package com.example.rqchallenge.employees;

import com.example.rqchallenge.employees.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeClient employeeClient;

    public EmployeeService(EmployeeClient employeeClient) {
        this.employeeClient = employeeClient;
    }

    public List<Employee> getAllEmployees() {
        logger.info("Fetching all employees");
        List<Employee> employees = employeeClient.getAllEmployees();
        logger.info("Retrieved {} employees", employees.size());
        return employees;
    }

    public List<Employee> getEmployeesByNameSearch(String name) {
        logger.info("Searching for employees with name: {}", name);
        List<Employee> employees = getAllEmployees();
        List<Employee> filteredEmployees = employees.stream()
                .filter(e -> e.getEmployeeName().contains(name))
                .collect(Collectors.toList());
        logger.info("Found {} employees matching the name: {}", filteredEmployees.size(), name);
        return filteredEmployees;
    }

    public Employee getEmployeeById(String id) {
        logger.info("Fetching employee with ID: {}", id);
        Employee employee = employeeClient.getEmployeeById(id);
        if (employee != null) {
            logger.info("Found employee: {}", employee.getEmployeeName());
        } else {
            logger.warn("No employee found with ID: {}", id);
        }
        return employee;
    }

    public Integer getHighestSalaryOfEmployees() {
        logger.info("Fetching highest salary among employees");
        List<Employee> employees = getAllEmployees();
        Integer highestSalary = employees.stream()
                .map(Employee::getEmployeeSalary)
                .map(salary -> {
                    try {
                        return Integer.parseInt(salary);
                    } catch (NumberFormatException e) {
                        logger.warn("Invalid salary format for employee: {}", salary);
                        return 0;
                    }
                })
                .max(Integer::compare)
                .orElse(0);
        logger.info("Highest salary found: {}", highestSalary);
        return highestSalary;
    }

    public List<String> getTopTenHighestEarningEmployeeNames() {
        logger.info("Fetching top ten highest earning employee names");
        List<Employee> employees = getAllEmployees();

        // Min-heap to store the top 10 employees with the highest salary
        PriorityQueue<Employee> topTenEmployees = new PriorityQueue<>(
                10, (e1, e2) -> Integer.compare(Integer.parseInt(e1.getEmployeeSalary()), Integer.parseInt(e2.getEmployeeSalary()))
        );

        for (Employee employee : employees) {
            try {
                int salary = Integer.parseInt(employee.getEmployeeSalary());

                if (topTenEmployees.size() < 10) {
                    topTenEmployees.add(employee);
                } else if (salary > Integer.parseInt(topTenEmployees.peek().getEmployeeSalary())) {
                    topTenEmployees.poll();  // Remove the employee with the lowest salary
                    topTenEmployees.add(employee);  // Add the current employee
                }
            } catch (NumberFormatException e) {
                logger.warn("Invalid salary format for employee: {}", employee.getEmployeeName());
            }
        }

        // Extract the employee names from the heap (sorted in descending order of salary)
        List<String> topEarners = topTenEmployees.stream()
                .sorted((e1, e2) -> Integer.compare(Integer.parseInt(e2.getEmployeeSalary()), Integer.parseInt(e1.getEmployeeSalary())))
                .map(Employee::getEmployeeName)
                .collect(Collectors.toList());

        logger.info("Top ten earners: {}", topEarners);
        return topEarners;
    }

    public Employee createEmployee(String name, String salary, String age) {
        logger.info("Creating new employee: {}", name);
        Employee result = employeeClient.createEmployee(name, salary, age);
        logger.info("Employee created with ID: {}, Name: {}", result.getId(), result.getEmployeeName());
        return result;
    }

    public String deleteEmployee(String id) {
        logger.info("Deleting employee with ID: {}", id);
        String result = employeeClient.deleteEmployee(id);
        logger.info("Employee deletion status: {}", result);
        return result;
    }
}