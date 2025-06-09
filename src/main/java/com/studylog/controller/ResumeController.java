package com.studylog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.studylog.util.FlaskClient;
import com.studylog.util.GeminiClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/resume")
public class ResumeController {
	
	private final GeminiClient geminiClient;
	private final FlaskClient flaskClient;
	
	@GetMapping("/upload")
	public String showUploadPage() {
		return "resume/upload"; // templates/resume/upload.html
	}

	@PostMapping("/image")
	public String handleImageUpload(@RequestParam("file") MultipartFile file, Model model) {
		try {
			// 1. Flask 서버로 이미지 전송해서 OCR 결과 받아오기
			String text = flaskClient.sendImageAndGetText(file);
			
			// 2. Gemini를 통해 오타 교정
			String correction = geminiClient.correctSpelling(text);

			// 3. Gemini를 통해 피드백 생성
			String feedback = geminiClient.getResumeFeedback(correction);

			// 4. 화면에 결과 전송
			model.addAttribute("resultText", feedback);

		} catch (Exception e) {
			log.error("오류 발생", e);
			model.addAttribute("resultText", "오류가 발생했습니다: " + e.getMessage());
		}

		return "resume/upload";
	}


}
