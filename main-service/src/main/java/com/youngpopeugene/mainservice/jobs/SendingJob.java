package com.youngpopeugene.mainservice.jobs;

import com.youngpopeugene.mainservice.kafka.KafkaProducer;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SendingJob implements Job {

    @Autowired
    private KafkaProducer kafkaProducer;

    @Override
    @Transactional(transactionManager = "transactionManager")
    public void execute(JobExecutionContext jobExecutionContext) {
        String loanId = jobExecutionContext.getMergedJobDataMap()
                .getString("loanId");
        kafkaProducer.send("topic-5", loanId);
    }
}