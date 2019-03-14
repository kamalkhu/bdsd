package nl.hu.bdsd.consumerCreator.tfidf;

import org.jsoup.Jsoup;

public class TFIDF {

	public String[] getWords(String document) {
		String cleanDocument = document.toLowerCase();
		// Remove html tags
		cleanDocument = Jsoup.parse(cleanDocument).text();
		// Remove special characters
		cleanDocument = cleanDocument.replaceAll("(\\\\.)|[^a-zA-Z]", " ");
		cleanDocument = cleanDocument.trim();
		return cleanDocument.split(" +");
	}
}