-- User 테이블 (개인회원)
insert into user_tb(username, email, password, tel, photo, address, birth, role)
values ('김대홍', '3@nate.com', '1234', '01010001111', 'basic.jpg', '부산', '1955-07-21', 0);
insert into user_tb(username, email, password, tel, photo, address, birth, position, role)
values ('유재석', '1@nate.com', '1234', '01012341234', 'basic.jpg', '서울', '1966-07-13', '백엔드', 1);
insert into user_tb(username, email, password, tel, photo, address, birth, position, role)
values ('박명수', 'cos@nate.com', '1234', '01012344321', 'basic.jpg', '대구', '1977-04-22', '임베디드', 1);
insert into user_tb(username, email, password, tel, photo, address, birth, position, role)
values ('노홍철', 'haha@nate.com', '1234', '01022223333', 'basic.jpg', '부산', '1988-11-11', '프론트엔드', 1);


-- User 테이블 (기업회원)
insert into user_tb(compname, comp_register, email, password, tel, photo, address, homepage, role)
values ('LG', '5050', '2@nate.com', '1234', '01055555000', 'LG_logo.png', '서울', 'lg.com', 2);
insert into user_tb(compname, comp_register, email, password, tel, photo, address, homepage, role)
values ('배달의민족', '4636', 'bm@nate.com', '1234', '01099998888', '배달의민족_logo.png', '부산', 'bm.com', 2);
insert into user_tb(compname, comp_register, email, password, tel, photo, address, homepage, role)
values ('kakao', '6666', 'kakao@nate.com', '1234', '01066667777', 'kakao_logo.png', '대구', 'kakao.com', 2);
insert into user_tb(compname, comp_register, email, password, tel, address, homepage, photo, role)
values ('네이버', '5555', 'naver@nate.com', '1234', '010612345678', '서울', 'naver.com', '네이버_logo.png', 2);
insert into user_tb(compname, comp_register, email, password, tel, address, homepage, photo, role)
values ('삼성', '4444', 'samsung@nate.com', '1234', '01087654321', '광주', 'samsung.com', '삼성_logo.png', 2);

-- insert into user_tb(username, email, password, tel, photo, address, birth, position, role)
-- values ('홍길동', 'hong@nate.com', '1234', '01012342143', 'basic.jpg', '부산', '1912-01-15', '풀스택', 1);
-- insert into user_tb(username, email, password, tel, photo, address, birth, position, role)
-- values ('임꺽정', 'im@nate.com', '1234', '01087664434', 'basic.jpg', '서울', '1922-03-13', '백엔드', 1);
-- insert into user_tb(username, email, password, tel, photo, address, birth, position, role)
-- values ('장보고', 'jang@nate.com', '1234', '01045454545', 'basic.jpg', '광주', '1944-07-07', 'IOS', 1);
-- insert into user_tb(username, email, password, tel, photo, address, birth, position, role)
-- values ('이순신', 'lee@nate.com', '1234', '01033335555', 'basic.jpg', '서울', '1111-07-07', '백엔드', 1);


