/**
 * Password Validator
 * 최소 8자, 최대 20자, 최소 하나의 문자, 하나의 숫자, 하나의 특수문자를 포함해야 합니다.
 */
export function isValidPassword(password) {
  if (!password) return false;

  const PASSWORD_PATTERN =
    /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,20}$/;

  return PASSWORD_PATTERN.test(password);
}

/**
 * 사용자 이름 Validator
 * - 6자 이상 20자 이하
 * - 영어 알파벳과 숫자만 허용
 */

export function isValidUsername(username) {
  if (!username) return false;

  const USERNAME_PATTERN = /^[A-Za-z0-9]{6,20}$/;
  return USERNAME_PATTERN.test(username);
}
