package com.example.demo.springSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.demo.service.UserService;

import lombok.AllArgsConstructor;


@EnableWebSecurity
@Configuration 
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserService userService;
	
	@Bean 
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers("/static/**", "/css/**", "/js/**", "/image/**", "/lib/**", "/fragments/**", "/layout/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			//페이지 권한
			.antMatchers("/admin/**").hasRole("ADMIN")
            .antMatchers("/user/**").hasRole("USER")
            .antMatchers("/**").permitAll()
			//.antMatchers("/user/**").hasRole("USER") // USER, ADMIN
            //.anyRequest().authenticated() // 나머지 요청들은 권한 종류 상관 없이 권한이 있어야 접근 가능
			
			//로그인
			.and()
				.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/main") // 로그인 성공 후 리다이렉트 주소
				.permitAll()
			//로그아웃
			.and()
				.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
				.logoutSuccessUrl("/main") // 로그아웃 성공 후 리다이렉트 주소
				.invalidateHttpSession(true) // 세션 삭제
				
			.and()
				.exceptionHandling().accessDeniedPage("/user/denied");

	}
	
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
		System.out.println("auth: " + auth);
	}
}