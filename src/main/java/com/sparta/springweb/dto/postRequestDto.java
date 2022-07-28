package com.sparta.springweb.dto;

import lombok.Getter;

@Getter
public class postRequestDto {
    private String title;
    private String contents;
    private int partyNum;
    private String locationName;
}