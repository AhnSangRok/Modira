package com.sparta.springweb.controller;


import com.sparta.springweb.dto.ContentsRequestDto;
import com.sparta.springweb.dto.ContentsResponseDto;
import com.sparta.springweb.model.Contents;
import com.sparta.springweb.security.UserDetailsImpl;
import com.sparta.springweb.service.ContentsService;
import com.sparta.springweb.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ContentsController {

    private final ContentsService contentsService;
    private final LikesService likesService;

    // 게시글 조회
    @GetMapping("/api/contents")
    public List<Contents> getContents() {
        return contentsService.getContents();
    }

    // 게시글 디테일 조회
    @GetMapping("/api/contents/{id}")
    public Contents getDetailContents(@PathVariable Long id) {
        Contents contents = contentsService.getDetailContents(id);
        return contents;
    }

    // 지역별 게시글 조회
    @GetMapping("/api/post/{locationName}")
    public List<ContentsResponseDto> getLocalContents(@PathVariable String locationName) {
        return contentsService.getLocalContents(locationName);
    }

    // 게시글 작성
    @PostMapping("/api/contents")
    public Contents createContents(@RequestBody ContentsRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 로그인 되어 있는 ID의 username
        if (userDetails != null) {
            Long userId = userDetails.getUser().getId();
            String username = userDetails.getUser().getUsername();
            Contents contents = contentsService.createContents(requestDto, username);
            return contents;
        }
        return null;
    }

    // 게시글 수정
    @PutMapping("/api/post/update/{id}")
    public Contents update(@PathVariable Long id, @RequestBody ContentsRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String username = userDetails.getUser().getUsername();
        Long userId = userDetails.getUser().getId();
        Contents contents = contentsService.update(id, requestDto, username, userId);
        return contents;
    }

    // 댓글 삭제
    @DeleteMapping("/api/post/delete/{id}")
    public void deletecontents(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 로그인 되어 있는 ID
        if (userDetails != null) {
            String username = userDetails.getUser().getUsername();
            Long userId = userDetails.getUser().getId();
            contentsService.deleteContent(id, username, userId);
        }
    }

    //좋아요 
    @PostMapping("/api/post/likes/{id}")
    public void likes(@PathVariable Long contentsid, @AuthenticationPrincipal UserDetailsImpl userDetails){
        if (userDetails != null) {
            likesService.likes(contentsid, userDetails.getUsername());
        }
    }
    @DeleteMapping("/api/post/unLikes/{id}")
    public void unLikes(@PathVariable Long contentsid, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails != null) {
            likesService.unLikes(contentsid, userDetails.getUsername());
        }
    }
}
