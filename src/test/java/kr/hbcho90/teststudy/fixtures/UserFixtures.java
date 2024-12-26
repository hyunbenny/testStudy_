package kr.hbcho90.teststudy.fixtures;

import kr.hbcho90.teststudy.user.UserEntity;
import kr.hbcho90.teststudy.user.dto.UserDto;

import java.time.LocalDateTime;

public class UserFixtures {
    public static UserEntity createUserEntity() {
        return UserEntity.builder()
                .userId("testId")
                .name("testName")
                .password("testPassword")
                .email("testId@email.com")
                .phoneNumber("01012341234")
                .deleted(false)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();
    }

    public static UserEntity createDeletedUserEntity() {
        return UserEntity.builder()
                .userId("testId2")
                .name("testName2")
                .password("testPassword2")
                .email("testId@email.com2")
                .phoneNumber("01012341235")
                .deleted(true)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();
    }

    public static UserDto createUserDto() {
        return UserDto.of(createUserEntity());
    }

}
