package com.jojoldu.book.springboot.dto;

import com.jojoldu.book.springboot.web.dto.HelloResponseDto;
import org.junit.Test;

// # import static
// - 클래스의 정적 메소드 / 정적 필드를 클래스명 없이 사용 가능해짐
// - 클래스 내에 동일한 이름의 메소드 / 필드 존재시, 클래스 자신의 메소드를 우선하므로 주의

// # assertj
// - 테스트 검증 라이브러리
import static org.assertj.core.api.Assertions.assertThat;

public class HelloResponseDtoTest {
    @Test
    public void 롬복_기능_테스트(){
        String name = "test";
        int amount = 1000;

        // when
        HelloResponseDto dto = new HelloResponseDto(name,amount);

        // then
        // assertTHat
        // - 검증 대상을메소드 인자로 받음, 메소드 체이닝 지원

        // # 메소드 체이닝
        // - OOP에서 여러 메소드르 이어서 호출하는 문법
        // - 메소드가 객체(this)를 반환하여 여러 메소드를 순차적으로 선언할 수 있도록 함
        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getAmount()).isEqualTo(amount);
    }
}
