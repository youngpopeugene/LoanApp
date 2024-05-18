package com.youngpopeugene.mainservice.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, String> userKafkaTemplate;

    public void send(String topicName, String message) {
        userKafkaTemplate.send(topicName, message);
    }
}