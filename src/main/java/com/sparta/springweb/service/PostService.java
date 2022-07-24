package com.sparta.springweb.service;

import com.sparta.springweb.dto.postRequestDto;
import com.sparta.springweb.dto.postResponseDto;
import com.sparta.springweb.model.Posts;
import com.sparta.springweb.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class PostService {

    //PostRepository 를 불러와 객체를 생성하여 PostRepository 에 연결
    private final PostRepository postRepository;


    // 게시글 작성
    @Transactional // 메소드 동작이 SQL 쿼리문임을 선언합니다, 메서드 종효 후 entity 에 변경 사항 적용
    public Posts createPosts(postRequestDto requestDto, String username) {
        Posts posts1 = checkAttack(requestDto, username);
        if (posts1 != null) return posts1;
        // 요청받은 DTO 로 DB에 저장할 객체 만들기
        Posts posts = new Posts(requestDto, username);
        //PostRepository에 함수 생성
        postRepository.save(posts);
        return posts;
    }

    // xss 공격 판별 메서드
    private Posts checkAttack(postRequestDto requestDto, String username) {
        String contentsCheck = requestDto.getContents();
        String titleCheck = requestDto.getTitle();
        if (contentsCheck.contains("script")||contentsCheck.contains("<")||contentsCheck.contains(">")){
            Posts posts = new Posts(requestDto, username,"잘못된 입력입니다(XSS 공격금지)");
            postRepository.save(posts);
            return posts;
        }
        if (titleCheck.contains("script")||titleCheck.contains("<")||titleCheck.contains(">")) {
            Posts posts = new Posts("잘못된 입력입니다(XSS 공격금지)", username, "잘못된 입력입니다(XSS 공격금지)");
            postRepository.save(posts);
            return posts;
        }
        return null;
    }

    // 게시글 조회
    public Page<Posts> getContents(int page, int size, String sortBy, boolean isAsc) {
        // 페이징 및 정렬
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        // 정렬 direction 과 정렬 항목을 파라미터로 받음
        Sort sort = Sort.by(direction, sortBy);
        // Pageable pageable = new PageRequest(page, size, sort)와 동일
        Pageable pageable = PageRequest.of(page, size, sort);
        //PostRepository에 함수 생성
        return postRepository.findAll(pageable);
    }

    // 게시글 디테일 조회
    public Posts getDetailContents(Long id) {
        //PostRepository에 함수 생성
        return postRepository.findById(id).orElseThrow(
                ()->new IllegalArgumentException("id가 존재하지 않습니다."));
    }

    // 각 지역별 게시글 조회
    public Page<postResponseDto> getLocalContents(
            String locationName,
            int page,
            int size,
            String sortBy,
            boolean isAsc
    ) {
        // 페이징 및 정렬
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        // 정렬 direction 과 정렬 항목을 파라미터로 받음
        Sort sort = Sort.by(direction, sortBy);
        // Pageable pageable = new PageRequest(page, size, sort)와 동일
        Pageable pageable = PageRequest.of(page, size, sort);
        //PostRepository에 함수 생성
        return postRepository.findByLocationName(locationName, pageable);
    }

    // 게시글 수정 기능
    @Transactional
    public Posts update(Long id, postRequestDto requestDto, String userName) {
        //PostRepository에 함수 생성
        Posts posts = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다."));
        // db에서 Id로 찾은 Posts정보의 userName추출, 즉 작성자
        String writer = posts.getUserName();
        // 로그인된 userName과 작성자를 비교
        if (Objects.equals(writer, userName)) {
            // 입력된 내용으로 db에 저장
            posts.update(requestDto, userName);
        }else new IllegalArgumentException("작성한 유저가 아닙니다.");
        return posts;
    }

    // 게시글 삭제
    public void deleteContent(Long id, String userName) {
        //PostRepository에 함수 생성
        Posts posts = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다."));
        // db에서 Id로 찾은 Posts정보의 userName추출, 즉 작성자
        String writer = posts.getUserName();
        // 로그인된 userName과 작성자를 비교
        if (Objects.equals(writer, userName)) {
            // 입력된 내용으로 db에서 삭제
            postRepository.deleteById(id);
        }else new IllegalArgumentException("작성한 유저가 아닙니다.");
    }
}