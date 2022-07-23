package com.sparta.springweb.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.springweb.model.Contents;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ContentsResponseDto {
    private Long id;
    private String title;
    private String nickName;
    private String contents;
    private String locationName;
    private int partyNum;
    private int joinNum;
    private boolean done;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime modifiedAt;
    private int countReply;

    public ContentsResponseDto(Long id, String title, String nickName, String contents, String locationName, int partyNum, int joinNum, boolean done, LocalDateTime modifiedAt, int countReply) {
        this.id = id;
        this.title = title;
        this.nickName = nickName;
        this.contents = contents;
        this.locationName = locationName;
        this.partyNum = partyNum;
        this.joinNum = joinNum;
        this.done = done;
        this.modifiedAt = modifiedAt;
        this.countReply = countReply;
    }

    @Builder
    public ContentsResponseDto(Contents content, int countReply) {
        this.id = content.getId();
        this.title = content.getTitle();
        this.nickName = content.getNickName();
        this.contents = content.getContents();
        this.locationName = content.getLocationName();
        this.partyNum = content.getPartyNum();
        this.joinNum = content.getJoinNum();
        this.modifiedAt = content.getModifiedAt();
        this.done = done;
        this.countReply = countReply;
    }
}
