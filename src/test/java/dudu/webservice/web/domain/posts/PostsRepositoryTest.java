package dudu.webservice.web.domain.posts;

import org.aspectj.lang.annotation.After;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
class PostsRepositoryTest {

    @Autowired
    private PostsRepository postsRepository;

    @After("")
    public void cleanup(){
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글_불러오기(){
        //given
        String title = "title";
        String content = "content";
        postsRepository.save(Posts.builder().title(title).content(content).author("aa").build());
        //when
        List<Posts> posts = postsRepository.findAll();

        //then
        Posts post = posts.get(0);
        Assertions.assertThat(post.getTitle()).isEqualTo(title);
        Assertions.assertThat(post.getContent()).isEqualTo(content);

    }
}