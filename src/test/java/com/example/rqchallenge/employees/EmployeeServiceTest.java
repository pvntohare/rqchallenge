package com.example.rqchallenge.employees;

import com.example.rqchallenge.employees.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EmployeeServiceTest {

    @Mock
    private EmployeeClient employeeClient;

    @InjectMocks
    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllEmployees() {
        List<Employee> mockEmployees = Arrays.asList(new Employee("1", "John Doe", "1000", "30", ""),
                new Employee("2", "Jane Doe", "1500", "25", ""));
        when(employeeClient.getAllEmployees()).thenReturn(mockEmployees);

        List<Employee> employees = employeeService.getAllEmployees();

        assertEquals(2, employees.size());
        verify(employeeClient, times(1)).getAllEmployees();
    }

    @Test
    void testSearchEmployeesByName() {
        List<Employee> mockEmployees = Arrays.asList(new Employee("1", "John Doe", "1000", "30", ""),
                new Employee("2", "Jane Doe", "1500", "25", ""));
        when(employeeClient.getAllEmployees()).thenReturn(mockEmployees);

        List<Employee> result = employeeService.getEmployeesByNameSearch("John");

        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getEmployeeName());
        verify(employeeClient, times(1)).getAllEmployees();
    }

    @Test
    void testGetEmployeeById() {
        Employee mockEmployee = new Employee("1", "John Doe", "1000", "30", "");
        when(employeeClient.getEmployeeById("1")).thenReturn(mockEmployee);

        Employee employee = employeeService.getEmployeeById("1");

        assertNotNull(employee);
        assertEquals("John Doe", employee.getEmployeeName());
        verify(employeeClient, times(1)).getEmployeeById("1");
    }

    @Test
    void testGetHighestSalaryOfEmployees() {
        List<Employee> mockEmployees = Arrays.asList(new Employee("1", "John Doe", "1000", "30", ""),
                new Employee("2", "Jane Doe", "1500", "25", ""));
        when(employeeClient.getAllEmployees()).thenReturn(mockEmployees);

        Integer highestSalary = employeeService.getHighestSalaryOfEmployees();

        assertEquals(1500, highestSalary);
        verify(employeeClient, times(1)).getAllEmployees();
    }

    @Test
    void testGetTopTenHighestEarningEmployeeNames() {
        List<Employee> mockEmployees = Arrays.asList(new Employee("1", "John Doe", "1000", "30", ""),
                new Employee("2", "Jane Doe", "1500", "25", ""));
        when(employeeClient.getAllEmployees()).thenReturn(mockEmployees);

        List<String> topEarners = employeeService.getTopTenHighestEarningEmployeeNames();

        assertEquals(2, topEarners.size());
        assertEquals("Jane Doe", topEarners.get(0));
        verify(employeeClient, times(1)).getAllEmployees();
    }

    @Test
    void testCreateEmployee() {
        Employee expectedResult = new Employee("1","John Doe", "1000", "30", "");
        when(employeeClient.createEmployee("John Doe", "1000", "30")).thenReturn(expectedResult);

        Employee result = employeeService.createEmployee("John Doe", "1000", "30");

        assertEquals(expectedResult, result);
        verify(employeeClient, times(1)).createEmployee("John Doe", "1000", "30");
    }

    @Test
    void testDeleteEmployee() {
        when(employeeClient.deleteEmployee("1")).thenReturn("success");

        String result = employeeService.deleteEmployee("1");

        assertEquals("success", result);
        verify(employeeClient, times(1)).deleteEmployee("1");
    }
}
