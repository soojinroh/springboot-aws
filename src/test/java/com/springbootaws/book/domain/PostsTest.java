package com.springbootaws.book.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PostsTest {

    @Test
    public void Posts_Lombok_test() throws Exception {
        // given
        Posts post = Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build();

        // when
        String author = post.getAuthor();

        // then
        assertThat(author).isEqualTo("author");
    }


}