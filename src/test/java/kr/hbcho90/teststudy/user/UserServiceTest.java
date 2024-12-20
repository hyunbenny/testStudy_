package kr.hbcho90.teststudy.user;

import kr.hbcho90.teststudy.common.exception.AlreadyUserExistException;
import kr.hbcho90.teststudy.common.exception.InvalidParameterException;
import kr.hbcho90.teststudy.common.exception.UserNotFoundException;
import kr.hbcho90.teststudy.user.dto.UserDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@DisplayName("회원기능 테스트")
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService sut;
    @Mock
    private UserRepository userRepository;

    @Nested
    @DisplayName("회원 정보 조회 테스트")
    class UserInfo {

        @Test
        @DisplayName("회원 정보 조회 성공")
        void givenUserId_whenGetUserInfo_thenReturnUserInfo() {
            // given
            String userId = "testId";
            String password = "testPassword";
            given(userRepository.findByUserId(userId)).willReturn(Optional.of(UserFixture.createUserEntity()));

            // when
            var userInfo = sut.getUserInfo(userId, password);

            // then
            then(userInfo)
                    .isNotNull()
                    .isInstanceOf(UserDto.class)
                    .hasFieldOrPropertyWithValue("userId", userId)
                    .hasFieldOrPropertyWithValue("password", password);
        }

        @Test
        @DisplayName("회원 정보가 존재하지 않는 경우 예외를 반환한다.")
        void givenUserIdAndPassword_whenUserNotExist_thenThrowException() {
            // given
            String userId = "testId";
            String password = "testPassword";
            given(userRepository.findByUserId(any())).willReturn(Optional.empty());

            // when & then
            thenThrownBy(() -> sut.getUserInfo(userId, password))
                    .isInstanceOf(UserNotFoundException.class)
                    .hasMessage("해당 회원 정보가 존재하지 않습니다.");
        }

        @Test
        @DisplayName("조회한 회원이 삭제된 회원인 경우 예외를 반환한다.")
        void givenUserIdAndPassword_whenUserIdAlreadyDeleted_thenThrowException() {
            // given
            String userId = "testId2";
            String password = "testPassword2";
            UserEntity deletedUserEntity = UserFixture.createDeletedUserEntity();
            given(userRepository.findByUserId(any())).willReturn(Optional.of(deletedUserEntity));

            // when & then
            thenThrownBy(() -> sut.getUserInfo(userId, password))
                    .isInstanceOf(UserNotFoundException.class)
                    .hasMessage("해당 회원 정보가 존재하지 않습니다.");
        }

        @Test
        @DisplayName("조회한 회원 정보의 비밀번호와 전달받은 비밀번호가 다른 경우 예외를 반환한다.")
        void givenUserIdAndWrongPassword_whenPasswordIsWrong_thenThrowException() {
            // given
            String userId = "testId";
            String password = "wrongPassword";
            given(userRepository.findByUserId(any())).willReturn(Optional.of(UserFixture.createUserEntity()));

            // when & then
            thenThrownBy(() -> sut.getUserInfo(userId, password))
                    .isInstanceOf(InvalidParameterException.class)
                    .hasMessage("요청 정보가 잘못되었습니다.");
        }

    }

    @Nested
    @DisplayName("회원가입 테스트")
    class SignUp {
        @Test
        @DisplayName("회원가입 성공")
        void givenSignUpRequest_whenRequestSignUp_thenSaveUserEntity() {
            // given
            given(userRepository.save(any())).willReturn(UserFixture.createUserEntity());
            var userDto = UserFixture.createUserDto();

            // when
            var user = sut.signUp(userDto);

            // then
            verify(userRepository, times(1)).save(any());
            then(user)
                    .isNotNull()
                    .isInstanceOf(UserDto.class)
                    .hasFieldOrProperty("id")
                    .hasFieldOrProperty("createdAt")
                    .hasFieldOrProperty("modifiedAt")
                    .hasFieldOrPropertyWithValue("userId", userDto.userId())
                    .hasFieldOrPropertyWithValue("name", userDto.name())
                    .hasFieldOrPropertyWithValue("email", userDto.email())
                    .hasFieldOrPropertyWithValue("phoneNumber", userDto.phoneNumber())
            ;
        }

        @Test
        @DisplayName("회원가입 시 중복된 아이디가 있으면 예외를 반환한다.")
        void givenSignUpRequest_whenSameUserIdExist_thenThrowException() {
            // given
            given(userRepository.findByUserId(any())).willReturn(Optional.of(UserFixture.createUserEntity()));
            var userDto = UserFixture.createUserDto();

            // when & then
            thenThrownBy(() -> sut.signUp(userDto))
                    .isInstanceOf(AlreadyUserExistException.class)
                    .hasMessage(String.format("이미 존재하는 아이디(%s)입니다.", userDto.userId()));
        }

        @Test
        @DisplayName("회원가입 시 중복된 이메일이 있으면 예외를 반환한다.")
        void givenSignUpRequest_whenSameEmailExist_thenThrowException() {
            // given
            given(userRepository.findByEmail(any())).willReturn(Optional.of(UserFixture.createUserEntity()));
            var userDto = UserFixture.createUserDto();

            // when & then
            thenThrownBy(() -> sut.signUp(userDto))
                    .isInstanceOf(AlreadyUserExistException.class)
                    .hasMessage(String.format("이미 존재하는 이메일(%s)입니다.", userDto.email()));
        }

    }

    @Nested
    @DisplayName("회원 정보 수정 테스트")
    class ModifyUserInfo {

    }

    @Nested
    @DisplayName("회원 탈퇴 테스트")
    class UserDelete {
        @Test
        @DisplayName("회원 정보 삭제(탈퇴)하면 deleted 상태를 true로 변경한다.")
        void givenUserIdAndPassword_whenUserExistAndPasswordIsValid_thenChangeUserDeletedStatus(){
            // given
            String userId = "testId";
            String password = "testPassword";
            given(userRepository.findByUserId(userId)).willReturn(Optional.of(UserFixture.createUserEntity()));

            // when
            var user = sut.deleteUser(userId, password);

            verify(userRepository, times(1)).save(any());
            then(user)
                    .isNotNull()
                    .hasFieldOrPropertyWithValue("userId", userId)
                    .hasFieldOrPropertyWithValue("deleted", true);
        }

        @Test
        @DisplayName("회원 정보 삭제(탈퇴) 시 비밀번호가 올바르지 않으면 예외를 반환한다.")
        void givenUserIdAndPassword_whenPasswordIsInvalid_thenThrowException(){
            // given
            String userId = "testId";
            String password = "wrongPassword";
            given(userRepository.findByUserId(userId)).willReturn(Optional.of(UserFixture.createUserEntity()));

            // when & then
            thenThrownBy(() -> sut.deleteUser(userId, password))
                    .isInstanceOf(InvalidParameterException.class)
                    .hasMessage("요청 정보가 잘못되었습니다.");
        }

    }


    @Disabled
    @Nested
    @DisplayName("비밀번호 검증 테스트 - Controller테스트에서 해야 함.")
    class PasswordValidation {

        @Nested
        @DisplayName("비밀번호 길이 검증")
        class PasswordLengthCheck {
            @Test
            @DisplayName("회원가입 시 비밀번호는 8자 이상 20자 이하인 경우 성공")
            void givenSignUpRequest_whenPasswordLengthIs8To20_thenOK() {
                // given

                // when

                // then
            }

            @Test
            @DisplayName("회원가입 시 비밀번호가 8자 미만인 경우 예외를 반환한다.")
            void givenSignUpRequest_whenPasswordLengthLt8_thenThrowException() {
                // given

                // when

                // then
            }

            @Test
            @DisplayName("회원가입 시 비밀번호가 20자 초과인 경우 예외를 반환한다.")
            void givenSignUpRequest_whenPasswordLengthGt20_thenThrowException() {
                // given

                // when

                // then
            }
        }

    }
    
}