CREATE TABLE USER_DETAILS (LOGIN_NAME VARCHAR2(25) NOT NULL, PASSWORD VARCHAR2(25) NOT NULL, EMAIL VARCHAR2(128), JOIN_DATE TIMESTAMP NOT NULL);

ALTER TABLE USER_DETAILS ADD CONSTRAINT USER_DETAILS_PK PRIMARY KEY (LOGIN_NAME);