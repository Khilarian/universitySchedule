package com.rumakin.universityschedule.dto;

import java.time.LocalDate;
import java.util.*;

import javax.validation.constraints.*;

import org.springframework.format.annotation.DateTimeFormat;

import com.rumakin.universityschedule.validation.annotation.*;

import io.swagger.annotations.ApiModel;

@ApiModel
public class LessonDto {

    private int id;
    
    @NotNull
    private int courseId;
    
    private String courseName;
    
    @NotNull
    private int lessonTypeId;
    
    private String lessonTypeName;
    
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;
    
    @NotNull
    private int timeSlotId;
    
    private String timeSlotName;
    
    @NotNull
    private int auditoriumId;
    
    private int auditoriumNumber;
    
    private Set<TeacherDto> teachers = new HashSet<>();
    
    private Set<GroupDto> groups = new HashSet<>();

    public LessonDto() {
    }

    public int getId() {
        return id;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getLessonTypeId() {
        return lessonTypeId;
    }

    public String getLessonTypeName() {
        return lessonTypeName;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getTimeSlotId() {
        return timeSlotId;
    }

    public String getTimeSlotName() {
        return timeSlotName;
    }

    public int getAuditoriumId() {
        return auditoriumId;
    }

    public int getAuditoriumNumber() {
        return auditoriumNumber;
    }

    public Set<TeacherDto> getTeachers() {
        return teachers;
    }

    public Set<GroupDto> getGroups() {
        return groups;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setLessonTypeId(int lessonTypeId) {
        this.lessonTypeId = lessonTypeId;
    }

    public void setLessonTypeName(String lessonTypeName) {
        this.lessonTypeName = lessonTypeName;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTimeSlotId(int timeSlotId) {
        this.timeSlotId = timeSlotId;
    }

    public void setTimeSlotName(String timeSlotName) {
        this.timeSlotName = timeSlotName;
    }

    public void setAuditoriumId(int auditoriumId) {
        this.auditoriumId = auditoriumId;
    }

    public void setAuditoriumNumber(int auditoriumNumber) {
        this.auditoriumNumber = auditoriumNumber;
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
                + ", timeSlotName=" + timeSlotName + ", auditoriumId=" + auditoriumId + ", auditoriumNumber="
                + auditoriumNumber + ", teachers=" + teachers + ", groups=" + groups + "]";
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
