package com.sparta.spring_assignment.entity;

import com.sparta.spring_assignment.dto.ScheduleRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.relational.core.mapping.Table;

import java.text.SimpleDateFormat;
import java.util.Date;
@Table
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
        this.date = requestDto.getDate();
        this.password = requestDto.getPassword();
    }

    public Schedule(long id, String title, String contents, String username, String password, String date) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.username = username;
        this.password = password;
        this.date = date;
    }
}