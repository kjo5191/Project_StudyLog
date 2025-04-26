package com.studylog.controller;

import com.studylog.domain.Question;
import com.studylog.repository.QuestionRepository;
import com.studylog.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Random;

@Controller
@Slf4j
public class QuestionController {
	
	@Autowired
    private QuestionService questionService;

//	Repository(DB)관련 모두 Service로 분리
//	@Autowired
//	private QuestionRepository questionRepository;

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
	
	@GetMapping("/question/new")
	public String newQuestionForm() {
		log.info("@# log : New Question");
		
		return "new";  // → templates/new.html
	}

	@PostMapping("/question/new")
	public String saveQuestion(Question question) {
		log.info("@# Controller : Save Question");
		
//		questionRepository.save(question);		//Controller가 직접 DB 관여
		questionService.saveQuestion(question);	//Service에 위임
		return "redirect:/question/random";  // 저장 후 랜덤 질문 페이지로 리디렉션
	}
	
	@GetMapping("/question/list")
	public String getQuestionList(Model model) {
		log.info("@# Controller : Get List");
		
		List<Question> questions = questionService.getQuestionList();
		
		model.addAttribute("questions", questions);
		return "list";
	}
	
/*
	@GetMapping("/question/edit/{id}")
	public String editForm(@PathVariable("id") Long id, Model model) {
		log.info("@# log : Edit");
		
		Question question = questionRepository.findById(id).orElse(null);
		model.addAttribute("question", question);
		return "edit";
	}

	@PostMapping("/question/edit/{id}")
	public String editSave(@PathVariable("id") Long id, Question updatedQuestion) {
		log.info("@# log : Edit Save");
		
		Question original = questionRepository.findById(id).orElse(null);
		if (original != null) {
			original.setCategory(updatedQuestion.getCategory());
			original.setContent(updatedQuestion.getContent());
			original.setModelAnswer(updatedQuestion.getModelAnswer());
			questionRepository.save(original);
		}
		return "redirect:/question/list";
	}

	@PostMapping("/question/delete/{id}")
	public String deleteQuestion(@PathVariable("id") Long id) {
		log.info("@# log : Delete");
		
		questionRepository.deleteById(id);
		return "redirect:/question/list";
	}
	*/
	
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
