package com.rumakin.universityschedule.model;

import javax.persistence.*;

@Entity
@Table
public class TimeSlot implements ModelEntity {

    @Id
    @Column(name = "time_slot_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "time_slot_seq")
    @SequenceGenerator(name = "time_slot_seq", sequenceName = "time_slot_time_slot_id_seq", allocationSize = 1)
    private int id;

    @Column(name = "time_slot_number")
    private Integer number;

    @Column(name = "time_slot_name")
    private String name;

    @Column(name = "time_slot_start")
    private String startTime;

    @Column(name = "time_slot_end")
    private String endTime;
    
    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
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
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        TimeSlot other = (TimeSlot) obj;
        if (id != other.id) return false;
        return true;
    }

    @Override
    public String toString() {
        return "TimeSlot [id=" + id + ", number=" + number + ", name=" + name + ", startTime=" + startTime + ", endTime="
                + endTime + "]";
    }

}
