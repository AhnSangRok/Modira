package com.sparta.springweb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자를 생성
@NoArgsConstructor // 파라미터가 없는 기본 생성자를 생성
public class SignupRequestDto {
    private String username;
    private String password;
    private String password2;

}