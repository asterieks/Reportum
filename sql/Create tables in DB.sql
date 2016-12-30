--clear tables
DROP TABLE "REPORTUM_REPOS"."USER_REPORTS";
DROP TABLE "REPORTUM_REPOS"."PROJECTS";
DROP TABLE "REPORTUM_REPOS"."USERS";
--clear sequence
DROP SEQUENCE user_report_id_seq;
DROP SEQUENCE user_report_record_id_seq;

--tables
CREATE TABLE "REPORTUM_REPOS"."USERS"
("USER_ID" VARCHAR2(255 BYTE) NOT NULL ENABLE,
"FULL_NAME" VARCHAR2(150 BYTE) NOT NULL ENABLE,
"ROLE" VARCHAR2(150 BYTE),
PRIMARY KEY ("USER_ID"));

CREATE TABLE "REPORTUM_REPOS"."PROJECTS"
("PROJECT_ID" NUMBER NOT NULL ENABLE,
"PROJECT_NAME" VARCHAR2(255 BYTE),
"RESPONSIBLE_TEAM_LEADER" VARCHAR2(255 BYTE),
"MANAGER" VARCHAR2(255 BYTE),
"REPORTER" VARCHAR2(255 BYTE),
PRIMARY KEY ("PROJECT_ID"));

CREATE TABLE "REPORTUM_REPOS"."USER_REPORTS"
("REPORT_ID" NUMBER NOT NULL ENABLE,
"RECORD_ID" NUMBER NOT NULL ENABLE,
"PROJECT_ID" NUMBER NOT NULL ENABLE,
"REVIEW_PART" VARCHAR2(4000 BYTE),
"ISSUE_PART" VARCHAR2(4000 BYTE),
"PLAN_PART" VARCHAR2(4000 BYTE),
"CREATION_DATE" TIMESTAMP (6) NOT NULL ENABLE,
PRIMARY KEY ("REPORT_ID"));

commit;

--sequences
CREATE SEQUENCE user_report_id_seq
  MINVALUE 1 MAXVALUE 999999999999999
  START WITH 1
  INCREMENT BY 1
  NOORDER NOCYCLE;

CREATE SEQUENCE user_report_record_id_seq
  MINVALUE 1 MAXVALUE 999999999999999
  START WITH 1
  INCREMENT BY 1
  NOORDER NOCYCLE;

commit;

--test instances
INSERT INTO "REPORTUM_REPOS"."USERS" (USER_ID, FULL_NAME, ROLE) VALUES ('asterieks@gmail.com', 'Maksym Sokil', '');

INSERT INTO "REPORTUM_REPOS"."PROJECTS" (PROJECT_ID, PROJECT_NAME, RESPONSIBLE_TEAM_LEADER,MANAGER,REPORTER) VALUES ('1', 'Reportum Test', '','','asterieks@gmail.com');
INSERT INTO "REPORTUM_REPOS"."PROJECTS" (PROJECT_ID, PROJECT_NAME, RESPONSIBLE_TEAM_LEADER,MANAGER,REPORTER) VALUES ('2', 'Test Project', '','','tester@gmail.com');
INSERT INTO "REPORTUM_REPOS"."PROJECTS" (PROJECT_ID, PROJECT_NAME, RESPONSIBLE_TEAM_LEADER,MANAGER,REPORTER) VALUES ('3', 'Second Project', '','','asterieks@gmail.com');

commit;