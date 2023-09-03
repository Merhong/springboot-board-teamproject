insert into user_tb(username, email ,password , tel, photo, address, birth, role) values('김대홍','kdh@nate.com', '1234', '01010001111' ,  'basic.jpg','부산시 화명동', now(), 0);
insert into user_tb(username, email ,password , tel, photo, address, birth, role) values('유재석', 'ssar@nate.com', '1234', '01012341234',  'basic.jpg','서울시 성동구', now(),1);
insert into user_tb(username, email ,password , tel, photo, address, birth, role) values('박명수', 'cos@nate.com','1234', '01012344321',  'basic.jpg','인천시 미추홀구', now(),1);
insert into user_tb(username, email ,password , tel, address, role) values('노홍철', 'haha@nate.com', '1234', '01022223333','부산시 수영구',1);

insert into user_tb(compname , comp_register  ,email , password, tel, address,homepage,photo,role) values('LG', '5050','love@nate.com', '1234', '01055555000', '서울','lg.com','LG_logo.png',2);
insert into user_tb(compname , comp_register  ,email , password, tel, address,homepage,photo,role) values('배달의민족', '4636','bm@nate.com', '1234', '01099998888', '부산','bm.com','배달의민족_logo.png', 2);
insert into user_tb(compname , comp_register  ,email , password, tel, address,homepage,photo,role) values('kakao', '6666','kakao@nate.com', '1234', '01066667777', '대구','kakao.com','kakao_logo.png', 2);
insert into user_tb(compname , comp_register  ,email , password, tel, address,homepage,photo,role) values('네이버', '5555','naver@nate.com', '1234', '010612345678', '서울','naver.com','네이버_logo.png', 2);
insert into user_tb(compname , comp_register  ,email , password, tel, address,homepage,photo,role) values('삼성', '4444','samsung@nate.com', '1234', '01087654321', '광주','samsung.com','삼성_logo.png', 2);

insert into resume_tb(user_id, title ,grade , career, personal_statement , disclosure ) values(2, '안녕', '대졸', '신입','하이',true);
insert into resume_tb(user_id, title ,grade , career, personal_statement , disclosure ) values(3, '니하오', '고졸', '4년','nihao',true);
insert into resume_tb(user_id, title ,grade , career, personal_statement , disclosure ) values(5, '헬로', '초대졸', '1년','hello',false);
insert into resume_tb(user_id, title ,grade , career, personal_statement , disclosure ) values(2, '사와디캅', '대졸', '신입','ssawadi',true);
insert into resume_tb(user_id, title ,grade , career, personal_statement , disclosure ) values(5, '나마스떼', '초대졸', '1년','nanstte',true);
insert into resume_tb(user_id, title ,grade , career, personal_statement , disclosure ) values(2, '반갑', '대졸', '신입','반갑습니다',false);

insert into skill_tb (skillname) values('Java');
insert into skill_tb (skillname) values('Spring');
insert into skill_tb (skillname) values('JavaScript');
insert into skill_tb (skillname) values('C');
insert into skill_tb (skillname) values('DB');
insert into skill_tb (skillname) values('Git');
insert into skill_tb (skillname) values('HTML/CSS');
insert into skill_tb (skillname) values('Python');

insert into posting_tb( user_id, title, desc, region, position, expiry_date, career, education) values(5, '임원구함', '내용1', '서울', '백엔드', '2023-9-2','8년','학력무관');
insert into posting_tb( user_id, title, desc, region, position, expiry_date, career, education) values(7, '신입모집합니다', '내용2', '대구', '풀스택', '2023-12-11','3년', '대학교 졸업');
insert into posting_tb( user_id, title, desc, region, position, expiry_date, career, education) values(6, '신입모집', '내용3', '부산', '백엔드', '2023-11-3','1년','석사 졸업');
insert into posting_tb( user_id, title, desc, region, position, expiry_date, career, education) values(7, '경력직 모집', '내용4', '대구', '빅데이터', '2025-01-11','7년', '학력무관');
insert into posting_tb( user_id, title, desc, region, position, expiry_date, career, education) values(5, '신입구함', '내용5', '광주', '프론트엔드', '2023-9-13','경력무관', '대학교 졸업');
insert into posting_tb( user_id, title, desc, region, position, expiry_date, career, education) values(5, '경력직구함', '내용6', '서울', '안드로이드', '2023-9-4','경력무관', '석사 졸업');
insert into posting_tb( user_id, title, desc, region, position, expiry_date, career, education) values(6, '개발자구함', '내용7', '부산', '서버', '2023-12-3','4년','학력무관');
insert into posting_tb( user_id, title, desc, region, position, expiry_date, career, education) values(8, '구인', '내용8', '부산', 'IOS', '2023-11-13','0-l년','고교 졸업');
insert into posting_tb( user_id, title, desc, region, position, expiry_date, career, education) values(8, '개발자모집', '내용9', '강원', '머신러닝', '2023-09-11','2년','고교 졸업');
insert into posting_tb( user_id, title, desc, region, position, expiry_date, career, education) values(9, '구인공고', '내용10', '대전', '백엔드', '2026-02-21','5년','박사 졸업');
insert into posting_tb( user_id, title, desc, region, position, expiry_date, career, education) values(9, '개발자구인', '내용11', '서울', '임베디드', '2045-03-21','6년','석사 졸업');
insert into posting_tb( user_id, title, desc, region, position, expiry_date, career, education) values(8, '제목1', '내용12', '서울', '프론트엔드', '2023-09-12','8년','대학 졸업');

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




insert into apply_tb( resume_id, posting_id, statement) values(1, 2, '대기');
insert into apply_tb( resume_id, posting_id, statement) values(4, 3, '합격');
insert into apply_tb( resume_id, posting_id, statement) values(2, 1, '대기');
insert into apply_tb( resume_id, posting_id, statement) values(5, 3, '불합');


insert into userbookmark_tb ( user_id, posting_id ) values(2, 2);
insert into userbookmark_tb ( user_id, posting_id ) values(2, 3);
insert into userbookmark_tb ( user_id, posting_id ) values(3, 1);
insert into userbookmark_tb ( user_id, posting_id ) values(5, 3);




-- insert into userskill_tb(user_id , java, db , html_and_css, python, javascript) values(2,true,true,true,true,true);
