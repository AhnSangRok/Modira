package com.sparta.springweb.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sparta.springweb.dto.ContentsRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
@Setter
@Getter // get 함수를 일괄적으로 만들어줍니다.
@NoArgsConstructor // 기본 생성자를 만들어줍니다.
@Entity // DB 테이블 역할을 합니다.
public class Contents extends Timestamped {

    // ID가 자동으로 생성 및 증가합니다.
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    // 반드시 값을 가지도록 합니다.
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String nickName;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String locationName;

    @Column(nullable = false)
    private int partyNum;

    @Column(nullable = false)
    private int joinNum;

    @Column(nullable = false)
    private boolean done;

    @JsonIgnoreProperties({"contentsList"})
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @Transient
    private long likesCount;

    @Transient
    private boolean likesState;




    public Contents(String title, String username, String contents) {
        this.title = title;
        this.nickName = username;
        this.contents = contents;
    }

    public Contents(ContentsRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.nickName = requestDto.getNickName();
        this.contents = requestDto.getContents();
    }

    public Contents(ContentsRequestDto requestDto, String username) {
        this.title = requestDto.getTitle();
        this.nickName = username;
        this.contents = requestDto.getContents();
    }

    public void update(ContentsRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.nickName = requestDto.getNickName();
        this.contents = requestDto.getContents();
    }

    public Contents(ContentsRequestDto requestDto, String username,String contents) {
        this.title = requestDto.getTitle();
        this.nickName = username;
        this.contents = contents;
    }

    @Builder
    public Contents(String title, String username, String contents, User user, Long likesCount) {
        this.title = title;
        this.nickName = username;
        this.contents = contents;
        this.user = user;
        this.likesCount = likesCount;
    }

    public void updateLikesCount(Long likesCount){
        this.likesCount = likesCount;
    }

    public void updateLikesState(boolean likesState){

        this.likesState = likesState;
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
