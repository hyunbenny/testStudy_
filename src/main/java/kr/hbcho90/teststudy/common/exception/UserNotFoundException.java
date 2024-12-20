package kr.hbcho90.teststudy.common.exception;

public class UserNotFoundException extends RuntimeException{

    private static final String MESSAGE = "해당 회원 정보가 존재하지 않습니다.";

    public UserNotFoundException() {
        super(MESSAGE);
    }
    public UserNotFoundException(String message) {
        super(message);
    }
}
