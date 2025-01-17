package com.example.tutoring.cmmn.valid.validator;

import com.example.tutoring.cmmn.valid.annotation.ValidPassword;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {
    
    private static final String PASSWORD_PATTERN = 
        "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$";
    // 최소 8자, 최대 20자, 최소 하나의 문자, 하나의 숫자, 하나의 특수문자 포함

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
        // 초기화 로직이 필요한 경우 여기에 작성
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            // null일 경우 검증 실패 (또는 다른 규칙 적용)
            return false;
        }
        // 비밀번호가 패턴에 맞는지 확인
        return password.matches(PASSWORD_PATTERN);
    }
}
