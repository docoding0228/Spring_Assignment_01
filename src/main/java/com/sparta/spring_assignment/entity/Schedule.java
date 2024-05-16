package com.sparta.spring_assignment.entity;

import com.sparta.spring_assignment.dto.ScheduleRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class Schedule {
    private Long id;
    private String title;
    private String contents;
    private String username;
    private String password;
    private String date;

    public Schedule(ScheduleRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.username = requestDto.getUsername();
        this.contents = requestDto.getContents();
        this.date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        this.id = requestDto.getId();
        this.password = requestDto.getPassword();
    }

    public void update(ScheduleRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.contents = requestDto.getContents();
        this.date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        this.id = requestDto.getId();
        this.password = requestDto.getPassword();
        this.title = requestDto.getTitle();
    }
}