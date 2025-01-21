package dudu.webservice.web;

import dudu.webservice.web.domain.posts.Posts;
import dudu.webservice.web.domain.posts.PostsRepository;
import dudu.webservice.web.dto.PostsSaveRequestDto;
import dudu.webservice.web.dto.PostsUpdateRequestDto;
import org.aspectj.lang.annotation.After;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private PostsRepository postsRepository;

    @AfterEach
    public void cleanup(){
        postsRepository.deleteAll();
    }

    @Test
    public void Posts_등록된다(){
        //given
        String title = "test";
        String content = "test";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder().title(title).content(content).author("aa").build();
        String url  = "http://localhost:" + port + "/api/v1/posts";
        //when
        ResponseEntity<Long> response = restTemplate.postForEntity(url, requestDto, Long.class);
        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isGreaterThan(0L);
        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);

    }

    @Test
    public void update(){
        //given
        Posts savePosts = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());


        Long id = savePosts.getId();
        String expectedTitle = "test";
        String expectedContent = "test";

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder().title(expectedTitle).content(expectedContent).build();
        String url  = "http://localhost:" + port + "/api/v1/posts/" + id;
        HttpEntity<PostsUpdateRequestDto> entity = new HttpEntity<>(requestDto);

        //when
        ResponseEntity<Long> response = restTemplate.exchange(url, HttpMethod.PUT, entity, Long.class);
        //then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isEqualTo(id);
        List<Posts> all = postsRepository.findAll();
        Assertions.assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        Assertions.assertThat(all.get(0).getContent()).isEqualTo(expectedContent);

    }

}