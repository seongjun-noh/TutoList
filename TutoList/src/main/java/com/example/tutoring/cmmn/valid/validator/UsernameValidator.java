package com.example.tutoring.cmmn.valid.validator;

import com.example.tutoring.cmmn.valid.annotation.ValidUsername;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UsernameValidator implements ConstraintValidator<ValidUsername, String> {

    private static final String USERNAME_PATTERN = "^[A-Za-z0-9]{6,20}$";
    // 6자 이상 20자 이하의 영어 알파벳과 숫자만 허용

    @Override
    public void initialize(ValidUsername constraintAnnotation) {
        // 초기화 로직이 필요하다면 여기에 작성 (현재는 필요 없음)
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if (username == null) {
            // null일 경우 검증 실패
            return false;
        }
        // 사용자 이름이 패턴에 맞는지 확인
        return username.matches(USERNAME_PATTERN);
    }
}
