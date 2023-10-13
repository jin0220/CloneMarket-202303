package com.example.toy.repository;

import com.example.toy.entity.Info;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InfoRepository extends JpaRepository<Info, Long> {
    Info findInfoByNum(Long num);
}
