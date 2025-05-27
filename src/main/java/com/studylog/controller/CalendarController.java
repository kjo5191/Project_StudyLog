package com.studylog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CalendarController {

	@GetMapping("/calendar")
	public String showCalendarPage() {
		return "calendar"; // → templates/calendar.html
	}
	
}
