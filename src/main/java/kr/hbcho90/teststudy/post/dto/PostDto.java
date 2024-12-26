package kr.hbcho90.teststudy.post.dto;

import kr.hbcho90.teststudy.post.PostEntity;
import kr.hbcho90.teststudy.user.dto.UserDto;
import lombok.Builder;

import java.time.LocalDateTime;

public record PostDto(String id,
                      String title,
                      String content,
                      UserDto user,
                      LocalDateTime createdAt,
                      LocalDateTime modifiedAt) {

    @Builder
    public PostDto(String id, String title, String content, UserDto user, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.user = user;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static PostDto of(String title, String content, UserDto user, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        return PostDto.builder()
                .title(title)
                .content(content)
                .user(user)
                .createdAt(createdAt)
                .modifiedAt(modifiedAt)
                .build();
    }

    public static PostDto of(String id, String title, String content, UserDto user, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        return PostDto.builder()
                .id(id)
                .title(title)
                .content(content)
                .user(user)
                .createdAt(createdAt)
                .modifiedAt(modifiedAt)
                .build();
    }

    public static PostDto of(PostEntity entity) {
        return PostDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .user(UserDto.of(entity.getUser()))
                .createdAt(entity.getCreatedAt())
                .modifiedAt(entity.getModifiedAt())
                .build();
    }

    public PostEntity toEntity() {
        return PostEntity.builder()
                .title(title)
                .content(content)
                .user(user.toEntity())
                .createdAt(createdAt)
                .modifiedAt(modifiedAt)
                .build();
    }
}
