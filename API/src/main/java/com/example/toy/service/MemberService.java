package com.example.toy.service;

import com.example.toy.entity.Member;
import com.example.toy.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;

    public boolean login(String phone){
        Member member = memberRepository.findUserByPhone(phone);

        if(member == null)
            return false;

        return true;
    }

    public boolean profile(String phoneNum, String nickName, String imgFile){
        Member member = new Member();
        member.setPhone(phoneNum);
        member.setNickName(nickName);
        member.setProfile(imgFile);

        Member member1 = memberRepository.findUserByPhone(phoneNum);
        if(member1.getPhone().isEmpty()){
            memberRepository.save(member);
        }
        else {

        }

        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
