package com.example.demo.filter;

import com.example.demo.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. 요청 헤더에서 Authorization 값을 가져옵니다.
        String authHeader = request.getHeader("Authorization");

        // 2. 헤더가 존재하고 "Bearer "로 시작하는지 확인합니다.
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // "Bearer " 부분을 제거하여 실제 토큰 값만 추출합니다.
            String token = authHeader.substring(7);

            try {
                // 3. JwtUtil의 validateToken 메서드를 통해 토큰의 유효성을 검증합니다.
                if (jwtUtil.validateToken(token)) {
                    // 4. 유효한 토큰인 경우, 토큰에서 username을 추출합니다.
                    String username = jwtUtil.extractUsername(token);

                    // 5. UserDetailsService를 통해 username에 해당하는 사용자 정보를 조회합니다.
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    // 6. 사용자 정보와 권한을 포함하는 인증 객체를 생성합니다.
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    // 7. 생성된 인증 객체를 SecurityContext에 설정하여 인증된 사용자로 처리합니다.
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                // 토큰 검증 중 오류가 발생하면 로그를 남기고 인증 없이 진행합니다.
                e.printStackTrace();
            }
        }

        // 8. 필터 체인의 다음 필터로 요청을 전달합니다.
        filterChain.doFilter(request, response);
    }
}
