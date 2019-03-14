package nl.hu.bdsd.consumerCreator;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import nl.hu.bdsd.consumerCreator.constants.IKafkaConstants;
import nl.hu.bdsd.consumerCreator.consumer.ConsumerCreator;

public class App {
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
		    	try {
		    		title = String.format("title = \"%s\"",
		    				jsonObject.get("_sources").getAsJsonObject().get("title").getAsString());
		    	}
		    	
		    	catch (NullPointerException e){
		    		title = "no title available";
		    	}
		    	
				// print the key and title for the consumer records.
				System.out.printf("key = %d, %s\n", record.key(), title);
			}
		}
	}
}