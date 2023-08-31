insert into user_tb(username, email ,password , tel, photo, address, birth, role) values('김대홍','kdh@nate.com', '1234', '01010001111' ,  'basic.jpg','부산시 화명동', now(), 0);
insert into user_tb(username, email ,password , tel, photo, address, birth, role) values('유재석', 'ssar@nate.com', '1234', '01012341234',  'basic.jpg','서울시 성동구', now(),1);
insert into user_tb(username, email ,password , tel, photo, address, birth, role) values('박명수', 'cos@nate.com','1234', '01012344321',  'basic.jpg','인천시 미추홀구', now(),1);

insert into user_tb(compname , comp_register  ,email , password, tel, address, role) values('LG', '5050','love@nate.com', '1234', '01055555000', '서울', 2);
insert into user_tb(username, email ,password , tel, address, role) values('노홍철', 'haha@nate.com', '1234', '01022223333','부산시 수영구',1);
insert into user_tb(compname , comp_register  ,email , password, tel, address, role) values('배달의민족', '4636','bm@nate.com', '1234', '01099998888', '부산', 2);

insert into resume_tb(user_id, title ,grade , career, personal_statement , disclosure ) values(2, '안녕', '대졸', '신입','하이',true);
insert into resume_tb(user_id, title ,grade , career, personal_statement , disclosure ) values(3, '니하오', '고졸', '4년','nihao',true);
insert into resume_tb(user_id, title ,grade , career, personal_statement , disclosure ) values(5, '헬로', '초대졸', '1년','hello',false);
insert into resume_tb(user_id, title ,grade , career, personal_statement , disclosure ) values(2, '사와디캅', '대졸', '신입','ssawadi',true);
insert into resume_tb(user_id, title ,grade , career, personal_statement , disclosure ) values(5, '나마스떼', '초대졸', '1년','nanstte',true);
insert into resume_tb(user_id, title ,grade , career, personal_statement , disclosure ) values(2, '반갑', '대졸', '신입','반갑습니다',false);

insert into skill_list_tb (name) values('java');
insert into skill_list_tb (name) values('DB');
insert into skill_list_tb (name) values('html_and_css');
insert into skill_list_tb (name) values('python');
insert into skill_list_tb (name) values('javascript');

insert into posting_tb( user_id, title, desc, region, homepage, position) values(4, '임원구함', '경력 4년 이상', '서울','lg.com', '백엔드');
insert into posting_tb( user_id, title, desc, region, homepage, position) values(6, '신입구함', '경력 무관', '부산','bm.com', '백엔드');
insert into posting_tb( user_id, title, desc, region, homepage, position) values(4, '신입구함', '경력 무관', '서울','lg.com', '프론트엔드');

insert into user_skill_tb(user_id , java, db , html_and_css, python, javascript) values(2,true,true,true,true,true);
insert into user_skill_tb(user_id , java, db , html_and_css, python, javascript) values(3,false,true,false,true,false);
insert into user_skill_tb(user_id , java, db , html_and_css, python, javascript) values(4,true,true,false,false,false);
insert into user_skill_tb(user_id , java, db , html_and_css, python, javascript) values(5,false,false,true,false,true);
insert into user_skill_tb(user_id , java, db , html_and_css, python, javascript) values(6,false,false,true,false,true);



-- insert into user_tb(username, password, email, pic_url) values('cos', '1234', 'cos@nate.com', 'spongebob.jpeg');
-- insert into user_tb(username, password, email, pic_url) values('asdf', 'asdf', 'asdf@nate.com', 'haejin.jpg');
-- insert into board_tb(title, content, user_id, created_at) values('제목1', '내용1', 1, now());
-- insert into board_tb(title, content, user_id, created_at) values('제목1', '내용2', 1, now());
-- insert into board_tb(title, content, user_id, created_at) values('제목1', '내용3', 1, now());
-- insert into board_tb(title, content, user_id, created_at) values('제목2', '내용4', 2, now());
-- insert into board_tb(title, content, user_id, created_at) values('제목3', '내용5', 2, now());
-- insert into board_tb(title, content, user_id, created_at) values('제목3', '내용5', 2, now());
-- insert into board_tb(title, content, user_id, created_at) values('제목3', '내용5', 2, now());
-- insert into board_tb(title, content, user_id, created_at) values('제목3', '내용5', 2, now());
-- insert into reply_tb(comment, board_id, user_id) values('댓글1', 1, 2);
-- insert into reply_tb(comment, board_id, user_id) values('댓글2', 1, 2);
