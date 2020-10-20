package com.rumakin.universityschedule.dto;

import java.util.Objects;

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

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof CourseDto)) return false;
        LessonTypeDto dto = (LessonTypeDto) obj;
        return getId().equals(dto.getId());
    }

    @Override
    public String toString() {
        return "LessonTypeDto [id=" + id + ", name=" + name + "]";
    }

}
