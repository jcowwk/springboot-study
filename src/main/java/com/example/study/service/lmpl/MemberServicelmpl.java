package com.example.study.service.lmpl;

import com.example.study.domain.Member;
import com.example.study.dto.MemberFormDTO;
import com.example.study.repository.MemberRepository;
import com.example.study.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServicelmpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public Long join(MemberFormDTO memberFormDTO) {
        Member member = Member.builder()
                .email(memberFormDTO.getEmail())
                .username(memberFormDTO.getUsername())
                .password(memberFormDTO.getPassword())
                .build();

        return memberRepository.save(member).getId();
    }
}
