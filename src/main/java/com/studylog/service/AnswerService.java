package com.studylog.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studylog.domain.Answer;
import com.studylog.repository.AnswerRepository;
import com.studylog.util.FeedbackParser;
import com.studylog.util.GeminiClient;

@Service
public class AnswerService {

	@Autowired
	private AnswerRepository answerRepository;
	@Autowired
	private GeminiClient geminiClient;
	
	
	public Answer save(Answer answer) {
		return answerRepository.save(answer);
	}
	
	public Optional<Answer> getLatestAnswer(Integer questionId) {
		return answerRepository.findTopByQuestionIdOrderByAnsweredAtDesc(questionId);
	}
	
	public void saveWithFeedback(Answer answer) {
		String questionText = answer.getQuestion().getQuestionText();
		String myAnswer = answer.getAnswerText();

		String feedback = geminiClient.getFeedback(questionText, myAnswer);
		answer.setAiFeedback(feedback);

		Map<String, Integer> scores = FeedbackParser.parseScores(feedback);
		answer.setScoreContent(scores.getOrDefault("충실도", 0));
		answer.setScoreLogic(scores.getOrDefault("논리성", 0));
		answer.setScoreGrammar(scores.getOrDefault("문법", 0));

		answerRepository.save(answer);
	}


}