-- Posting(공고) 테이블
insert into posting_tb(user_id, title, desc, region, position, expiry_date, career, education)
values (5, '임원구함', '내용1내용1내용1내용1내용1내용1내용1내용1', '서울', '백엔드', '2023-10-1', '8년', '학력무관');
insert into posting_tb(user_id, title, desc, region, position, expiry_date, career, education)
values (7, '신입모집합니다', '내용2내용2내용2내용2내용2', '대구', '풀스택', '2023-9-8', '3년', '대학교 졸업');
insert into posting_tb(user_id, title, desc, region, position, expiry_date, career, education)
values (6, '배민신입구함', '내용3내용3내용3내용3', '부산', '백엔드', '2023-09-11', '1년', '석사 졸업');
insert into posting_tb(user_id, title, desc, region, position, expiry_date, career, education)
values (7, '경력직 모집', '내용4내용4내용4내용4내용4', '대구', '빅데이터', '2023-12-21', '7년', '학력무관');
insert into posting_tb(user_id, title, desc, region, position, expiry_date, career, education)
values (5, '이직하실분', '내용5내용5내용5내용5내용5내용5', '서울', '프론트엔드', '2023-09-12', '경력무관', '대학교 졸업');
insert into posting_tb(user_id, title, desc, region, position, expiry_date, career, education)
values (5, '경력직구함', '내용6', '서울', '안드로이드', '2023-9-4', '경력무관', '석사 졸업');
insert into posting_tb(user_id, title, desc, region, position, expiry_date, career, education)
values (6, '개발자구함', '내용7', '부산', '서버', '2023-12-3', '4년', '학력무관');
insert into posting_tb(user_id, title, desc, region, position, expiry_date, career, education)
values (8, '구인', '내용8', '부산', 'IOS', '2023-11-13', '0-l년', '고교 졸업');
insert into posting_tb(user_id, title, desc, region, position, expiry_date, career, education)
values (8, '개발자모집', '내용9', '강원', '머신러닝', '2023-09-13', '2년', '고교 졸업');
insert into posting_tb(user_id, title, desc, region, position, expiry_date, career, education)
values (9, '삼성구인공고', '내용10', '대전', '백엔드', '2024-02-21', '5년', '박사 졸업');
insert into posting_tb(user_id, title, desc, region, position, expiry_date, career, education)
values (9, '개발자구인', '내용11', '서울', '임베디드', '2023-12-21', '6년', '석사 졸업');
insert into posting_tb(user_id, title, desc, region, position, expiry_date, career, education)
values (8, '구인홍보', '내용12', '서울', '프론트엔드', '2023-09-14', '8년', '대학 졸업');
insert into posting_tb(user_id, title, desc, region, position, expiry_date, career, education)
values (6, '이직자구함', '내용13', '서울', '백엔드', '2023-09-22', '6년', '학력무관');
insert into posting_tb(user_id, title, desc, region, position, expiry_date, career, education)
values (9, '삼성모집', '내용14', '서울', '백엔드', '2023-12-22', '5년', '학력무관');


-- Resume(이력서) 테이블
insert into resume_tb(user_id, title, grade, career, personal_statement, disclosure)
values (2, '유재석지원서1', '대졸', '신입', '하이', true);
insert into resume_tb(user_id, title, grade, career, personal_statement, disclosure)
values (3, '박명수지원서1', '고졸', '4년', 'nihao', true);
-- insert into resume_tb(user_id, title, grade, career, personal_statement, disclosure)
-- values (4, '지원서1', '초대졸', '1년', 'hello', false);
-- insert into resume_tb(user_id, title, grade, career, personal_statement, disclosure)
-- values (2, '지원서2', '대졸', '신입', 'ssawadi', true);
-- insert into resume_tb(user_id, title, grade, career, personal_statement, disclosure)
-- values (4, '이력서에요', '초대졸', '1년', 'nanstte', true);
-- insert into resume_tb(user_id, title, grade, career, personal_statement, disclosure)
-- values (2, '지원서입니다', '대졸', '신입', '반갑습니다', false);ㄴ
-- insert into resume_tb(user_id, title, grade, career, personal_statement, disclosure)
-- values (10, '홍길동지원서1', '대졸', '신입', '반갑습니다1', true);
-- insert into resume_tb(user_id, title, grade, career, personal_statement, disclosure)
-- values (10, '이력서1', '대졸', '신입', '반갑습니다2', true);
-- insert into resume_tb(user_id, title, grade, career, personal_statement, disclosure)
-- values (11, '임지원서1', '고졸', '3년', '반갑습니다3', true);
-- insert into resume_tb(user_id, title, grade, career, personal_statement, disclosure)
-- values (11, '임지원서2', '고졸', '3년', '반갑습니다4', true);
-- insert into resume_tb(user_id, title, grade, career, personal_statement, disclosure)
-- values (11, '이력서2', '고졸', '3년', '반갑습니다4', true);
-- insert into resume_tb(user_id, title, grade, career, personal_statement, disclosure)
-- values (12, '장보고지원서2', '대졸', '2년', '반갑습니다5', true);
-- insert into resume_tb(user_id, title, grade, career, personal_statement, disclosure)
-- values (12, '이력서3', '대졸', '2년', '반갑습니다6', false);
-- insert into resume_tb(user_id, title, grade, career, personal_statement, disclosure)
-- values (12, '구인자입니다', '대졸', '2년', '반갑습니다7', true);
-- insert into resume_tb(user_id, title, grade, career, personal_statement, disclosure)
-- values (13, '구인구직', '고졸', '5년', '반갑습니다8', true);

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
values (1, 2);
insert into userskill_tb(skill_id, user_id)
values (2, 2);
insert into userskill_tb(skill_id, user_id)
values (6, 2);
insert into userskill_tb(skill_id, user_id)
values (3, 3);
insert into userskill_tb(skill_id, user_id)
values (5, 3);
insert into userskill_tb(skill_id, user_id)
values (6, 4);
insert into userskill_tb(skill_id, user_id)
values (7, 4);
-- insert into userskill_tb(skill_id, user_id)
-- values (1, 10);
-- insert into userskill_tb(skill_id, user_id)
-- values (4, 10);
-- insert into userskill_tb(skill_id, user_id)
-- values (5, 10);
-- insert into userskill_tb(skill_id, user_id)
-- values (3, 11);
-- insert into userskill_tb(skill_id, user_id)
-- values (6, 11);
-- insert into userskill_tb(skill_id, user_id)
-- values (8, 11);
-- insert into userskill_tb(skill_id, user_id)
-- values (3, 12);
-- insert into userskill_tb(skill_id, user_id)
-- values (7, 12);
-- insert into userskill_tb(skill_id, user_id)
-- values (1, 13);


