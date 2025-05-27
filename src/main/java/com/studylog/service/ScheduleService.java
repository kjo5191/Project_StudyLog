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
	
	public void update(Long id, ScheduleDTO dto) {
		Schedule schedule = scheduleRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 일정입니다. id=" + id));

		schedule.setTitle(dto.getTitle());
		schedule.setContent(dto.getContent());

		if (dto.getStart() != null) {
			schedule.setStart(dto.getStart());
		}
		if (dto.getEnd() != null) {
			schedule.setEnd(dto.getEnd());
		}

		schedule.setDone(dto.isDone());
		scheduleRepository.save(schedule);
	}

	public void delete(Long id) {
		scheduleRepository.deleteById(id);
	}
	
}
