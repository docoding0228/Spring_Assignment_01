package com.sparta.spring_assignment.dto;

import com.sparta.spring_assignment.entity.Schedule;
import lombok.Getter;

@Getter
public class ScheduleResponseDto {
    private Long id;
    private String title; // 할일 제목
    private String contents; // 할일 내용
    private String username; // 담당자
    private String password; // 비밀번호
    private String date; // 작성일

    public ScheduleResponseDto(Schedule memo) {
        this.id = memo.getId();
        this.title = memo.getTitle();
        this.contents = memo.getContents();
        this.username = memo.getUsername();
        this.password = memo.getPassword();
        this.date = memo.getDate();
    }
}
