package com.rumakin.universityschedule.dto;

import javax.validation.constraints.*;

import com.rumakin.universityschedule.validation.annotation.*;

import io.swagger.annotations.ApiModelProperty;

@UniqueBuildingName
@UniqueBuildingAddress
public class BuildingDto {

    @ApiModelProperty(notes = "Id of the building")
    private int id;
    @NotBlank(message = "{com.rumakin.universityschedule.validation.mandatory.name}")
    @Size(min = 2, max = 50, message = "{com.rumakin.universityschedule.validation.length.name}")
    @Pattern(regexp = "[A-Z][a-z]+(\\s[A-Z][a-z]+)*", message = "{com.rumakin.universityschedule.validation.illegal.buildingname}")
    @ApiModelProperty(notes = "Name of the building")
    private String name;

    @NotBlank(message = "{com.rumakin.universityschedule.validation.mandatory.building.address}")
    @Size(min = 2, max = 200, message = "{com.rumakin.universityschedule.validation.length.address}")
    @Pattern(regexp = "[A-Z][a-z]+(\\s[A-Z][a-z]+)*", message = "{com.rumakin.universityschedule.validation.illegal.buildingaddress}")
    @ApiModelProperty(notes = "Address of the building")
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

    public Integer getId() {
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
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
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
