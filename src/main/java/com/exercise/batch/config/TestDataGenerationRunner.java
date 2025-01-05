package com.exercise.batch.config;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TestDataGenerationRunner implements CommandLineRunner {
    private final JobLauncher jobLauncher;
    private final Job job;

    @Override
    public void run(String... args) throws Exception {
        try {
            JobParameters parameters = new JobParametersBuilder()
                .addString("version", "1.0")
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();

            JobExecution execution = jobLauncher.run(job, parameters);

            log.info("Job Execution Status: {}", execution.getStatus());
        } catch (Exception e) {
            log.error("Job 실행 중 에러 발생", e);
        }
    }
}
