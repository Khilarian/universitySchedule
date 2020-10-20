package com.rumakin.universityschedule.dto;

import java.util.Objects;

import javax.validation.constraints.*;

import com.rumakin.universityschedule.validation.annotation.*;

import io.swagger.annotations.ApiModel;

@VerifiedTimeSlot
@ApiModel
public class TimeSlotDto {

    private int id;

    @NotBlank
    private String name;

    @NotNull
    @Min(1)
    private Integer number;

    @NotBlank
    @Pattern(regexp = "(([0-1][0-9])|([2][0-3]))\\:[0-5][0-9]", message = "{com.rumakin.universityschedule.validation.illegal.timeformat}")
    private String startTime;

    @NotBlank
    @Pattern(regexp = "(([0-1][0-9])|([2][0-3]))\\:[0-5][0-9]", message = "{com.rumakin.universityschedule.validation.illegal.timeformat}")
    private String endTime;

    public TimeSlotDto() {
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

    public void setNumber(Integer number) {
        this.number = number;
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

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof CourseDto)) return false;
        TimeSlotDto dto = (TimeSlotDto) obj;
        return getId() == dto.getId();
    }

    @Override
    public String toString() {
        return "TimeSlotDto [id=" + id + ", name=" + name + ", number=" + number + ", startTime=" + startTime
                + ", endTime=" + endTime + "]";
    }

}
