package com.studylog.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.studylog.domain.Question;
import com.studylog.repository.QuestionRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class QuestionService {
	
	@Autowired
	private QuestionRepository questionRepository;

	public Question getRandomQuestion() {
		log.info("@# Service : Random Question");
		
		List<Question> questions = questionRepository.findAll();

		if (questions.isEmpty()) {
			return null;
		} else {
			Random random = new Random();
			return questions.get(random.nextInt(questions.size()));
		}
	}	
	
	public void saveQuestion(Question question) {
		log.info("@# Service : Save Question");
		questionRepository.save(question);
	}
	
	public List<Question> getQuestionList() {
		log.info("@# Service : Get List");
		
		List<Question> questions = questionRepository.findAll();
		
		return questions;
	}	
	
	public Question getQuestionById(Long id) {
		log.info("@# Service : Get Question By Id");

		return questionRepository.findById(id).orElse(null);
	}

	public void updateQuestion(Long id, Question updatedQuestion) {
		log.info("@# Service : Update Question");

		Question original = questionRepository.findById(id).orElse(null);
		if (original != null) {
			original.setCategory(updatedQuestion.getCategory());
			original.setContent(updatedQuestion.getContent());
			original.setModelAnswer(updatedQuestion.getModelAnswer());
			questionRepository.save(original);
		}
	}

	public void deleteQuestion(Long id) {
		log.info("@# Service : Delete Question");

		questionRepository.deleteById(id);
	}

	//카테고리 
	public List<Question> getQuestionsByCategory(String category) {
		log.info("@# Service : Get Questions By Category");

		return questionRepository.findByCategory(category);	
	}
	
}

