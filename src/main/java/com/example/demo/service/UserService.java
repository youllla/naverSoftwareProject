package com.example.demo.service;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.example.demo.mapper.UserMapper;
import com.example.demo.springSecurity.Role;
import com.example.demo.springSecurity.UserEntity;
import com.example.demo.util.EmailSender;
import com.example.demo.util.TempKey;
import com.example.demo.vo.UserVO;


@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
	@Autowired
	private UserMapper userMapper;
	
	//회원가입
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor={Exception.class})
	public String joinUser(UserVO userVO) throws Exception {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		userVO.setPassword(passwordEncoder.encode(userVO.getPassword()));
		
		if (userVO.getEmail().equals("ula0614@gridone.co.kr")) {
			userVO.setRole(Role.ADMIN.getValue());
		} else {
			userVO.setRole(Role.USER.getValue());
		}
		
		//DB insert 성공 여부
		int insertResult = userMapper.insertUser(userVO);
		//alert 내용
		String msg = "";
		
		if(insertResult != 0) {
			//메일 인증 고유키 생성
			String authKey = new TempKey().getKey(20, false);
			userVO.setAuthKey(authKey);
	
			System.out.println("authKey : " + userVO.getAuthKey());
			System.out.println("email : " + userVO.getEmail());
	
			int updateResult = userMapper.updateAuthKey(userVO);
			
			if (updateResult != 0) {
				EmailSender.sendEmail(userVO, authKey);
				msg = "가입하신 이메일 주소로 인증 이메일이 전송되었습니다.";
			}
		} else {
			msg = "회원가입을 실패하였습니다.";
		}
		
		return msg;
	}

	//로그인
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<UserEntity> userEntityWrapper = userMapper.findByEmail(email);
		
		UserEntity userEntity = userEntityWrapper.get();
		
		System.out.println("userEntityWrapper : " + userEntityWrapper);
		System.out.println("userEntity : " + userEntity);
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		//UserVO userVO = new UserVO();
		
		//if(userVO.getRole() == "ADMIN") { //조건 수정
		if(("ula0614@gridone.co.kr").equals(email)) { //조건 수정
			authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
		} else {
			authorities.add(new SimpleGrantedAuthority(Role.USER.getValue()));
		}
		System.out.println(new User(userEntity.getEmail(), userEntity.getPassword(), authorities));
		
		return new User(userEntity.getEmail(), userEntity.getPassword(), authorities);
	}

	public int updateActive(String email, String authKey) throws Exception {
		int result = userMapper.updateActive(email, authKey);
		System.out.println("result : " + result);
		return result;
	}
}
