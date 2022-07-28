package com.sparta.springweb.controller;

import com.sparta.springweb.dto.postRequestDto;
import com.sparta.springweb.model.Posts;
import com.sparta.springweb.security.UserDetailsImpl;
import com.sparta.springweb.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class postController {

    private final PostsService postsService;

//     게시글 조회
    @GetMapping("/api/post")
    public List<Posts> getContents() {
        return postsService.getContents();
    }

    // 게시글 디테일 조회
    @GetMapping("/api/post/{id}")
    public Posts getDetailContents(@PathVariable Long id) {
        Posts posts = postsService.getDetailContents(id);
        return posts;
    }

//     지역별 게시글 조회
    @GetMapping("/api/post/local/{locationName}")
    public Page<Posts> getLocalContents(@PathVariable("locationName") int locationName, @RequestParam int page, @RequestParam int size) {
        page -= 1;
        return postsService.getLocalContents(locationName, page, size);
    }
//    @GetMapping("/api/post/local/{locationName}")
//        public List<Posts> getLocalContents(@PathVariable("locationName") int locationName) {
//            return postsService.getLocalContents(locationName);
//        }

    // 게시글 작성
    @PostMapping("/api/post")
    // @AuthenticationPrincipal 로그인한 사용자의 정보를 파라메터로 받음
    public Posts createPosts(@RequestBody postRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 로그인 되어 있는 ID의 username
        if (userDetails != null) {
            String username = userDetails.getUsername();
            Posts posts = postsService.createContents(requestDto, username);
            return posts;
        } else return null;
    }

    // 게시글 수정
    @PutMapping("/api/post/{id}")
    public Posts update(@PathVariable Long id, @RequestBody postRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String username = userDetails.getUsername();
//        Long userId = userDetails.getUser().getId();
        Posts posts = postsService.update(id, requestDto, username);
        return posts;
    }

    // 게시글 삭제
    @DeleteMapping("/api/post/{id}")
    public void deletecontents(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 로그인 되어 있는 ID
        if (userDetails != null) {
            String username = userDetails.getUsername();
//            Long userId = userDetails.getUser().getId();
            postsService.deleteContent(id, username);
        }
    }



}