package com.example.toy.service;

import com.example.toy.entity.Info;
import com.example.toy.repository.InfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InfoService {
    private final InfoRepository infoRepository;

    public List<Info> getInfoList(int page){
        return infoRepository.findAll();
    }

    public Info getInfo(Long num){
        return infoRepository.findInfoByNum(num);
    }

    public boolean setInfo(Info info){
        Info chk = infoRepository.save(info);

        if(chk != null) return true;
        else return false;
    }
}
