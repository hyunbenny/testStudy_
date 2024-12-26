package kr.hbcho90.teststudy.post.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public record PostResponse(String code,
                           String message,
                           String title,
                           String content,
                           String author,
                           LocalDateTime createdAt,
                           LocalDateTime modifiedAt) {

    @Builder
    public static PostResponse of(String code, String message, PostDto post) {
        return new PostResponse(code, message, post.title(), post.content(), post.user().userId(), post.createdAt(), post.modifiedAt());
    }
}
