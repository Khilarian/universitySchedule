package com.rumakin.universityschedule.dto;

import javax.validation.constraints.*;

import com.rumakin.universityschedule.validation.annotation.*;

import io.swagger.annotations.ApiModel;

@UniqueCourseName
@ApiModel
public class CourseDto {

    private Integer id;
    
    @NotBlank(message = "{com.rumakin.universityschedule.validation.mandatory.name}")
    @Size(min = 2, max = 50, message = "{com.rumakin.universityschedule.validation.length.name}")
    @Pattern(regexp = "[A-Z]+[a-z]*(\\s{1}[a-zA-Z]*)*", message = "{com.rumakin.universityschedule.validation.illegal.coursename}")
    private String name;
    
    @NotNull
    private Integer facultyId;
    
    private String facultyName;

    public CourseDto() {
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getFacultyId() {
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

    public void setFacultyId(Integer facultyId) {
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
        final int prime = 31;
        int result = 1;
        result = prime * result + ((facultyId == null) ? 0 : facultyId.hashCode());
        result = prime * result + ((facultyName == null) ? 0 : facultyName.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof CourseDto)) return false;
        CourseDto dto = (CourseDto) obj;
        return getId().equals(dto.getId());
    }

}
