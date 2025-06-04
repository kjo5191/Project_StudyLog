package com.studylog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.studylog.domain.Question;
import com.studylog.service.QuestionService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/question")
public class QuestionController {

//	Repository(DB)관련 모두 Service 로 분리
//	@Autowired
//	private QuestionRepository questionRepository;
	@Autowired
	private QuestionService questionService;

    QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    
    @GetMapping("/{id}")
    public String getQuestionDetail(@PathVariable("id") Integer id, Model model) {
    	log.info("@# Controller : Get Question Detail");

    	Question question = questionService.getQuestionById(id);  // Optional로 처리
    	model.addAttribute("question", question);
    	
    	return "question/detail";  // → templates/question/detail.html
    }

    
	@GetMapping("/random")
	public String getRandomQuestion(Model model) {
		log.info("@# controller : Random Question");
		
		Question randomQuestion = questionService.getRandomQuestion();

		if (randomQuestion == null) {
			model.addAttribute("question", "질문이 없습니다.");
		} else {
			model.addAttribute("question", randomQuestion);
		}

		log.info("question class = {}", randomQuestion.getClass().getName());

		return "question/random";  // → templates/question/random.html
	}
	
	
	@GetMapping("/new")
	public String showNewForm(Model model) {
		model.addAttribute("categories", List.of(
			"Java", "DB", "Spring", "HTTP", "백엔드 개념", "객체지향 설계", "보안", "컴퓨터 기초", "JavaScript", "Spring MVC", "운영체제"
		));
		return "question/new";
	}

	
	@PostMapping("/new")
	public String saveQuestion(Question question) {
		log.info("@# Controller : Save Question");
		
//		questionRepository.save(question);		//Controller 가 직접 DB 관여
		questionService.saveQuestion(question);	//Service 에 위임
		return "redirect:/question/random";  // 저장 후 랜덤 질문 페이지로 리디렉션
	}

	
	@GetMapping("/list")
	public String getQuestionList(@RequestParam(value = "category", required = false) List<String> category, Model model) {
		log.info("@# Controller : Get List");

		List<Question> questions;

		if (category == null || category.isEmpty()) {
			questions = questionService.getQuestionList();
		} else {
			questions = questionService.getQuestionsByCategory(category);
		}

		model.addAttribute("questions", questions);
		return "question/list";
	}

	
	@GetMapping("/edit/{id}")
	public String editForm(@PathVariable("id") Integer id, Model model) {
		Question q = questionService.getQuestionById(id);
		model.addAttribute("question", q);
		model.addAttribute("categories", List.of(
			"Java", "DB", "Spring", "HTTP", "백엔드 개념", "객체지향 설계", "보안", "컴퓨터 기초", "JavaScript", "Spring MVC", "운영체제"
		));
		return "question/edit";
	}

	
	@PostMapping("/edit/{id}")
	public String editSave(@PathVariable("id") Integer id, @ModelAttribute Question updatedQuestion) {
		log.info("@# Controller : Edit Save");

		questionService.updateQuestion(id, updatedQuestion);
		return "redirect:/question/list";
	}

	
	@PostMapping("/delete/{id}")
	public String deleteQuestion(@PathVariable("id") Integer id) {
		log.info("@# Controller : Delete");

		questionService.deleteQuestion(id);
		return "redirect:/question/list";
	}
	
	
	@PostMapping("/answer/save")
	public String saveMyAnswer(@RequestParam("questionId") Integer questionId,
							   @RequestParam("myAnswer") String myAnswer,
							   Model model) {
		log.info("@# Controller : Save MyAnswer");

		Question question = questionService.getQuestionById(questionId);
		if (question != null) {
			question.setMyAnswer(myAnswer);
			questionService.saveQuestion(question);  // 피드백 없이 저장
			model.addAttribute("message", "내 답변이 저장되었습니다.");
		} else {
			model.addAttribute("error", "질문을 찾을 수 없습니다.");
		}
		
		model.addAttribute("question", question);

		return "question/random";  // 현재 질문 그대로 다시 랜더링
	}
	
	
	@PostMapping("/answer/feedback")
	public String requestAiFeedback(@RequestParam("questionId") Integer questionId, Model model) {
		log.info("@# Controller : Request AI Feedback");

		Question question = questionService.getQuestionById(questionId);
		if (question != null && question.getMyAnswer() != null && !question.getMyAnswer().isBlank()) {
			questionService.saveWithFeedback(question);  // 기존 답변 기준으로 피드백 요청
			model.addAttribute("message", "AI 피드백이 저장되었습니다.");
		} else {
			model.addAttribute("error", "먼저 답변을 작성하고 저장해야 AI 피드백을 요청할 수 있습니다.");
		}

		model.addAttribute("question", question);
		return "question/random";
	}


}
