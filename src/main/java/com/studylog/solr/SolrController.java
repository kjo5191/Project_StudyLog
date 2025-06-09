package com.studylog.solr;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/solr")
@RequiredArgsConstructor
public class SolrController {

	private final SolrService solrService;

	@GetMapping("/search")
	public List<QuestionSolrDocument> search(@RequestParam("q") String keyword) {
		log.info("🔍 Solr 검색 요청: {}", keyword);
		return solrService.searchByKeyword(keyword);
	}
}
