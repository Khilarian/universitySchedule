package com.rumakin.universityschedule.dto;

import io.swagger.annotations.ApiModelProperty;

public class LessonTypeDto {
    
    @ApiModelProperty(notes = "Id of the lesson type")
    private int id;
    @ApiModelProperty(notes = "Name of the lesson type")
    private String name;
    
    public LessonTypeDto() {
    }

    public int getId() {
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
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        LessonTypeDto other = (LessonTypeDto) obj;
        if (id != other.id) return false;
        if (name == null) {
            if (other.name != null) return false;
        } else if (!name.equals(other.name)) return false;
        return true;
    }

    @Override
    public String toString() {
        return "LessonTypeDto [id=" + id + ", name=" + name + "]";
    }
    
    

}
