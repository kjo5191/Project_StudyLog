package com.studylog.service;

import com.studylog.domain.Schedule;
import com.studylog.dto.ScheduleDTO;
import com.studylog.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

	@Autowired
	private ScheduleRepository scheduleRepository;

	public List<ScheduleDTO> getAll() {
		return scheduleRepository.findAll().stream().map(schedule -> ScheduleDTO.builder()
				.id(schedule.getId())
				.title(schedule.getTitle())
				.content(schedule.getContent())
				.start(schedule.getStart())
				.end(schedule.getEnd())
				.done(schedule.isDone())
				.build()
		).collect(Collectors.toList());
	}

	public void create(ScheduleDTO dto) {
		Schedule schedule = Schedule.builder()
				.title(dto.getTitle())
				.content(dto.getContent())
				.start(dto.getStart())
				.end(dto.getEnd())
				.done(dto.isDone())
				.build();
		scheduleRepository.save(schedule);
	}
}
