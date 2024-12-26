package kr.hbcho90.teststudy.post;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;


//@SpringBootTest
//@Transactional
//@Rollback
//@ExtendWith(SpringExtension.class)
@DataJpaTest
@Sql({"/sql/post-test-data.sql"})
@DisplayName("게시글 리포지토리 테스트")
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Test
    @DisplayName("게시글 단건 조회 테스트")
    void findById() {
        // given
        String id = "95f7175cddf346a";

        // when
        PostEntity postEntity = postRepository.findById(id).get();

        // then
        assertThat(postEntity).isNotNull();
        assertThat(postEntity.getId()).isEqualTo("95f7175cddf346a");
        assertThat(postEntity.getUser().getId()).isEqualTo("649466541960560");
        assertThat(postEntity.getUser().getUserId()).isEqualTo("test1");
        assertThat(postEntity.getUser().getName()).isEqualTo("테스트1");

    }

    @Test
    @DisplayName("게시글 리스트 조회 테스트")
    void findPosts() {
        // when
        Pageable pageable = Pageable.ofSize(10);
        Page<PostEntity> result = postRepository.findAll(pageable);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isEqualTo(12L);
        assertThat(result.getNumberOfElements()).isEqualTo(10);
        assertThat(result.getContent().get(0).getTitle()).isEqualTo("test title 1");
    }

    @Test
    @DisplayName("사용자별 게시글 리스트 조회 테스트")
    void findPostsByUserId() {
        // when
        String userId = "649466541960560";
        Pageable pageable = Pageable.ofSize(10);
        Page<PostEntity> result = postRepository.findAllByUserId(pageable, userId);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isEqualTo(2L);
        assertThat(result.getNumberOfElements()).isEqualTo(2L);
        assertThat(result.getContent().get(0).getTitle()).isEqualTo("test title 1");
        assertThat(result.getContent().get(1).getTitle()).isEqualTo("test title 12");
    }

    @Test
    @DisplayName("게시글 삭제 테스트")
    void givenPostId_whenDeletingPost_thenDeletePost() {
        // given
        String id = "95f7175cddf346a";

        // when
        postRepository.deleteById(id);

        // then
        assertThat(postRepository.findById(id)).isEmpty();
        assertFalse(postRepository.findById(id).isPresent());
    }

    @Test
    @DisplayName("게시글 수정 테스트")
    void givenPostEntity_whenUpdatingPost_thenUpdatePost() {
        // given
        PostEntity postEntity = postRepository.findById("585op20076b4294").get();
        postEntity.update("update title 10", "update content 10");

        // when
        PostEntity updatedPostEntity = postRepository.save(postEntity);

        // then
        assertThat(updatedPostEntity).isNotNull();
        assertThat(updatedPostEntity.getTitle()).isEqualTo("update title 10");
        assertThat(updatedPostEntity.getContent()).isEqualTo("update content 10");
        assertThat(updatedPostEntity.getModifiedAt()).isNotNull();
    }


}