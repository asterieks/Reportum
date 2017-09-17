DROP TABLE REPORTS;
DROP TABLE PROJECTS;
DROP TABLE USERS;

CREATE TABLE USERS
("USER_ID" VARCHAR2(255 BYTE) NOT NULL ENABLE,
"FULL_NAME" VARCHAR2(150 BYTE) NOT NULL ENABLE,
"PASSWORD" VARCHAR2(150 BYTE) NOT NULL ENABLE,
"PROFILE" VARCHAR2(150 BYTE),
"AUTHORITIES" VARCHAR2(150 BYTE),
"PRIVATE_SECRET" VARCHAR2(4000 BYTE),
"PUBLIC_SECRET" VARCHAR2(4000 BYTE),
PRIMARY KEY ("USER_ID"));

CREATE TABLE PROJECTS
("PROJECT_ID" NUMBER NOT NULL ENABLE,
"PROJECT_NAME" VARCHAR2(255 BYTE),
"RESPONSIBLE_TEAM_LEADER" VARCHAR2(255 BYTE),
"MANAGER" VARCHAR2(255 BYTE),
"REPORTER" VARCHAR2(255 BYTE),
"STATE" VARCHAR2(255 BYTE),
"STATE_DATE" TIMESTAMP (6),
PRIMARY KEY ("PROJECT_ID"));

CREATE TABLE REPORTS
("REPORT_ID" NUMBER NOT NULL ENABLE,
"PROJECT_ID" NUMBER NOT NULL ENABLE,
"REVIEW_PART" VARCHAR2(4000 BYTE),
"ISSUE_PART" VARCHAR2(4000 BYTE),
"PLAN_PART" VARCHAR2(4000 BYTE),
"CREATION_DATE" TIMESTAMP (6) NOT NULL ENABLE,
"REPORTED_BY" VARCHAR2(255 BYTE),
PRIMARY KEY ("REPORT_ID"));

commit;

INSERT INTO USERS (USER_ID, FULL_NAME, PASSWORD, PROFILE, AUTHORITIES, PRIVATE_SECRET, PUBLIC_SECRET) VALUES ('asterieks@gmail.com', 'Maksym Sokil', '1', 'REPORTER', 'ROLE_REPORTER','','');
INSERT INTO USERS (USER_ID, FULL_NAME, PASSWORD, PROFILE, AUTHORITIES, PRIVATE_SECRET, PUBLIC_SECRET) VALUES ('lead@gmail.com', 'Yevhenii Reva', '2','LEAD', 'ROLE_MANAGER','','');
INSERT INTO USERS (USER_ID, FULL_NAME, PASSWORD, PROFILE, AUTHORITIES, PRIVATE_SECRET, PUBLIC_SECRET) VALUES ('tester@gmail.com', 'A Person', '3', 'REPORTER', 'ROLE_REPORTER','','');

INSERT INTO PROJECTS (PROJECT_ID, PROJECT_NAME, RESPONSIBLE_TEAM_LEADER,MANAGER,REPORTER,STATE,STATE_DATE) VALUES ('1', 'BAT',  'lead@gmail.com', '', 'asterieks@gmail.com','Delayed','');
INSERT INTO PROJECTS (PROJECT_ID, PROJECT_NAME, RESPONSIBLE_TEAM_LEADER,MANAGER,REPORTER,STATE,STATE_DATE) VALUES ('2', 'EFP',   'lead@gmail.com', '', 'tester@gmail.com','Delayed','');
INSERT INTO PROJECTS (PROJECT_ID, PROJECT_NAME, RESPONSIBLE_TEAM_LEADER,MANAGER,REPORTER,STATE,STATE_DATE) VALUES ('3', 'ULAS', 'lead@gmail.com', '', 'asterieks@gmail.com','Delayed','');

commit;