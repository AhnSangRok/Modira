package com.sparta.springweb.controller;

import com.sparta.springweb.dto.LoginRequestDto;
import com.sparta.springweb.dto.SignupRequestDto;
import com.sparta.springweb.jwt.JwtTokenProvider;
import com.sparta.springweb.security.UserDetailsImpl;
import com.sparta.springweb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    // 회원 로그인
    @PostMapping("/user/login")
    public String login(@RequestBody LoginRequestDto loginRequestDto) throws IllegalAccessException {
        if (userService.login(loginRequestDto)) {
            String token = jwtTokenProvider.createToken(loginRequestDto.getUsername());
            return token;
        } else {
            throw new IllegalAccessException("아이디 비밀번호를 확인해 주세요");
        }
    }

    // 회원 가입 요청 처리
    @PostMapping("/user/signup")
    public String registerUser(@Valid @RequestBody SignupRequestDto requestDto) {
        String result = userService.registerUser(requestDto);
        //            model.addAttribute("errortext", userService.registerUser(requestDto));
        return result.equals("") ? "회원가입 성공" : result;
    }

    // 로그아웃
    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        return userService.logout(request);
    }

    @PostMapping("/user/userinfo")
    @ResponseBody
    public String getUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        String username = userDetails.getUser().getUsername();
        return username;
    }
}