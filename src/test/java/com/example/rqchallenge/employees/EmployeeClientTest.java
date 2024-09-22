package com.example.rqchallenge.employees;

import com.example.rqchallenge.employees.model.Employee;
import com.example.rqchallenge.employees.model.EmployeesResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EmployeeClientTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private EmployeeClient employeeClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllEmployees() {
        List<Employee> mockEmployees = Arrays.asList(new Employee("1", "John Doe", "1000", "30", ""),
                new Employee("2", "Jane Doe", "1500", "25", ""));
        EmployeesResponse mockResponse = new EmployeesResponse();
        mockResponse.setData(mockEmployees);

        when(restTemplate.getForEntity(anyString(), eq(EmployeesResponse.class)))
                .thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        List<Employee> employees = employeeClient.getAllEmployees();

        assertEquals(2, employees.size());
        verify(restTemplate, times(1)).getForEntity(anyString(), eq(EmployeesResponse.class));
    }

    @Test
    void testGetEmployeeById() {
        Employee mockEmployee = new Employee("1", "John Doe", "1000", "30", "");
        EmployeesResponse mockResponse = new EmployeesResponse();
        mockResponse.setData(List.of(mockEmployee));

        when(restTemplate.getForEntity(anyString(), eq(EmployeesResponse.class)))
                .thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        Employee employee = employeeClient.getEmployeeById("1");

        assertNotNull(employee);
        assertEquals("John Doe", employee.getEmployeeName());
        verify(restTemplate, times(1)).getForEntity(anyString(), eq(EmployeesResponse.class));
    }

    @Test
    void testCreateEmployee() {
        Employee expectedResult = new Employee("1", "John Doe", "1000", "30", "");
        when(restTemplate.postForEntity(anyString(), any(), eq(Employee.class)))
                .thenReturn(ResponseEntity.ok(expectedResult));

        Employee response = employeeClient.createEmployee("John Doe", "1000", "30");

        assertNotNull(response);
        assertEquals(expectedResult, response);
        verify(restTemplate, times(1)).postForEntity(anyString(), any(), eq(Employee.class));
    }

    @Test
    void testDeleteEmployee() {
        when(restTemplate.exchange(anyString(), eq(HttpMethod.DELETE), eq(null), eq(String.class)))
                .thenReturn(new ResponseEntity<>("success", HttpStatus.OK));

        String response = employeeClient.deleteEmployee("1");

        assertEquals("success", response);
        verify(restTemplate, times(1)).exchange(anyString(), eq(HttpMethod.DELETE), eq(null), eq(String.class));
    }
}
