-- User 테이블 (개인회원)
insert into user_tb(username, email, password, tel, photo, address, birth, role)
values ('김대홍', 'kdh@nate.com', '1234', '01010001111', 'basic.jpg', '부산시 화명동', '1955-07-21', 0);
insert into user_tb(username, email, password, tel, photo, address, birth, role)
values ('유재석', 'ssar@nate.com', '1234', '01012341234', 'basic.jpg', '서울시 성동구', '1966-07-13', 1);
insert into user_tb(username, email, password, tel, photo, address, birth, role)
values ('박명수', 'cos@nate.com', '1234', '01012344321', 'basic.jpg', '인천시 미추홀구', '1977-04-22', 1);
insert into user_tb(username, email, password, tel, photo, address, birth, role)
values ('노홍철', 'haha@nate.com', '1234', '01022223333', 'basic.jpg', '부산시 수영구', '1988-11-11', 1);


-- User 테이블 (기업회원)
insert into user_tb(compname, comp_register, email, password, tel, photo, address, homepage, role)
values ('LG', '5050', 'love@nate.com', '1234', '01055555000', 'LG_logo.png', '서울', 'lg.com', 2);
insert into user_tb(compname, comp_register, email, password, tel, photo, address, homepage, role)
values ('배달의민족', '4636', 'bm@nate.com', '1234', '01099998888', '배달의민족_logo.png', '부산', 'bm.com', 2);
insert into user_tb(compname, comp_register, email, password, tel, photo, address, homepage, role)
values ('kakao', '6666', 'kakao@nate.com', '1234', '01066667777', 'kakao_logo.png', '대구', 'kakao.com', 2);
insert into user_tb(compname , comp_register  ,email , password, tel, address,homepage,photo,role) values('네이버', '5555','naver@nate.com', '1234', '010612345678', '서울','naver.com','네이버_logo.png', 2);
insert into user_tb(compname , comp_register  ,email , password, tel, address,homepage,photo,role) values('삼성', '4444','samsung@nate.com', '1234', '01087654321', '광주','samsung.com','삼성_logo.png', 2);


-- Posting(공고) 테이블
insert into posting_tb(user_id, title, desc, region, position, expiry_date, career, education)
values (5, '임원구함', '경력 4년 이상', '서울', '백엔드', '2023-10-1', '8년', '학력무관');
insert into posting_tb(user_id, title, desc, region, position, expiry_date, career, education)
values (7, '신입모집합니다', '경력 2년 이상', '대구', '풀스택', '2024-01-2', '3년', '대학교 졸업');
insert into posting_tb(user_id, title, desc, region, position, expiry_date, career, education)
values (6, '신입구함', '경력 무관', '부산', '백엔드', '2023-09-11', '1년', '석사 졸업');
insert into posting_tb(user_id, title, desc, region, position, expiry_date, career, education)
values (7, '경력직 모집', '경력 7년 이상', '대구', '빅데이터', '2023-12-21', '7년', '학력무관');
insert into posting_tb(user_id, title, desc, region, position, expiry_date, career, education)
values (5, '신입구함', '경력 무관', '서울', '프론트엔드', '2023-09-12', '경력무관', '대학교 졸업');
insert into posting_tb( user_id, title, desc, region, position, expiry_date, career, education) values(5, '경력직구함', '내용6', '서울', '안드로이드', '2023-9-4','경력무관', '석사 졸업');
insert into posting_tb( user_id, title, desc, region, position, expiry_date, career, education) values(6, '개발자구함', '내용7', '부산', '서버', '2023-12-3','4년','학력무관');
insert into posting_tb( user_id, title, desc, region, position, expiry_date, career, education) values(8, '구인', '내용8', '부산', 'IOS', '2023-11-13','0-l년','고교 졸업');
insert into posting_tb( user_id, title, desc, region, position, expiry_date, career, education) values(8, '개발자모집', '내용9', '강원', '머신러닝', '2023-09-13','2년','고교 졸업');
insert into posting_tb( user_id, title, desc, region, position, expiry_date, career, education) values(9, '구인공고', '내용10', '대전', '백엔드', '2024-02-21','5년','박사 졸업');
insert into posting_tb( user_id, title, desc, region, position, expiry_date, career, education) values(9, '개발자구인', '내용11', '서울', '임베디드', '2023-12-21','6년','석사 졸업');
insert into posting_tb( user_id, title, desc, region, position, expiry_date, career, education) values(8, '제목1', '내용12', '서울', '프론트엔드', '2023-09-03','8년','대학 졸업');


