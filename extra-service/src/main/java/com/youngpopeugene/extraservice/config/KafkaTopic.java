package com.youngpopeugene.extraservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopic {
    @Bean
    public NewTopic topic2() {
        return TopicBuilder.name("topic-2").build();
    }

    @Bean
    public NewTopic topic4() {
        return TopicBuilder.name("topic-4").build();
    }

    @Bean
    public NewTopic topic6() {
        return TopicBuilder.name("topic-6").build();
    }
}