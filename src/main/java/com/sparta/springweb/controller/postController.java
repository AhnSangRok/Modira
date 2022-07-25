package com.sparta.springweb.controller;

import com.sparta.springweb.dto.postRequestDto;
import com.sparta.springweb.dto.postResponseDto;
import com.sparta.springweb.model.Posts;
import com.sparta.springweb.security.UserDetailsImpl;
import com.sparta.springweb.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor // final 필드 생성자 생성
@RestController  // JSON으로 데이터를 주고받음을 선언합니다.
public class postController {

    //PostService를 불러와 객체를 생성하여 PostService 연결
    private final PostService postService;

    // 게시글 조회
    @GetMapping("/api/post")
    public Page<Posts> getContents(
            @RequestParam int page, // 페이지 번호
            @RequestParam int size, // 한 페이지에 보여줄 게시물 개수
            @RequestParam String sortBy, // 정렬 항목 ex) 작성시간, 인원 수....
            @RequestParam boolean isAsc // true = 오름차순, false = 내림차순
    ) {
        //PostService에 함수를 만들어줌
        page -= 1; // 프론트에서 받아오는 페이지 번호 값은 1부터 시작이므로 -1 을 해주어 변환함
        return postService.getContents(page, size, sortBy, isAsc);
    }

    // 게시글 디테일 조회
    @GetMapping("/api/post/{id}")
    // @PathVariable {템플릿 변수} 와 동일한 이름을 갖는 파라미터에 매핑
    public Posts getDetailContents(@PathVariable Long id) {
        //PostService에 함수를 만들어줌
        return postService.getDetailContents(id);
    }

    // 지역별 게시글 조회
    @GetMapping("/api/post/{locationName}")
    // @PathVariable {템플릿 변수} 와 동일한 이름을 갖는 파라미터에 매핑
    public Page<postResponseDto> getLocalContents(
            @PathVariable String locationName, // 지역명
            @RequestParam int page, // 페이지 번호
            @RequestParam int size, // 한 페이지에 보여줄 게시물 개수
            @RequestParam String sortBy, // 정렬 항목 ex) 작성시간, 인원 수....
            @RequestParam boolean isAsc // true = 오름차순, false = 내림차순
    ) {
        //PostService에 함수를 만들어줌
        // 프론트에서 받아오는 페이지 번호 값은 1부터 시작이므로 -1 을 해주어 변환함
        page -= 1;
        return postService.getLocalContents(locationName, page, size, sortBy, isAsc);
    }

    // 게시글 작성
    @PostMapping("/api/post")
    // @RequestBody 객체를 본문으로 전달
    // @AuthenticationPrincipal 로그인한 사용자의 정보를 파라메터로 받음
    public Posts createPosts(@RequestBody postRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // UserDetailsImpl에 로그인되어 있는 정보의 유무 확인
        if (userDetails != null) {
            // UserDetailsImpl에 로그인되어 있는 ID의 UserName
            String username = userDetails.getUsername();
            //PostService에 함수를 만들어줌
            return postService.createPosts(requestDto, username);
        } else return null;
    }

    // 게시글 수정
    @PutMapping("/api/post/{id}")
    // @PathVariable {템플릿 변수} 와 동일한 이름을 갖는 파라미터에 매핑
    // @RequestBody 객체를 본문으로 전달
    // @AuthenticationPrincipal 로그인한 사용자의 정보를 파라메터로 받음
    public Posts update(@PathVariable Long id, @RequestBody postRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // UserDetailsImpl에 로그인되어 있는 ID의 UserName
        String username = userDetails.getUsername();
        //PostService에 함수를 만들어줌
        return postService.update(id, requestDto, username);
    }

    // 게시글 삭제
    @DeleteMapping("/api/post/{id}")
    // @PathVariable {템플릿 변수} 와 동일한 이름을 갖는 파라미터에 매핑
    // @AuthenticationPrincipal 로그인한 사용자의 정보를 파라메터로 받음
    public void deletecontents(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // UserDetailsImpl에 로그인되어 있는 정보의 유무 확인
        if (userDetails != null) {
            // UserDetailsImpl에 로그인되어 있는 ID의 UserName
            String username = userDetails.getUsername();
            //PostService에 함수를 만들어줌
            postService.deleteContent(id, username);
        }
    }

}