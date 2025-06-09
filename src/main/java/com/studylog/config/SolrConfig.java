package com.studylog.config;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SolrConfig {

	@Bean
	public SolrClient solrClient() {
		String solrUrl = "http://localhost:8983/solr/question";
		return new HttpSolrClient.Builder(solrUrl).build();
	}
}
