package com.project.homepage_v2.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.http.HttpSession;

@Configuration
public class SecurityConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
				.csrf(c -> c.disable())
				.formLogin(in -> in.loginPage("/login")
						.loginProcessingUrl("/login")
						.usernameParameter("id")
						.passwordParameter("pwd")
						.failureUrl("/login?error")
						.defaultSuccessUrl("/").permitAll())
				.exceptionHandling(e -> e.accessDeniedPage("/error"))
				.authorizeHttpRequests(a -> a
						.requestMatchers(
								// 자원
								"/", "/css/**", "/js/**", "/img/**","/image/**", "/thumbnail/**", "/image/", "/favicon.ico", "/.well-known/**",
								// 로그인/로그아웃
								"/login", "/logout",
								// 예외 처리
								"/error",
								// 게시판
								"/about_me", "/b/**"
								)
						.permitAll()
						.anyRequest()
						.hasRole("ADMIN"));
		httpSecurity.logout(out -> {
			out.logoutUrl("/logout").addLogoutHandler((request, response, authentication) -> {
				HttpSession session = request.getSession();
				
				if (session != null) {
					session.invalidate();
				}
			}).logoutSuccessHandler((request, response, authentication) -> {
				response.sendRedirect("/");
			});
		});
		return httpSecurity.build();
	}

	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}