package com.rumakin.universityschedule.dto;

import javax.validation.constraints.*;

import com.rumakin.universityschedule.validation.annotation.*;

import io.swagger.annotations.ApiModel;

@UniqueGroupName
@ApiModel
public class GroupDto {

    private Integer id;
    
    @NotBlank(message = "{com.rumakin.universityschedule.validation.mandatory.name}")
    @Pattern(regexp = "[A-Z]{2}-[0-9]{3}", message = "{com.rumakin.universityschedule.validation.illegal.groupname}")
    private String name;
    
    @NotNull
    private Integer facultyId;
    
    private String facultyName;

    public GroupDto() {
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

    public void setFacultyId(Integer facultyId) {
        this.facultyId = facultyId;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    @Override
    public String toString() {
        return "GroupDto [id=" + id + ", name=" + name + ", facultyId=" + facultyId + ", facultyName=" + facultyName
                + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof GroupDto)) return false;
        GroupDto dto = (GroupDto) obj;
        return getId().equals(dto.getId());
    }

}
