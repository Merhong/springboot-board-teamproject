# 스프링부트 게시판 팀 프로젝트(4조)

## 기획 (완료)

## 화면설계 (완료)

## 화면코드 (완료)

## 테이블설계 (완료)

## 1단계 기능

- (특징 : 자바스크립트, 예외처리)
- 회원가입(기업/개인)
- 로그인
- 로그아웃
- 회원정보 보기(기업/개인)
- 회원정보 수정(기업/개인)

### <개인>

- 회원정보(마이페이지) 보기
- 회원정보(마이페이지) 수정
- 이력서 등록
- 이력서 관리(상세보기, 수정, 삭제)
- 지원내역 보기
- 지역별 공고 상세보기
- 기술별 공고 상세보기
- 직무별 공고 상세보기
- 공고 지원하기
- 공고 북마크하기 + 북마크 표시
- 북마크 리스트 보기
- 기업추천(기술, 직무)

### <기업>

- 회원정보(기업페이지) 보기
- 회원정보(기업페이지) 수정
- 공고등록
- 공고관리(지원자보기, 수정, 삭제)
- 이력서 북마크(자세히보기, 입사 제안, 삭제)
- 인재찾기(기술별, 직무별)

- 예외처리

## 2단계 기능

- 유저네임 중복체크 (AJAX)
- 검색하기
- 회원가입시 사진등록 (파일등록)

## 3단계 기능

- 섬머노트
- 필터(Filter)
- 유효성검사 자동화

## 테이블 쿼리

```sql
CREATE TABLE user_tb
(
    id            INT AUTO_INCREMENT PRIMARY KEY,
    address       VARCHAR(255),
    birth         DATE,
    comp_register VARCHAR(255),
    compname      VARCHAR(255),
    created_at    TIMESTAMP,
    email         VARCHAR(255),
    homepage      VARCHAR(255),
    message       BOOLEAN DEFAULT FALSE,
    password      VARCHAR(255),
    photo         VARCHAR(255),
    position      VARCHAR(255),
    role          INT,
    state_change  BOOLEAN DEFAULT FALSE,
    tel           VARCHAR(255),
    username      VARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE posting_tb
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    career      VARCHAR(255),
    created_at  TIMESTAMP,
    description VARCHAR(255),
    education   VARCHAR(255),
    expiry_date TIMESTAMP,
    photo       VARCHAR(255),
    position    VARCHAR(255) NOT NULL,
    region      VARCHAR(255),
    title       VARCHAR(100) NOT NULL,
    user_id     INT,
    FOREIGN KEY (user_id) REFERENCES user_tb (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE resume_tb
(
    id                 INT AUTO_INCREMENT PRIMARY KEY,
    career             VARCHAR(255),
    disclosure         BOOLEAN,
    grade              VARCHAR(255),
    personal_statement TEXT,
    title              VARCHAR(255),
    user_id            INT,
    FOREIGN KEY (user_id) REFERENCES user_tb (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE apply_tb
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    created_at TIMESTAMP,
    statement  VARCHAR(255),
    posting_id INT,
    resume_id  INT,
    user_id    INT,
    FOREIGN KEY (posting_id) REFERENCES posting_tb (id),
    FOREIGN KEY (resume_id) REFERENCES resume_tb (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES user_tb (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE compbookmark_tb
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    resume_id INT,
    user_id   INT,
    FOREIGN KEY (resume_id) REFERENCES resume_tb (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES user_tb (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE master_tb
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    category   VARCHAR(100) NOT NULL,
    content    VARCHAR(255),
    created_at TIMESTAMP,
    title      VARCHAR(100) NOT NULL,
    user_id    INT,
    FOREIGN KEY (user_id) REFERENCES user_tb (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE postingskill_tb
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    posting_id INT,
    skill_id   INT,
    FOREIGN KEY (posting_id) REFERENCES posting_tb (id),
    FOREIGN KEY (skill_id) REFERENCES skill_tb (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE recommend_tb
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    created_at TIMESTAMP,
    statement  VARCHAR(255),
    posting_id INT,
    resume_id  INT,
    FOREIGN KEY (posting_id) REFERENCES posting_tb (id),
    FOREIGN KEY (resume_id) REFERENCES resume_tb (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE reply_tb
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    content    VARCHAR(255),
    created_at TIMESTAMP,
    master_id  INT,
    FOREIGN KEY (master_id) REFERENCES master_tb (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE skill_tb
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    skillname VARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE userbookmark_tb
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    posting_id INT,
    user_id    INT,
    FOREIGN KEY (posting_id) REFERENCES posting_tb (id),
    FOREIGN KEY (user_id) REFERENCES user_tb (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE userskill_tb
(
    id       INT AUTO_INCREMENT PRIMARY KEY,
    skill_id INT,
    user_id  INT,
    FOREIGN KEY (skill_id) REFERENCES skill_tb (id),
    FOREIGN KEY (user_id) REFERENCES user_tb (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

```
