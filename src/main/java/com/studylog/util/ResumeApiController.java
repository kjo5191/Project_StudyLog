package com.studylog.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ResumeApiController {
	
	private final GeminiClient geminiClient;

	@Autowired
	public ResumeApiController(GeminiClient geminiClient) {
		this.geminiClient = geminiClient;
	}
	
	@PostMapping("/api/resume")
	public ResponseEntity<String> receiveResumeText(@RequestParam("text") String text) {
		log.info("받은 텍스트: "+ text);
		
		// 1. 오타 교정
		String correctedText = geminiClient.correctSpelling(text);
		// 2. 이력서 피드백
		String feedback = geminiClient.getResumeFeedback(correctedText);
		log.info("📨 최종 피드백: {}", feedback);

		return ResponseEntity.ok(feedback);
	}
}
