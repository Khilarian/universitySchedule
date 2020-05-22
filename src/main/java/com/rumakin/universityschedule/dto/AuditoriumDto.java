package com.rumakin.universityschedule.dto;

import com.rumakin.universityschedule.models.Auditorium;

public class AuditoriumDto {

    private int id;
    private int number;
    private int capacity;
    private int buildingId;
    private String buildingName;
    private String buildingAddress;

    public AuditoriumDto() {
    }

    public AuditoriumDto(int id, int number, int capacity, int buildingId, String buildingName,
            String buildingAddress) {
        this.id = id;
        this.number = number;
        this.capacity = capacity;
        this.buildingId = buildingId;
        this.buildingName = buildingName;
        this.buildingAddress = buildingAddress;
    }

    public AuditoriumDto(Auditorium auditorium) {
        this.id = auditorium.getId();
        this.number = auditorium.getNumber();
        this.capacity = auditorium.getCapacity();
        this.buildingId = auditorium.getBuilding().getId();
        this.buildingName = auditorium.getBuilding().getName();
        this.buildingAddress = auditorium.getBuilding().getAddress();
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
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
        result = prime * result + capacity;
        result = prime * result + id;
        result = prime * result + number;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AuditoriumDto other = (AuditoriumDto) obj;
        if (buildingAddress == null) {
            if (other.buildingAddress != null) return false;
        } else if (!buildingAddress.equals(other.buildingAddress)) return false;
        if (buildingId != other.buildingId) return false;
        if (buildingName == null) {
            if (other.buildingName != null) return false;
        } else if (!buildingName.equals(other.buildingName)) return false;
        if (capacity != other.capacity) return false;
        if (id != other.id) return false;
        return number == other.number;
    }

}
