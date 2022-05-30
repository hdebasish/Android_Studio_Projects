package com.example.myjobscheduler;

import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("id")
    private String id;

    @SerializedName("employee_name")
    private String employeeName;

    @SerializedName("employee_salary")
    private String employeeSalary;

    @SerializedName("employee_age")
    private String employeeAge;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeSalary() {
        return employeeSalary;
    }

    public void setEmployeeSalary(String employeeSalary) {
        this.employeeSalary = employeeSalary;
    }

    public String getEmployeeAge() {
        return employeeAge;
    }

    public void setEmployeeAge(String employeeAge) {
        this.employeeAge = employeeAge;
    }
}
