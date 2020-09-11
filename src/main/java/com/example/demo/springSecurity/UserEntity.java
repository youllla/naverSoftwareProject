package com.example.demo.springSecurity;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserEntity {
	private int userNo;
	private String name;
	private String email;
	private String password;
	private String tel;
	private String address;
	private String gender;
	private String interest;
	private String role;
	private String authKey;
	private int active;
	private LocalDateTime regDate;
	private LocalDateTime updDate;
	
	@Builder
	public UserEntity(int userNo,
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
		this.regDate = LocalDateTime.now();
		this.updDate = LocalDateTime.now();
	}

}
