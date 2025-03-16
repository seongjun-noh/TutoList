import { library } from '@fortawesome/fontawesome-svg-core'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import { fas } from '@fortawesome/free-solid-svg-icons'
import { far } from '@fortawesome/free-regular-svg-icons'
import { fab } from '@fortawesome/free-brands-svg-icons'

// 아이콘 라이브러리에 추가
library.add(fas, far, fab)

// FontAwesomeIcon 컴포넌트를 기본 내보내기
export default FontAwesomeIcon
