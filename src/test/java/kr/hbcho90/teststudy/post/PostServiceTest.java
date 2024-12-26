package kr.hbcho90.teststudy.post;

import jakarta.persistence.EntityNotFoundException;
import kr.hbcho90.teststudy.fixtures.PostFixtures;
import kr.hbcho90.teststudy.post.dto.PostDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.verify;

@DisplayName("게시글 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class PostServiceTest {
    @InjectMocks
    private PostService sut;

    @Mock
    private PostRepository postRepository;

    @Nested
    @DisplayName("게시글 등록 테스트")
    class PostRegister {

        @Test
        @DisplayName("게시글 등록을 요청하면 게시글을 저장한다.")
        void givenPostInfo_whenSavingPost_thenSavePostInfo() {
            // given
            PostEntity postEntity = PostFixtures.getPostEntity();
            given(postRepository.save(any())).willReturn(postEntity);
            PostDto postDto = PostDto.of(postEntity);


            // when
            PostDto result = sut.registerPost(postDto);

            // then
            verify(postRepository).save(any());
            then(result).isNotNull()
                    .hasFieldOrProperty("id")
                    .hasFieldOrPropertyWithValue("title", postDto.title())
                    .hasFieldOrPropertyWithValue("content", postDto.content())
                    .hasFieldOrPropertyWithValue("user", postDto.user())
            ;
        }

    }

    @Nested
    @DisplayName("게시글 조회 테스트")
    class PostGet {

        @Test
        @DisplayName("게시글 아이디로 조회하면 게시글 정보를 반환한다.")
        void givenPostId_whenSearchingPostInfo_thenReturnPostInfo() {
            // given
            PostEntity postEntity = PostFixtures.getPostEntity();
            given(postRepository.findById(any())).willReturn(Optional.of(postEntity));

            // when
            PostDto result = sut.getPostInfo(postEntity.getId());

            // then
            then(result).isNotNull()
                    .hasFieldOrPropertyWithValue("id", postEntity.getId())
                    .hasFieldOrPropertyWithValue("title", postEntity.getTitle())
                    .hasFieldOrPropertyWithValue("content", postEntity.getContent())
                    .hasFieldOrPropertyWithValue("user.id", postEntity.getUser().getId())
                    .hasFieldOrPropertyWithValue("user.userId", postEntity.getUser().getUserId())
                    .hasFieldOrPropertyWithValue("createdAt", postEntity.getCreatedAt())
                    .hasFieldOrPropertyWithValue("modifiedAt", postEntity.getModifiedAt())
            ;
        }

        @Test
        @DisplayName("게시글 아이디로 조회할 때 게시글 정보가 없으면 예외를 반환한다.")
        void givenPostId_whenSearchingPostInfo_thenReturnException() {
            // given
            PostEntity postEntity = PostFixtures.getPostEntity();
            given(postRepository.findById(any())).willReturn(Optional.empty());

            // when & then
            thenThrownBy(() -> sut.getPostInfo(postEntity.getId()))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessage("해당 게시글이 존재하지 않습니다.")
            ;
        }
    }

    @Nested
    @DisplayName("게시글 리스트 조회 테스트")
    class PostList {

        @Test
        @DisplayName("게시글 리스트 조회 결과가 없으면 빈 리스트를 반환한다.")
        void givenPageable_whenSearchingResultIsNothing_thenReturnEmptyList() {
            // given
            final int PAGE = 10;
            final int SIZE = 10;
            given(postRepository.findAll((Pageable) any())).willReturn(Page.empty());

            // when
            Page<PostDto> result = sut.getPostList(PAGE, SIZE);

            // then
            then(result).isEmpty();
        }


        @Test
        @DisplayName("게시글 리스트를 조회하면 게시글 리스트를 반환한다.")
        void whenSearchingPostList_thenReturnPostList() {
            // given
            final int SIZE = 10;
            final int PAGE = 1;
            Pageable pageable = PageRequest.of(PAGE, SIZE);
            List<PostEntity> postEntities = PostFixtures.getPostEntities(10);
            List<PostDto> postDtos = postEntities.stream().map(postEntity -> PostDto.of(postEntity)).collect(Collectors.toList());

            given(postRepository.findAll(pageable)).willReturn(new PageImpl<>(postEntities, pageable, 10));

            // when
            Page<PostDto> result = sut.getPostList(PAGE, SIZE);

            // then
            then(result).isNotEmpty().hasSize(SIZE);
            assertThat(result).hasSize(SIZE);
            assertThat(result.getContent().get(0)).hasFieldOrPropertyWithValue("title", "this is title1");
            assertThat(result.getContent().get(0)).hasFieldOrPropertyWithValue("content", postDtos.get(0).content());
            assertThat(result.getContent().get(SIZE-1)).hasFieldOrPropertyWithValue("title", postDtos.get(SIZE-1).title());
            assertThat(result.getContent().get(SIZE-1)).hasFieldOrPropertyWithValue("content", postDtos.get(SIZE-1).content());
        }

        @Test
        @DisplayName("게시글 리스트를 조회할 때, 총 페이지수를 초과한 요청을 하면 마지막 페이지를 반환한다.")
        void givenPageable_whenRequestIsOverData_thenReturnEmptyList() {
            // given
            final int PAGE = 10;
            final int SIZE = 10;
            Pageable pageable = PageRequest.of(PAGE, SIZE);
            Pageable lastPage = PageRequest.of(8, SIZE);
            List<PostEntity> postEntities = PostFixtures.getPostEntities(10);
            List<PostDto> postDtos = postEntities.stream().map(postEntity -> PostDto.of(postEntity)).collect(Collectors.toList());

            given(postRepository.findAll(pageable)).willReturn(new PageImpl<>(Collections.emptyList(), pageable, 90));
            given(postRepository.findAll(lastPage)).willReturn(new PageImpl<>(postEntities, lastPage, 90));

            // when & then
//            thenThrownBy(() -> sut.getPostList(PAGE, SIZE))
//                    .isInstanceOf(IllegalArgumentException.class)
//                    .hasMessage("페이지가 존재하지 않습니다. 페이지 번호를 확인해주세요.")
//            ;

            // when
            Page<PostDto> result = sut.getPostList(PAGE, SIZE);

            // then
            then(result)
                    .isNotEmpty()
                    .hasSize(10)
            ;

            assertThat(result.getTotalPages()).isEqualTo(9);
            assertThat(result.getNumberOfElements()).isEqualTo(10);
            assertThat(result.getTotalElements()).isEqualTo(90);
            assertThat(result.getPageable().getPageNumber()).isEqualTo(8);
            assertThat(result.getContent()).isNotEmpty();
            assertThat(result.getContent().get(0)).hasFieldOrPropertyWithValue("title", postDtos.get(0).title());
            assertThat(result.getContent().get(9)).hasFieldOrPropertyWithValue("title", postDtos.get(9).title());
        }
    }

    @Nested
    @DisplayName("게시글 수정 테스트")
    class PostModify {

    }

    @Nested
    @DisplayName("게시글 삭제 테스트")
    class PostDelete {

    }

}