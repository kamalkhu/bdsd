package nl.hu.bdsd.consumerCreator;

import java.io.IOException;
import java.time.Duration;
import java.util.*;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import nl.hu.bdsd.consumerCreator.constants.IKafkaConstants;
import nl.hu.bdsd.consumerCreator.consumer.ConsumerCreator;

import nl.hu.bdsd.consumerCreator.persistence.Document;
import nl.hu.bdsd.consumerCreator.tfidf.TFIDF;

public class App {

	private static HashMap<Long, Document> articles = new HashMap<Long, Document>();

	public static void main(String[] args) throws IOException {
		runConsumer();
	}

	static void runConsumer() throws IOException {
		Consumer<Long, String> consumer = ConsumerCreator.createConsumer();
		consumer.subscribe(Arrays.asList(IKafkaConstants.TOPIC_NAME));
		JsonParser parser = new JsonParser();

		while (true) {
			ConsumerRecords<Long, String> records = consumer.poll(Duration.ofMillis((long) 100));

			for (ConsumerRecord<Long, String> record : records) {
				JsonObject jsonObject = parser.parse(record.value()).getAsJsonObject();
				
				JsonObject _source;
				try {
					_source = jsonObject.get("_source").getAsJsonObject();
				}

				catch (NullPointerException e) {
					System.out.println(String.format("Skipping message with key %s: no _source field found", record.key()));
					continue;
				}
				
				String _id;
				try {
					_id = jsonObject.get("_id").getAsString();
				}

				catch (NullPointerException e) {
					System.out.println(String.format("Skipping message with key %s: no _id field found", record.key()));
					continue;
				}
				
				JsonElement titleElement = _source.get("title");
				JsonElement sourceElement = _source.get("source");
				JsonElement textElement = _source.get("description");
				JsonElement locationElement = _source.get("location");
				JsonElement dateElement = _source.get("date");
				
				String title;
				String source;
				String text;
				String location;
				String date;
				
				if (!titleElement.equals(null)) { title = titleElement.getAsJsonObject().get("title").getAsString(); }
				else { title = null; }
				
				if (!sourceElement.equals(null)) { source = sourceElement.getAsJsonObject().get("source").getAsString(); }
				else { source = null; }
				
				if (!textElement.equals(null)) { text = textElement.getAsJsonObject().get("text").getAsString(); }
				else { text = null; }
				
				if (!locationElement.equals(null)) { location = locationElement.getAsJsonObject().get("location").getAsString(); }
				else { location = null; }
				
				if (!dateElement.equals(null)) { date = dateElement.getAsJsonObject().get("date").getAsString(); }
				else { date = null; }
				
				//Create new document from message
				Document doc = new Document(_id, title, source, text, location, date);
				//Add it to the list with all articles
				articles.put(record.key(), doc);
				//Get the TF scores for the document
				doc.setTfScores(TFIDF.computeTF(doc.getText()));

				//If there is a batch of 1000 Articles ready, calculate the IDF and TF-IDF and then insert them into Mongo DB
				if(articles.size() >= 1000) {
					HashMap<String, Double> idfScores = TFIDF.computeIDF(articles);
					System.out.println("Calculating IDF anf TF-IDF scores for batch...");
					
					for (Document article: articles.values()){
						article.setTfIdfScores(TFIDF.computeTFIDF(article.getTfScores(), idfScores));
					}
					
					System.out.println("Done!");
					//Empty the articles Map and wait for another batch
					articles.clear();
				}
			}
		}
	}
}