-- PostingSkill 테이블 (중간 테이블)
insert into postingSkill_tb(skill_id, posting_id)
values (1, 1);
insert into postingSkill_tb(skill_id, posting_id)
values (2, 1);
insert into postingSkill_tb(skill_id, posting_id)
values (5, 2);
insert into postingSkill_tb(skill_id, posting_id)
values (6, 2);
insert into postingSkill_tb(skill_id, posting_id)
values (1, 3);
insert into postingSkill_tb(skill_id, posting_id)
values (2, 4);
insert into postingSkill_tb(skill_id, posting_id)
values (5, 4);
insert into postingSkill_tb(skill_id, posting_id)
values (1, 5);
insert into postingSkill_tb(skill_id, posting_id)
values (5, 5);
insert into postingSkill_tb(skill_id, posting_id)
values (4, 6);
insert into postingSkill_tb(skill_id, posting_id)
values (7, 6);
insert into postingSkill_tb(skill_id, posting_id)
values (8, 6);
insert into postingSkill_tb(skill_id, posting_id)
values (4, 7);
insert into postingSkill_tb(skill_id, posting_id)
values (6, 7);
insert into postingSkill_tb(skill_id, posting_id)
values (2, 8);
insert into postingSkill_tb(skill_id, posting_id)
values (3, 8);
insert into postingSkill_tb(skill_id, posting_id)
values (4, 8);
insert into postingSkill_tb(skill_id, posting_id)
values (3, 9);
insert into postingSkill_tb(skill_id, posting_id)
values (6, 9);
insert into postingSkill_tb(skill_id, posting_id)
values (4, 10);
insert into postingSkill_tb(skill_id, posting_id)
values (2, 11);
insert into postingSkill_tb(skill_id, posting_id)
values (7, 11);
insert into postingSkill_tb(skill_id, posting_id)
values (7, 12);
insert into postingSkill_tb(skill_id, posting_id)
values (3, 13);
insert into postingSkill_tb(skill_id, posting_id)
values (6, 13);
insert into postingSkill_tb(skill_id, posting_id)
values (1, 14);
insert into postingSkill_tb(skill_id, posting_id)
values (4, 14);


