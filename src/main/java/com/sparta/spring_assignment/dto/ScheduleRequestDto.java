package com.sparta.spring_assignment.dto;

import lombok.Getter;

import java.util.Date;

@Getter
public class ScheduleRequestDto {
    private Long id;
    private String username;
    private String contents;
    private String title;
    private String password;
    private String date;
}
