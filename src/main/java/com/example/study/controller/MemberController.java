package com.example.study.controller;

import com.example.study.dto.MemberFormDTO;
import com.example.study.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/")
    public String Home() {
        return "main";
    }

    @GetMapping("/members/new")
    public String createMemberForm() {
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String createMember(MemberFormDTO memberFormDTO) {
        Long memberId = memberService.join(memberFormDTO);
        return "main";
    }

    @GetMapping("/members")
    public String showMember() {
        return "members/showMember";
    }
}