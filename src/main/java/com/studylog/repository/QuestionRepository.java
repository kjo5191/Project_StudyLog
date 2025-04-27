package com.studylog.repository;

import com.studylog.domain.Question;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
	List<Question> findByCategory(String category); // 카테고리로 질문 필터링
}
