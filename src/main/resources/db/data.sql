insert into user_tb(username, email ,password , tel, photo, address, birth, role) values('김대홍','kdh@nate.com', '1234', '01010001111' ,  'basic.jpg','부산시 화명동', now(), 0);
insert into user_tb(username, email ,password , tel, photo, address, birth, role) values('유재석', 'ssar@nate.com', '1234', '01012341234',  'basic.jpg','서울시 성동구', now(),1);
insert into user_tb(username, email ,password , tel, photo, address, birth, role) values('박명수', 'cos@nate.com','1234', '01012344321',  'basic.jpg','인천시 미추홀구', now(),1);
insert into user_tb(username, email ,password , tel, address, role) values('노홍철', 'haha@nate.com', '1234', '01022223333','부산시 수영구',1);

insert into user_tb(compname , comp_register  ,email , password, tel, address, role) values('LG', '5050','love@nate.com', '1234', '01055555000', '서울', 2);
insert into user_tb(compname , comp_register  ,email , password, tel, address, role) values('배달의민족', '4636','bm@nate.com', '1234', '01099998888', '부산', 2);
insert into user_tb(compname , comp_register  ,email , password, tel, address, role) values('kakao', '6666','kakao@nate.com', '1234', '01066667777', '대구', 2);

insert into resume_tb(user_id, title ,grade , career, personal_statement , disclosure ) values(2, '안녕', '대졸', '신입','하이',true);
insert into resume_tb(user_id, title ,grade , career, personal_statement , disclosure ) values(3, '니하오', '고졸', '4년','nihao',true);
insert into resume_tb(user_id, title ,grade , career, personal_statement , disclosure ) values(5, '헬로', '초대졸', '1년','hello',false);
insert into resume_tb(user_id, title ,grade , career, personal_statement , disclosure ) values(2, '사와디캅', '대졸', '신입','ssawadi',true);
insert into resume_tb(user_id, title ,grade , career, personal_statement , disclosure ) values(5, '나마스떼', '초대졸', '1년','nanstte',true);
insert into resume_tb(user_id, title ,grade , career, personal_statement , disclosure ) values(2, '반갑', '대졸', '신입','반갑습니다',false);

-- insert into skill_tb (name) values('java');
-- insert into skill_tb (name) values('DB');
-- insert into skill_tb (name) values('html_and_css');
-- insert into skill_tb (name) values('python');
-- insert into skill_tb (name) values('javascript');

insert into posting_tb( user_id, title, desc, region, homepage, position, expiry_date, career, education) values(5, '임원구함', '경력 4년 이상', '서울','lg.com', '백엔드', '3333-04-1','8년','학력무관');
insert into posting_tb( user_id, title, desc, region, homepage, position, expiry_date, career, education) values(7, '신입모집합니다', '경력 2년 이상', '대구','kakao.com', '풀스택', '2025-01-11','3년', '대학교 졸업');
insert into posting_tb( user_id, title, desc, region, homepage, position, expiry_date, career, education) values(6, '신입구함', '경력 무관', '부산','bm.com', '백엔드', '2022-02-27','1년','석사 졸업');
insert into posting_tb( user_id, title, desc, region, homepage, position, expiry_date, career, education) values(7, '경력직 모집', '경력 7년 이상', '대구','kakao.com', '빅데이터', '2025-01-11','7년', '학력무관');
insert into posting_tb( user_id, title, desc, region, homepage, position, expiry_date, career, education) values(5, '신입구함', '경력 무관', '서울','lg.com', '프론트엔드', '2023-05-12','경력무관', '대학교 졸업');

insert into postingSkill_tb(skill, posting_id) values('Java', 1);
insert into postingSkill_tb(skill, posting_id) values('Spring', 1);
insert into postingSkill_tb(skill, posting_id) values('Git', 1);
insert into postingSkill_tb(skill, posting_id) values('C', 1);
insert into postingSkill_tb(skill, posting_id) values('Java', 2);
insert into postingSkill_tb(skill, posting_id) values('JavaScript', 2);
insert into postingSkill_tb(skill, posting_id) values('Git', 3);
insert into postingSkill_tb(skill, posting_id) values('HTML/CSS', 3);
insert into postingSkill_tb(skill, posting_id) values('C', 3);
insert into postingSkill_tb(skill, posting_id) values('Java', 4);
insert into postingSkill_tb(skill, posting_id) values('JavaScript', 4);
insert into postingSkill_tb(skill, posting_id) values('Git', 4);
insert into postingSkill_tb(skill, posting_id) values('Python', 5);
insert into postingSkill_tb(skill, posting_id) values('JavaScript', 5);


insert into apply_tb( resume_id, posting_id, statement) values(1, 2, '대기');
insert into apply_tb( resume_id, posting_id, statement) values(4, 3, '합격');
insert into apply_tb( resume_id, posting_id, statement) values(2, 1, '대기');
insert into apply_tb( resume_id, posting_id, statement) values(5, 3, '불합');


insert into userbookmark_tb ( user_id, posting_id ) values(2, 2);
insert into userbookmark_tb ( user_id, posting_id ) values(2, 3);
insert into userbookmark_tb ( user_id, posting_id ) values(3, 1);
insert into userbookmark_tb ( user_id, posting_id ) values(5, 3);


insert into compbookmark_tb ( resume_id, posting_id ) values(2, 1);
insert into compbookmark_tb ( resume_id, posting_id ) values(1, 2);




-- insert into userskill_tb(user_id , java, db , html_and_css, python, javascript) values(2,true,true,true,true,true);
