package com.sparta.springweb.service;

import com.sparta.springweb.dto.ContentsRequestDto;
import com.sparta.springweb.dto.ContentsResponseDto;
import com.sparta.springweb.model.Contents;
import com.sparta.springweb.repository.ContentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class ContentsService {

    private final ContentsRepository contentsRepository;


    // 게시글 작성 - 삽입할 데이터 추가 07.23
    @Transactional // 메소드 동작이 SQL 쿼리문임을 선언합니다.
    public Contents createContents(ContentsRequestDto requestDto, String username) {
        Contents contents1 = checkAttack(requestDto, username);
        if (contents1 != null) return contents1;
        // 요청받은 DTO 로 DB에 저장할 객체 만들기
        Contents contents = new Contents(requestDto, username);
        contentsRepository.save(contents);
        return contents;
    }

    // xxs 공격 판별 메서드
    private Contents checkAttack(ContentsRequestDto requestDto, String username) {
        String contentsCheck = requestDto.getContents();
        String titleCheck = requestDto.getTitle();
        if (contentsCheck.contains("script")||contentsCheck.contains("<")||contentsCheck.contains(">")){
            Contents contents = new Contents(requestDto, username,"잘못된 입력입니다(XSS 공격금지)");
            contentsRepository.save(contents);
            return contents;
        }
        if (titleCheck.contains("script")||titleCheck.contains("<")||titleCheck.contains(">")) {
            Contents contents = new Contents("잘못된 입력입니다(XSS 공격금지)", username, "잘못된 입력입니다(XSS 공격금지)");
            contentsRepository.save(contents);
            return contents;
        }
        return null;
    }

    // 게시글 조회
    public List<Contents> getContents() {
        return contentsRepository.findAll();
    }

    // 게시글 디테일 조회
    public Contents getDetailContents(Long id) {
        Contents contents = contentsRepository.findById(id).orElseThrow(
                ()->new IllegalArgumentException("id가 존재하지 않습니다."));
        return contents;
    }

    // 지역 게시글 조회
    public List<ContentsResponseDto> getLocalContents(String locationName) {
        List<ContentsResponseDto> contentsResponseDtoList = contentsRepository.findByLocationName(locationName);
        return contentsResponseDtoList;
    }

    // 게시글 수정 기능 - 수정할 데이터 추가 07.23
    @Transactional
    public Contents update(Long id, ContentsRequestDto requestDto,  String userName, Long userId) {
        Contents contents = contentsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다."));
        String writer = contents.getNickName();
        Long writerId = contents.getId();
        if (Objects.equals(writer, userName) && Objects.equals(writerId, userId)) {
            contents.update(requestDto);;
        }else new IllegalArgumentException("작성한 유저가 아닙니다.");
        return contents;
    }

    // 게시글 삭제
    public void deleteContent(Long id, String userName, Long userId) {
        Contents contents = contentsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다."));
        String writer = contents.getNickName();
        Long writerId = contents.getId();
        if (Objects.equals(writer, userName) && Objects.equals(writerId, userId)) {
            contentsRepository.deleteById(id);
        }else new IllegalArgumentException("작성한 유저가 아닙니다.");
    }
}