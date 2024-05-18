package com.youngpopeugene.extraservice.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, String> userKafkaTemplate;

    public void send(String topicName, String message) {
        userKafkaTemplate.send(topicName, message);
    }
}