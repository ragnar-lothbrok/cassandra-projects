package com.edureka.kafka.threads;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edureka.cassandra.java.client.repository.LogStreamRepository;
import com.edureka.kafka.dto.LogStream;
import com.edureka.storm.log.parser.CommonLogParser;

public class LogStreamConsumerThread extends Thread {

	private static final Logger logger = LoggerFactory.getLogger(LogStreamConsumerThread.class);

	private KafkaConsumer<String, String> productKafkaConsumer;

	private LogStreamRepository logStreamRepository;

	public LogStreamConsumerThread(final KafkaConsumer<String, String> kafkaConsumer,
			final LogStreamRepository logStreamRepository) {
		super();
		this.productKafkaConsumer = kafkaConsumer;
		this.logStreamRepository = logStreamRepository;
	}

	@Override
	public void run() {
		logger.info("Consumer thread is started.");
		try {
			while (true) {
				try {
					ConsumerRecords<String, String> records = this.productKafkaConsumer.poll(1000);
					for (ConsumerRecord<String, String> record : records) {
						logger.info("Consuming from topic = {}, partition = {}, offset = {}, key = {}, value = {}",
								record.topic(), record.partition(), record.offset(), record.key(), record.value());
						String log = record.value();
						LogStream logStream = CommonLogParser.parse(log);
						logger.info("Consumed log = {} ", logStream);
						logStreamRepository.insert(logStream);
						logStreamRepository.updateRequestCount(logStream);
						logStreamRepository.updateRequestStatusCount(logStream);
						logStreamRepository.updateRequestPageUrlStatusCount(logStream);
					}
					this.productKafkaConsumer.commitSync();
				} catch (Exception e) {
					logger.error("Exception occured while consuming event = {} ", e);
				}
			}
		} finally {
			this.productKafkaConsumer.close();
		}
	}

}
