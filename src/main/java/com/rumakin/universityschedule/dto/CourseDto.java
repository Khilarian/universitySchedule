package com.rumakin.universityschedule.dto;

import java.util.Objects;

import javax.validation.constraints.*;
import com.rumakin.universityschedule.validation.annotation.*;

import io.swagger.annotations.ApiModel;

@UniqueCourseName
@ApiModel
public class CourseDto {

    private Integer id;
    
    @NotBlank(message = "{com.rumakin.universityschedule.validation.mandatory.name}")
    @Size(min = 2, max = 50, message = "{com.rumakin.universityschedule.validation.length.name}")
    @Pattern(regexp = "[A-Za-z]+(\\s[A-Z]*[a-z]+)*", message = "{com.rumakin.universityschedule.validation.illegal.coursename}")
    private String name;
    
    private int facultyId;
    
    private String facultyName;

    public CourseDto() {
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getFacultyId() {
        return facultyId;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCourseName(String name) {
        this.name = name;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
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
        return Objects.hash(getId());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof CourseDto)) return false;
        CourseDto dto = (CourseDto) obj;
        return getId().equals(dto.getId());
    }

}
