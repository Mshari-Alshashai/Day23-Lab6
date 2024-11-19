package com.example.lab6.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Employee {

    @NotEmpty(message = "ID should not be empty")
    @Size(min = 3,message = "ID should have more than 2 characters")
    private String ID;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 5,message = "The name should have more than 4 characters")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Name must contain only alphabetic characters")
    private String name;

    @Email
    private String email;

    @NotEmpty(message = "Phone number cannot be empty")
    @Pattern(regexp = "^05\\d{8}$", message = "Phone number must start with 05 and be exactly 10 digits long")
    private String phoneNumber;

    @Positive
    @Min(value = 25,message = "Age must be at least 25")
    private int age;

    @NotEmpty(message = "The position should not be empty")
    @Pattern(regexp = "^(supervisor|coordinator)$", message = "Position must be either 'supervisor' or 'coordinator'")
    private String position;

    @AssertFalse(message = "On Leave should be false")
    private boolean onLeave;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull(message = "The Hire Date Should Not Be Null")
    @PastOrPresent(message = "The date should be in the present or the past")
    private LocalDate hireDate;

    @Positive(message = "The Annual Leave should be positive")
    private int annualLeave;
}
