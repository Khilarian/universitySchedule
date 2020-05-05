package com.rumakin.universityschedule.dto;

import java.time.LocalDate;

public class LessonDto {

    private int id;
    private int courseId;
    private String courseName;
    private int lessonTypeId;
    private int auditoriumId;
    private LocalDate date;
    private int timeSlotId;

    // List of groups?
    // List of teachers?
    public LessonDto() {
    }

    public LessonDto(int id, int courseId, String courseName, int lessonTypeId, int auditoriumId, LocalDate date,
            int timeSlotId) {
        this.id = id;
        this.courseId = courseId;
        this.courseName = courseName;
        this.lessonTypeId = lessonTypeId;
        this.auditoriumId = auditoriumId;
        this.date = date;
        this.timeSlotId = timeSlotId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getLessonTypeId() {
        return lessonTypeId;
    }

    public void setLessonTypeId(int lessonTypeId) {
        this.lessonTypeId = lessonTypeId;
    }

    public int getAuditoriumId() {
        return auditoriumId;
    }

    public void setAuditoriumId(int auditoriumId) {
        this.auditoriumId = auditoriumId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getTimeSlotId() {
        return timeSlotId;
    }

    public void setTimeSlotId(int timeSlotId) {
        this.timeSlotId = timeSlotId;
    }

    @Override
    public String toString() {
        return "LessonDto [id=" + id + ", courseId=" + courseId + ", courseName=" + courseName + ", lessonTypeId="
                + lessonTypeId + ", auditoriumId=" + auditoriumId + ", date=" + date + ", timeSlotId=" + timeSlotId
                + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + auditoriumId;
        result = prime * result + courseId;
        result = prime * result + ((courseName == null) ? 0 : courseName.hashCode());
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        result = prime * result + id;
        result = prime * result + lessonTypeId;
        result = prime * result + timeSlotId;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        LessonDto other = (LessonDto) obj;
        if (auditoriumId != other.auditoriumId) return false;
        if (courseId != other.courseId) return false;
        if (courseName == null) {
            if (other.courseName != null) return false;
        } else if (!courseName.equals(other.courseName)) return false;
        if (date == null) {
            if (other.date != null) return false;
        } else if (!date.equals(other.date)) return false;
        if (id != other.id) return false;
        if (lessonTypeId != other.lessonTypeId) return false;
        return timeSlotId == other.timeSlotId;
    }

}
