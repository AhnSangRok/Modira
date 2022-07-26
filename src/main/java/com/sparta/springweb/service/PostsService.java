package com.sparta.springweb.service;

import com.sparta.springweb.dto.postRequestDto;
import com.sparta.springweb.dto.postResponseDto;
import com.sparta.springweb.model.Posts;
import com.sparta.springweb.repository.LikesRepository;
import com.sparta.springweb.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;
    private final LikesRepository likesRepository;

    // 게시글 작성
    @Transactional // 메소드 동작이 SQL 쿼리문임을 선언합니다.
    public Posts createContents(postRequestDto requestDto, String username) {
        Posts posts1 = checkAttack(requestDto, username);
        if (posts1 != null) return posts1;
//        // 요청받은 DTO 로 DB에 저장할 객체 만들기
        Posts posts = new Posts(requestDto, username);
        postsRepository.save(posts);
        return posts;
    }

    // xxs 공격 판별 메서드
    private Posts checkAttack(postRequestDto requestDto, String username) {
        String contentsCheck = requestDto.getContents();
        String titleCheck = requestDto.getTitle();
        if (contentsCheck.contains("script")||contentsCheck.contains("<")||contentsCheck.contains(">")){
            Posts posts = new Posts(requestDto, username,"잘못된 입력입니다(XSS 공격금지)");
            postsRepository.save(posts);
            return posts;
        }
        if (titleCheck.contains("script")||titleCheck.contains("<")||titleCheck.contains(">")) {
            Posts posts = new Posts("잘못된 입력입니다(XSS 공격금지)", username, "잘못된 입력입니다(XSS 공격금지)");
            postsRepository.save(posts);
            return posts;
        }
        return null;
    }

    //     게시글 조회
    public List<Posts> getContents() {
//        return postsRepository.findAll();
        return postsRepository.findAllByOrderByModifiedAtDesc(); //등록 시간순으로 정렬
    }
//    public Page<Posts> getContents(int page, int size, String sortBy, boolean isAsc) {
//        // 페이징 및 정렬
//        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
//        // 정렬 direction 과 정렬 항목을 파라미터로 받음
//        Sort sort = Sort.by(direction, sortBy);
//        // Pageable pageable = new PageRequest(page, size, sort)와 동일
//        Pageable pageable = PageRequest.of(page, size, sort);
//        //PostRepository에 함수 생성
//        return postsRepository.findAll(pageable);
//    }


    // 게시글 디테일 조회
    public Posts getDetailContents(Long id) {
        Posts posts = postsRepository.findById(id).orElseThrow(
                ()->new IllegalArgumentException("id가 존재하지 않습니다."));

//        int count = likesRepository.countByPostsId(id);
//        posts.updateLikesCount(count);
        return posts;
    }

    // 각 지역별 게시글 조회
    public List<postResponseDto> getLocalContents(String locationName) {
        List<postResponseDto> postResponseDtoList = postsRepository.findByLocationName(locationName);
        return postResponseDtoList;
    }

    // 게시글 수정 기능
    @Transactional
    public Posts update(Long id, postRequestDto requestDto, String userName, Long userId) {
        Posts posts = postsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다."));
        String writer = posts.getUserName();
        Long writerId = posts.getId();
        if (Objects.equals(writer, userName) && Objects.equals(writerId, userId)) {
            posts.update(requestDto, userName);
        }else new IllegalArgumentException("작성한 유저가 아닙니다.");
        return posts;
    }

    // 게시글 삭제
    public void deleteContent(Long id, String userName, Long userId) {
        Posts posts = postsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다."));
        String writer = posts.getUserName();
        Long writerId = posts.getId();
        if (Objects.equals(writer, userName) && Objects.equals(writerId, userId)) {
            postsRepository.deleteById(id);
        }else new IllegalArgumentException("작성한 유저가 아닙니다.");
    }
}