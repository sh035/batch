package com.exercise.batch.config;

import com.exercise.batch.member.Member;
import com.exercise.batch.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

@RequiredArgsConstructor
public class MemberWriter implements ItemWriter<Member> {

    private final MemberRepository memberRepository;

    @Override
    public void write(Chunk<? extends Member> chunk) throws Exception {
        memberRepository.saveAll(chunk.getItems());
    }
}
