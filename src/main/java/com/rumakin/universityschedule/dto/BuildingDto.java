package com.rumakin.universityschedule.dto;

import javax.validation.constraints.*;

public class BuildingDto {

    private int id;
    @NotBlank(message = "Name is mandatory")
    @Size(min = 2, max = 50, message = "Name length should be between 2 and 50 characters")
    @Pattern(regexp = "[A-Z][a-z]+(\\s[A-Z][a-z]+)*", message = "Invalid building name format, should be \"Name optional optional etc\"")
    private String name;

    @NotBlank(message = "Address is mandatory")
    @Size(min = 2, max = 200, message = "Address length should be between 2 and 200 characters")
    @Pattern(regexp = "[A-Z][a-z]+(\\s[A-Z][a-z]+)*", message = "Invalid building address format, should be \"Address optional optional etc\"")
    private String address;

    public BuildingDto() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "BuildingDto [id=" + id + ", name=" + name + ", address=" + address + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((address == null) ? 0 : address.hashCode());
        result = prime * result + id;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        BuildingDto other = (BuildingDto) obj;
        if (address == null) {
            if (other.address != null) return false;
        } else if (!address.equals(other.address)) return false;
        if (id != other.id) return false;
        if (name == null) {
            if (other.name != null) return false;
        } else if (!name.equals(other.name)) return false;
        return true;
    }

}
