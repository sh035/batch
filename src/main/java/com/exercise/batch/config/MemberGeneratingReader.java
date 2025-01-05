package com.exercise.batch.config;

import com.exercise.batch.member.Member;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemReader;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
public class MemberGeneratingReader implements ItemReader<Member> {

    private final String apiUrl;
    private final RestTemplate restTemplate;
    private final int pageSize;

    private final int totalCount;
    private int current = 0;

    @Override
    public Member read() {
        if (current >= totalCount) {
            return null;
        }

        current++;
        return Member.builder()
            .name("Test User " + current)
            .email("user" + current + "@gmail.com")
            .createdAt(LocalDateTime.now())
            .build();
    }
}
