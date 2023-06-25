package com.example.toy.interceptor;

import com.example.toy.configuration.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class JwtTokenInterceptor implements HandlerInterceptor {
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 컨트롤러 실행 전 수행한다. 반환 값이 true일 경우 컨트롤러로 진입하고 false일 경우 진입하지 않는다.
     * @param handler 진입하려는 컨트롤러의 클래스 객체가 담겨있다.
     * */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("JwtToken 호출");
        String accessToken = request.getHeader("X-AUTH-TOKEN");
        System.out.println("AccessToken:" + accessToken);
//        String refreshToken = request.getHeader("REFRESH_TOKEN");
//        System.out.println("RefreshToken:" + refreshToken);

        // accessToken 유효하면 true
        if(accessToken != null && jwtTokenProvider.validationToken(accessToken)){
            System.out.println("valid Access Token.");
            return true;
        }
        else if(accessToken != null){
            System.out.println("Invalid Access Token.");
        }

        response.setStatus(401);
        response.setHeader("ACCESS_TOKEN", accessToken);
//        response.setHeader("REFRESH_TOKEN", refreshToken);
        response.setHeader("msg", "Invalid Access Token.");
        System.out.println("Invalid Access Token.");
        return false;
    }
}
