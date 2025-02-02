package com.example.tutoring.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.tutoring.security.filter.JsonUsernamePasswordFilter;
import com.example.tutoring.security.handler.CustomAccessDeniedHandler;
import com.example.tutoring.security.handler.CustomAuthenticationEntryPointHandler;
import com.example.tutoring.security.handler.CustomLogoutSuccessHandler;
import com.example.tutoring.security.handler.LoginFailureHandler;
import com.example.tutoring.security.handler.LoginSuccessHandler;
import com.example.tutoring.security.service.PrincipalDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Value("auth.login.remember-me.name")
    private String rememberMeName;
    @Value("auth.login.remember-me.key")
    private String rememberMeKey;

    private final ObjectMapper objectMapper;

    private final PrincipalDetailsService principalDetailsService;

    private final LoginSuccessHandler loginSuccessHandler;
    private final LoginFailureHandler loginFailureHandler;
    private final CustomLogoutSuccessHandler logoutSuccessHandler;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomAuthenticationEntryPointHandler customAuthenticationEntryPointHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(Customizer.withDefaults())

            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/", "/login", "/signup/**", "/login/check").permitAll()
                .requestMatchers("/**").authenticated()
            )

            .formLogin(formLogin -> formLogin.disable())
            .logout(logout -> logout
                .logoutSuccessHandler(logoutSuccessHandler)
            )
            .rememberMe(rememberMe -> {
                rememberMe
                    .key(rememberMeKey)                       // 토큰을 생성하기 위한 키
                    .tokenValiditySeconds(7 * 24 * 60 * 60)       // 7일 동안 유효
                    .rememberMeCookieName(rememberMeName)   // 쿠키 이름 지정
                    .userDetailsService(principalDetailsService); // 인증된 사용자 정보 제공
            })

            .exceptionHandling(handling -> {
                handling.accessDeniedHandler(customAccessDeniedHandler);
                handling.authenticationEntryPoint(customAuthenticationEntryPointHandler);
            })

            .addFilterBefore(jsonUsernamePasswordFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("https://localhost:3000"));                  // SvelteKit 애플리케이션이 실행 중인 주소를 허용
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));    // 허용할 HTTP 메서드 설정
        configuration.setAllowedHeaders(List.of("*"));                                      // 모든 헤더 허용
        configuration.setAllowCredentials(true);                                                // 쿠키와 자격 증명 허용 필요 시 설정
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);     // 모든 경로에 대해 이 CORS 설정 적용
        return source;
    }

    @Bean
    JsonUsernamePasswordFilter jsonUsernamePasswordFilter() {
        JsonUsernamePasswordFilter jsonUsernamePasswordFilter = new JsonUsernamePasswordFilter(objectMapper);
        jsonUsernamePasswordFilter.setAuthenticationManager(authenticationManager());
        jsonUsernamePasswordFilter.setAuthenticationSuccessHandler(loginSuccessHandler);
        jsonUsernamePasswordFilter.setAuthenticationFailureHandler(loginFailureHandler);
        jsonUsernamePasswordFilter.setFilterProcessesUrl("/login");
        return jsonUsernamePasswordFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(principalDetailsService);

        return new ProviderManager(provider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}