package kr.hbcho90.teststudy.post;


import jakarta.persistence.*;
import kr.hbcho90.teststudy.common.util.IdGenerator;
import kr.hbcho90.teststudy.user.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "posts",
        indexes = {
        @Index(name = "idx_post_title", columnList = "title", unique = true)
}
)
@Getter
@ToString
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class PostEntity {

    @Id
    @Column(columnDefinition = "VARCHAR(15)")
    private String id;
    private String title;
    private String content;


    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private boolean deleted;


    @Builder
    public PostEntity(String title, String content, UserEntity user, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = IdGenerator.generateId();
        this.title = title;
        this.content = content;
        this.user = user;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.deleted = false;
    }

    public void delete() {
        this.deleted = true;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
        this.modifiedAt = LocalDateTime.now();
    }
}
