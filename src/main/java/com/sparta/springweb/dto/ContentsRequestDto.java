package com.sparta.springweb.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ContentsRequestDto {
    String title;
    String imageUrl;
    String contents;
    int partyNum;
    String locationName;
}