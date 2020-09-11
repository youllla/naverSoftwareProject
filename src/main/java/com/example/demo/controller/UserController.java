package com.example.demo.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.service.UserService;
import com.example.demo.vo.UserVO;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor 
public class UserController {
	private UserService userService;

	//회원가입 페이지
	@GetMapping("/register")
	public String register() throws Exception {
		return "/user/register";
	}
	
	//회원가입 진행
	@PostMapping("/register")
	public String registerProcess(UserVO userVO, Model model) throws Exception {
		String result = userService.joinUser(userVO);
		System.out.println("result : " + result);
		
		//model.addAttribute("result", result);
		
		return "redirect:/login";
	}

	//회원가입 이메일 인증 성공 시 active
	@GetMapping("/registerConfirm")
	public ModelAndView registerConfirm(@RequestParam("email") String email, @RequestParam("authKey") String authKey, ModelAndView mav) throws Exception{
		//email, authKey 가 일치할경우 active 업데이트
		int result = userService.updateActive(email, authKey);
		System.out.println("email : " + email);
		System.out.println("authKey : " + authKey);
		
		if (result != 0) {
			mav.setViewName("/user/login");
		} else {
			mav.setViewName("/user/main");
		}
		return mav;
	}

	//로그인
	@GetMapping("/login")
	public String login() throws Exception {
		return "/user/login";
	}

	/*
	 * //로그인 성공 페이지
	 * 
	 * @GetMapping("/loginSuccess") public String loginSuccess() throws Exception {
	 * return "/user/loginSuccess"; }
	 */
	
	//로그아웃
	@GetMapping("/user/logout")
	public String logout() throws Exception {
		return "/user/logout";
	}
	
	//인증되지 않은 사용자 접근 시
	@GetMapping("/user/denied")
    public String dispDenied() {
        return "/denied";
    }
    
	// 내 정보 페이지
	@GetMapping("/user/info")
	public String dispMyInfo() {
		return "/myinfo"; 
	}
}
