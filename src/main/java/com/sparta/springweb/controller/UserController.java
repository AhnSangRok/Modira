package com.sparta.springweb.controller;

import com.sparta.springweb.dto.LoginRequestDto;
import com.sparta.springweb.dto.SignupRequestDto;
import com.sparta.springweb.jwt.JwtTokenProvider;
import com.sparta.springweb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController // JSON으로 데이터를 주고받음을 선언합니다.
@RequiredArgsConstructor // final 필드 생성자 생성
public class UserController {

    //UserService 불러와 객체를 생성하여 UserService 연결
    private final UserService userService;
    //JwtTokenProvider 불러와 객체를 생성하여 JwtTokenProvider 연결
    private final JwtTokenProvider jwtTokenProvider;

    // 회원 로그인
    @PostMapping("/user/login")
    // @RequestBody 객체를 본문으로 전달
    public String login(@RequestBody LoginRequestDto loginRequestDto) {
        //UserService에 함수를 만들어줌
        if (userService.login(loginRequestDto)) {
            // if값이 true라면 토큰 생성
            String token = jwtTokenProvider.createToken(loginRequestDto.getUsername());
            // (ARC로 토큰을 넣어주기위해)로그인 시 토큰 출력 -> 차후 변경
            return token;
        } else {
            return "닉네임 또는 패스워드를 확인해주세요";
        }
    }

    // 회원 가입 요청 처리
    @PostMapping("/user/signup")
    // @Valid (service 단이 아닌 객체 안에서) 들어오는 객체 값에 대해 검증
    // @RequestBody 객체를 본문으로 전달
    public String registerUser(@Valid @RequestBody SignupRequestDto requestDto) {
        //UserService에 함수를 만들어줌
        String result = userService.registerUser(requestDto);
        // return값이 비었다면 회원가입 성공(return값에 에러 문구가 뜬다면 에러문구 출력)
        return result.isEmpty() ? "회원가입 성공" : result;
    }

    // 로그아웃
    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        //UserService에 함수를 만들어줌
        return userService.logout(request);
    }
}