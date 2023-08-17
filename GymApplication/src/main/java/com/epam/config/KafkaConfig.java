package com.epam.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
	
	@Bean
	public NewTopic notificationTopic() {
		return TopicBuilder.name("topic-notification").build();
	}
	
	@Bean
	public NewTopic reportTopic() {
		return TopicBuilder.name("topic-report-data").build();
	}

}
