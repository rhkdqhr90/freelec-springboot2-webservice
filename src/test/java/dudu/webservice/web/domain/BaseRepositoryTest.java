package dudu.webservice.web.domain;

import dudu.webservice.web.domain.posts.Posts;
import dudu.webservice.web.domain.posts.PostsRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class BaseRepositoryTest {
    @Autowired
    PostsRepository postsRepository;
    @Test
    public void BaseTimeEntity(){
        //given
        LocalDateTime now = LocalDateTime.of(2019,6,4,0,0,0,0);
        postsRepository.save(Posts.builder().title("title").content("content").author("author").build());
        //when
        List<Posts> postsList = postsRepository.findAll();
        //then

        Posts posts = postsList.get(0);
        System.out.println(">>>>>>>>create Date"+posts.getCreatedDate()+">>modifyDate"+posts.getModifiedDate()+"<<<<<<");
        Assertions.assertThat(posts.getCreatedDate()).isAfter(now);
        Assertions.assertThat(posts.getModifiedDate()).isAfter(now);
    }
}
