package kr.hbcho90.teststudy.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hbcho90.teststudy.TestStudyApplication;
import kr.hbcho90.teststudy.fixtures.PostFixtures;
import kr.hbcho90.teststudy.post.dto.PostDto;
import kr.hbcho90.teststudy.post.dto.PostRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("게시글 컨트롤러 테스트")
@WebMvcTest(PostController.class)
@Import(TestStudyApplication.class)
public class PostControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private PostService postService;


    @Nested
    @DisplayName("게시글 조회 테스트")
    class PostSearch {

        @Test
        @WithAnonymousUser
        @DisplayName("게시글 페이지를 호출하면 게시글 리스트를 반환한다.")
        void given_when_then() throws Exception {
            // given
//            List<PostDto> postDots = PostFixtures.getPostEntities(10).stream().map(PostDto::of).collect(Collectors.toList());
            given(postService.getPostList(1, 10)).willReturn(new PageImpl<>(PostFixtures.getPostEntities(10).stream().map(PostDto::of).collect(Collectors.toList()), PageRequest.of(1 - 1, 10), 10)); // PageRequest는 0부터 시작함.

            // when & then
            mvc.perform(get("/api/v1/posts")
                            .param("page", "1")
                            .param("size", "10")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.total").value(10))
                    .andExpect(jsonPath("$.code").value("0000"))
                    .andExpect(jsonPath("$.message").value("SUCCESS"))
                    .andExpect(jsonPath("$.posts[0].title").value("this is title1"))
                    .andExpect(jsonPath("$.posts[9].title").value("this is title10"))
                    .andDo(print())
            ;

        }

        @Test
        @WithAnonymousUser
        @DisplayName("게시글 상세 페이지를 호출하면 게시글 정보를 반환한다.")
        void givenPostId_whenRequestPostInfo_thenReturnPostInfo() throws Exception {
            // given
            PostEntity postEntity = PostFixtures.getPostEntity();
            given(postService.getPostInfo(any())).willReturn(PostDto.of(postEntity));

            // when & then
            mvc.perform(get("/api/v1/posts/{postId}", postEntity.getId())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value("0000"))
                    .andExpect(jsonPath("$.message").value("SUCCESS"))
                    .andExpect(jsonPath("$.title").value("this is title"))
                    .andDo(print())
            ;
        }

    }

    @Nested
    @DisplayName("테스트 등록 테스트")
    class savePost {

        @Test
        @WithMockUser
        @DisplayName("로그인하지 않은 사용자는 게시글을 등록할 수 없다.")
        void givenPostRequest_whenNotLoggedInUser_thenReturnUnAuthorizedException() throws Exception {
            // given
            String title = "hello world";
            String content = "This is content..";
            String userId = "testUser";

            // when
            mvc.perform(post("/api/v1/posts")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(PostRequest.builder().title(title).content(content).userId(userId).build())))
                    .andDo(print())
                    .andExpect(status().isUnauthorized())
            ;

        }

        @Test
        @WithMockUser
        @DisplayName("로그인하지 않은 사용자는 게시글을 등록할 수 없다.")
        void givenPostRequest_whenWithLoggedInUser_thenSavePost() throws Exception {
            // given
            String title = "hello world";
            String content = "This is content..";
            String userId = "testUser";

            // when
            mvc.perform(post("/api/v1/posts")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(PostRequest.builder().title(title).content(content).userId(userId).build())))
                    .andDo(print())
                    .andExpect(status().isOk())
            ;

        }
    }
}
