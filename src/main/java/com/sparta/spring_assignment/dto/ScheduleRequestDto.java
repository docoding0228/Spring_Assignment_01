package com.sparta.spring_assignment.dto;

import lombok.Getter;

import java.util.Date;

@Getter
public class ScheduleRequestDto {
    private Long id;
    private String title; // 할일 제목
    private String contents; // 할일 내용
    private String username; // 담당자
    private String password; // 비밀번호
    private String date; // 작성일
}