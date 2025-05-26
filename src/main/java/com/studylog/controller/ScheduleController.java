package com.studylog.controller;

import com.studylog.dto.ScheduleDTO;
import com.studylog.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

	@Autowired
	private ScheduleService scheduleService;

	@GetMapping
	public List<ScheduleDTO> getAllSchedules() {
		return scheduleService.getAll();
	}

	@PostMapping
	public void createSchedule(@RequestBody ScheduleDTO dto) {
		scheduleService.create(dto);
	}
}
