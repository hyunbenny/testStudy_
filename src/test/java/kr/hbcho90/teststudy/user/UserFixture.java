package kr.hbcho90.teststudy.user;

import kr.hbcho90.teststudy.common.util.IdGenerator;
import kr.hbcho90.teststudy.user.dto.UserDto;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

public class UserFixture {
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
        return UserDto.of("testId", "testPassword", "testName", "testId@email.com", "01012341234");
    }

}
