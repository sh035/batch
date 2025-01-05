package com.exercise.batch.config;

import com.exercise.batch.member.Member;
import com.exercise.batch.member.MemberRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.SimpleJob;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Bean
    public Job generateJob(JobRepository jobRepository, Step generateMemberStep) {
        return new JobBuilder("generateMemberStep", jobRepository)
            .start(generateMemberStep)
            .build();
    }

    @Bean
    public Step generateMemberStep(JobRepository jobRepository,
        PlatformTransactionManager transactionManager,
        MemberRepository memberRepository) {
        return new StepBuilder("generateMemberStep", jobRepository)
            .<Member, Member>chunk(100, transactionManager)
            .reader(new MemberGeneratingReader(1000))
            .writer(new MemberWriter(memberRepository))
            .build();
    }

    @Bean
    public JobLauncher jobLauncher(JobRepository jobRepository) throws Exception {
        TaskExecutorJobLauncher jobLauncher = new TaskExecutorJobLauncher();
        jobLauncher.setJobRepository(jobRepository);
        // 비동기로 실행하기 위한 TaskExecutor 설정
        SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
        jobLauncher.setTaskExecutor(taskExecutor);
        jobLauncher.afterPropertiesSet();
        return jobLauncher;
    }



}
