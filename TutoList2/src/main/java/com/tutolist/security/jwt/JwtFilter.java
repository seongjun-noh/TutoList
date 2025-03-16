package com.tutolist.security.jwt;

import java.io.IOException;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.tutolist.common.enums.UserRole;
import com.tutolist.common.error.exception.UnauthorizedException;
import com.tutolist.security.details.PrincipalDetails;
import com.tutolist.service.redis.RedisService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final RedisService redisService;
    private static final String ANONYMOUS_KEY = "anonymous";

    private void setAnonymousAuthentication() {
        Authentication anonymous = new AnonymousAuthenticationToken(
            ANONYMOUS_KEY,
            "anonymousUser",
            AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS")
        );
        SecurityContextHolder.getContext().setAuthentication(anonymous);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // 헤더에서 토큰 추출
        String header = request.getHeader("Authorization");

        // 토큰이 없거나 Bearer로 시작하지 않으면 익명 인증 설정
        if (header == null || !header.startsWith("Bearer ")) {
            setAnonymousAuthentication();
            chain.doFilter(request, response);
            return;
        }

        // 토큰이 있으면 검증
        String token = header.substring(7);
        
        try {
            // 토큰 검증
            Claims claims = jwtUtil.parseToken(token);
            if (claims == null || !jwtUtil.isAccessToken(claims) || redisService.isBlacklisted(token)) {
                setAnonymousAuthentication();
                chain.doFilter(request, response);
                return;
            }

            // 토큰에서 정보 추출
            Long id = jwtUtil.getId(claims);
            String roleStr = jwtUtil.getRole(claims);
            UserRole role = UserRole.valueOf(roleStr);
            String username = jwtUtil.getUsername(claims);

            // 인증 객체 생성
            PrincipalDetails principalDetails = PrincipalDetails.builder()
                .id(id)
                .role(role)
                .email(username)
                .build();

            Authentication authentication = new JwtAuthentication(principalDetails);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            chain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            throw new UnauthorizedException("토큰이 만료되었습니다. 토큰을 갱신해주세요.");
        }
    }
}
