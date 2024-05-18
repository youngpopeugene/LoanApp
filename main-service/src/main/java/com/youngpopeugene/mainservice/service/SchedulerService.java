package com.youngpopeugene.mainservice.service;

import com.youngpopeugene.mainservice.exception.JobException;
import com.youngpopeugene.mainservice.jobs.SendingJob;
import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.springframework.stereotype.Service;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

@Service
@RequiredArgsConstructor
public class SchedulerService {

    private final Scheduler scheduler;

    public JobKey startSendingJob(String loanId, int duration) throws SchedulerException {
        JobDetail job = newJob(SendingJob.class)
                .usingJobData("loanId", loanId)
                .build();
        Trigger trigger = newTrigger()
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(20)
                        .withRepeatCount(duration - 1))
                .startAt(DateBuilder.futureDate(20, DateBuilder.IntervalUnit.SECOND))
                .build();
        scheduler.scheduleJob(job, trigger);
        return job.getKey();
    }

    public void deleteJob(String jobKeyName) {
        try {
            JobKey jobKey = new JobKey(jobKeyName);
            scheduler.deleteJob(jobKey);
        } catch (SchedulerException e) {
            throw new JobException("Problems with deleting job");
        }
    }
}