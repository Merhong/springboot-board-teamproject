insert into user_tb(username, email ,password , tel, address, role) values('김대홍', '1234', 'kdh@nate.com', '01010001111','부산시 화명동',0);
insert into user_tb(username, email ,password , tel, address, role) values('유재석', '1234', 'ujs@nate.com', '01012341234','서울시 성동구',1);
insert into user_tb(username, email ,password , tel, address, role) values('박명수', '1234', 'pms@nate.com', '01012344321','인천시 미추홀구
',1);
insert into user_tb(compname , comp_register  ,email , tel, password, address, role) values('LG', '5050','1234', 'lg@nate.com', '01055555000','서울',2);
insert into user_tb(username, email ,password , tel, address, role) values('노홍철', '1234', 'nhc@nate.com', '01022223333','부산시 수영구',1);
insert into user_tb(compname , comp_register  ,email , tel, password, address, role) values('배달의민족', '4636','1234', 'bm@nate.com', '01099998888','부산',2);


insert into resume_tb(user_id, title ,grade , career, personal_statement , disclosure ) values(2, '안녕', '대졸', '신입','하이',true);
insert into resume_tb(user_id, title ,grade , career, personal_statement , disclosure ) values(3, '니하오', '고졸', '4년','nihao',true);
insert into resume_tb(user_id, title ,grade , career, personal_statement , disclosure ) values(5, '헬로', '초대졸', '1년','hello',false);
insert into resume_tb(user_id, title ,grade , career, personal_statement , disclosure ) values(2, '사와디캅', '대졸', '신입','ssawadi',true);
insert into resume_tb(user_id, title ,grade , career, personal_statement , disclosure ) values(5, '나마스떼', '초대졸', '1년','nanstte',true);
insert into resume_tb(user_id, title ,grade , career, personal_statement , disclosure ) values(2, '반갑', '대졸', '신입','반갑습니다',false);

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
