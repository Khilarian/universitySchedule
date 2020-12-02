package com.rumakin.universityschedule.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import javax.validation.constraints.*;

import org.springframework.format.annotation.DateTimeFormat;

import com.rumakin.universityschedule.validation.annotation.*;

import io.swagger.annotations.ApiModel;

@MaxAuditoriumCapacity
@FreeAuditorium
@TeachersLimits
@BusyTeachers
@BusyGroups
@ApiModel
public class LessonDto {

    private Integer id;

    @NotNull
    private Integer courseId;

    private String courseName;

    @NotNull
    private Integer lessonTypeId;

    private String lessonTypeName;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

    @NotNull
    private Integer timeSlotId;

    private Integer timeSlotNumber;

    private String timeSlotName;

    private LocalTime timeSlotStartTime;

    private LocalTime timeSlotEndTime;

    @NotNull
    private Integer auditoriumId;

    private Integer auditoriumNumber;

    private Integer buildingId;

    private String buildingName;

    private Set<TeacherDto> teachers = new HashSet<>();

    private Set<GroupDto> groups = new HashSet<>();

    public LessonDto() {
    }

    public LessonDto(Integer id, @NotNull Integer courseId, String courseName, @NotNull Integer lessonTypeId,
            String lessonTypeName, @NotNull LocalDate date, @NotNull Integer timeSlotId, Integer timeSlotNumber,
            String timeSlotName, LocalTime timeSlotStartTime, LocalTime timeSlotEndTime, @NotNull Integer auditoriumId,
            Integer auditoriumNumber, Integer buildingId, String buildingName) {
        this.id = id;
        this.courseId = courseId;
        this.courseName = courseName;
        this.lessonTypeId = lessonTypeId;
        this.lessonTypeName = lessonTypeName;
        this.date = date;
        this.timeSlotId = timeSlotId;
        this.timeSlotNumber = timeSlotNumber;
        this.timeSlotName = timeSlotName;
        this.timeSlotStartTime = timeSlotStartTime;
        this.timeSlotEndTime = timeSlotEndTime;
        this.auditoriumId = auditoriumId;
        this.auditoriumNumber = auditoriumNumber;
        this.buildingId = buildingId;
        this.buildingName = buildingName;
    }

    public Integer getId() {
        return id;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public Integer getLessonTypeId() {
        return lessonTypeId;
    }

    public String getLessonTypeName() {
        return lessonTypeName;
    }

    public LocalDate getDate() {
        return date;
    }

    public Integer getTimeSlotId() {
        return timeSlotId;
    }

    public Integer getTimeSlotNumber() {
        return timeSlotNumber;
    }

    public String getTimeSlotName() {
        return timeSlotName;
    }

    public LocalTime getTimeSlotStartTime() {
        return timeSlotStartTime;
    }

    public LocalTime getTimeSlotEndTime() {
        return timeSlotEndTime;
    }

    public Integer getAuditoriumId() {
        return auditoriumId;
    }

    public Integer getAuditoriumNumber() {
        return auditoriumNumber;
    }

    public Integer getBuildingId() {
        return buildingId;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public Set<TeacherDto> getTeachers() {
        return teachers;
    }

    public Set<GroupDto> getGroups() {
        return groups;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setLessonTypeId(Integer lessonTypeId) {
        this.lessonTypeId = lessonTypeId;
    }

    public void setLessonTypeName(String lessonTypeName) {
        this.lessonTypeName = lessonTypeName;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTimeSlotId(Integer timeSlotId) {
        this.timeSlotId = timeSlotId;
    }

    public void setTimeSlotNumber(Integer timeSlotNumber) {
        this.timeSlotNumber = timeSlotNumber;
    }

    public void setTimeSlotName(String timeSlotName) {
        this.timeSlotName = timeSlotName;
    }

    public void setTimeSlotStartTime(LocalTime timeSlotStartTime) {
        this.timeSlotStartTime = timeSlotStartTime;
    }

    public void setTimeSlotEndTime(LocalTime timeSlotEndTime) {
        this.timeSlotEndTime = timeSlotEndTime;
    }

    public void setAuditoriumId(Integer auditoriumId) {
        this.auditoriumId = auditoriumId;
    }

    public void setAuditoriumNumber(Integer auditoriumNumber) {
        this.auditoriumNumber = auditoriumNumber;
    }

    public void setBuildingId(Integer buildingId) {
        this.buildingId = buildingId;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public void setTeachers(Set<TeacherDto> teachers) {
        this.teachers = teachers;
    }

    public void setGroups(Set<GroupDto> groups) {
        this.groups = groups;
    }

    @Override
    public String toString() {
        return "LessonDto [id=" + id + ", courseId=" + courseId + ", courseName=" + courseName + ", lessonTypeId="
                + lessonTypeId + ", lessonTypeName=" + lessonTypeName + ", date=" + date + ", timeSlotId=" + timeSlotId
                + ", timeSlotName=" + timeSlotName + ", timeSlotStartTime=" + timeSlotStartTime + ", timeSlotEndTime="
                + timeSlotEndTime + ", auditoriumId=" + auditoriumId + ", auditoriumNumber=" + auditoriumNumber
                + ", buildingName=" + buildingName + ", teachers=" + teachers + ", groups=" + groups + "]";
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
        LessonDto other = (LessonDto) obj;
        return id == other.id;
    }

}
