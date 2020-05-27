package com.rumakin.universityschedule.dto;

public class AuditoriumDto {

    private int id;
    private int number;
    private int capacity;
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
