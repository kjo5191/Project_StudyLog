package com.studylog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.studylog.domain.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Integer>{

	// 특정 질문에 대한 가장 최신 Answer 1개 조회
	@Query("SELECT a FROM Answer a WHERE a.question.id = :questionId ORDER BY a.answeredAt DESC LIMIT 1")
	Optional<Answer> findTopByQuestionIdOrderByAnsweredAtDesc(@Param("questionId") Integer questionId);
}	
