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
    	
    	return "question";  // → templates/question.html
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

		return "random";  // → templates/random.html
	}
	
//	new.html 호출
	@GetMapping("/new")
	public String newQuestionForm() {
		log.info("@# log : New Question");
		
		return "new";  // → templates/new.html
	}

	@PostMapping("/new")
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
		return "list";
	}
	
	@GetMapping("/edit/{id}")
	public String editForm(@PathVariable("id") Integer id, Model model) {
		log.info("@# Controller : Edit Form");

		Question question = questionService.getQuestionById(id);
		model.addAttribute("question", question);
		return "edit";
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

}
