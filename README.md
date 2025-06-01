# Project_StudyLog

개발자 면접 대비용 질문과 답변을 관리할 수 있는 웹 애플리케이션입니다.  
Spring Boot, Thymeleaf, MySQL을 사용하여 구현했습니다.

## 주요 기능

- 랜덤 질문 조회
- 질문 등록 / 수정 / 삭제
- 질문 목록 보기
- Git 연동으로 버전 관리

## 기술 스택

- Backend: Spring Boot, JPA
- Frontend: Thymeleaf
- DB: MySQL
- Build: Gradle

## 프로젝트 실행 방법

1. 이 저장소를 클론합니다.
```bash
git clone https://github.com/kjo5191/Project_StudyLog.git
```

2. IDE에서 프로젝트를 열고 DB 정보를 `application.properties`에 입력합니다.

3. 실행
```bash
./gradlew bootRun
```

## 프로젝트 구조 (예시)

```
studylog/
 ┣ src/
 ┃ ┣ main/
 ┃ ┃ ┣ java/
 ┃ ┃ ┃ ┗ com.studylog
 ┃ ┃ ┃   ┣ domain/
 ┃ ┃ ┃   ┣ repository/
 ┃ ┃ ┃   ┣ controller/
 ┃ ┃ ┣ resources/
 ┃ ┃ ┃ ┣ templates/
 ┃ ┃ ┃ ┗ application.properties
 ┗ build.gradle
```

## 개발자

- [kjun](https://github.com/kjo5191)
