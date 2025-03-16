module.exports = {
  root: true,
  env: {
    node: true,
    es6: true
  },
  // Vue SFC를 파싱하기 위해 vue-eslint-parser 사용
  parser: 'vue-eslint-parser',
  parserOptions: {
    // 실제 JS 파싱에는 @babel/eslint-parser (또는 babel-eslint)를 사용
    parser: '@babel/eslint-parser',
    ecmaVersion: 2020,
    sourceType: 'module',
    requireConfigFile: false, // 별도의 Babel 설정 파일 없이도 파싱
  },
  extends: [
    'plugin:vue/vue3-essential', // 또는 'plugin:vue/vue3-recommended'
    'eslint:recommended'
  ],
  globals: {
    // Vue 3 SFC 컴파일러 매크로를 전역 변수로 등록
    defineProps: 'readonly',
    defineEmits: 'readonly',
    defineExpose: 'readonly',
    withDefaults: 'readonly'
  },
  rules: {
    // 필요한 규칙들을 추가/수정하세요.
    // 예를 들어, multi-word-component-names 규칙을 끄고 싶다면:
    'vue/multi-word-component-names': 'off'
  }
}
