package nl.hu.bdsd.consumerCreator;

import java.io.IOException;
import java.time.Duration;
import java.util.*;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import nl.hu.bdsd.consumerCreator.constants.IKafkaConstants;
import nl.hu.bdsd.consumerCreator.consumer.ConsumerCreator;

import nl.hu.bdsd.consumerCreator.persistence.Document;

public class App {

	private static HashMap<Long, Document> articles = new HashMap<Long, Document>();
	private static HashMap<String, Double> idfScores = new HashMap<String, Double>();

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

				String title;
				String source;
				String text;
				String location;
				String date;
				try {
					title = jsonObject.get("_source").getAsJsonObject().get("title").getAsString();
					source = jsonObject.get("_source").getAsJsonObject().get("source").getAsString();
					text = jsonObject.get("_source").getAsJsonObject().get("text").getAsString();
					location = jsonObject.get("_source").getAsJsonObject().get("location").getAsString();
					date = jsonObject.get("_source").getAsJsonObject().get("date").getAsString();
				}

				catch (NullPointerException e){
					title = "no title available";
					break;
				}

				// print the key and title for the consumer records.
				System.out.printf("key = %d, %s\n", record.key(), title);
				//Create new document from message
				Document doc = new Document(title, source, text, location, date);
				//Add it to the list with all articles
				articles.put(record.key(), doc);

				handleArticle(doc);
			}
		}
	}

	public static void handleArticle(Document doc) {
		HashMap<String, Double> tfScores = computeTF(doc.getText());
		doc.setTfScores(tfScores);
	}

	public static HashMap<String, Double> computeTF(String articleText) {
		String[] words = articleText.split("\\s+");
		int totalWordCount = words.length;
		HashMap<String, Double> tfMap = new HashMap<String, Double>();
		for(String word: words) {
			double wordFreq = wordCounter(words, word);
			double tfScore = wordFreq / totalWordCount;
			tfMap.put(word, tfScore);
		}
		return tfMap;
	}

	public static double wordCounter(String[]allWords, String targetWord) {
		double count = 0.0;
		for(String word: allWords) {
			if(word.equals(targetWord)) {
				count += 1;
			}
		}
		return count;
	}

	public static HashMap<String, Double> computeIDF(HashMap<String, String> articlesText) {
		HashMap<String, Double> idfScores = new HashMap<String, Double>();
		HashMap<String, Integer> wordCounter = new HashMap<String, Integer>();
		for(Map.Entry<String, String> text: articlesText.entrySet()) {
			String[] wordsArray = text.getValue().split("\\s+");
			Set<String> words = new HashSet<String>();
			for(String splitWord: wordsArray) {
				words.add(splitWord);
			}
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

	public static HashMap<String, Double> computeTFIDF(HashMap<String, Double> tfScores, HashMap<String, Double> idfScores) {
		HashMap<String, Double> tfIdfScores = new HashMap<String, Double>();
		for(Map.Entry<String, Double> tfScore: tfScores.entrySet()) {
			tfIdfScores.put(tfScore.getKey(), tfScore.getValue() * idfScores.get(tfScore.getKey()));
		}
		return tfIdfScores;
	}
}