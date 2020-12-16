package com.rumakin.universityschedule.dto;

import java.time.LocalDate;
import java.util.List;

import io.swagger.annotations.ApiModel;

@ApiModel
public class ScheduleDayDto implements Dto {

    private LocalDate date;
    private List<LessonDto> lessons;

    public ScheduleDayDto() {
    }

    public ScheduleDayDto(LocalDate date, List<LessonDto> lessons) {
        this.date = date;
        this.lessons = lessons;
    }

    public LocalDate getDate() {
        return date;
    }

    public List<LessonDto> getLessons() {
        return lessons;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setLessons(List<LessonDto> lessons) {
        this.lessons = lessons;
    }

    @Override
    public String toString() {
        return "ScheduleDayDto [date=" + date + ", lessons=" + lessons + "]";
    }
}
