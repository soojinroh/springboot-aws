package com.springbootaws.book.web;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class IndexControllerTest {
    private static final Logger log = LoggerFactory.getLogger(IndexControllerTest.class);

    @Autowired
    WebTestClient webTestClient;

    @Test
    public void 메인페이지_로딩() throws Exception {
        // given
        webTestClient.get().uri("/")
                .exchange()
                .expectStatus()
                .isOk();

        // when

        // then
    }


}