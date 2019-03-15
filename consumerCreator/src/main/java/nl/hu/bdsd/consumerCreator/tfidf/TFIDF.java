package nl.hu.bdsd.consumerCreator.tfidf;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;

import nl.hu.bdsd.consumerCreator.persistence.Document;
import nl.hu.bdsd.consumerCreator.persistence.Keyword;

public class TFIDF {
	
	public static String[] getWords(String document) {
		String cleanDocument = document.toLowerCase();
		// Remove html tags
		cleanDocument = Jsoup.parse(cleanDocument).text();
		// Remove special characters
		cleanDocument = cleanDocument.replaceAll("(\\\\.)|[^a-zA-Z]", " ");
		cleanDocument = cleanDocument.trim();
		return cleanDocument.split(" +");
	}
	
	public static HashMap<String, Double> computeTF(String[] words) {
		int totalWordCount = words.length;
		HashMap<String, Double> tfMap = new HashMap<String, Double>();
		for(String word: words) {
			double wordFreq = wordCounter(words, word);
			double tfScore = wordFreq / totalWordCount;
			tfMap.put(word, tfScore);
		}
		return tfMap;
	}

	public static double wordCounter(String[] allWords, String targetWord) {
		double count = 0.0;
		for(String word: allWords) {
			if(word.equals(targetWord)) {
				count += 1;
			}
		}
		return count;
	}

	public static HashMap<String, Double> computeIDF(HashMap<Long, Document> articles) {
		HashMap<String, Double> idfScores = new HashMap<String, Double>();

		HashMap<String, Integer> wordCounter = new HashMap<String, Integer>();
		for(Document article: articles.values()) {
			Set<String> words = new HashSet<String>(Arrays.asList(getWords(article.getText())));
			for(String word: words) {
				if(wordCounter.containsKey(word)) {
					wordCounter.put(word, wordCounter.get(word) + 1);
				}
				else {
					wordCounter.put(word, 1);
				}
			}
		}

		for(Map.Entry<String, Integer> word: wordCounter.entrySet()) {
			double score = Math.log10(articles.size() /  (double) word.getValue());
			idfScores.put(word.getKey(), score);
		}
		return idfScores;
	}

	public static ArrayList<Keyword> computeTFIDF(HashMap<String, Double> tfScores, HashMap<String, Double> idfScores) {
		ArrayList<Keyword> tfIdfScores = new ArrayList<Keyword>();
		for(Map.Entry<String, Double> tfScore: tfScores.entrySet()) {
			Keyword word = new Keyword(tfScore.getKey(), tfScore.getValue() * idfScores.get(tfScore.getKey()));
			tfIdfScores.add(word);
		}
		return tfIdfScores;
	}
}
