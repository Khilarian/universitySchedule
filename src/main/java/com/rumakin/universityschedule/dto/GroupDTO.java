package com.rumakin.universityschedule.dto;

public class GroupDTO {

    int id;
    String name;
    int facultyId;

    GroupDTO() {
    }

    public GroupDTO(int id, String name, int facultyId) {
        this.id= id;
        this.name = name;
        this.facultyId = facultyId;
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
    
    public void setId(int id) {
        this.id=id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    @Override
    public String toString() {
        return "GroupDTO [ name=" + name + ", facultyId=" + facultyId + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + facultyId;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        GroupDTO other = (GroupDTO) obj;
        if (facultyId != other.facultyId) return false;
        if (name == null) {
            if (other.name != null) return false;
        } else if (!name.equals(other.name)) return false;
        return true;
    };

}
