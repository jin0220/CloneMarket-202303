package com.example.toy.repository;

import com.example.toy.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {
    Member findUserByPhone(String phone);

    @Modifying // jpa에서 @Query를 사용하여 update 시 필요한 어노테이션
    @Transactional // 이 어노테이션도 필요함.
    @Query(value = "update Member set refreshToken=:refreshToken where phone=:phone") // jpql로 작성시 테이블명과 필드명은 엔티티명과 속성명으로 작성해야함.
    int updateRefreshToken(@Param("refreshToken") String refreshToken, @Param("phone") String phone);
}
