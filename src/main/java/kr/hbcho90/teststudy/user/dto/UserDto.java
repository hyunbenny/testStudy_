package kr.hbcho90.teststudy.user.dto;

import kr.hbcho90.teststudy.user.UserEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserDto(
        String id,
        String userId,
        String name,
        String password,
        String email,
        String phoneNumber, 
        boolean deleted, 
        LocalDateTime createdAt,
        LocalDateTime modifiedAt) {
    
    public static UserDto of(String userId, String password, String name, String email, String phoneNumber) {
        return new UserDto(null, userId, name, password, email, phoneNumber, false, null, null);
    }
    
    public static UserDto of(UserEntity entity) {
        return new UserDto(entity.getId(), entity.getUserId(), entity.getName(), null, entity.getEmail(), entity.getPhoneNumber(),
                entity.isDeleted(), entity.getCreatedAt(), entity.getModifiedAt());
    }

    public UserEntity toEntity() {
        return UserEntity.builder()
                .userId(userId)
                .name(name)
                .password(password)
                .email(email)
                .phoneNumber(phoneNumber)
                .build();
    }
    
}