-- Resume(이력서) 테이블
insert into resume_tb(user_id, title, grade, career, personal_statement, disclosure)
values (2, '지원서1', '대졸', '신입', '하이', true);
insert into resume_tb(user_id, title, grade, career, personal_statement, disclosure)
values (3, '지원서1', '고졸', '4년', 'nihao', true);
insert into resume_tb(user_id, title, grade, career, personal_statement, disclosure)
values (4, '지원서1', '초대졸', '1년', 'hello', false);
insert into resume_tb(user_id, title, grade, career, personal_statement, disclosure)
values (2, '지원서2', '대졸', '신입', 'ssawadi', true);
insert into resume_tb(user_id, title, grade, career, personal_statement, disclosure)
values (4, '지원서2', '초대졸', '1년', 'nanstte', true);
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
insert into userskill_tb(skill_id, user_id) values (1, 2);
insert into userskill_tb(skill_id, user_id) values (5, 2);
insert into userskill_tb(skill_id, user_id) values (6, 2);
insert into userskill_tb(skill_id, user_id) values (2, 3);
insert into userskill_tb(skill_id, user_id) values (3, 3);
insert into userskill_tb(skill_id, user_id) values (4, 4);
insert into userskill_tb(skill_id, user_id) values (7, 4);
insert into userskill_tb(skill_id, user_id) values (8, 4);


-- PostingSkill 테이블 (중간 테이블)
insert into postingSkill_tb(skill_id, posting_id) values(1, 1);
insert into postingSkill_tb(skill_id, posting_id) values(2, 1);
insert into postingSkill_tb(skill_id, posting_id) values(5, 2);
insert into postingSkill_tb(skill_id, posting_id) values(6, 2);
insert into postingSkill_tb(skill_id, posting_id) values(1, 3);
insert into postingSkill_tb(skill_id, posting_id) values(2, 4);
insert into postingSkill_tb(skill_id, posting_id) values(5, 4);
insert into postingSkill_tb(skill_id, posting_id) values(1, 5);
insert into postingSkill_tb(skill_id, posting_id) values(5, 5);
insert into postingSkill_tb(skill_id, posting_id) values(4, 6);
insert into postingSkill_tb(skill_id, posting_id) values(7, 6);
insert into postingSkill_tb(skill_id, posting_id) values(8, 6);
insert into postingSkill_tb(skill_id, posting_id) values(4, 7);
insert into postingSkill_tb(skill_id, posting_id) values(6, 7);
insert into postingSkill_tb(skill_id, posting_id) values(2, 8);
insert into postingSkill_tb(skill_id, posting_id) values(3, 8);
insert into postingSkill_tb(skill_id, posting_id) values(4, 8);
insert into postingSkill_tb(skill_id, posting_id) values(3, 9);
insert into postingSkill_tb(skill_id, posting_id) values(6, 9);
insert into postingSkill_tb(skill_id, posting_id) values(4, 10);
insert into postingSkill_tb(skill_id, posting_id) values(2, 11);
insert into postingSkill_tb(skill_id, posting_id) values(7, 11);
insert into postingSkill_tb(skill_id, posting_id) values(7, 12);


-- Apply 테이블 (중간 테이블)
insert into apply_tb(resume_id, posting_id, statement)
values (1, 2, '대기');
insert into apply_tb(resume_id, posting_id, statement)
values (4, 3, '합격');
insert into apply_tb(resume_id, posting_id, statement)
values (2, 1, '대기');
insert into apply_tb(resume_id, posting_id, statement)
values (5, 3, '불합');
insert into apply_tb(resume_id, posting_id, statement) values (1, 1, '불합');
insert into apply_tb(resume_id, posting_id, statement) values (2, 5, '대기');
insert into apply_tb(resume_id, posting_id, statement) values (3, 1, '대기');
insert into apply_tb(resume_id, posting_id, statement) values (4, 5, '합격');
insert into apply_tb(resume_id, posting_id, statement) values (5, 6, '대기');
insert into apply_tb(resume_id, posting_id, statement) values (6, 6, '불합');

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




