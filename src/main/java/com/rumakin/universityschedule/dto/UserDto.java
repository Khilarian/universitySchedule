package com.rumakin.universityschedule.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.rumakin.universityschedule.validation.annotation.*;

import io.swagger.annotations.ApiModel;

@PasswordUpdate
@ApiModel
public class UserDto {

    private Integer id;

    @NotBlank
    @Pattern(regexp = "[A-Z][a-z]*", message = "{com.rumakin.universityschedule.validation.illegal.personfirstname}")
    private String firstName;

    @NotBlank
    @Pattern(regexp = "[A-Z][a-z]*(\\s{1}[a-zA-Z]*)*", message = "{com.rumakin.universityschedule.validation.illegal.personlastname}")
    private String lastName;

    @NotBlank
    @Pattern(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$", message = "{com.rumakin.universityschedule.validation.illegal.email}")
    private String email;

    @NotBlank
    private String role;

    @NotBlank
    private String status;

    private String password;

    private String newPassword;

    public UserDto() {
    }

    public Integer getId() {
        return id;
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

    public String getRole() {
        return role;
    }

    public String getStatus() {
        return status;
    }

    public String getPassword() {
        return password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public void setRole(String role) {
        this.role = role;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public String toString() {
        return "UserDto [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
                + ", role=" + role + ", status=" + status + ", password=" + password + ", newPassword=" + newPassword
                + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
        result = prime * result + ((newPassword == null) ? 0 : newPassword.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((role == null) ? 0 : role.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof UserDto)) return false;
        UserDto dto = (UserDto) obj;
        return getId().equals(dto.getId());
    }

}
