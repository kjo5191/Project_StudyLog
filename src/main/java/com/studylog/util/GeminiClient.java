package com.studylog.util;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class GeminiClient {

	@Value("${gemini.api.key}")
	private String apiKey;

	@Value("${gemini.api.url}")
	private String apiUrl;

	private final RestTemplate restTemplate = new RestTemplate();

	public String getFeedback(String question, String myAnswer) {
		log.info("🚀 Gemini 요청 시작");

		String prompt = String.format("""
			면접 질문: %s
			내 답변: %s

			항목별로 평가해줘:
			1. 충실도
			2. 논리성
			3. 문법

			각 항목에 대해 10점 만점 점수와 간단한 피드백을 작성해줘.
			아래와 같은 형식으로 응답해줘:

			충실도: [숫자]/10
			논리성: [숫자]/10
			문법: [숫자]/10
			피드백: [문장]
			""", question, myAnswer);

		Map<String, Object> message = Map.of("parts", List.of(Map.of("text", prompt)));
		Map<String, Object> body = Map.of("contents", List.of(message));

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("x-goog-api-key", apiKey);

		HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

		try {
			ResponseEntity<Map> response = restTemplate.postForEntity(apiUrl, request, Map.class);
			List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.getBody().get("candidates");
			Map<String, Object> content = (Map<String, Object>) candidates.get(0).get("content");
			List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");
			String result = (String) parts.get(0).get("text");

			log.info("✅ Gemini 응답: {}", result);
			return result;
		} catch (Exception e) {
			log.error("❌ Gemini 호출 실패", e);
			return "AI 피드백 생성 실패";
		}
	}
}
