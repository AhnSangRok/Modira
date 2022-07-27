package com.sparta.springweb.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class postRequestDto {
    private String title;
    private String contents;
    private int partyNum;
    private String locationName;
}