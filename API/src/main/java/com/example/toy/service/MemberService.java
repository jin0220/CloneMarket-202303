package com.example.toy.service;

import com.example.toy.entity.Member;
import com.example.toy.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
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

    public Member getMemberInfo(String phoneNum) {
        return memberRepository.findUserByPhone(phoneNum);
    }

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        Member member = memberRepository.findUserByPhone(phone);

        if(member == null)
            new UsernameNotFoundException("사용자를 찾을 수 없습니다.");

        return User.builder()
                .username(member.getPhone())
                .password("")
                .roles("")
                .build();
    }
}
