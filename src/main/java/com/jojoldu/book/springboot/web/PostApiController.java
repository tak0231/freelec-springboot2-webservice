package com.jojoldu.book.springboot.web;

import com.jojoldu.book.springboot.service.posts.PostsService;
import com.jojoldu.book.springboot.web.dto.PostsResponseDto;
import com.jojoldu.book.springboot.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

// final 키워드 또는 @NotNull이 붙은 필드에 대해 생성자를 만들어줌
// 의존성주입(DI) 중 Constructor Injection(생성자 주입)을 임의의 코드 없이 자동으로 설정
@RequiredArgsConstructor

// Controller + ResponseBody => 즉, JSON 형태로 객체 데이터를 반환하는 역할
// 메서드 각각에 쓸 필요 없이 전체에 ResponseBody를 한 효과
@RestController
public class PostApiController {
    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto){
        return postsService.save(requestDto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById (@PathVariable Long id){
        return postsService.findById(id);
    }


}

/*
    @RequestBody
    - HTTP 요청의 body 내용을 자바 객체로 맾핑하는 역할

    @ResponseBody
    - 자바 객체를 HTTP 요청의 body 내용으로 매핑하는 역할할

    # HTTP 요청
    - 구조 : start line / headers /body
 */
