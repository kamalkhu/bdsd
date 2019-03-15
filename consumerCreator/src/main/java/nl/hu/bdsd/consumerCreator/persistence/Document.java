package nl.hu.bdsd.consumerCreator.persistence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Document {

	public String _id;
	public String title;
	public String source;
	public String text;
	public String location;
	public String date;
	public HashMap<String, Double> tfScores;
	public HashMap<String, Double> idfScores;
	public ArrayList<Keyword> tfIdfScores;
	public ArrayList<Keyword> topKeywords;
	private String[] keywords;
	private String[] parties;

	public Document(String _id, String title, String source, String text, String location, String date, String[] parties) {
		this._id = _id;
		this.title = title;
		this.source = source;
		this.text = text;
		this.location = location;
		this.date = date;
		this.setParties(parties);
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public HashMap<String, Double> getTfScores() {
		return tfScores;
	}

	public void setTfScores(HashMap<String, Double> tfScores) {
		this.tfScores = tfScores;
	}

	public HashMap<String, Double> getIdfScores() {
		return idfScores;
	}

	public void setIdfScores(HashMap<String, Double> idfScores) {
		this.idfScores = idfScores;
	}

	public void setTfIdfScores(ArrayList<Keyword> tfIdfScores) {
		this.tfIdfScores = tfIdfScores;
	}

	public ArrayList<String> getTopKeywords(int numberOfKeyWords) {
		Collections.sort(this.tfIdfScores, (w1, w2) -> w1.getScore().compareTo(w2.getScore()));
		ArrayList<String> keywords = new ArrayList<String>();
		for(Keyword keyword: this.tfIdfScores.subList(0, Integer.min(numberOfKeyWords, this.tfIdfScores.size()))) {
			keywords.add(keyword.getWord());
		}
		return keywords;
	}

	public String[] getParties() {
		return parties;
	}

	public void setParties(String[] parties) {
		this.parties = parties;
	}	
}
