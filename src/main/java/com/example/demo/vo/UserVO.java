package com.example.demo.vo;


import java.time.LocalDateTime;

import com.example.demo.springSecurity.UserEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserVO {
	private int userNo; //회원번호(PK)
	private String name; //이름
	private String email; //이메일
	private String password; //비밀번호
	private String tel; //연락처
	private String address; //주소
	private String gender; //성별
	private String interest; //관심분야
	private String role; //권한
	private String authKey; //인증키
	private int active; //인증여부
	private LocalDateTime regDate; //등록일
	private LocalDateTime updDate; //수정일
	
	public UserEntity toEntity() {
		return UserEntity.builder()
				.userNo(userNo)
				.name(name)
				.email(email)
				.password(password)
				.tel(tel)
				.address(address)
				.gender(gender)
				.interest(interest)
				.role(role)
				.authKey(authKey)
				.active(active)
				.regDate(regDate)
				.updDate(updDate)
				.build();
	}
	
	@Builder
	public UserVO(int userNo, 
				String name, 
				String email, 
				String password, 
				String tel, 
				String address, 
				String gender, 
				String interest, 
				String role,
				String authKey,
				int active, 
				LocalDateTime regDate, 
				LocalDateTime updDate) {
		this.userNo = userNo;
		this.name = name;
		this.email = email;
		this.password = password;
		this.tel = tel;
		this.address = address;
		this.gender = gender;
		this.interest = interest;
		this.role = role;
		this.authKey = authKey;
		this.active = active;
		this.regDate = regDate;
		this.updDate = updDate;
		
		System.out.println("VO email : " + email);
		System.out.println("VO password : " + password);
	}
}
