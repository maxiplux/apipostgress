package com.domain.apipostgress;

/**
 * User: franc
 * Date: 09/09/2018
 * Time: 1:17
 */

import com.domain.apipostgress.model.Question;
import com.domain.apipostgress.repository.QuestionRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class QuestionTestApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private QuestionRepository questionRepository;


    @Test
    public void testCreateQuestion() {
        Question tweet = new Question("Que vida mas hermosa", "Que vida mas hermosa");

        webTestClient.post().uri("/questions")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(tweet), Question.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.description").isEqualTo("Que vida mas hermosa")
                .jsonPath("$.title").isEqualTo("Que vida mas hermosa");
    }

    @Test
    public void testGetAllQuestions() {
        webTestClient.get().uri("/questions")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBodyList(Question.class);
    }

    @Test
    public void testGetSingleQuestion() {

        Question tweet = this.questionRepository.save(new Question("Que vida mas hermosa 2", "Que vida mas hermosa 2 "));

        webTestClient.get()
                .uri("/questions/{questionId}", Collections.singletonMap("questionId", tweet.getId()))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(response ->
                        Assertions.assertThat(response.getResponseBody()).isNotNull());
    }

    @Test
    public void testUpdateQuestion() {

        Question tweet = this.questionRepository.save(new Question("Que vida mas hermosa", "Que vida mas hermosa"));
        Question newTweetData = new Question("Que vida mas hermosa 3", "Que vida mas hermosa 3");

        webTestClient.put()
                .uri("/questions/{id}", Collections.singletonMap("id", tweet.getId()))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(newTweetData), Question.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.title").isEqualTo("Que vida mas hermosa 3");
    }

    @Test
    public void testDeleteQuestion() {

        Question tweet = this.questionRepository.save(new Question("Que vida mas hermosa delete", "Que vida mas hermosa delete"));

        webTestClient.delete()
                .uri("/questions/{id}", Collections.singletonMap("id", tweet.getId()))
                .exchange()
                .expectStatus().isOk();
    }
}
