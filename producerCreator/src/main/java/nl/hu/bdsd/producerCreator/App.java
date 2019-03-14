package nl.hu.bdsd.producerCreator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import nl.hu.bdsd.producerCreator.constants.IKafkaConstants;
import nl.hu.bdsd.producerCreator.producer.ProducerCreator;

public class App {
	public static void main(String[] args) throws IOException {
		int messageCount = IKafkaConstants.MESSAGE_COUNT;
		
		if(args.length > 0) {
			try {
				messageCount = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				// Use default messageCount
			}
			
		}
		runProducer(messageCount);
	}

	static void runProducer(int messageCount) throws IOException {
		String file = "complete-dump.json";
		Producer<Long, String> producer = ProducerCreator.createProducer();

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {

			for (int index = 0; index < messageCount; index++) {
				String line = br.readLine();
				final ProducerRecord<Long, String> record = new ProducerRecord<Long, String>(IKafkaConstants.TOPIC_NAME,
						(long) index, line.substring(0, line.length() - 1));

				try {
					RecordMetadata metadata = producer.send(record).get();
					System.out.println("Record sent with key " + index + " to partition " + metadata.partition()
							+ " with offset " + metadata.offset());
				}
				
				catch (ExecutionException e) {
					System.out.println("Error in sending record");
					System.out.println(e);
				}
				
				catch (InterruptedException e) {
					System.out.println("Error in sending record");
					System.out.println(e);
				}
			}
		}
	}
}