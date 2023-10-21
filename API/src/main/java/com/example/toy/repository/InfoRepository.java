package com.example.toy.repository;

import com.example.toy.entity.Info;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface InfoRepository extends JpaRepository<Info, Long> {
    Info findInfoByNum(Long num);

    @Modifying // jpa에서 @Query를 사용하여 update 시 필요한 어노테이션
    @Transactional // 이 어노테이션도 필요함.
    @Query(value = "update Info set title=:title, content=:content where num=:num")
    int modify(@Param("num") Long num, @Param("title") String title, @Param("content") String content);
}
