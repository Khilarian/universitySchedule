package com.rumakin.universityschedule.dto;

import java.time.LocalDate;
import java.util.List;

import io.swagger.annotations.ApiModel;

@ApiModel
public class ScheduleDayDto {
    
    private LocalDate date;
    
    private List<LessonDto> lessons;

}
