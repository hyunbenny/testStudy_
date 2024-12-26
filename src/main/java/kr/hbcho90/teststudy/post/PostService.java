package kr.hbcho90.teststudy.post;

import jakarta.persistence.EntityNotFoundException;
import kr.hbcho90.teststudy.post.dto.PostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public PostDto registerPost(PostDto postDto) {
        return PostDto.of(postRepository.save(postDto.toEntity()));
    }

    public PostDto getPostInfo(String id) {
        PostEntity postEntity = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("해당 게시글이 존재하지 않습니다."));
        return PostDto.of(postEntity);
    }


    public Page<PostDto> getPostList(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PostEntity> entities = postRepository.findAll(pageable);

//        if (pageable.getPageNumber() >= entities.getTotalPages()){
//            throw new IllegalArgumentException("페이지가 존재하지 않습니다. 페이지 번호를 확인해주세요.");
//        }

        if (pageable.getPageNumber() >= entities.getTotalPages() && entities.getTotalPages() > 0) {
            Pageable lastPage = PageRequest.of(entities.getTotalPages() - 1, pageable.getPageSize());
            return postRepository.findAll(lastPage).map(PostDto::of);
        }

        return entities.map(PostDto::of);
    }
    // 게시글 등록


    // 게시글 조회


    // 게시글 수정


    // 게시글 삭제

}
