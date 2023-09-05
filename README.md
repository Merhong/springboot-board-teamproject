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
create
database blogdb;
use
blogdb;

create table user_tb
(
    id            integer auto_increment,
    address       varchar(255),
    birth         date,
    comp_register varchar(255),
    compname      varchar(255),
    created_at    timestamp,
    email         varchar(255),
    homepage      varchar(255),
    password      varchar(255),
    photo         varchar(255),
    role          integer,
    tel           varchar(255),
    username      varchar(255),
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

create table posting_tb
(
    id          integer auto_increment,
    career      varchar(255),
    created_at  timestamp,
    desc        varchar(255),
    education   varchar(255),
    expiry_date timestamp,
    photo       varchar(255),
    position    varchar(255) not null,
    region      varchar(255),
    title       varchar(100) not null,
    user_id     integer,
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

create table resume_tb
(
    id                 integer auto_increment,
    career             varchar(255),
    disclosure         boolean,
    grade              varchar(255),
    personal_statement clob,
    title              varchar(255),
    user_id            integer,
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

create table skill_tb
(
    id        integer auto_increment,
    skillname varchar(255),
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

create table master_tb
(
    id         integer auto_increment,
    content    varchar(255),
    created_at timestamp,
    title      varchar(100) not null,
    user_id    integer,
    primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
```
