package org.shared.common.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

	@Bean
	public NewTopic inventoryCheckTopic() {
		return TopicBuilder.name("inventory-check")
				.partitions(3)
				.replicas(1)
				.build();
	}
	
	@Bean
	public NewTopic inventoryResponseTopic() {
		return TopicBuilder.name("inventory-response")
				.partitions(3)
				.replicas(1)
				.build();
	}
	
	@Bean
	public NewTopic orderCreatedTopic() {
		return TopicBuilder.name("order-topic")
				.partitions(3)
				.replicas(1)
				.build();
	}
}