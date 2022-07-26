package com.sparta.springweb.controller;

import com.sparta.springweb.dto.LikeDto;
import com.sparta.springweb.dto.postRequestDto;
import com.sparta.springweb.dto.postResponseDto;
import com.sparta.springweb.model.Likes;
import com.sparta.springweb.model.Posts;
import com.sparta.springweb.repository.PostsRepository;
import com.sparta.springweb.security.UserDetailsImpl;
import com.sparta.springweb.service.LikesService;
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
    private final LikesService likesService;

//    @GetMapping("/api/post")
//    public Page<Posts> getContents(
//            @RequestParam int page, // 페이지 번호
//            @RequestParam int size, // 한 페이지에 보여줄 게시물 개수
//            @RequestParam String sortBy, // 정렬 항목 ex) 작성시간, 인원 수....
//            @RequestParam boolean isAsc // true = 오름차순, false = 내림차순
//    ) {
//        //PostService에 함수를 만들어줌
//        // 프론트에서 받아오는 페이지 번호 값은 1부터 시작이므로 -1 을 해주어 변환함
//        page -= 1;
//        return postsService.getContents(page, size, sortBy, isAsc);
//    }
    // 게시글 조회

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

    // 지역별 게시글 조회
//    @GetMapping("/api/post/{locationName}")
//    public List<postResponseDto> getLocalContents(@PathVariable String locationName) {
//        return postsService.getLocalContents(locationName);
//    }

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
        Long userId = userDetails.getUser().getId();
        Posts posts = postsService.update(id, requestDto, username, userId);
        return posts;
    }

    // 게시글 삭제
    @DeleteMapping("/api/post/{id}")
    public void deletecontents(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 로그인 되어 있는 ID
        if (userDetails != null) {
            String username = userDetails.getUsername();
            Long userId = userDetails.getUser().getId();
            postsService.deleteContent(id, username, userId);
        }
    }

    //좋아요
    @PostMapping("/api/likes/{contentsId}")
    public void likes(@PathVariable Long contentsId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        if (userDetails != null) {
            LikeDto likeDto = new LikeDto(contentsId, userDetails.getUsername());

            likesService.likes(likeDto);
        }
    }
    @GetMapping("/api/likes/{contentsId}")
    public List<Likes> getLikes(@PathVariable Long contentsId){
        return likesService.findLikes(contentsId);
    }

//    @DeleteMapping("/api/post/unLikes/{contentsId}")
//    public void unLikes(@PathVariable Long contentsId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        if (userDetails != null) {
//            likesService.unLikes(contentsId, userDetails.getUsername());
//        }
//    }

}