package com.studylog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.studylog.domain.JobApplication;
import com.studylog.repository.JobApplicationRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/job")
@RequiredArgsConstructor
public class JobApplicationController {
	
	private final JobApplicationRepository jobApplicationRepository;
	
	@GetMapping("/list")
	public String showJobList(Model model) {
		model.addAttribute("applications", jobApplicationRepository.findAll());
		return "job/list";  // src/main/resources/templates/job/list.html
	}
	
	@GetMapping("/new")
	public String newForm(Model model) {
		model.addAttribute("jobApplication", new JobApplication());
		return "job/new";
	}
	
	@GetMapping("/edit/{id}")
	public String editForm(@PathVariable("id") Integer id, Model model) {
		JobApplication job = jobApplicationRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException(id + " : 해당 ID의 일정이 없습니다."));
		model.addAttribute("jobApplication", job);
		return "job/edit";
	}

	@PostMapping("/save")
	public String save(@ModelAttribute JobApplication jobApplication) {
		jobApplicationRepository.save(jobApplication);
		return "redirect:/job/list";
	}
	
	@PostMapping("/delete/{id}")
//	GET 요청으로 삭제하면 주소창에서 직접 삭제가 가능하므로 POST 로 구현
	public String delete(@PathVariable("id") Integer id) {
		jobApplicationRepository.deleteById(id);
		return "redirect:/job/list";
	}
	
}
