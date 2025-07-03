# 📚 StudyLog - 개발자 면접 질문 & 학습 기록 플랫폼

면접 준비 과정을 체계적으로 기록하고,  
AI 피드백과 검색 기능을 통해 자기주도 학습을 돕는 웹 기반 플랫폼입니다.

---

## 🗂️ 목차

- [프로젝트 개요](#프로젝트-개요)
- [기능 소개](#기능-소개)
- [기술 스택](#기술-스택)
- [시스템 구조](#시스템-구조)
- [주요 기능 화면](#주요-기능-화면)
- [설치 및 실행 방법](#설치-및-실행-방법)
- [프로젝트 폴더 구조](#프로젝트-폴더-구조)
- [회고 및 개선 방향](#회고-및-개선-방향)

---

## ✅ 프로젝트 개요

| 항목 | 내용 |
|------|------|
| 프로젝트명 | StudyLog |
| 개발기간 | 2025.04.22 ~ 진행 중 |
| 개발방식 | 개인 프로젝트 |
| 핵심 목표 | 면접 질문 관리 + AI 피드백 + 일정 관리 + 검색 기능 통합 |

> 📌 **Velog 학습 기록 기반의 실제 질문들을 정리하고 실습하는 과정에서 시작됨.**

---

## 💡 기능 소개

### 1. 질문 관리 (CRUD)
- 카테고리별 분류, 질문 내용 작성 및 수정
- 랜덤 질문 기능

### 2. 답변 저장 & AI 피드백
- AJAX 기반 실시간 저장
- Flask 서버 연동 → OpenAI API로 AI 피드백 생성 및 저장
- 피드백 로딩 시 스피너 & 오버레이 표시

### 3. 일정 관리 (캘린더)
- FullCalendar 기반 일정 등록/수정
- 면접 일정, 지원 마감일 등 필터링

### 4. 검색 기능
- Apache Solr 도입
- 전체 텍스트 검색 + 다중 필드 검색 + 유사도 기반 정렬

> 📌 위 기능들은 모두 개별 페이지로 구성되며, 공통 레이아웃을 통해 통일성 유지

---

## 🛠 기술 스택

| 분류 | 기술 |
|------|------|
| Back-end | Spring Boot, JPA, MySQL |
| Front-end | Thymeleaf, Bootstrap5, JavaScript (AJAX) |
| AI 연동 | Python Flask + OpenAI GPT API |
| 검색 기능 | Apache Solr |
| 기타 | Gradle, Git, Postman, EC2 (배포 시 예정) |

---

## 🧩 시스템 구조

```plaintext
┌────────────┐       ┌────────────┐       ┌──────────────┐
│  Frontend  │──────▶│ Spring Boot│──────▶│ MySQL (JPA)  │
│ (Thymeleaf)│       └────┬───────┘       └──────────────┘
└────────────┘            │
                         ▼
                  ┌──────────────┐
                  │  Flask AI API│
                  │  (OpenAI)    │
                  └──────────────┘

+ Apache Solr 서버는 별도 운영되어 검색 요청 처리
```

> 📌 다이어그램 이미지를 따로 추가할 수도 있습니다.

---

## 🖼 주요 기능 화면

> 아래는 주요 화면 캡처 및 설명입니다.

- 질문 작성 페이지
- 랜덤 질문 + 답변 저장 + AI 피드백 UI  
- 피드백 결과 표시  
- 일정 캘린더 등록/필터링  
- Solr 검색 결과 페이지

> ✅ 이미지 삽입 위치:  
> `![랜덤 질문 화면](./images/random_question.png)`  
> `![AI 피드백](./images/ai_feedback.png)`

---

## 💻 설치 및 실행 방법

```bash
# 1. 레포지토리 클론
git clone https://github.com/본인계정명/StudyLog.git

# 2. 백엔드 실행
cd StudyLog
./gradlew bootRun

# 3. MySQL DB 구성
- schema.sql 및 data.sql 참고
- application.yml 또는 properties에서 DB 설정 수정

# 4. Flask 서버 실행 (AI 피드백)
cd flask-server
python app.py

# 5. Solr 실행
bin/solr start
```

> 🔧 자세한 설정은 `docs/setup.md` 참고

---

## 📁 프로젝트 폴더 구조

```plaintext
StudyLog/
 ├── src/
 │   ├── main/
 │   │   ├── java/com/studylog/
 │   │   │   ├── controller/
 │   │   │   ├── domain/
 │   │   │   ├── repository/
 │   │   │   ├── service/
 │   │   │   └── StudyLogApplication.java
 │   │   └── resources/
 │   │       ├── templates/
 │   │       ├── static/
 │   │       └── application.yml
 ├── flask-server/
 │   └── app.py
 ├── solr/
 │   └── core 설정 및 schema.xml
 └── build.gradle
```

---

## 🔍 회고 및 개선 방향

- 🔄 [ ] OAuth2 로그인 도입
- 🔄 [ ] 카테고리 다대다 구조 개선 (JPA 연관관계)
- 🔄 [ ] AI 피드백에 대한 분석 시각화 (그래프 등)
- 🔄 [ ] 사용자별 질문/답변 히스토리 페이지 구성

> ✅ 이 프로젝트는 지속적으로 리팩토링 및 확장 예정입니다.

---

## 🔗 기타 링크

- [Velog 정리글 모음](https://velog.io/@본인아이디/studylog)
- [포트폴리오 PDF 첨부 (선택)]
- [시연 영상 링크 (유튜브 등)]
