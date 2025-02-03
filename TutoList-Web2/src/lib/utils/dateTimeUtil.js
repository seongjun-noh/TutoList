import moment from "moment/moment";
import ko from "moment/locale/ko";

/**
 * 주어진 날짜 문자열을 입력 형식(inputFormat)을 기반으로 파싱하고,
 * 출력 형식(outputFormat)으로 변환하여 반환하는 함수.
 *
 * @param {string} dateString - 원본 날짜 문자열 (예: "2025-02-03")
 * @param {string} [inputFormat="YYYY-MM-DD"] - 원본 문자열의 형식 (기본값: "YYYY-MM-DD")
 * @param {string} outputFormat - 변환 후의 형식 (예: "YYYY년 MM월 DD일")
 * @returns {string} 지정한 출력 형식으로 변환된 날짜 문자열
 */
export function formatDate(dateString, inputFormat = "YYYY-MM-DD", outputFormat) {
  return moment(dateString, inputFormat).format(outputFormat);
}

/**
 * 주어진 시간 문자열을 입력 형식(inputFormat)을 기반으로 파싱하고,
 * 출력 형식(outputFormat)으로 변환하여 반환하는 함수.
 *
 * @param {string} timeString - 원본 시간 문자열 (예: "14:30:45")
 * @param {string} inputFormat - 원본 문자열의 형식 (예: "HH:mm:ss")
 * @param {string} outputFormat - 변환 후의 형식 (예: "HH시 mm분 ss초")
 * @returns {string} 지정한 출력 형식으로 변환된 시간 문자열
 */
export function formatTime(timeString, inputFormat, outputFormat, locale = 'ko') {
  moment.locale(locale);

  return moment(timeString, inputFormat).format(outputFormat);
}
