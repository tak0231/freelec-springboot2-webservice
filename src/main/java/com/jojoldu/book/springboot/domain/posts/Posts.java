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

    private String author;

    @Builder
    public Posts(String title, String content, String author){
        this.title = title;
        this.content = content;
        this. author = author;
    }
}
