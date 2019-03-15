package nl.hu.bdsd.consumerCreator.persistence;

import java.util.HashMap;

public class Document {
	private String _id;
	private String title;
	private String source;
	private String text;
	private String location;
	private String date;
	private HashMap<String, Double> tfScores;
	private HashMap<String, Double> idfScores;
	private HashMap<String, Double> tfIdfScores;
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

	public HashMap<String, Double> getTfIdfScores() {
		return tfIdfScores;
	}

	public void setTfIdfScores(HashMap<String, Double> tfIdfScores) {
		this.tfIdfScores = tfIdfScores;
	}

	public String[] getKeywords() {
		return keywords;
	}

	public void setKeywords(String[] keywords) {
		this.keywords = keywords;
	}

	public String[] getParties() {
		return parties;
	}

	public void setParties(String[] parties) {
		this.parties = parties;
	}	
}
