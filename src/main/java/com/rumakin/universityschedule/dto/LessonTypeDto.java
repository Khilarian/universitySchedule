package com.rumakin.universityschedule.dto;

import javax.validation.constraints.*;

import com.rumakin.universityschedule.validation.annotation.VerifiedLessonType;

import io.swagger.annotations.ApiModel;

@VerifiedLessonType
@ApiModel
public class LessonTypeDto {

    private Integer id;

    @NotBlank
    private String name;

    public LessonTypeDto() {
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof LessonTypeDto)) return false;
        LessonTypeDto dto = (LessonTypeDto) obj;
        return getId().equals(dto.getId());
    }

    @Override
    public String toString() {
        return "LessonTypeDto [id=" + id + ", name=" + name + "]";
    }

}
