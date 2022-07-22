package com.sparta.springweb.dto;

import lombok.Getter;

@Getter
public class ContentsRequestDto {
    String title;
    String nickName;
    String imageUrl;
    String contents;
    int partyNum;
    int joinNum;
    boolean done;
    String locationName;
}