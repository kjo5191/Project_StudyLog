package com.studylog.solr;

import lombok.*;

import java.util.List;

import org.apache.solr.client.solrj.beans.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionSolrDocument {

	@Field
	private String id;

	@Field
	private List<String> category;
	
	@Field
	private String content;

	@Field
	private String modelAnswer;

	@Field
	private String myAnswer;

}
