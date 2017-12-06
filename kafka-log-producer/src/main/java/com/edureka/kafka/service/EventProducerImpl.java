package com.edureka.kafka.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.edureka.kafka.api.EventProducerApi;
import com.edureka.kafka.config.props.KafkaProperties;
import com.edureka.kafka.producer.EventCallBack;
import com.edureka.kafka.utility.FileUtility;

@Service
public class EventProducerImpl implements EventProducerApi {

	@Autowired
	private KafkaProperties kafkaProperties;

	private static final Logger LOGGER = LoggerFactory.getLogger(EventProducerImpl.class);

	@Autowired
	@Qualifier("eventProducer")
	Producer<String, String> eventProducer;

	@PostConstruct
	public void init() {
		List<String> logs = FileUtility.readFile(kafkaProperties.getFilePath());
		for (String product : logs) {
			dispatch(product);
			Utils.sleep(1000);
		}
	}

	public void dispatch(String product) {
		LOGGER.info("Event dispatch started = {} ", product);
		ProducerRecord<String, String> data = new ProducerRecord<String, String>(kafkaProperties.getProducttopic(),
				product);
		eventProducer.send(data, new EventCallBack());
	}

}
