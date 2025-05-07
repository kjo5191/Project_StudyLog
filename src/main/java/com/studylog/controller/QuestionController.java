package com.studylog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.studylog.domain.Question;
import com.studylog.service.QuestionService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class QuestionController {

//	Repository(DB)관련 모두 Service 로 분리
//	@Autowired
//	private QuestionRepository questionRepository;
	@Autowired
	private QuestionService questionService;

    QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

	@GetMapping("/question/random")
	public String getRandomQuestion(Model model) {
		log.info("@# controller : Random Question");
		
		Question randomQuestion = questionService.getRandomQuestion();

		if (randomQuestion == null) {
			model.addAttribute("question", "질문이 없습니다.");
		} else {
			model.addAttribute("question", randomQuestion);
		}

		return "random";  // → templates/random.html
	}
	
//	new.html 호출
	@GetMapping("/question/new")
	public String newQuestionForm() {
		log.info("@# log : New Question");
		
		return "new";  // → templates/new.html
	}

	@PostMapping("/question/new")
	public String saveQuestion(Question question) {
		log.info("@# Controller : Save Question");
		
//		questionRepository.save(question);		//Controller 가 직접 DB 관여
		questionService.saveQuestion(question);	//Service 에 위임
		return "redirect:/question/random";  // 저장 후 랜덤 질문 페이지로 리디렉션
	}

/*	
	@GetMapping("/question/list")
	public String getQuestionList(Model model) {
		log.info("@# Controller : Get List");
		
		List<Question> questions = questionService.getQuestionList();
		
		model.addAttribute("questions", questions);
		return "list";
	}
*/
	@GetMapping("/question/list")
	public String getQuestionList(@RequestParam(value = "category", required = false) String category, Model model) {
		log.info("@# Controller : Get List");

		List<Question> questions;

		if (category == null || category.isBlank()) {
			questions = questionService.getQuestionList();
		} else {
			questions = questionService.getQuestionsByCategory(category);
		}

		model.addAttribute("questions", questions);
		return "list";
	}
	
	@GetMapping("/question/edit/{id}")
	public String editForm(@PathVariable("id") Long id, Model model) {
		log.info("@# Controller : Edit Form");

		Question question = questionService.getQuestionById(id);
		model.addAttribute("question", question);
		return "edit";
	}

	@PostMapping("/question/edit/{id}")
	public String editSave(@PathVariable("id") Long id, Question updatedQuestion) {
		log.info("@# Controller : Edit Save");

		questionService.updateQuestion(id, updatedQuestion);
		return "redirect:/question/list";
	}

	@PostMapping("/question/delete/{id}")
	public String deleteQuestion(@PathVariable("id") Long id) {
		log.info("@# Controller : Delete");

		questionService.deleteQuestion(id);
		return "redirect:/question/list";
	}

}
