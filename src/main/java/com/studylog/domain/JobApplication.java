package com.studylog.domain;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class JobApplication {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String company;	// 회사명
	private String industry;	// 직군
	private String position;	// 직무명
	private String status;	// 상태 (지원 전, 서류 제출, 면접 합격 등)
	
	private LocalDate applyDate;
	private LocalDate deadline;
	private LocalDate interview1;
	private LocalDate interview2;
	private LocalDate interview3;
	private LocalDate interview4;

	private String notes; // 비고
}
