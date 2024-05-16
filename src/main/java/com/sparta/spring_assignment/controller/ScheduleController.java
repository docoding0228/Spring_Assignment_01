package com.sparta.spring_assignment.controller;
import com.sparta.spring_assignment.dto.ScheduleRequestDto;
import com.sparta.spring_assignment.dto.ScheduleResponseDto;
import com.sparta.spring_assignment.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ScheduleController {

    private final JdbcTemplate jdbcTemplate;

    public ScheduleController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/schedule")
    public ScheduleResponseDto createMemo(@RequestBody ScheduleRequestDto requestDto) {
        // RequestDto -> Entity
        Schedule schedule = new Schedule(requestDto);

        // DB 저장
        KeyHolder keyHolder = new GeneratedKeyHolder(); // 기본 키를 반환받기 위한 객체

        String sql = "INSERT INTO schedule (title, contents, username, password, date) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update( con -> {
                    PreparedStatement preparedStatement = con.prepareStatement(sql,
                            Statement.RETURN_GENERATED_KEYS);

                    preparedStatement.setString(1, schedule.getTitle());
                    preparedStatement.setString(2, schedule.getContents());
                    preparedStatement.setString(3, schedule.getUsername());
                    preparedStatement.setString(4, schedule.getPassword());
                    preparedStatement.setString(5, schedule.getDate());
                    return preparedStatement;
                },
                keyHolder);

        // DB Insert 후 받아온 기본키 확인
        Long id = keyHolder.getKey().longValue();
        schedule.setId(id);

        // Entity -> ResponseDto
        ScheduleResponseDto ScheduleResponseDto = new ScheduleResponseDto(schedule);

        return ScheduleResponseDto;
    }

    @GetMapping("/schedule")
    public List<ScheduleResponseDto> getMemos() {
        // DB 조회
        String sql = "SELECT * FROM schedule";

        return jdbcTemplate.query(sql, new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                // SQL 의 결과로 받아온 Memo 데이터들을 MemoResponseDto 타입으로 변환해줄 메서드
                Long id = rs.getLong("id");
                String title = rs.getString("title");
                String contents = rs.getString("contents");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String date = rs.getString("date");
                return new ScheduleResponseDto(id, title, contents, username, password, date);
            }
        });
    }

    @GetMapping("/schedule/{id}")
    public ScheduleResponseDto getMemoById(@PathVariable Long id) {
        Schedule schedule = findById(id);
        if (schedule != null) {
            return new ScheduleResponseDto(schedule);
        } else {
            throw new IllegalArgumentException("해당 ID의 일정을 찾을 수 없습니다: " + id);
        }
    }


    @PutMapping("/schedule/{id}")
    public Long updateMemo(@PathVariable Long id, @RequestBody ScheduleRequestDto requestDto, @RequestParam String password) {
        // 해당 메모가 DB에 존재하는지 확인
        Schedule schedule = findById(id);
        if (schedule != null) {
            // 일정의 패스워드가 요청에서 받은 패스워드와 일치하는지 확인
            if (schedule.getPassword().equals(password)) {
                // 패스워드 일치하면 해당 일정 업데이트
                String sql = "UPDATE schedule SET title = ?, contents = ?, username = ?, password = ?, date = ? WHERE id = ?";
                jdbcTemplate.update(sql, requestDto.getTitle(), requestDto.getContents(), requestDto.getUsername(), requestDto.getPassword(), requestDto.getDate(), id);
                return id;
            } else {
                throw new IllegalArgumentException("패스워드가 일치하지 않습니다.");
            }
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }


    @DeleteMapping("/schedule/{id}")
    public Long deleteMemo(@PathVariable Long id, @RequestParam String password) {
        // 해당 메모가 DB에 존재하는지 확인
        Schedule schedule = findById(id);
        if (schedule != null) {
            // 일정의 패스워드가 요청에서 받은 패스워드와 일치하는지 확인
            if (schedule.getPassword().equals(password)) {
                // 패스워드 일치하면 해당 일정 삭제
                String sql = "DELETE FROM schedule WHERE id = ?";
                jdbcTemplate.update(sql, id);
                return id;
            } else {
                throw new IllegalArgumentException("패스워드가 일치하지 않습니다.");
            }
        } else {
            throw new IllegalArgumentException("선택한 아이디는 존재하지 않습니다.");
        }
    }

    private Schedule findById(Long id) {
        String sql = "SELECT * FROM schedule WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) ->
                new Schedule(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("contents"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("date")
                )
        );
    }
}