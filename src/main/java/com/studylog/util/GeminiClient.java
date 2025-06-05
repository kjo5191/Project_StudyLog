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

	// 면접 질문 피드백
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
	
	
	// 오타 교정
	public String correctSpelling(String originalText) {
		log.info("📝 오타 교정 요청 시작");

		String prompt = String.format("""
			다음은 이력서를 OCR 처리 한 내용이야. 텍스트의 오타나 문법 오류를 수정해서, 수정된 결과만 보여줘.  
			원본:  
			%s
		""", originalText);

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

			log.info("🟢 오타 교정 결과: {}", result);
			return result;
		} catch (Exception e) {
			log.error("❌ 오타 교정 실패", e);
			return originalText; // 실패 시 원문 그대로 반환
		}
	}
	
	
	// 이력서 피드백
	public String getResumeFeedback(String resumeText) {
		log.info("📄 이력서 Gemini 피드백 요청 시작");

		String prompt = String.format("""
			다음은 이력서 자기소개서 문장입니다. 전체 내용을 자연스럽게 요약한 뒤, 아래 항목에 따라 피드백을 제공해줘:

			- 문장 구성의 자연스러움
			- 오타 및 맞춤법 오류
			- 내용의 논리성과 설득력

			마지막엔 개선이 필요한 문장만 골라 원문 + 수정 예시 형태로 보여줘.

			[이력서 텍스트]
			%s
			""", resumeText);

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

			log.info("📄 Gemini 이력서 피드백 응답 완료");
			return result;
		} catch (Exception e) {
			log.error("❌ Gemini 이력서 피드백 실패", e);
			return "AI 피드백 생성 실패";
		}
	}

}
