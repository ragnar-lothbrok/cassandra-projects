package com.edureka.kafka.threads;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edureka.cassandra.java.client.repository.ProductRepository;
import com.edureka.kafka.dto.Product;
import com.edureka.kafka.performance.MonitoringCache;
import com.edureka.kafka.performance.MonitoringCache.Caches;

public class ProductKafkaConsumerThread extends Thread {

	private static final Logger logger = LoggerFactory.getLogger(ProductKafkaConsumerThread.class);

	private KafkaConsumer<String, Product> productKafkaConsumer;

	private ProductRepository productRepository;

	public ProductKafkaConsumerThread(final KafkaConsumer<String, Product> kafkaConsumer,
			final ProductRepository productRepository) {
		super();
		this.productKafkaConsumer = kafkaConsumer;
		this.productRepository = productRepository;
	}

	@Override
	public void run() {
		logger.info("Consumer thread is started.");
		try {
			while (true) {
				try {
					ConsumerRecords<String, Product> records = this.productKafkaConsumer.poll(1000);
					for (ConsumerRecord<String, Product> record : records) {
						logger.info("Consuming from topic = {}, partition = {}, offset = {}, key = {}, value = {}",
								record.topic(), record.partition(), record.offset(), record.key(), record.value());
						MonitoringCache.updateStats(Caches.PRODUCT_EVENT, 1);

						Product product = record.value();
						
						productRepository.insertProduct(product);
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
