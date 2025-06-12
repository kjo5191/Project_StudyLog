package com.studylog.util;

import java.util.regex.*;
import java.util.*;

public class FeedbackParser {

	public static Map<String, Integer> parseScores(String feedback) {
		Map<String, Integer> scores = new HashMap<>();

		Pattern pattern = Pattern.compile("(충실도|논리성|문법)[:：]\\s*(\\d{1,2})\\s*/\\s*10");
		Matcher matcher = pattern.matcher(feedback);

		while (matcher.find()) {
			String type = matcher.group(1);  // 항목명
			int score = Integer.parseInt(matcher.group(2));
			scores.put(type, score);
		}

		return scores;
	}
	
	public static String parseFeedbackMessage(String feedback) {
		Pattern pattern = Pattern.compile("피드백[:：]\\s*(.+)", Pattern.DOTALL);
		Matcher matcher = pattern.matcher(feedback);

		if (matcher.find()) {
			return matcher.group(1).trim();
		}

		return "피드백 없음";
	}	
	
}
