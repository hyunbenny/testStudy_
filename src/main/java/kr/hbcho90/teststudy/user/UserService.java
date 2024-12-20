package kr.hbcho90.teststudy.user;

import jakarta.validation.constraints.NotNull;
import kr.hbcho90.teststudy.common.exception.AlreadyUserExistException;
import kr.hbcho90.teststudy.common.exception.InvalidParameterException;
import kr.hbcho90.teststudy.common.exception.UserNotFoundException;
import kr.hbcho90.teststudy.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 사용자 등록(가입)
    public UserDto signUp(UserDto request) {
        userRepository.findByUserId(request.userId()).ifPresent(u -> {throw new AlreadyUserExistException("아이디", request.userId());});
        userRepository.findByEmail(request.email()).ifPresent(u -> {throw new AlreadyUserExistException("이메일", request.email());});

        return UserDto.of(userRepository.save(request.toEntity()));
    }

    // 사용자 정보 조회
    public UserDto getUserInfo(@NotNull String userId, @NotNull String password) {
        UserEntity userEntity = userRepository.findByUserId(userId).orElseThrow(() -> new UserNotFoundException());
        if(userEntity.isDeleted()) throw new UserNotFoundException();
        if(!userEntity.getPassword().equals(password)) throw new InvalidParameterException();

        return UserDto.of(userEntity);
    }

    // 사용자 정보 수정


    // 사용자 삭제(탈퇴)
    public UserDto deleteUser(@NotNull String userId, @NotNull String password) {
        UserEntity userEntity = userRepository.findByUserId(userId).orElseThrow(() -> new UserNotFoundException());
        if(!userEntity.getPassword().equals(password)) throw new InvalidParameterException();

        userEntity.changeUserStatusToDelete();
        userRepository.save(userEntity);

        return UserDto.of(userEntity);
    }

}
