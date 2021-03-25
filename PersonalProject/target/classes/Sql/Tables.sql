--drop table users1;
--drop table user_groups;
--drop table tasks;

Create table users1(
userId int Primary key NOT NULL,
username Varchar(15) NOT NULL UNIQUE,
password varchar(10) NOT NULL
);

CREATE OR REPLACE TRIGGER increments
  BEFORE INSERT ON users1
  FOR EACH ROW
BEGIN
 :NEW.userId := user_seq.NEXTVAL;
END;

create sequence user_seq
minvalue 1
maxvalue 99999999999999
start with 1
increment by 1;

create table user_groups(
group_name varchar(20),
userId int,
 FOREIGN KEY (userId) REFERENCES users1(userId),
 groupId int primary key);

 CREATE OR REPLACE TRIGGER increments1
  BEFORE INSERT ON user_groups
  FOR EACH ROW
BEGIN
 :NEW.groupId := user_seq.NEXTVAL;
END;

Create table tasks(
taskId int primary key,
desc1 VARCHAR2(30),
assgnment_dt TIMESTAMP NOT NULL,
assigneId int,
 FOREIGN KEY (assigneId) REFERENCES users1(userId)
);

CREATE OR REPLACE TRIGGER increments2
  BEFORE INSERT ON tasks
  FOR EACH ROW
BEGIN
 :NEW.taskId := user_seq.NEXTVAL;
END;