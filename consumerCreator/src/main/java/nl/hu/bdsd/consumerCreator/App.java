package nl.hu.bdsd.consumerCreator;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import nl.hu.bdsd.consumerCreator.constants.IKafkaConstants;
import nl.hu.bdsd.consumerCreator.consumer.ConsumerCreator;

public class App {
	public static void main(String[] args) throws IOException {
		runConsumer();
	}

	static void runConsumer() throws IOException {
		Consumer<Long, String> consumer = ConsumerCreator.createConsumer();
		consumer.subscribe(Arrays.asList(IKafkaConstants.TOPIC_NAME));
		while (true) {
	         ConsumerRecords<Long, String> records = consumer.poll(Duration.ofMillis((long) 100));
	         for (ConsumerRecord<Long, String> record : records)
	         
	         // print the offset,key and value for the consumer records.
	         System.out.printf("offset = %d, key = %s, value = %s\n", 
	            record.offset(), record.key(), record.value());
	      }
	}
}