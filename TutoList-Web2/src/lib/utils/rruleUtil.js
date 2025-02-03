/**
 * rrule 문자열을 받아 한국어 설명 문자열로 변환합니다.
 * 예시: "FREQ=WEEKLY;INTERVAL=2;BYDAY=MO,WE,FR" 
 *      → "2주 간격, 요일: 월, 수, 금"
 *
 * @param {string} rruleString - rrule 문자열
 * @returns {string} 한국어로 변환된 반복 규칙 설명
 */
export function translateRRule(rruleString) {
  if (!rruleString) return "반복 없음";

  // "RRULE:" 접두사가 있다면 제거
  rruleString = rruleString.replace(/^RRULE:/, "");

  // rrule 파라미터를 key/value 객체로 변환
  const params = rruleString.split(";").reduce((acc, part) => {
    const [key, value] = part.split("=");
    acc[key] = value;
    return acc;
  }, {});

  let description = "";
  const freq = params.FREQ;
  // INTERVAL 값을 숫자로 변환 (기본값 1)
  const interval = parseInt(params.INTERVAL || "1", 10);
  const byday = params.BYDAY;

  // FREQ 값에 따른 반복 주기 변환
  // interval이 1일 경우와 2이상일 경우를 구분합니다.
  switch (freq) {
    case "DAILY":
      description += (interval === 1) ? "매일" : `${interval}일 간격`;
      break;
    case "WEEKLY":
      description += (interval === 1) ? "매주" : `${interval}주 간격`;
      break;
    case "MONTHLY":
      description += (interval === 1) ? "매달" : `${interval}달 간격`;
      break;
    case "YEARLY":
      description += (interval === 1) ? "매년" : `${interval}년 간격`;
      break;
    default:
      // 다른 FREQ 값이 들어온 경우, interval 값을 함께 표시
      description += (interval === 1) ? freq : `${interval}회 간격 (${freq})`;
  }

  // BYDAY가 있을 경우 요일 정보를 한글로 변환
  if (byday) {
    const dayMap = {
      "MO": "월",
      "TU": "화",
      "WE": "수",
      "TH": "목",
      "FR": "금",
      "SA": "토",
      "SU": "일"
    };
    const days = byday
      .split(",")
      .map(day => dayMap[day] || day)
      .join(", ");
    description += `, 요일: ${days}`;
  }

  return description;
}
