package com.example.toy.service;

import com.example.toy.dto.InfoDto;
import com.example.toy.entity.Info;
import com.example.toy.entity.Member;
import com.example.toy.repository.InfoRepository;
import com.example.toy.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InfoService {
    private final InfoRepository infoRepository;
    private final MemberRepository memberRepository;

    public List<Info> getInfoList(int page){
        return infoRepository.findAll();
    }

    public InfoDto getInfo(String phoneNum, Long num){
        InfoDto result = new InfoDto();
        Info data = infoRepository.findInfoByNum(num);
        Member member = memberRepository.findUserByPhone(phoneNum);
        System.out.println("member.getNickName() = " + member.getNickName());

        result.setNum(data.getNum());
        result.setNickName(member.getNickName());
        result.setWriter(member.getPhone());
        result.setProfile(member.getProfile());
        result.setTitle(data.getTitle());
        result.setContent(data.getContent());
        result.setTime(data.getTime());
        result.setDate(data.getDate());
        result.setTown(data.getTown());
        result.setCommentCnt(data.getCommentCnt());
        result.setViewCnt(data.getViewCnt());

        return result;

    }

    public boolean setInfo(Info info){
        Member member = memberRepository.findUserByPhone(info.getWriter());
        info.setTown(member.getTown());

        Info chk = infoRepository.save(info);

        if(chk != null) return true;
        else return false;
    }

    public boolean deleteInfo(Long num) {
        infoRepository.delete(infoRepository.findInfoByNum(num));
        return true;
    }

    public boolean modInfo(Long num, String title, String content){
        infoRepository.modify(num, title, content);
        return true;
    }
}
