-- MySQL
-- CREATE TABLE STUDENT (
--     SNO VARCHAR2(3 BYTE) NOT NULL ,
--     SNAME VARCHAR2(9 BYTE) NOT NULL ,
--     SSEX CHAR(2 BYTE) NOT NULL
-- );
-- INSERT INTO STUDENT VALUES ('001', 'KangKang', 'M ');
-- INSERT INTO STUDENT VALUES ('002', 'Mike', 'M ');
-- INSERT INTO STUDENT VALUES ('003', 'Jane', 'F ');

-- Postgresql
-- 若表存在则drop
DROP TABLE IF EXISTS student;

CREATE TABLE "student" (
                           "sno" VARCHAR(3) NOT NULL,
                           "sname" VARCHAR(9) NOT NULL,
                           "ssex" CHAR(2) NOT NULL
);
INSERT INTO student VALUES ('001', 'KangKang', 'M ');
INSERT INTO student VALUES ('002', 'Mike', 'M ');
INSERT INTO student VALUES ('003', 'Jane', 'F ');