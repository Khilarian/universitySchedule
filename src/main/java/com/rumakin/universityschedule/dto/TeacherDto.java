package com.rumakin.universityschedule.dto;

import java.util.*;

import javax.validation.constraints.*;

import com.rumakin.universityschedule.validation.annotation.*;

import io.swagger.annotations.ApiModel;

@UniqueTeacherEmail
@UniqueTeacherPhone
@ApiModel
public class TeacherDto {

    private int personId;

    @NotBlank
    @Pattern(regexp = "[A-Z][a-z]*", message = "{com.rumakin.universityschedule.validation.illegal.personfirstname}")
    private String firstName;

    @NotBlank
    @Pattern(regexp = "[A-Z][a-z]*(\\s{1}[a-zA-Z]*)*", message = "{com.rumakin.universityschedule.validation.illegal.personlastname}")
    private String lastName;

    @Pattern(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$", message = "{com.rumakin.universityschedule.validation.illegal.email}")
    private String email;

    @NotBlank
    @Pattern(regexp = "^(\\+7)\\(\\d{3}\\)\\d{7,10}$$", message = "{com.rumakin.universityschedule.validation.illegal.phone}")
    private String phone;

    @Min(1)
    private int facultyId;

    private String facultyName;

    private List<CourseDto> courses = new ArrayList<>();

    public TeacherDto() {
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

    public int getFacultyId() {
        return facultyId;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public List<CourseDto> getCourses() {
        return courses;
    }

    public void setId(int personId) {
        this.personId = personId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setCourses(List<CourseDto> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "TeacherDto [personId=" + personId + ", firstName=" + firstName + ", lastName=" + lastName + ", email="
                + email + ", phone=" + phone + ", facultyId=" + facultyId + ", facultyName=" + facultyName
                + ", courses=" + courses + "]";
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
        TeacherDto other = (TeacherDto) obj;
        return personId == other.personId;
    }

}
