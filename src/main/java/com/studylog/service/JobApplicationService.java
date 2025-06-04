package com.studylog.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.studylog.domain.JobApplication;
import com.studylog.repository.JobApplicationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobApplicationService {

	private final JobApplicationRepository jobApplicationRepository;

	public List<JobApplication> findAll() {
		return jobApplicationRepository.findAll();
	}

	public JobApplication findById(Integer id) {
		return jobApplicationRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException(id + " : 해당 ID의 일정이 없습니다."));
	}

	public JobApplication save(JobApplication jobApplication) {
		return jobApplicationRepository.save(jobApplication);
	}

	public void deleteById(Integer id) {
		jobApplicationRepository.deleteById(id);
	}
}
