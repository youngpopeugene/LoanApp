package com.youngpopeugene.mainservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopic {
    @Bean
    public NewTopic topic1() {
        return TopicBuilder.name("topic-1").build();
    }

    @Bean
    public NewTopic topic3() {
        return TopicBuilder.name("topic-3").build();
    }

    @Bean
    public NewTopic topic5() {
        return TopicBuilder.name("topic-5").build();
    }
}