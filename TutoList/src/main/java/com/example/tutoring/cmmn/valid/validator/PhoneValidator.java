package com.example.tutoring.cmmn.valid.validator;

import com.example.tutoring.cmmn.valid.annotation.ValidPhone;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<ValidPhone, String> {

    private static PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

    // @Override
    // public void initialize(ValidUsername constraintAnnotation) {
    //     // 초기화 로직이 필요하다면 여기에 작성 (현재는 필요 없음)
    // }

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext context) {
        if (phone == null) {
            // null일 경우 검증 실패
            return false;
        }

        // 전화번호를 E.164형식으로 변환
		Phonenumber.PhoneNumber parsedNumber = null;
		try {
			parsedNumber = phoneUtil.parse(phone, "KR");
		} catch (NumberParseException e) {
			return false;
		}

        return phoneUtil.isValidNumber(parsedNumber);
    }
}
