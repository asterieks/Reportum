DROP TABLE REPORTS;
DROP TABLE PROJECTS;
DROP TABLE USERS;
DROP TABLE JOB_STATE;

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
"ACTUAL" NUMBER(1,0) default 0 NOT NULL
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

CREATE TABLE JOB_STATE
("JOB_ID" NUMBER NOT NULL ENABLE,
"JOB_NAME" VARCHAR2(150 BYTE) NOT NULL ENABLE,
"STATE_DATE" TIMESTAMP (6),
PRIMARY KEY ("JOB_ID"));

INSERT INTO JOB_STATE (JOB_ID, JOB_NAME, STATE_DATE) VALUES ('1', 'PROJECT STATE', null);

CREATE SEQUENCE USER_REPORT_ID_SEQ  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;

commit;