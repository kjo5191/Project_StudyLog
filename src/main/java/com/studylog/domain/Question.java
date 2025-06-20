package com.studylog.domain;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ElementCollection
	private List<String> questionCategory;
	
	@Column(columnDefinition = "TEXT")
	private String questionText;

	@Column(columnDefinition = "TEXT")
	private String modelAnswer;
	
	@Column(columnDefinition = "TEXT")
	private String myAnswer;

	@Column(columnDefinition = "TEXT")
	private String aiFeedback;

	private Integer scoreLogic;
	private Integer scoreContent;
	private Integer scoreGrammar;
}
