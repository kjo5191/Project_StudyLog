package com.studylog.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.studylog.domain.Answer;
import com.studylog.domain.Question;
import com.studylog.service.AnswerService;
import com.studylog.service.QuestionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/answer")
@RequiredArgsConstructor
public class AnswerController {

	private final AnswerService answerService;
	private final QuestionService questionService;
	
	@PostMapping("/save")
	@ResponseBody
	public ResponseEntity<String> saveAnswer(@RequestParam("questionId") Integer questionId,
											 @RequestParam("answerText") String answerText) {
		Question question = questionService.getQuestionById(questionId);
		if (question == null) {
			return ResponseEntity.badRequest().body("질문을 찾을 수 없습니다.");
		}

		Answer answer = Answer.builder()
				.question(question)
				.answerText(answerText)
				.build();
		answerService.save(answer);
		
		return ResponseEntity.ok("저장 성공");
	}

	
	@GetMapping("/latest/{questionId}")
	@ResponseBody
	public Answer getLatestAnswer(@PathVariable("questionId") Integer questionId) {
		log.info("@# Controller : Get Latest Answer");
		return answerService.getLatestAnswer(questionId)
				.orElse(null);	// 없을 경우 null 반환
	}
	
	
	@PostMapping("/feedback")
	@ResponseBody
	public Map<String, String> requestAiFeedback(@RequestParam("questionId") Integer questionId) {
		log.info("@# Controller : Request AI Feedback");

		Optional<Answer> optionalAnswer = answerService.getLatestAnswer(questionId);

		if (optionalAnswer.isPresent()) {
			Answer answer = optionalAnswer.get();
			String answerText = answer.getAnswerText();

			if (answerText != null && !answerText.isBlank()) {
				answerService.saveWithFeedback(answer); // 이 안에서 Gemini 호출
				return Collections.singletonMap("message", "AI 피드백이 저장되었습니다.");
			} else {
				return Collections.singletonMap("message", "답변 내용이 비어 있습니다.");
			}
		} else {
			return Collections.singletonMap("message", "해당 질문에 대한 답변이 없습니다.");
		}
	}


	
}
