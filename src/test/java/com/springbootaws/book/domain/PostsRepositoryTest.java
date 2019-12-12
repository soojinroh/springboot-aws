package com.springbootaws.book.domain;

import com.springbootaws.book.domain.posts.Posts;
import com.springbootaws.book.domain.posts.PostsRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PostsRepositoryTest {

    private static final Logger log = LoggerFactory.getLogger(PostsRepositoryTest.class);

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    EntityManager em;


    @AfterEach
    void tearDown() {
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글_저장_불러오기() throws Exception {
        // given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("author")
                .build());

        // when
        List<Posts> postsList = postsRepository.findAll();

        // then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    public void BaseTimeEntity_등록() throws Exception {
        // given
        LocalDateTime now = LocalDateTime.now();
        Posts savedPosts = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        // when
        Posts findPosts = postsRepository.findById(savedPosts.getId()).orElseThrow();

        // then
        log.info(">>>>>>>>>>>>>>> now=" + now);
        log.info(">>>>>>>>>>>>>>> createDate=" + findPosts.getCreatedDate());
        log.info(">>>>>>>>>>>>>>> modifiedDate=" + findPosts.getModifiedDate());

        assertThat(findPosts.getCreatedDate()).isAfter(now);
        assertThat(findPosts.getModifiedDate()).isAfter(now);
    }

    @Test
    @Transactional
    public void modifiedTime_테스트() throws Exception {
        // given
        Posts savedPosts = Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build();
        em.persist(savedPosts);

        // when
        Posts findPosts = em.find(Posts.class, 1L);
        findPosts.update("updatedTitle", "updatedContents");
        em.flush();

        // then
        log.info(">>>>>>>>>>>>>>> createDate=" + findPosts.getCreatedDate());
        log.info(">>>>>>>>>>>>>>> modifiedDate=" + findPosts.getModifiedDate());

        assertThat(findPosts.getCreatedDate()).isBefore(findPosts.getModifiedDate());
    }


}
