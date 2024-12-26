package kr.hbcho90.teststudy.post.dto;

import lombok.Builder;

import java.util.List;

public record PostListResponse( int total,
                                int page,
                                int size,
                                String code,
                                String message,
                                List<PostDto>posts) {

    @Builder
    public static PostListResponse of(int total, int page, int size, String code, String message, List<PostDto> posts) {
        return new PostListResponse(total, page, size, code, message, posts);
    }
}
