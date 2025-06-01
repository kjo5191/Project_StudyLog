package com.studylog.controller;

import com.studylog.dto.ScheduleDTO;
import com.studylog.service.ScheduleService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/schedule")
public class ScheduleApiController {

	@Autowired
	private ScheduleService scheduleService;

	@GetMapping
	public List<ScheduleDTO> getAllSchedules() {
		return scheduleService.getAll();
	}

	@PostMapping
	public void createSchedule(@RequestBody ScheduleDTO dto) {
		log.info("요청 들어온 DTO: " + dto);
		scheduleService.create(dto);
	}
	
	@PutMapping("/{id}")
	public void updateSchedule(@PathVariable("id") Long id, @RequestBody ScheduleDTO dto) {
		log.info("수정 요청: {}", dto);
		scheduleService.update(id, dto);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Long id) {
		scheduleService.delete(id);
	}


}
