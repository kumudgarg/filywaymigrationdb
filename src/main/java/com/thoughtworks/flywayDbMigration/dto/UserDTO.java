package com.thoughtworks.flywayDbMigration.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

@Data
public class UserDTO {

    private String userName;

    private String firstName;

    private String lastName;

    private String address;

    private String companyName;

    private String jobType;

    private String salary;

    public UserDTO() {
    }

    public UserDTO(String userName, String firstName, String lastName, String address, String companyName, String jobType, String salary) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.companyName = companyName;
        this.jobType = jobType;
        this.salary = salary;
    }
}
