package com.sparta.springweb.model;

import com.sparta.springweb.dto.postRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.time.LocalDateTime;

@Getter // get 함수를 일괄적으로 만들어줍니다.
@NoArgsConstructor // 기본 생성자를 만들어줍니다.
@Entity // DB 테이블 역할을 합니다.
// Timestamped 상속
public class Posts extends Timestamped {

    // ID가 자동으로 생성 및 증가합니다.
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    // 반드시 값을 가지도록 합니다.
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private String locationName;

    @Column(nullable = false)
    private int partyNum;

    @Column
    private int joinNum = 0;

    @Column
    private boolean dones = false;

    // xxs 공격 판단 매소드 객체
    public Posts(String title, String username, String contents) {
        this.title = title;
        this.userName = username;
        this.contents = contents;
    }
    public Posts(postRequestDto requestDto, String username, String contents) {
        this.title = requestDto.getTitle();
        this.userName = username;
        this.contents = contents;
    }

    // 게시글 작성 메소드 객체
    public Posts(postRequestDto requestDto, String userName) {
        this.title = requestDto.getTitle();
        this.userName = userName;
        this.contents = requestDto.getContents();
        this.locationName = requestDto.getLocationName();
        this.partyNum = requestDto.getPartyNum();
        this.imageUrl = requestDto.getImageUrl();
    }

    // update 메소드 객체
    public void update(postRequestDto requestDto, String userName) {
        this.title = requestDto.getTitle();
        this.userName = userName;
        this.contents = requestDto.getContents();
        this.locationName = requestDto.getLocationName();
        this.partyNum = requestDto.getPartyNum();
        this.imageUrl = requestDto.getImageUrl();
    }

    @Getter // private를 조회하기 위해 사용
    @MappedSuperclass // Entity가 자동으로 컬럼으로 인식합니다.
    @EntityListeners(AuditingEntityListener.class) // 생성/변경 시간을 자동으로 업데이트합니다.
    public abstract static class Timestamped {

        @CreatedDate
        private LocalDateTime createdAt;

        @LastModifiedDate
        private LocalDateTime modifiedAt;
    }
}