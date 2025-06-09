package com.studylog.solr;

import java.util.Collections;
import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.stereotype.Service;

import com.studylog.domain.Question;
import com.studylog.repository.QuestionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SolrService {

	private final QuestionRepository questionRepository;
	private final SolrClient solrClient;
	
	public List<QuestionSolrDocument> searchByKeyword(String keyword) {
		try {
			SolrQuery query = new SolrQuery();
//			query.setQuery(String.format("questionText:\"%s\" OR modelAnswer:\"%s\"", keyword, keyword));
			query.setQuery(keyword); // 모든 필드에서 keyword를 검색
			query.set("defType", "edismax"); // 쿼리 구문 확장
			query.set("qf", "questionCategory questionText modelAnswer"); // 검색 대상 필드

			QueryResponse response = solrClient.query(query);
			return response.getBeans(QuestionSolrDocument.class);
		} catch (Exception e) {
			log.error("❌ Solr 검색 실패", e);
			return Collections.emptyList();
		}
	}

	
	public void syncAllQuestionsToSolr() {
		try {
			List<Question> allQuestions = questionRepository.findAll();

			for (Question q : allQuestions) {
				QuestionSolrDocument doc = convertToSolrDoc(q);
				solrClient.addBean(doc);
			}

			solrClient.commit();
			log.info("✅ 전체 질문 Solr로 동기화 완료");

		} catch (Exception e) {
			log.error("❌ Solr 동기화 실패", e);
		}
	}

	
	private QuestionSolrDocument convertToSolrDoc(Question entity) {
		QuestionSolrDocument doc = new QuestionSolrDocument();
		doc.setId(entity.getId().toString());
		doc.setQuestionCategory(entity.getQuestionCategory());
		doc.setQuestionText(entity.getQuestionText());
		doc.setModelAnswer(entity.getModelAnswer());
		doc.setMyAnswer(entity.getMyAnswer());
		
		return doc;
	}

	
	public void insertDummyData() {
		try {
			QuestionSolrDocument doc = new QuestionSolrDocument();
			doc.setId("java_test");
			doc.setQuestionText("Java is a powerful language used in Spring.");
			doc.setModelAnswer("This is a Java-related answer.");
			solrClient.addBean(doc);
			solrClient.commit();

			log.info("✅ Solr Insert 성공: {}");
		} catch (Exception e) {
			log.error("❌ Solr Insert 실패", e);
		}
	}
}