-- Apply 테이블 (중간 테이블)
-- insert into apply_tb(resume_id, posting_id, statement, user_id)
-- values (1, 2, '대기', 2);
-- insert into apply_tb(resume_id, posting_id, statement, user_id)
-- values (4, 3, '합격', 2);
-- insert into apply_tb(resume_id, posting_id, statement, user_id)
-- values (2, 1, '대기', 3);
-- insert into apply_tb(resume_id, posting_id, statement, user_id)
-- values (5, 3, '불합', 3);
-- insert into apply_tb(resume_id, posting_id, statement)
-- values (1, 1, '불합');
-- insert into apply_tb(resume_id, posting_id, statement)
-- values (2, 5, '대기');
-- -- insert into apply_tb(resume_id, posting_id, statement) 비공개이력서임
-- -- values (3, 1, '대기');
-- insert into apply_tb(resume_id, posting_id, statement)
-- values (4, 5, '합격');
-- insert into apply_tb(resume_id, posting_id, statement)
-- values (5, 6, '대기');
-- -- insert into apply_tb(resume_id, posting_id, statement) 비공개이력서임
-- -- values (6, 6, '불합');
-- insert into apply_tb(resume_id, posting_id, statement)
-- values (7, 2, '대기');
-- insert into apply_tb(resume_id, posting_id, statement)
-- values (7, 6, '대기');
-- insert into apply_tb(resume_id, posting_id, statement)
-- values (8, 1, '대기');
-- insert into apply_tb(resume_id, posting_id, statement)
-- values (8, 7, '합격');
-- insert into apply_tb(resume_id, posting_id, statement)
-- values (9, 1, '대기');
-- insert into apply_tb(resume_id, posting_id, statement)
-- values (9, 2, '대기');
-- insert into apply_tb(resume_id, posting_id, statement)
-- values (10, 5, '불합');
-- insert into apply_tb(resume_id, posting_id, statement)
-- values (10, 4, '대기');

-- UserBookmark 테이블 (중간 테이블)
-- insert into userbookmark_tb (user_id, posting_id)
-- values (2, 2);
-- insert into userbookmark_tb (user_id, posting_id)
-- values (2, 3);
-- insert into userbookmark_tb (user_id, posting_id)
-- values (3, 1);
-- insert into userbookmark_tb (user_id, posting_id)
-- values (4, 3);

-- CompBookmark 테이블 (중간 테이블)
-- insert into compbookmark_tb (user_id, resume_id)
-- values (5, 1);
-- insert into compbookmark_tb (user_id, resume_id)
-- values (5, 2);
-- insert into compbookmark_tb (user_id, resume_id)
-- values (5, 7);
-- insert into compbookmark_tb (user_id, resume_id)
-- values (5, 8);
-- insert into compbookmark_tb (user_id, resume_id)
-- values (5, 9);
-- insert into compbookmark_tb (user_id, resume_id)
-- values (6, 10);
-- insert into compbookmark_tb (user_id, resume_id)
-- values (7, 11);


-- Recommend 테이블 (중간 테이블)
-- insert into recommend_tb(posting_id, resume_id, statement)
-- values (1, 1, '대기');
-- insert into recommend_tb(posting_id, resume_id, statement)
-- values (1, 2, '대기');
-- insert into recommend_tb(posting_id, resume_id, statement) values (1, 3, '수락'); 비공개이력서임
-- insert into recommend_tb(posting_id, resume_id, statement)
-- values (5, 4, '거절');
-- insert into recommend_tb(posting_id, resume_id, statement)
-- values (5, 5, '대기');
-- insert into recommend_tb(posting_id, resume_id, statement) values (1, 6, '대기'); 비공개이력서임
-- insert into recommend_tb(posting_id, resume_id, statement)
-- values (4, 7, '대기');
-- insert into recommend_tb(posting_id, resume_id, statement)
-- values (1, 8, '수락');
-- insert into recommend_tb(posting_id, resume_id, statement)
-- values (5, 9, '대기');


-- Master 테이블
insert into master_tb (category, user_id, title, content)
values ('불만', 1, '질문', '아침 메뉴 추천');
insert into master_tb (category, user_id, title, content)
values ('불만', 5, '문의', '점심 메뉴 추천');
insert into master_tb (category, user_id, title, content)
values ('불만', 3, '의문', '저녁 메뉴 추천');





