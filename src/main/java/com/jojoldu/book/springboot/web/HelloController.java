package com.jojoldu.book.springboot.web;

import com.jojoldu.book.springboot.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// # @RestController
// - JSON을 반환하느 컨트롤러로 만듦
// - 예전에는 @ResponseBody를 각각 메소드마다 해줬지만
// - 지금은 이렇게 한번에 처리
@RestController
public class HelloController {

    // # GetMapping
    // HTTP Method인Get의 요청을 받을 수 있는 API를만들어 줌
    // -> 예전에는 @RequestMapping(method = RequestMethod.GET)으로 사용이 됨
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("/hello/dto")

    // # @RequestParam("name")
    // - 외부에서 API로 넘긴 파라미터를 가져오는어노테이션
    // - "name"이라는 이름의 파라미터(주소로넘겨받은 파라미터) 값을 가지고 옴
    // 그리고 뒤의 변수에 담음(저장)
    public HelloResponseDto helloDto(@RequestParam("name") String name,
                                     @RequestParam("amount") int amount){

        return new HelloResponseDto(name,amount);
    }
}
