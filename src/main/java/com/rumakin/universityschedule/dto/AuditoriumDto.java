package com.rumakin.universityschedule.dto;

public class AuditoriumDto {

    private int id;
    private int number;
    private int capacity;
    private int buildingId;

    public AuditoriumDto() {
    }

    public AuditoriumDto(int id, int number, int capacity, int buildingId) {
        this.id = id;
        this.number = number;
        this.capacity = capacity;
        this.buildingId = buildingId;
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
                + buildingId + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + buildingId;
        result = prime * result + capacity;
        result = prime * result + id;
        result = prime * result + number;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        AuditoriumDto other = (AuditoriumDto) obj;
        if (buildingId != other.buildingId) return false;
        if (capacity != other.capacity) return false;
        if (id != other.id) return false;
        if (number != other.number) return false;
        return true;
    }
    
    
}
