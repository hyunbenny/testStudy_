package kr.hbcho90.teststudy.common.exception;

public class InvalidParameterException extends RuntimeException{

    private static final String MESSAGE = "요청 정보가 잘못되었습니다.";

    public InvalidParameterException() {
        super(MESSAGE);
    }
    public InvalidParameterException(String message) {
        super(message);
    }
}
