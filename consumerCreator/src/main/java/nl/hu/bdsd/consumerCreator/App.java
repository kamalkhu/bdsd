package nl.hu.bdsd.consumerCreator;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import nl.hu.bdsd.consumerCreator.constants.IKafkaConstants;
import nl.hu.bdsd.consumerCreator.consumer.ConsumerCreator;
import nl.hu.bdsd.consumerCreator.persistence.ArticleDAO;
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
					System.out.println(
							String.format("Skipping message with key %s: no _source field found", record.key()));
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
				JsonElement partiesElement = _source.get("parties");

				String title;
				String source;
				String text;
				String location;
				String date;
				String[] parties;

				if (titleElement != null) {
					title = titleElement.getAsString();
				} else {
					title = null;
				}

				if (sourceElement != null) {
					source = sourceElement.getAsString();
				} else {
					source = null;
				}

				if (textElement != null) {
					text = textElement.getAsString();
				} else {
					text = null;
				}

				if (locationElement != null) {
					location = locationElement.getAsString();
				} else {
					location = null;
				}

				if (dateElement != null) {
					date = dateElement.getAsString();
				} else {
					date = null;
				}
				
				if (partiesElement != null) {
					JsonArray partiesArray = partiesElement.getAsJsonArray();
					List<String> partiesList = new ArrayList<String>();
					for(int i = 0; i < partiesArray.size(); i++){
						partiesList.add(partiesArray.get(i).getAsString());
					}
					parties = partiesList.toArray(new String[0]);
					
				} else {
					parties = null;
				}

				// Create new document from message
				Document doc = new Document(_id, title, source, text, location, date, parties);
				// Get the TF scores for the document
				doc.setTfScores(TFIDF.computeTF(TFIDF.getWords(doc.getText())));
				// Add it to the list with all articles
				articles.put(record.key(), doc);

				// If there is a batch of 1000 Articles ready, calculate the IDF and TF-IDF and
				// then insert them into MongoDB
				if (articles.size() >= 1000) {
					ArticleDAO aDao = new ArticleDAO();
					HashMap<String, Double> idfScores = TFIDF.computeIDF(articles);
					System.out.println("Calculating IDF anf TF-IDF scores for batch...");

					for (Document article : articles.values()) {
						article.setTfIdfScores(TFIDF.computeTFIDF(article.getTfScores(), idfScores));
						aDao.insertArticle(article);
					}

					System.out.println("Done!");
					// Empty the articles Map and wait for another batch
					articles.clear();
				}
			}
		}
	}
}