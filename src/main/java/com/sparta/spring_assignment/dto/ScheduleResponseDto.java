package com.sparta.spring_assignment.dto;

import com.sparta.spring_assignment.entity.Schedule;
import lombok.Getter;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Getter
public class ScheduleResponseDto implements Comparable<ScheduleResponseDto> {
    private Long id;
    private String title;
    private String contents;
    private String username;
    private String password;
    private String date;

    public ScheduleResponseDto(Schedule memo) {
        this.id = memo.getId();
        this.title = memo.getTitle();
        this.contents = memo.getContents();
        this.username = memo.getUsername();
        this.password = memo.getPassword();
        this.date = memo.getDate();
    }

    // 작성일을 기준으로 내림차순으로 정렬
    @Override
    public int compareTo(ScheduleResponseDto o) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(o.getDate()).compareTo(sdf.parse(this.date));
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
}