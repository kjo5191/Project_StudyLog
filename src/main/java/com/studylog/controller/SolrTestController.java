package com.studylog.controller;

import com.studylog.solr.SolrService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SolrTestController {

	private final SolrService solrService;

	@GetMapping("/solr/insert")
	public String insertTest() {
		solrService.insertDummyData();
		return "OK";
	}
	
	@GetMapping("/solr/sync-all")
	public String syncAll() {
		solrService.syncAllQuestionsToSolr();
		return "✅ 전체 Question 데이터를 Solr에 동기화했습니다.";
	}
}
