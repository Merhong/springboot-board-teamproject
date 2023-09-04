-- User 테이블 (개인회원)
insert into user_tb(username, email, password, tel, photo, address, birth, role)
values ('김대홍', 'kdh@nate.com', '1234', '01010001111', 'basic.jpg', '부산시 화명동', now(), 0);
insert into user_tb(username, email, password, tel, photo, address, birth, role)
values ('유재석', 'ssar@nate.com', '1234', '01012341234', 'basic.jpg', '서울시 성동구', now(), 1);
insert into user_tb(username, email, password, tel, photo, address, birth, role)
values ('박명수', 'cos@nate.com', '1234', '01012344321', 'basic.jpg', '인천시 미추홀구', now(), 1);
insert into user_tb(username, email, password, tel, address, role)
values ('노홍철', 'haha@nate.com', '1234', '01022223333', '부산시 수영구', 1);


-- User 테이블 (기업회원)
insert into user_tb(compname, comp_register, email, password, tel, photo, address, homepage, role)
values ('LG', '5050', 'love@nate.com', '1234', '01055555000', 'LG_logo.png', '서울', 'lg.com', 2);
insert into user_tb(compname, comp_register, email, password, tel, photo, address, homepage, role)
values ('배달의민족', '4636', 'bm@nate.com', '1234', '01099998888', '배달의민족_logo.png', '부산', 'bm.com', 2);
insert into user_tb(compname, comp_register, email, password, tel, photo, address, homepage, role)
values ('kakao', '6666', 'kakao@nate.com', '1234', '01066667777', 'kakao_logo.png', '대구', 'kakao.com', 2);


-- Posting(공고) 테이블
insert into posting_tb(user_id, title, desc, region, position, expiry_date, career, education)
values (5, '임원구함', '경력 4년 이상', '서울', '백엔드', '3333-04-1', '8년', '학력무관');
insert into posting_tb(user_id, title, desc, region, position, expiry_date, career, education)
values (7, '신입모집합니다', '경력 2년 이상', '대구', '풀스택', '2025-01-11', '3년', '대학교 졸업');
insert into posting_tb(user_id, title, desc, region, position, expiry_date, career, education)
values (6, '신입구함', '경력 무관', '부산', '백엔드', '2022-02-27', '1년', '석사 졸업');
insert into posting_tb(user_id, title, desc, region, position, expiry_date, career, education)
values (7, '경력직 모집', '경력 7년 이상', '대구', '빅데이터', '2025-01-11', '7년', '학력무관');
insert into posting_tb(user_id, title, desc, region, position, expiry_date, career, education)
values (5, '신입구함', '경력 무관', '서울', '프론트엔드', '2023-05-12', '경력무관', '대학교 졸업');


-- Resume(이력서) 테이블
insert into resume_tb(user_id, title, grade, career, personal_statement, disclosure)
values (2, '지원서1', '대졸', '신입', '하이', true);
insert into resume_tb(user_id, title, grade, career, personal_statement, disclosure)
values (3, '지원서1', '고졸', '4년', 'nihao', true);
insert into resume_tb(user_id, title, grade, career, personal_statement, disclosure)
values (5, '지원서1', '초대졸', '1년', 'hello', false);
insert into resume_tb(user_id, title, grade, career, personal_statement, disclosure)
values (2, '지원서2', '대졸', '신입', 'ssawadi', true);
insert into resume_tb(user_id, title, grade, career, personal_statement, disclosure)
values (5, '지원서2', '초대졸', '1년', 'nanstte', true);
insert into resume_tb(user_id, title, grade, career, personal_statement, disclosure)
values (2, '지원서3', '대졸', '신입', '반갑습니다', false);


-- Skill(기술) 테이블
insert into skill_tb (skillname)
values ('Java');
insert into skill_tb (skillname)
values ('Spring');
insert into skill_tb (skillname)
values ('JavaScript');
insert into skill_tb (skillname)
values ('C');
insert into skill_tb (skillname)
values ('DB');
insert into skill_tb (skillname)
values ('Git');
insert into skill_tb (skillname)
values ('HTML/CSS');
insert into skill_tb (skillname)
values ('Python');


-- UserSkill 테이블 (중간 테이블)
insert into userskill_tb(skill_id, user_id)
values (1, 1);


-- PostingSkill 테이블 (중간 테이블)
insert into postingskill_tb(skill_id, posting_id)
values (3, 1);
insert into postingskill_tb(skill_id, posting_id)
values (3, 1);
insert into postingskill_tb(skill_id, posting_id)
values (1, 1);
insert into postingskill_tb(skill_id, posting_id)
values (1, 1);
insert into postingskill_tb(skill_id, posting_id)
values (1, 2);
insert into postingskill_tb(skill_id, posting_id)
values (1, 2);
insert into postingskill_tb(skill_id, posting_id)
values (1, 3);
insert into postingskill_tb(skill_id, posting_id)
values (1, 3);
insert into postingskill_tb(skill_id, posting_id)
values (1, 3);
insert into postingskill_tb(skill_id, posting_id)
values (1, 4);
insert into postingskill_tb(skill_id, posting_id)
values (1, 4);
insert into postingskill_tb(skill_id, posting_id)
values (1, 4);
insert into postingskill_tb(skill_id, posting_id)
values (1, 5);
insert into postingskill_tb(skill_id, posting_id)
values (1, 5);


-- Apply 테이블 (중간 테이블)
insert into apply_tb(resume_id, posting_id, statement)
values (1, 2, '대기');
insert into apply_tb(resume_id, posting_id, statement)
values (4, 3, '합격');
insert into apply_tb(resume_id, posting_id, statement)
values (2, 1, '대기');
insert into apply_tb(resume_id, posting_id, statement)
values (5, 3, '불합');


-- UserBookmark 테이블 (중간 테이블)
insert into userbookmark_tb (user_id, posting_id)
values (2, 2);
insert into userbookmark_tb (user_id, posting_id)
values (2, 3);
insert into userbookmark_tb (user_id, posting_id)
values (3, 1);
insert into userbookmark_tb (user_id, posting_id)
values (5, 3);

-- CompBookmark 테이블 (중간 테이블)
insert into compbookmark_tb (resume_id, posting_id)
values (2, 1);
insert into compbookmark_tb (resume_id, posting_id)
values (1, 2);


-- Recommend 테이블 (중간 테이블)


-- Master 테이블
insert into master_tb (category, user_id, title, content) values ('불만', 1, '질문', '아침 메뉴 추천');
insert into master_tb (category, user_id, title, content) values ('불만', 5, '문의', '점심 메뉴 추천');
insert into master_tb (category, user_id, title, content) values ('불만', 3, '의문', '저녁 메뉴 추천');





