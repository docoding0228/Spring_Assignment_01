package com.sparta.spring_assignment.controller;

import com.sparta.spring_assignment.dto.ScheduleRequestDto;
import com.sparta.spring_assignment.dto.ScheduleResponseDto;
import com.sparta.spring_assignment.entity.Schedule;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ScheduleController {

    private final Map<Long, Schedule> memoList = new HashMap<>();

    @PostMapping("/schedule")
    public ScheduleResponseDto createMemo(@RequestBody ScheduleRequestDto requestDto) {
        // RequestDto -> Entity
        Schedule memo = new Schedule(requestDto);

        // Memo Max ID Check
        Long maxId = memoList.size() > 0 ? Collections.max(memoList.keySet()) + 1 : 1;
        memo.setId(maxId);

        // DB 저장
        memoList.put(memo.getId(), memo);

        // Entity -> ResponseDto
        ScheduleResponseDto memoResponseDto = new ScheduleResponseDto(memo);

        return memoResponseDto;
    }

    @GetMapping("/schedule")
    public List<ScheduleResponseDto> getMemos() {
        // Map To List
        List<ScheduleResponseDto> responseList = memoList.values().stream()
                .map(ScheduleResponseDto::new).toList();

        return responseList;
    }

    @PutMapping("/schedule/{id}")
    public Long updateMemo(@PathVariable Long id, @RequestBody ScheduleRequestDto requestDto) {
        // 해당 메모가 DB에 존재하는지 확인
        if(memoList.containsKey(id)) {
            // 해당 메모 가져오기
            Schedule memo = memoList.get(id);

            // memo 수정
            memo.update(requestDto);
            return memo.getId();
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }

    @DeleteMapping("/schedule/{id}")
    public Long deleteMemo(@PathVariable Long id) {
        // 해당 메모가 DB에 존재하는지 확인
        if(memoList.containsKey(id)) {
            // 해당 메모 삭제하기
            memoList.remove(id);
            return id;
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }
}
