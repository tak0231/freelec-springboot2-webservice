package com.jojoldu.book.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

// # JpaRepository<엔티티 클래스, Pk 타입>
public interface PostsRepository extends JpaRepository<Posts,Long> {

}
