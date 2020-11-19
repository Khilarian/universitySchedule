package com.rumakin.universityschedule.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.rumakin.universityschedule.validation.annotation.*;

import io.swagger.annotations.ApiModel;

@UniqueStudentEmail
@UniqueStudentPhone
@ApiModel
public class StudentDto {

    private Integer personId;

    @NotBlank
    @Pattern(regexp = "[A-Z][a-z]*", message = "{com.rumakin.universityschedule.validation.illegal.personfirstname}")
    private String firstName;

    @NotBlank
    @Pattern(regexp = "[A-Z][a-z]*(\\s{1}[a-zA-Z]*)*", message = "{com.rumakin.universityschedule.validation.illegal.personlastname}")
    private String lastName;

    @Pattern(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$", message = "{com.rumakin.universityschedule.validation.illegal.email}")
    private String email;

    @NotBlank
    @Pattern(regexp = "^(\\+7)\\(\\d{3}\\)\\d{7,10}$", message = "{com.rumakin.universityschedule.validation.illegal.phone}")
    private String phone;

    private Integer groupId;

    private String groupName;

    public StudentDto() {
    }

    public Integer getId() {
        return personId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setId(Integer personId) {
        this.personId = personId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public String toString() {
        return "StudentDto [personId=" + personId + ", firstName=" + firstName + ", lastName=" + lastName + ", groupId="
                + groupId + ", groupName=" + groupName + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + personId;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        StudentDto other = (StudentDto) obj;
        if (personId != other.personId) return false;
        return true;
    }

}
