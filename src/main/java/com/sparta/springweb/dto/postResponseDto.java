package com.sparta.springweb.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class postResponseDto {
    private Long id;
    private String title;
    private String userName;
    private String contents;
    private String locationName;
    private int partyNum;
    private int joinNum;
    private boolean dones;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime modifiedAt;


    public postResponseDto(Long id, String title, String userName, String contents, String locationName, int partyNum, int joinNum, boolean done, LocalDateTime modifiedAt, int countReply) {
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
}
