package com.studylog.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	public String saveAnswer(@RequestParam("questionId") Integer questionId,
							 @RequestParam("answerText") String answerText,
							 Model model) {
		Question question = questionService.getQuestionById(questionId);
		if (question == null) {
			Answer answer = Answer.builder()
					.question(question)
					.answerText(answerText)
					.build();
			answerService.save(answer);
			model.addAttribute("message", "답변이 저장되었습니다.");
		} else {
			model.addAttribute("error", "질문을 찾을 수 없습니다.");
		}
		model.addAttribute("question", question);
		
		return "question/random";
		
	}
	
	@GetMapping("/latest/{questionId}")
	@ResponseBody
	public Answer getLatestAnswer(@PathVariable("questionId") Integer questionId) {
		log.info("@# Controller : Get Latest Answer");
		return answerService.getLatestAnswer(questionId)
				.orElse(null);	// 없을 경우 null 반환
	}
	
	@PostMapping("/feedback")
	public String requestAiFeedback(@RequestParam("questionId") Integer questionId, Model model) {
		log.info("@# AnswerController : Request AI Feedback");

		// 가장 최근 Answer 불러오기
		Optional<Answer> optionalAnswer = answerService.getLatestAnswer(questionId);

		if (optionalAnswer.isPresent()) {
			Answer answer = optionalAnswer.get();

			if (answer.getAnswerText() != null && !answer.getAnswerText().isBlank()) {
				answerService.saveWithFeedback(answer);  // 피드백 요청 및 저장
				model.addAttribute("message", "AI 피드백이 저장되었습니다.");
			} else {
				model.addAttribute("error", "답변 내용을 먼저 작성해주세요.");
			}
			model.addAttribute("question", answer.getQuestion());
		} else {
			model.addAttribute("error", "해당 질문에 대한 답변이 없습니다.");
			model.addAttribute("questionId", questionId); // 혹시 템플릿에서 필요할 수 있음
		}

		return "question/random";
	}

	
}
