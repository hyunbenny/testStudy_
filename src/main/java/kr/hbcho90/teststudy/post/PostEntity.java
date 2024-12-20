package kr.hbcho90.teststudy.post;


import jakarta.persistence.*;
import kr.hbcho90.teststudy.common.util.AuditFields;
import kr.hbcho90.teststudy.user.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Entity
@Table(name = "posts",
        indexes = {
        @Index(name = "idx_post_title", columnList = "title", unique = true)
}
)
@Getter
@ToString
public class PostEntity {

    @Id
    @Column(columnDefinition = "VARCHAR(15)")
    private String id;
    private String title;
    private String content;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity user;

}
