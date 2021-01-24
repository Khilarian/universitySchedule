package com.rumakin.universityschedule.controller.formatter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import com.rumakin.universityschedule.dto.GroupDto;

@Component
public class GroupFormatter implements Formatter<GroupDto> {

    @Override
    public String print(GroupDto group, Locale locale) {
        return group.getId().toString();
    }

    @Override
    public GroupDto parse(String id, Locale locale) throws ParseException {
        GroupDto group = new GroupDto();
        group.setId(Integer.parseInt(id));
        return group;
    }

}
