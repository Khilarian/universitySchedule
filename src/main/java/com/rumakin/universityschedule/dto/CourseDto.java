package com.rumakin.universityschedule.dto;

import javax.validation.constraints.*;
import com.rumakin.universityschedule.validation.annotation.*;

import io.swagger.annotations.ApiModelProperty;

@UniqueCourseName
public class CourseDto {

    @ApiModelProperty(notes = "Id of the course")
    private int id;
    @NotBlank(message = "{com.rumakin.universityschedule.validation.mandatory.name}")
    @Size(min = 2, max = 50, message = "{com.rumakin.universityschedule.validation.length.name}")
    @Pattern(regexp = "[A-Za-z]+(\\s[A-Za-z]+)*", message = "{com.rumakin.universityschedule.validation.illegal.coursename}")
    @ApiModelProperty(notes = "Name of the course")
    private String name;
    @ApiModelProperty(notes = "Id of the faculty")
    private int facultyId;
    @ApiModelProperty(notes = "Name of the faculty")
    private String facultyName;

    public CourseDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setCourseName(String name) {
        this.name = name;
    }

    public int getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    @Override
    public String toString() {
        return "CourseDto [id=" + id + ", name=" + name + ", facultyId=" + facultyId + ", facultyName=" + facultyName
                + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + facultyId;
        result = prime * result + ((facultyName == null) ? 0 : facultyName.hashCode());
        result = prime * result + id;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        CourseDto other = (CourseDto) obj;
        if (facultyId != other.facultyId) return false;
        if (facultyName == null) {
            if (other.facultyName != null) return false;
        } else if (!facultyName.equals(other.facultyName)) return false;
        if (id != other.id) return false;
        if (name == null) {
            if (other.name != null) return false;
        } else if (!name.equals(other.name)) return false;
        return true;
    }

}
