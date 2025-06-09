package com.studylog.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studylog.domain.Question;
import com.studylog.repository.QuestionRepository;
import com.studylog.util.GeminiClient;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class QuestionService {
	
	@Autowired
	private QuestionRepository questionRepository;
	@Autowired
	private GeminiClient geminiClient;
	
	public Question getQuestionById(Integer id) {
		log.info("@# Service : Get Question By Id");
		
//		Question question = questionRepository.findById(id); // JPA에서는 이런식으로 타입을 받지 않음.
		Optional<Question> optional = questionRepository.findById(id);	// 바로 return 할 수 있지만 가시성을 위해 Optional 풀어씀.
		
		return optional.orElse(null);	// JPA는 값이 없는 경우를 위해 orElse(null) 을 해주거나 if문 처리를 해줘야함.
	}

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
	

	public void updateQuestion(Integer id, Question updatedQuestion) {
		log.info("@# Service : Update Question");

		Question original = questionRepository.findById(id).orElse(null);
		if (original != null) {
			original.setQuestionCategory(null);
			original.setQuestionText(null);
			original.setModelAnswer(updatedQuestion.getModelAnswer());
			questionRepository.save(original);
		}
	}

	public void deleteQuestion(Integer id) {
		log.info("@# Service : Delete Question");

		questionRepository.deleteById(id);
	}

	//카테고리 
	public List<Question> getQuestionsByCategory(List<String> category) {
		log.info("@# Service : Get Questions By Category");

		return questionRepository.findByQuestionCategoryIn(category);	
	}
	
	public void saveWithFeedback(Question question) {
		String aiFeedback = geminiClient.getFeedback(question.getQuestionText(), question.getMyAnswer());
		question.setAiFeedback(aiFeedback);
		questionRepository.save(question);
	}
	
}

