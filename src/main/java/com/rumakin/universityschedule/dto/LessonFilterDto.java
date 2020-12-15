package com.rumakin.universityschedule.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import static java.util.Objects.isNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.rumakin.universityschedule.validation.annotation.CheckGroupAndTeacher;

import io.swagger.annotations.ApiModel;

@CheckGroupAndTeacher
@ApiModel
public class LessonFilterDto {

    private Integer groupId;

    private Integer teacherId;

    @NotNull
    private Integer monthScheduleCheck;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

    public LessonFilterDto() {
    }

    public LessonFilterDto(Integer groupId, Integer teacherId, Integer monthScheduleCheck, LocalDate date) {
        this.groupId = groupId;
        this.teacherId = teacherId;
        this.monthScheduleCheck = monthScheduleCheck;
        this.date = date;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public Integer getMonthScheduleCheck() {
        return monthScheduleCheck;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public void setMonthScheduleCheck(Integer monthScheduleCheck) {
        this.monthScheduleCheck = monthScheduleCheck;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isEmpty() {
        return isNull(this.getGroupId()) && isNull(this.getTeacherId()) && isNull(this.getMonthScheduleCheck())
                && isNull(this.getDate());
    }

    @Override
    public String toString() {
        return "LessonFilterDto [groupId=" + groupId + ", teacherId=" + teacherId + ", monthScheduleCheck="
                + monthScheduleCheck + ", date=" + date + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        result = prime * result + ((groupId == null) ? 0 : groupId.hashCode());
        result = prime * result + ((monthScheduleCheck == null) ? 0 : monthScheduleCheck.hashCode());
        result = prime * result + ((teacherId == null) ? 0 : teacherId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        LessonFilterDto other = (LessonFilterDto) obj;
        if (date == null) {
            if (other.date != null) return false;
        } else if (!date.equals(other.date)) return false;
        if (groupId == null) {
            if (other.groupId != null) return false;
        } else if (!groupId.equals(other.groupId)) return false;
        if (monthScheduleCheck == null) {
            if (other.monthScheduleCheck != null) return false;
        } else if (!monthScheduleCheck.equals(other.monthScheduleCheck)) return false;
        if (teacherId == null) {
            if (other.teacherId != null) return false;
        } else if (!teacherId.equals(other.teacherId)) return false;
        return true;
    }

}
