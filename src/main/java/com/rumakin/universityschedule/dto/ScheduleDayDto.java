package com.rumakin.universityschedule.dto;

import java.time.LocalDate;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Model that represent day(date) with lessons by request")
public class ScheduleDayDto implements Dto {

    private LocalDate date;
    private List<LessonDto> lessons;
    @ApiModelProperty(notes = "true to fill calendar  UI with empty day")
    private boolean mockDay;

    public ScheduleDayDto() {
    }

    public ScheduleDayDto(LocalDate date, List<LessonDto> lessons) {
        this.date = date;
        this.lessons = lessons;
    }

    public ScheduleDayDto(boolean mockDay) {
        this.mockDay = mockDay;
    }

    public LocalDate getDate() {
        return date;
    }

    public List<LessonDto> getLessons() {
        return lessons;
    }

    public boolean getMockDay() {
        return mockDay;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setLessons(List<LessonDto> lessons) {
        this.lessons = lessons;
    }

    public void setMockDay(boolean mockDay) {
        this.mockDay = mockDay;
    }

    @Override
    public String toString() {
        return "ScheduleDayDto [date=" + date + ", lessons=" + lessons + ", mockDay=" + mockDay + "]";
    }
}
