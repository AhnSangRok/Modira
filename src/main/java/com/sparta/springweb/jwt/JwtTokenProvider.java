package com.sparta.springweb.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@RequiredArgsConstructor // final 필드 생성자 생성
@Component
// 토큰 생성, 유효성 검사
public class JwtTokenProvider {

    // secretKey 와 같은 민감정보는 숨기는 것이 좋다. (이것은 연습이라서 노출함)
    // @Value secretKey값 매핑
    @Value("K7kjHSF345h345S86F3A2erGB98iWIad")
    private String secretKey;

    // 토큰 유효시간 30분 설정 (1000L = 1초, 1000L * 60 = 1분)
    private static final long TOKEN_VALID_TIME = 1000L * 60 * 30;

    //UserDetailsService 불러와 객체를 생성하여 UserDetailsService 연결
    private final UserDetailsService userDetailsService;

    // 객체 초기화, secretKey 를 Base64로 인코딩한다.
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // JWT 토큰 생성(로그인 서비스에 사용)
    public String createToken(String userPk) {
        // primeKey 로 userName 을 저장
        Claims claims = Jwts.claims().setSubject(userPk);// JWT payload 에 저장되는 정보단위
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + TOKEN_VALID_TIME)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey)  // 사용할 암호화 알고리즘과 signature 에 들어갈 secret값 세팅(암호화)
                .compact();
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 회원 정보 추출, 토큰 검증에  사용
    public String getUserPk(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey) // 사용할 암호화 알고리즘과 signature 에 들어갈 secret값 복호화
                .parseClaimsJws(token) // 토큰을 넣어 풀어줌, signature을 사용하였으므로 Jwt가 아닌 Jws임을 유의
                .getBody() // 복호화 후의 디코딩한 payload에 접근
                .getSubject();
    }

    // Request의 Header에서 token 값을 가져옵니다. "X-AUTH-TOKEN" : "TOKEN값'
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(jwtToken);
            return !claims
                    .getBody()
                    .getExpiration()
                    .before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    // 토큰 파기 (만료일자 0으로 변경)
    public boolean invalidateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(jwtToken);
            Date now = new Date();
                    claims
                    .getBody()
                    .setExpiration(new Date(now.getTime() - (1000L * 60 * 30)));
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}