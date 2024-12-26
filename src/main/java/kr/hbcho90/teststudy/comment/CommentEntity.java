package kr.hbcho90.teststudy.comment;

import jakarta.persistence.*;
import kr.hbcho90.teststudy.common.util.IdGenerator;
import kr.hbcho90.teststudy.post.PostEntity;
import lombok.*;

import java.time.LocalDateTime;

//@Entity
@Table(name = "comments")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentEntity {

    @Id
    @Column(length = 15)
    private String id;

    private String comment;
    private LocalDateTime createdAt;
    private String createdBy;

    @ManyToOne
    @JoinColumn(name = "postId")
    private PostEntity post;

    @Builder
    public CommentEntity(String comment, LocalDateTime createdAt, String createdBy, PostEntity post) {
        this.id = IdGenerator.generateId();
        this.comment = comment;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.post = post;
    }
}
