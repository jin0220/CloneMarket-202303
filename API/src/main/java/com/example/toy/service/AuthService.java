package com.example.toy.service;

import com.example.toy.configuration.JwtTokenProvider;
import com.example.toy.entity.Member;
import com.example.toy.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    public final JwtTokenProvider jwtTokenProvider;
    public final MemberRepository memberRepository;

    public Map<String, String> issueAccessToken(HttpServletRequest request) {
        String refreshToken = jwtTokenProvider.resolveRefreshToken(request);
        String userId;
        String accessToken = null;

        if(jwtTokenProvider.validationToken(refreshToken)){
            log.info("유효한 토큰");

            userId = jwtTokenProvider.getUserPk(refreshToken);

            Member member = memberRepository.findUserByPhone(userId);
            String tokenFromDB = member.getRefreshToken();
            if (refreshToken.equals(tokenFromDB)) {
                log.info("Access Token 재발급");
                accessToken = jwtTokenProvider.createToken(userId);
            }
            else {
                log.info("유효하지 않은 토큰");
            }
        }
        else {
            return null;
        }

        Map<String, String> map = new HashMap<>();
        map.put("accessToken", accessToken);
        map.put("refreshToken", refreshToken);
        map.put("userId", userId);

        return map; // 발급할때 리프레시 토큰도 다시 재발급할 수 있도록 추후 수정
    }

    public Member getUserId(String token) {
        Authentication authentication = jwtTokenProvider.getAuthentication(token);

        Member member = memberRepository.findUserByPhone(authentication.getName());

        return member;
    }

    public boolean updateRefreshToken(String refreshToken, String phoneNum) {
        int check = memberRepository.updateRefreshToken(refreshToken, phoneNum);
        if(check == 1)
            return true;
        else
            return false;
    }
}
