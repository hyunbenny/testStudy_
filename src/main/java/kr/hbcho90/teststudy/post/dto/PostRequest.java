package kr.hbcho90.teststudy.post.dto;

import kr.hbcho90.teststudy.post.PostEntity;
import kr.hbcho90.teststudy.user.dto.UserDto;
import lombok.Builder;

import java.time.LocalDateTime;

public record PostRequest(String title,
                      String content,
                      String userId) {

    @Builder
    public PostRequest(String title, String content, String userId) {
        this.title = title;
        this.content = content;
        this.userId = userId;
    }
}

