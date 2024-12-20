package kr.hbcho90.teststudy.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record SignupRequest(
        @NotBlank(message = "아이디는 필수값입니다.")
        @Size(min = 6, max = 20, message = "아이디는 6자 이상 20자 이하여야 합니다.")
        @Pattern(regexp = "^[A-Za-z0-9]+$", message = "아이디는 영대소문자와 숫자로만 구성되어야 합니다.")
        String userId,

        @NotBlank(message = "비밀번호는 필수입니다.")
        @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하여야 합니다.")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@$%^*])[A-Za-z\\d!@$%^*]{8,20}$",
                 message = "비밀번호는 8자 이상 20자 이하의 영대소문자, 숫자, 특수문자가 포함된 문자열로 구성되어야 합니다.")
        String password,

        String name,

        String email,

        String phoneNumber) {

    public static SignupRequest of(String userId, String password, String name, String email, String phoneNumber) {
        return new SignupRequest(userId, password, name, email, phoneNumber);
    }

}
