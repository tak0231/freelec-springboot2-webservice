package com.jojoldu.book.springboot.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Getter
@NoArgsConstructor

// # Entity 클래스
// - 실제 DB와 매칭될 클래스
// - 즉, 테이블과 링크될 클래스
@Entity
public class Posts {

    // # Id = > PK(primary key) 필드
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    //
    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT",nullable = false)
    private String content;


    // 굳이 명시하지 않아도 Column 이 됨
    // but 위에서처럼 명시하면 최대크기 등 추가적인 옵션의 변경이 가능함
    private String author;

    // # 빌더 패턴
    // - 디자인 패턴의 일종
    // - 생성자에서 인자가 많을 떄 고려해볼 수 이쓴 패턴
    // - 정보들을 자바 빈즈패턴처럼 받지만, 데이터일관성을 위해 정보들을 다 받은 후에 객체를 생성함
    @Builder
    public Posts(String title, String content, String author){
        this.title = title;
        this.content = content;
        this. author = author;
    }

    /*

    # 점중적 생성자 패턴 = 점층적 생성자 패턴
    - 있어야할 멤버변수를 위해 -> 생성자에 매개변수를 넣음
    - 그리고, 선택적으로 인자를 받기 윟 ㅐ추가적인 생성자를 만듦

    @ 단점
    - 인자들이 많을 수록 생성자가 ㅁ낳아짐
    2.

    # 자바 빈즈 패턴
    - 가독성이 해결됨
    - 대신 코드가 늘어나고 "객체일관성"이 꺠짐

    @ 객체일관성
    - 한번 객체 생성할 떄, 그 객체가 변할 여지가 생기는 것
    - 객체를 생성하고, 그 뒤에 값이 떡칠됨

    ex) Test testInfo = new Test();
    test.setName();
    test.setMath();
    tset.setEng();
    ...
    - 이런식으로 값을 계속 입혀줘야 함

    # 빌더 패턴




    **/
}
