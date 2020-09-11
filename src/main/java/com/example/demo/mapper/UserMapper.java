package com.example.demo.mapper;

import java.util.Map;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.springSecurity.UserEntity;
import com.example.demo.vo.UserVO;

@Mapper
public interface UserMapper {
	//회원가입
	public int insertUser(UserVO userVO) throws Exception;

	//로그인
	public Optional<UserEntity> findByEmail(String email);

	//인증 고유키 업데이트
	public int updateAuthKey(UserVO userVO) throws Exception;

	//인증 여부 확인 active컬럼 업데이트
	public int updateActive(String email, String authKey) throws Exception;

}
