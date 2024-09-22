package com.example.rqchallenge.employees.model;

import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class EmployeesResponse {
    private String status;
    private List<Employee> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Employee> getData() {
        return data;
    }

    public void setData(List<Employee> data) {
        this.data = data;
    }
}
