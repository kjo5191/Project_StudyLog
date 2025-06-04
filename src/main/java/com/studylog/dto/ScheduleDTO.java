package com.studylog.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleDTO {
	private Long id;
	private String title;
	private String content;
	private LocalDateTime start;
	private LocalDateTime end;
	private boolean done;
	private boolean allDay;
}
