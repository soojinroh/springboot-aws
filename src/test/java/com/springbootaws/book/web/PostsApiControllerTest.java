package com.springbootaws.book.web;

import com.springbootaws.book.domain.posts.Posts;
import com.springbootaws.book.domain.posts.PostsRepository;
import com.springbootaws.book.web.dto.PostsSaveRequestDto;
import com.springbootaws.book.web.dto.PostsUpdateRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class PostsApiControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private PostsRepository postsRepository;

    @AfterEach
    void tearDown() {
//        postsRepository.deleteAll();
        ;
    }

    @Test
    public void Posts_등록된다() throws Exception {
        // given
        String title = "title";
        String content = "content";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("author")
                .build();

        // when
        // then
        webTestClient.post().uri("/api/v1/posts")
                .body(Mono.just(requestDto), PostsSaveRequestDto.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(response -> assertThat(response.getResponseBody()).isNotNull());
    }

    @Test
    public void Posts_수정된다() throws Exception {
        // given
        Posts savedPosts = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        Long updateId = savedPosts.getId();
        String expectedTitle = "title2";
        String expectedContent = "content2";

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();

        // when
        // then
        webTestClient.put().uri("/api/v1/posts/{id}", updateId)
                .body(Mono.just(requestDto), PostsUpdateRequestDto.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(response -> assertThat(response.getResponseBody()).isNotNull());

        Posts updatedPosts = postsRepository.findById(updateId).orElseThrow(IllegalArgumentException::new);
        assertThat(updatedPosts.getTitle()).isEqualTo(expectedTitle);
        assertThat(updatedPosts.getContent()).isEqualTo(expectedContent);
    }

}
