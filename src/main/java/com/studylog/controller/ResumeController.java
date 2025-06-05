package com.studylog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.studylog.util.GeminiClient;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/resume")
public class ResumeController {
	
	@GetMapping("/upload")
	public String showUploadPage() {
		return "resume/upload"; // templates/resume/upload.html
	}

	@PostMapping("/image")
	public String handleImageUpload(@RequestParam("file") MultipartFile file, Model model) {
		// ... flask 전송 + Gemini 피드백 로직
		String feedback = GeminiClient.getResumeFeedback(text); // 결과 문자열

		model.addAttribute("resultText", feedback);
		return "resume/upload";
	}

}
