package com.example.toy.service;

import com.example.toy.entity.Member;
import com.example.toy.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public boolean login(String phone){
        Member member = memberRepository.findUserByPhone(phone);

        if(member == null)
            return false;

        return true;
    }
}
