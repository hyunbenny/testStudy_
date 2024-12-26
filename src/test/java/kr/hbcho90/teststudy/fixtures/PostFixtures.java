package kr.hbcho90.teststudy.fixtures;

import kr.hbcho90.teststudy.post.PostEntity;
import kr.hbcho90.teststudy.post.dto.PostDto;
import kr.hbcho90.teststudy.post.dto.PostRequest;
import kr.hbcho90.teststudy.user.UserEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PostFixtures {

    public static PostEntity getPostEntity() {
        return PostEntity.builder()
                .title("this is title")
                .content("hello world. this is content")
                .user(UserFixtures.createUserEntity())
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();
    }

    public static List<PostEntity> getPostEntities(int listCount) {
        List<PostEntity> postEntities = new ArrayList<>();
        UserEntity user = UserFixtures.createUserEntity();
        for (int i = 1; i <= listCount; i++) {
            postEntities.add(PostEntity.builder()
                    .title("this is title" + i)
                    .content("hello world. this is content" + i)
                    .user(user)
                    .createdAt(LocalDateTime.now())
                    .modifiedAt(LocalDateTime.now())
                    .build());
        }
        return postEntities;
    }

    public static PostDto createPostDto() {
        return PostDto.of("this is title", "hello world. this is content", UserFixtures.createUserDto(), LocalDateTime.now(), LocalDateTime.now());
    }

}
