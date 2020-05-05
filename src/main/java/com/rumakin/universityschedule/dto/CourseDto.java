package com.rumakin.universityschedule.dto;

public class CourseDto {

    private int courseId;
    private String courseName;
    private int facultyId;
    private String facultyName;

    public CourseDto() {
    }

    public CourseDto(int courseId, String courseName, int facultyId, String facultyName) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.facultyId = facultyId;
        this.facultyName = facultyName;
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

    public int getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    @Override
    public String toString() {
        return "CourseDto [courseId=" + courseId + ", courseName=" + courseName + ", facultyId=" + facultyId
                + ", facultyName=" + facultyName + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + courseId;
        result = prime * result + ((courseName == null) ? 0 : courseName.hashCode());
        result = prime * result + facultyId;
        result = prime * result + ((facultyName == null) ? 0 : facultyName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        CourseDto other = (CourseDto) obj;
        if (courseId != other.courseId) return false;
        if (courseName == null) {
            if (other.courseName != null) return false;
        } else if (!courseName.equals(other.courseName)) return false;
        if (facultyId != other.facultyId) return false;
        if (facultyName == null) {
            if (other.facultyName != null) return false;
        } else if (!facultyName.equals(other.facultyName)) return false;
        return true;
    }

}
