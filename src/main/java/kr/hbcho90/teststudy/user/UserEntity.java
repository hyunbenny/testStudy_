package kr.hbcho90.teststudy.user;

import jakarta.persistence.*;
import kr.hbcho90.teststudy.common.util.IdGenerator;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users",
        indexes = {
        @Index(name = "idx_user_id", columnList = "userId", unique = true),
        @Index(name = "idx_user_name", columnList = "name", unique = true),
        @Index(name = "idx_user_email", columnList = "email", unique = true)
})
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Getter
@ToString
public class UserEntity {

    @Id
    @Column(length = 15, columnDefinition = "VARCHAR(15)")
    private String id;
    private String userId;
    private String name;
    private String password;
    private String email;
    private String phoneNumber;
    private boolean deleted = false;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @Builder
    public UserEntity(String userId, String name, String password, String email, String phoneNumber, boolean deleted, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = IdGenerator.generateId();
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.deleted = deleted;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public void changeUserStatusToDelete() {
        this.deleted = true;
    }
}
