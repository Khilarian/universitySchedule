package com.rumakin.universityschedule.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class FacultyDto {

    private int id;
    
    @NotBlank(message = "Name is mandatory")
    @Size(min = 2, max = 50, message = "Name length should be between 2 and 50 characters")
    @Pattern(regexp = "[A-Z][a-z]+(\\s[A-Z][a-z]+)*", message = "Invalid faculty name format, should be \"Name optional optional etc\"")
    private String name;

    public FacultyDto() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "FacultyDto [id=" + id + ", name=" + name + "]";
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
        if (obj == null || getClass() != obj.getClass()) return false;
        FacultyDto other = (FacultyDto) obj;
        if (id != other.id) return false;
        if (name == null) {
            if (other.name != null) return false;
        } else if (!name.equals(other.name)) return false;
        return true;
    }

}
