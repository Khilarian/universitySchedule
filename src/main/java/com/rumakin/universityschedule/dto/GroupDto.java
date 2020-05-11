package com.rumakin.universityschedule.dto;

import com.rumakin.universityschedule.models.Group;

public class GroupDto {

    private int id;
    private String name;
    private int facultyId;
    private String facultyName;

    public GroupDto() {
    }

    public GroupDto(int id, String name, int facultyId) {
        this.id = id;
        this.name = name;
        this.facultyId = facultyId;
    }

    public GroupDto(int id, String name, int facultyId, String facultyName) {
        this.id = id;
        this.name = name;
        this.facultyId = facultyId;
        this.facultyName = facultyName;
    }

    public GroupDto(Group group) {
        this.id = group.getId();
        this.name = group.getName();
        this.facultyId = group.getFaculty().getId();
        this.facultyName = group.getFaculty().getName();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getFacultyId() {
        return facultyId;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    @Override
    public String toString() {
        return "GroupDto [id=" + id + ", name=" + name + ", facultyId=" + facultyId + ", facultyName=" + facultyName
                + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + facultyId;
        result = prime * result + ((facultyName == null) ? 0 : facultyName.hashCode());
        result = prime * result + id;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        GroupDto other = (GroupDto) obj;
        if (facultyId != other.facultyId) return false;
        if (facultyName == null) {
            if (other.facultyName != null) return false;
        } else if (!facultyName.equals(other.facultyName)) return false;
        if (id != other.id) return false;
        if (name == null) {
            if (other.name != null) return false;
        } else if (!name.equals(other.name)) return false;
        return true;
    };

}
