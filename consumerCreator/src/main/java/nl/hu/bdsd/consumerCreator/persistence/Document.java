package nl.hu.bdsd.consumerCreator.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.DB;
import com.mongodb.MongoClient;

public class Document {
	public String title;
	public String source;
	public String text;
	public String location;
	public String date;
	public HashMap<String, Double> tfScores;
	public HashMap<String, Double> idfScores;
	public HashMap<String, Double> tfIdfScores;
	public String[] keywords;

	public Document(String title, String source, String text, String location, String date) {
		this.title = title;
		this.source = source;
		this.text = text;
		this.location = location;
		this.date = date;
	}

	public HashMap<String, Double> getIdfScores() {
		return idfScores;
	}

	public HashMap<String, Double> getTfScores() {
		return this.tfScores;
	}

	public HashMap<String, Double> getTfIdfScores() {
		return this.tfIdfScores;
	}

	public void setTfScores(HashMap<String, Double> tfScores) {
		this.tfScores = tfScores;
	}

	public void setIdfScores(HashMap<String, Double> idfScores) {
		this.idfScores = idfScores;
	}

	public void setTfIdfScores(HashMap<String, Double> tfIdfScores) {
		this.tfIdfScores = tfIdfScores;
	}

	public void setKeywords(String[] keywords){ this.keywords = keywords; }

	public String getText(){ return this.text; }

	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		JsonObject keywords = new JsonObject();
		for(String keyword: this.keywords) {
			keywords.addProperty("keyword", keyword);
		}
		json.addProperty("title", this.title);
		json.addProperty("source", this.source);
		json.addProperty("text", this.text);
		json.addProperty("location", this.location);
		json.addProperty("date", this.date);
		json.add("keywords", keywords);
		return json;
	}
}
