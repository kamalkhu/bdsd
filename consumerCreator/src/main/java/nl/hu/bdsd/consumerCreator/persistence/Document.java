package nl.hu.bdsd.consumerCreator.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.mongodb.DB;
import com.mongodb.MongoClient;

public class Document {
	public String _id;
	public String title;
	public String source;
	public String text;
	public String location;
	public String date;
	public HashMap<String, Double> tfScores;
	public HashMap<String, Double> idfScores;
	public HashMap<String, Double> tfIdfScores;

	public Document(String _id, String title, String source, String text, String location, String date) {
		this._id = _id;
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

	public String getText(){ return this.text; }
}
