package com.studylog.api;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.studylog.util.GeminiClient;
import com.studylog.util.MultipartInputStreamFileResource;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/resume")
public class ResumeApiController {
	
	private final RestTemplate restTemplate = new RestTemplate();
	private final String flaskUrl = "http://localhost:5000/upload";
	private final GeminiClient geminiClient;
	
	@PostMapping("/upload")
	public ResponseEntity<String> uploadResume(@RequestParam("file") MultipartFile file) {
		log.info("📤 자소서 이미지 수신 및 Flask 전송 시작");

		try {
			// 파일을 Flask에 전송할 body 구성
			MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
			body.add("file", new MultipartInputStreamFileResource(file.getInputStream(), file.getOriginalFilename()));

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.MULTIPART_FORM_DATA);

			HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

			// Flask 서버로 전송
			ResponseEntity<String> response = restTemplate.postForEntity(flaskUrl, requestEntity, String.class);

			log.info("✅ Flask 응답 수신: {}", response.getBody());
			return ResponseEntity.ok(response.getBody());

		} catch (IOException e) {
			log.error("❌ 파일 처리 실패", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 전송 실패");
		}
	}
	
	
}
