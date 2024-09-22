package com.example.rqchallenge.employees;

import com.example.rqchallenge.employees.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllEmployees() throws IOException {
        List<Employee> mockEmployees = Arrays.asList(new Employee("1", "John Doe", "1000", "30", ""),
                new Employee("2", "Jane Doe", "1500", "25", ""));
        when(employeeService.getAllEmployees()).thenReturn(mockEmployees);

        ResponseEntity<List<Employee>> response = employeeController.getAllEmployees();

        assertEquals(2, Objects.requireNonNull(response.getBody()).size());
        verify(employeeService, times(1)).getAllEmployees();
    }

    @Test
    void testSearchEmployeesByName() {
        List<Employee> mockEmployees = List.of(new Employee("1", "John Doe", "1000", "30", ""));
        when(employeeService.getEmployeesByNameSearch("John")).thenReturn(mockEmployees);

        ResponseEntity<List<Employee>> response = employeeController.getEmployeesByNameSearch("John");

        assertEquals(1, Objects.requireNonNull(response.getBody()).size());
        assertEquals("John Doe", response.getBody().get(0).getEmployeeName());
        verify(employeeService, times(1)).getEmployeesByNameSearch("John");
    }

    @Test
    void testGetEmployeeById() {
        Employee mockEmployee = new Employee("1", "John Doe", "1000", "30", "");
        when(employeeService.getEmployeeById("1")).thenReturn(mockEmployee);

        ResponseEntity<Employee> response = employeeController.getEmployeeById("1");

        assertNotNull(response.getBody());
        assertEquals("John Doe", response.getBody().getEmployeeName());
        verify(employeeService, times(1)).getEmployeeById("1");
    }

    @Test
    void testGetHighestSalaryOfEmployees() {
        when(employeeService.getHighestSalaryOfEmployees()).thenReturn(1500);

        ResponseEntity<Integer> response = employeeController.getHighestSalaryOfEmployees();

        assertEquals(1500, response.getBody());
        verify(employeeService, times(1)).getHighestSalaryOfEmployees();
    }

    @Test
    void testGetTopTenHighestEarningEmployeeNames() {
        List<String> mockNames = Arrays.asList("Jane Doe", "John Doe");
        when(employeeService.getTopTenHighestEarningEmployeeNames()).thenReturn(mockNames);

        ResponseEntity<List<String>> response = employeeController.getTopTenHighestEarningEmployeeNames();

        assertEquals(2, Objects.requireNonNull(response.getBody()).size());
        verify(employeeService, times(1)).getTopTenHighestEarningEmployeeNames();
    }

    @Test
    void testCreateEmployee() {
        Map<String, Object> employeeInput = new HashMap<>();
        employeeInput.put("name", "John Doe");
        employeeInput.put("salary", "1000");
        employeeInput.put("age", "30");
        Employee expectedResult = new Employee("1","John Doe", "1000", "30", "");
        when(employeeService.createEmployee("John Doe", "1000", "30")).thenReturn(expectedResult);

        ResponseEntity<Employee> response = employeeController.createEmployee(employeeInput);

        assertEquals(expectedResult, response.getBody());
        verify(employeeService, times(1)).createEmployee("John Doe", "1000", "30");
    }

    @Test
    void testDeleteEmployeeById() {
        // Mock the service response
        when(employeeService.deleteEmployee("1")).thenReturn("success");

        // Call the controller method
        ResponseEntity<String> response = employeeController.deleteEmployeeById("1");

        // Assert the expected result
        assertEquals("success", response.getBody());
        verify(employeeService, times(1)).deleteEmployee("1");
    }
}
