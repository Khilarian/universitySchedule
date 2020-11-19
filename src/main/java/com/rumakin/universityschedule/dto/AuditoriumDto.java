package com.rumakin.universityschedule.dto;

import javax.validation.constraints.*;

import com.rumakin.universityschedule.validation.annotation.*;

import io.swagger.annotations.ApiModel;

import java.util.Optional;

@UniqueAuditorium
@ApiModel
public class AuditoriumDto implements Dto {

    private Integer id;

    @Min(1)
    @Max(999)
    @Digits(integer = 3, fraction = 0, message = "{com.rumakin.universityschedule.validation.illegal.auditoriumnumber}")
    private Integer number;

    @Min(1)
    @Max(100)
    @Digits(integer = 3, fraction = 0, message = "{com.rumakin.universityschedule.validation.illegal.auditoriumcapacity}")
    private Integer capacity;

    @NotNull
    private Integer buildingId;

    private String buildingName;

    private String buildingAddress;

    public AuditoriumDto() {
    }

    public Integer getId() {
        return id;
    }

    public Integer getNumber() {
        return number;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public Integer getBuildingId() {
        return buildingId;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public String getBuildingAddress() {
        return buildingAddress;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public void setBuildingAddress(String buildingAddress) {
        this.buildingAddress = buildingAddress;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNumber(Integer number) {
        this.number = Optional.ofNullable(number).orElse(0);
    }

    public void setCapacity(Integer capacity) {
        if (capacity == null) {
            this.capacity = 0;
        } else {
            this.capacity = capacity;
        }
    }

    public void setBuildingId(Integer buildingId) {
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
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        AuditoriumDto other = (AuditoriumDto) obj;
        if (id == null) {
            if (other.id != null) return false;
        } else if (!id.equals(other.id)) return false;
        return true;
    }

}
