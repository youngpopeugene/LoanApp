package com.youngpopeugene.extraservice.kafka;

import com.youngpopeugene.extraservice.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaConsumer {

    private final LoanService loanService;

    @KafkaListener(topics = "topic-1", groupId = "group")
    public void listener1(String data) {
        loanService.methodForTopic1(data);
    }

    @KafkaListener(topics = "topic-3", groupId = "group")
    public void listener3(String data) {
        loanService.methodForTopic3(data);
    }

    @KafkaListener(topics = "topic-5", groupId = "group")
    public void listener5(String data) {
        loanService.methodForTopic5(data);
    }
}