package com.rumakin.universityschedule.dto;

import javax.validation.constraints.*;

import com.rumakin.universityschedule.validation.annotation.*;

import io.swagger.annotations.ApiModel;

import java.util.Optional;

@UniqueAuditorium
@AuditoriumBuilding
@ApiModel
public class AuditoriumDto {

    private int id;
    @Min(1)
    @Max(999)
    @Digits(integer = 3, fraction = 0, message = "{com.rumakin.universityschedule.validation.illegal.auditoriumnumber}")
    private Integer number;
    @Min(1)
    @Max(100)
    @Digits(integer = 3, fraction = 0, message = "{com.rumakin.universityschedule.validation.illegal.auditoriumcapacity}")
    private Integer capacity;
    private int buildingId;
    private String buildingName;
    private String buildingAddress;

    public AuditoriumDto() {
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getBuildingAddress() {
        return buildingAddress;
    }

    public void setBuildingAddress(String buildingAddress) {
        this.buildingAddress = buildingAddress;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number= Optional.ofNullable(number).orElse(0);
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        if (capacity == null) {
            this.capacity = 0;
        } else {
            this.capacity = capacity;
        }
    }

    public int getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

    @Override
    public String toString() {
        return "AuditoriumDto [id=" + id + ", number=" + number + ", capacity=" + capacity + ", buildingId="
                + buildingId + ", buildingName=" + buildingName + ", buildingAddress=" + buildingAddress + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((buildingAddress == null) ? 0 : buildingAddress.hashCode());
        result = prime * result + buildingId;
        result = prime * result + ((buildingName == null) ? 0 : buildingName.hashCode());
        result = prime * result + ((capacity == null) ? 0 : capacity.hashCode());
        result = prime * result + id;
        result = prime * result + ((number == null) ? 0 : number.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        AuditoriumDto other = (AuditoriumDto) obj;
        if (buildingAddress == null) {
            if (other.buildingAddress != null) return false;
        } else if (!buildingAddress.equals(other.buildingAddress)) return false;
        if (buildingId != other.buildingId) return false;
        if (buildingName == null) {
            if (other.buildingName != null) return false;
        } else if (!buildingName.equals(other.buildingName)) return false;
        if (capacity == null) {
            if (other.capacity != null) return false;
        } else if (!capacity.equals(other.capacity)) return false;
        if (id != other.id) return false;
        if (number == null) {
            if (other.number != null) return false;
        } else if (!number.equals(other.number)) return false;
        return true;
    }

}
