package com.todolist.util.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todolist.domain.user.model.User;
import com.todolist.domain.user.repository.UserRepository;
import com.todolist.exception.ErrorResponse;
import com.todolist.util.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
public class AuthFilter implements Filter {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public AuthFilter(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String url = httpServletRequest.getRequestURI();

        if (StringUtils.hasText(url) &&
                (url.startsWith("/api/user") || url.startsWith("/swagger") || url.startsWith("/v3/api-docs") || url.startsWith("/swagger-ui") || url.startsWith("/api/todos/get")
                || url.startsWith("/api/todos/all")) // 인증하지 않아도 되는 조건은 다음으로
                // 리스트 로 추후?
        ) {
            log.info("인증처리를 하지 않는 URL{}", url);
            // 회원가입, 로그인 관련 API 는 인증 필요없이 요청 진행
            filterChain.doFilter(servletRequest, servletResponse); // 다음 Filter 로 이동
        } else {
            // 나머지 API 요청은 인증 처리 진행
            // 토큰 확인
            String tokenValue = jwtUtil.getTokenFromRequest(httpServletRequest);
            if (StringUtils.hasText(tokenValue)) { // 토큰이 존재하면 검증 시작
                // JWT 토큰 substring
                String token = jwtUtil.substringToken(tokenValue);

                // 토큰 검증
                if (!jwtUtil.validateToken(token)) {
                    String message = "토큰검증에 실패했습니다";
                    exceptionHandler((HttpServletResponse) servletResponse, message, HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                    //doFilter 를 빠져나감
                }
                // 토큰에서 사용자 정보 가져오기
                Claims info = jwtUtil.getUserInfoFromToken(token);

                Optional<User> user = userRepository.findByUsername(info.getSubject());
                if(user.isEmpty()){
                    exceptionHandler((HttpServletResponse) servletResponse, "유저가 정보가 없습니다",HttpServletResponse.SC_NOT_FOUND);
                    return;
                    //doFilter 를 빠져나감
                }
                servletRequest.setAttribute("user", user);
                filterChain.doFilter(servletRequest, servletResponse); // 다음 Filter 로 이동
            } else {
                exceptionHandler((HttpServletResponse) servletResponse, "토큰이 없습니다",HttpServletResponse.SC_UNAUTHORIZED);
                //여기서 끝내버려야함 다음 필터 ㄴㄴ
            }
        }



    }


    private void exceptionHandler(HttpServletResponse response, String message, int statusCode) {
        response.setStatus(statusCode);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        try {
            String json = new ObjectMapper().writeValueAsString(
                    new ErrorResponse(
                            message,
                            response.getStatus()
                    )
            );
            response.getWriter().write(json);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}
