package com.studylog.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.studylog.domain.JobApplication;
import com.studylog.service.JobApplicationService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/job")
@RequiredArgsConstructor
public class JobApplicationController {
	
	private final JobApplicationService jobApplicationService;
	
	@GetMapping("/list")
	public String showJobList(Model model) {
		model.addAttribute("applications", jobApplicationService.findAll());
		return "job/list";
	}

	@GetMapping("/new")
	public String newForm(Model model) {
		model.addAttribute("jobApplication", new JobApplication());
		return "job/new";
	}

	@GetMapping("/edit/{id}")
	public String editForm(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("jobApplication", jobApplicationService.findById(id));
		return "job/edit";
	}

	@PostMapping("/save")
	public String save(@ModelAttribute JobApplication jobApplication) {
		jobApplicationService.save(jobApplication);
		return "redirect:/job/list";
	}

	@PostMapping("/delete/{id}")
	public String delete(@PathVariable("id") Integer id) {
		jobApplicationService.deleteById(id);
		return "redirect:/job/list";
	}
	
	
	// [캘린더 연동용 JSON API]
	@GetMapping("/calendar")
	@ResponseBody
	public List<Map<String, Object>> getCalendarEvents() {
		List<JobApplication> applications = jobApplicationService.findAll();
		List<Map<String, Object>> events = new ArrayList<>();

		for (JobApplication app : applications) {
			if (app.getApplyDate() != null) events.add(createEvent(app, "지원일", app.getApplyDate().toString()));
			if (app.getDeadline() != null) events.add(createEvent(app, "마감일", app.getDeadline().toString()));
			if (app.getInterview1() != null) events.add(createEvent(app, "1차 면접", app.getInterview1().toString()));
			if (app.getInterview2() != null) events.add(createEvent(app, "2차 면접", app.getInterview2().toString()));
			if (app.getInterview3() != null) events.add(createEvent(app, "3차 면접", app.getInterview3().toString()));
			if (app.getInterview4() != null) events.add(createEvent(app, "최종 면접", app.getInterview4().toString()));
		}
		return events;
	}
	
	private Map<String, Object> createEvent(JobApplication app, String label, String date) {
		Map<String, Object> event = new HashMap<>();
		event.put("title", app.getCompany() + " - " + label);
		event.put("start", date);
		event.put("url", "/job/edit/" + app.getId());
		return event;
	}	
	
}
