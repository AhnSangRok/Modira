package com.sparta.springweb.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.springweb.model.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class postResponseDto {
    private Long id;
    private String title;
    private String userName;
    private String contents;
    private int locationName;
    private int partyNum;
    private int joinNum;
    private boolean dones;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime modifiedAt;


    public postResponseDto(Long id, String title, String userName, String contents, int locationName, int partyNum, int joinNum, boolean done, LocalDateTime modifiedAt, int countReply) {
        this.id = id;
        this.title = title;
        this.userName = userName;
        this.contents = contents;
        this.locationName = locationName;
        this.partyNum = partyNum;
        this.joinNum = joinNum;
        this.dones = dones;
        this.modifiedAt = modifiedAt;
    }

    @Builder
    public postResponseDto(Posts content, int countReply) {
        this.id = content.getId();
        this.title = content.getTitle();
        this.userName = content.getUserName();
        this.contents = content.getContents();
        this.locationName = content.getLocationName();
        this.partyNum = content.getPartyNum();
        this.joinNum = content.getJoinNum();
        this.modifiedAt = content.getModifiedAt();
        this.dones = dones;
    }
}
