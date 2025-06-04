package com.studylog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.studylog.domain.JobApplication;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Integer>{

}
