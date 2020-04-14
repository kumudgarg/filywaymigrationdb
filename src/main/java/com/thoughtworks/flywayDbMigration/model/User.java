package com.thoughtworks.flywayDbMigration.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,name = "username")
    private String userName;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "address")
    private String address;

    @Column(name = "companyname")
    private String companyName;

    @Column(name = "jobtype")
    private String jobType;

    @Column(name = "salary")
    private String salary;

    public User() {
    }

    public User(Long id, String userName, String firstName, String lastName, String address, String companyName, String jobType, String salary) {
        this.id = id;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.companyName = companyName;
        this.jobType = jobType;
        this.salary = salary;

    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", companyName='" + companyName + '\'' +
                ", jobType='" + jobType + '\'' +
                ", salary='" + salary + '\'' +
                '}';
    }
}
