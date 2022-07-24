package com.sparta.springweb.service;


import com.sparta.springweb.dto.LoginRequestDto;
import com.sparta.springweb.dto.SignupRequestDto;
import com.sparta.springweb.jwt.JwtTokenProvider;
import com.sparta.springweb.model.User;
import com.sparta.springweb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor // final 필드 생성자 생성
public class UserService {

    //필요한 곳을 불러와 객체를 생성하여 연결
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    // 로그인
    public Boolean login(LoginRequestDto loginRequestDto){
        // LoginRequestDto 에 있는 UserNAme 을 통해 user 정보 불러오기
        User user = userRepository.findByUsername(loginRequestDto.getUsername())
                .orElse(null);
        // user 정보가 비었거나 입력된 password 가 저장되어 있는 password 와 다르다면 false
        // matches(CharSequence rawPassword, String encodedPassword) => 인코딩된 password 를 디코딩하여 일반 password 와 비교
        return user != null && passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword());
    }

    // 회원가입
    public String registerUser(SignupRequestDto requestDto) {
        // 정상 작동할 경우 출력될 값
        String error = "";
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();
        String password2 = requestDto.getPassword2();
        String pattern = "^[a-zA-Z0-9]*$";

        // 회원 ID 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            return "중복된 id 입니다.";
        }

        // 회원가입 조건
        if (username.length() < 3) {
            return "닉네임을 3자 이상 입력하세요";
        } else if (!Pattern.matches(pattern, username)) {
            return "알파벳 대소문자와 숫자로만 입력하세요";
        } else if (!password.equals(password2)) {
            return "비밀번호가 일치하지 않습니다";
        } else if (password.length() < 4) {
            return "비밀번호를 4자 이상 입력하세요";
        } else if (password.contains(username)) {
            return "비밀번호에 닉네임을 포함할 수 없습니다.";
        }

        // 패스워드 인코딩
        password = passwordEncoder.encode(password);
        requestDto.setPassword(password);

        // 요청받은 DTO 로 DB에 저장할 객체 만들기 (유저 정보 저장)
        User user = new User(username, password);
        //OrderRepository에 함수를 만들어줌
        userRepository.save(user);
        return error;
    }

    // 로그아웃
    // 정보를 컨트롤러로 보냈을 때 HttpServletRequest 객체 안에 모든 데이터들이 들어감
    public String logout(HttpServletRequest request) {
        // jwtTokenProvider에서 token값을 가져옴
        String header = jwtTokenProvider.resolveToken(request);
        // 가져온 token값 파기
        jwtTokenProvider.invalidateToken(header);
        return "logout";
    }
}