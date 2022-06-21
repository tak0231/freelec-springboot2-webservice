package com.jojoldu.book.springboot.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter

// @RequiredArgsConstructor
// - @NotNull이나 final 키워드를 사용하고 있는 속성(attribute)들만으로 이루어진 생성자를 자동으로 만들어줌
@RequiredArgsConstructor
public class HelloResponseDto {

    /*
        variable not initialized in the default constructor 에러 발생

    */
    private final String name;
    private final int amount;
}
