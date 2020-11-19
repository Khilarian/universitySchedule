package com.rumakin.universityschedule.dto;

import java.time.LocalTime;

import javax.validation.constraints.*;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rumakin.universityschedule.validation.annotation.*;

import io.swagger.annotations.ApiModel;

@VerifiedTimeSlot
@ApiModel
public class TimeSlotDto {

    private Integer id;

    @NotBlank
    private String name;

    @NotNull
    @Min(1)
    private Integer number;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime endTime;

    public TimeSlotDto() {
    }

    public Integer getId() {
        return id;
    }

    public Integer getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    @JsonFormat(pattern = "HH:mm")
    public LocalTime getStartTime() {
        return startTime;
    }

    @JsonFormat(pattern = "HH:mm")
    public LocalTime getEndTime() {
        return endTime;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
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
        TimeSlotDto other = (TimeSlotDto) obj;
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
        return "TimeSlotDto [id=" + id + ", name=" + name + ", number=" + number + ", startTime=" + startTime
                + ", endTime=" + endTime + "]";
    }

}
