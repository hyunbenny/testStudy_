package kr.hbcho90.teststudy.common.exception;

public class AlreadyUserExistException extends RuntimeException{
    private static final String MESSAGE_FORMAT = "이미 존재하는 %s(%s)입니다.";

    public AlreadyUserExistException(String type, String detail) {
        super(String.format(MESSAGE_FORMAT, type, detail));
    }

}
