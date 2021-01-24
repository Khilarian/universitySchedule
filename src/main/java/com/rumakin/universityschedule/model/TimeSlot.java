package com.rumakin.universityschedule.model;

import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class TimeSlot implements ModelEntity {

    @Id
    @Column(name = "time_slot_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "time_slot_number")
    private Integer number;

    @Column(name = "time_slot_name")
    private String name;

    @Column(name = "time_slot_start")
    private LocalTime startTime;

    @Column(name = "time_slot_end")
    private LocalTime endTime;

    public TimeSlot() {
    }

    public TimeSlot(Integer number, String name, LocalTime startTime, LocalTime endTime) {
        this.number = number;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public TimeSlot(int id, Integer number, String name, LocalTime startTime, LocalTime endTime) {
        this.id = id;
        this.number = number;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Integer getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
        result = prime * result + id;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((number == null) ? 0 : number.hashCode());
        result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        TimeSlot other = (TimeSlot) obj;
        if (endTime == null) {
            if (other.endTime != null) return false;
        } else if (!endTime.equals(other.endTime)) return false;
        if (id != other.id) return false;
        if (name == null) {
            if (other.name != null) return false;
        } else if (!name.equals(other.name)) return false;
        if (number == null) {
            if (other.number != null) return false;
        } else if (!number.equals(other.number)) return false;
        if (startTime == null) {
            if (other.startTime != null) return false;
        } else if (!startTime.equals(other.startTime)) return false;
        return true;
    }

    @Override
    public String toString() {
        return "TimeSlot [id=" + id + ", number=" + number + ", name=" + name + ", startTime=" + startTime
                + ", endTime=" + endTime + "]";
    }

}
