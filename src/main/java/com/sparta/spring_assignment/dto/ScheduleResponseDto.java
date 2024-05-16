package com.sparta.spring_assignment.dto;

import com.sparta.spring_assignment.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Getter
@AllArgsConstructor
public class ScheduleResponseDto {
    private Long id;
    private String title;
    private String contents;
    private String username;
    private String password;
    private String date;

    public ScheduleResponseDto(Schedule Schedule) {
        this.id = Schedule.getId();
        this.title = Schedule.getTitle();
        this.contents = Schedule.getContents();
        this.username = Schedule.getUsername();
        this.password = Schedule.getPassword();
        this.date = Schedule.getDate();
    }
}