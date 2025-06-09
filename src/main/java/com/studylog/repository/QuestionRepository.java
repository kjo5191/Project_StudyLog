package com.studylog.repository;

import com.studylog.domain.Question;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
//	Question findById(Integer id); // CRUD 기본 메서드는 이미 JpaRepository 내부에 존재하므로 선언 필요 X 
	List<Question> findByQuestionCategoryIn(List<String> categoryNames); // 카테고리로 질문 필터링
}
