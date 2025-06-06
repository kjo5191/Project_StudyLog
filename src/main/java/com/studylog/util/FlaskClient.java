package com.studylog.util;

import java.io.IOException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class FlaskClient {

	private final RestTemplate restTemplate = new RestTemplate();

	@Value("${flask.api.url}")
	private String flaskUrl;

	public String sendImageAndGetText(MultipartFile file) throws IOException {
		log.info("📤 Flask로 이미지 전송 시작");

		// 1. MultipartFile → Resource 변환
		Resource resource = new ByteArrayResource(file.getBytes()) {
			@Override
			public String getFilename() {
				return Objects.requireNonNull(file.getOriginalFilename());
			}
		};

		// 2. 헤더 설정
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		// 3. 요청 바디 생성
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("file", resource);

		HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

		// 4. POST 요청
		ResponseEntity<String> response = restTemplate.postForEntity(flaskUrl, request, String.class);

		log.info("✅ Flask 응답 수신 완료");

		if (response.getStatusCode() == HttpStatus.OK) {
			return response.getBody();
		} else {
			throw new IOException("Flask 서버 응답 실패: " + response.getStatusCode());
		}
	}
}
