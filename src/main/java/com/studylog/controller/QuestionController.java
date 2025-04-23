package com.studylog.controller;

import com.studylog.domain.Question;
import com.studylog.repository.QuestionRepository;

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
	private QuestionRepository questionRepository;

	@GetMapping("/question/random")
	public String getRandomQuestion(Model model) {
		log.info("@# log : Random Question");
		
		List<Question> questions = questionRepository.findAll();

		if (questions.isEmpty()) {
			model.addAttribute("question", "질문이 없습니다.");
		} else {
			Random random = new Random();
			Question randomQuestion = questions.get(random.nextInt(questions.size()));
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
		log.info("@# log : Save Question");
		
		questionRepository.save(question);
		return "redirect:/question/random";  // 저장 후 랜덤 질문 페이지로 리디렉션
	}
	
	@GetMapping("/question/list")
	public String getQuestionList(Model model) {
		log.info("@# log : List");
		
		List<Question> questions = questionRepository.findAll();
		model.addAttribute("questions", questions);
		return "list";
	}

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

}
