
/* Drop Tables */
/*
DROP TABLE AAMUINTER CASCADE CONSTRAINTS;
DROP TABLE AUTHORITY_SECURITY CASCADE CONSTRAINTS;
DROP TABLE COMMUCOMMENT CASCADE CONSTRAINTS;
DROP TABLE COMMUPHOTO CASCADE CONSTRAINTS;
DROP TABLE COMMUPLACE CASCADE CONSTRAINTS;
DROP TABLE LIKEBOARD CASCADE CONSTRAINTS;
DROP TABLE COMMUNITY CASCADE CONSTRAINTS;
DROP TABLE DINERINFO CASCADE CONSTRAINTS;
DROP TABLE HOTELINFO CASCADE CONSTRAINTS;
DROP TABLE PLACESINFO CASCADE CONSTRAINTS;
DROP TABLE ROUTE CASCADE CONSTRAINTS;
DROP TABLE PLACES CASCADE CONSTRAINTS;
DROP TABLE RATEREVIEW CASCADE CONSTRAINTS;
DROP TABLE ROUTEBBSPHOTO CASCADE CONSTRAINTS;
DROP TABLE ROUTEBBS CASCADE CONSTRAINTS;
DROP TABLE ROUTEBOARD CASCADE CONSTRAINTS;
DROP TABLE THEME CASCADE CONSTRAINTS;
DROP TABLE USERS CASCADE CONSTRAINTS;
*/

CREATE SEQUENCE SEQ_REV
NOCACHE
NOCYCLE
;
CREATE SEQUENCE SEQ_REP
NOCACHE
NOCYCLE
;
CREATE SEQUENCE SEQ_COM
NOCACHE
NOCYCLE
;
CREATE SEQUENCE SEQ_PLA
NOCACHE
NOCYCLE
;


/* Create Tables */

CREATE TABLE AAMUINTER
(
	ID varchar2(20) NOT NULL,
	THEMEID number NOT NULL
);


CREATE TABLE AUTHORITY_SECURITY
(
	ID varchar2(20) NOT NULL,
	ENABLED number(1,0) DEFAULT 1 NOT NULL,
	AUTHORITY varchar2(20) DEFAULT 'ROLE_USER' NOT NULL
);


CREATE TABLE COMMUCOMMENT
(
	CNO number NOT NULL,
	ID varchar2(20) NOT NULL,
	LNO number NOT NULL,
	REPLY nvarchar2(500) NOT NULL,
	RDATE date DEFAULT SYSDATE NOT NULL,
	PRIMARY KEY (CNO)
);


CREATE TABLE COMMUNITY
(
	LNO number NOT NULL,
	ID varchar2(20) NOT NULL,
	TITLE nvarchar2(100) NOT NULL,
	CONTENT nvarchar2(2000) NOT NULL,
	POSTDATE date DEFAULT SYSDATE NOT NULL,
	LIKECOUNT number DEFAULT 0 NOT NULL,
	RCOUNT number DEFAULT 0 NOT NULL,
	PRIMARY KEY (LNO)
);


CREATE TABLE COMMUPHOTO
(
	LNO number NOT NULL,
	PHOTO nvarchar2(100) NOT NULL
);


CREATE TABLE COMMUPLACE
(
	LNO number NOT NULL,
	PLACE nvarchar2(1000) NOT NULL
);


CREATE TABLE DINERINFO
(
	CONTENTID number NOT NULL,
	MENU nvarchar2(2000),
	PARK nvarchar2(100),
	PRIMARY KEY (CONTENTID)
);


CREATE TABLE HOTELINFO
(
	CONTENTID number NOT NULL,
	CHECKIN nvarchar2(100),
	CHECKOUT nvarchar2(100),
	PRIMARY KEY (CONTENTID)
);


CREATE TABLE LIKEBOARD
(
	ID varchar2(20) NOT NULL,
	LNO number NOT NULL
);


CREATE TABLE PLACES
(
	CONTENTID number NOT NULL,
	AREACODE number NOT NULL,
	CONTENTTYPEID number NOT NULL,
	SIGUNGUCODE number NOT NULL,
	TITLE nvarchar2(200) NOT NULL,
	ADDR nvarchar2(100) NOT NULL,
	BIGIMAGE varchar2(1000) NOT NULL,
	SMALLIMAGE varchar2(1000) NOT NULL,
	MAPX number(23,20) NOT NULL,
	MAPY number(23,20) NOT NULL,
	KAKAOKEY number,
	TEL nvarchar2(100),
	PRIMARY KEY (CONTENTID)
);


CREATE TABLE PLACESINFO
(
	CONTENTID number NOT NULL,
	RESTTIME nvarchar2(2000),
	PLAYTIME nvarchar2(2000),
	PRIMARY KEY (CONTENTID)
);


CREATE TABLE RATEREVIEW
(
	RBN number NOT NULL,
	ID varchar2(20) NOT NULL,
	RATE number NOT NULL,
	REVIEW nvarchar2(30),
	RDATE date DEFAULT SYSDATE NOT NULL,
	RNO number NOT NULL,
	PRIMARY KEY (RNO)
);


CREATE TABLE ROUTE
(
	RBN number NOT NULL,
	CONTENTID number NOT NULL,
	ATIME nvarchar2(10) NOT NULL,
	DAY number NOT NULL,
	MTIME nvarchar2(10) NOT NULL
);


CREATE TABLE ROUTEBBS
(
	RBN number NOT NULL,
	TITLE nvarchar2(100) NOT NULL,
	POSTDATE date DEFAULT SYSDATE NOT NULL,
	CONTENT nvarchar2(2000),
	THEMEID number NOT NULL,
	PRIMARY KEY (RBN)
);


CREATE TABLE ROUTEBBSPHOTO
(
	RBN number NOT NULL,
	PHOTO nvarchar2(100) NOT NULL
);


CREATE TABLE ROUTEBOARD
(
	RBN number NOT NULL,
	TITLE nvarchar2(100) NOT NULL,
	ROUTEDATE date DEFAULT SYSDATE NOT NULL,
	STARTTIME long NOT NULL,
	ID varchar2(20) NOT NULL,
	PRIMARY KEY (RBN)
);


CREATE TABLE THEME
(
	THEMEID number NOT NULL,
	THEMENAME nvarchar2(20) NOT NULL,
	PRIMARY KEY (THEMEID)
);


CREATE TABLE USERS
(
	ID varchar2(20) NOT NULL,
	EMAIL nvarchar2(50) NOT NULL UNIQUE,
	PWD nvarchar2(30) NOT NULL,
	NAME nvarchar2(20) NOT NULL,
	GENDER nvarchar2(10) NOT NULL,
	PHONENUM nvarchar2(20) NOT NULL,
	ADDRID number NOT NULL,
	SELF nvarchar2(2000) NOT NULL,
	JOINDATE date DEFAULT SYSDATE NOT NULL,
	USERPROFILE nvarchar2(200) NOT NULL,
	PRIMARY KEY (ID)
);



/* Create Foreign Keys */

ALTER TABLE COMMUCOMMENT
	ADD FOREIGN KEY (LNO)
	REFERENCES COMMUNITY (LNO)
;


ALTER TABLE COMMUPHOTO
	ADD FOREIGN KEY (LNO)
	REFERENCES COMMUNITY (LNO)
;


ALTER TABLE COMMUPLACE
	ADD FOREIGN KEY (LNO)
	REFERENCES COMMUNITY (LNO)
;


ALTER TABLE LIKEBOARD
	ADD FOREIGN KEY (LNO)
	REFERENCES COMMUNITY (LNO)
;


ALTER TABLE HOTELINFO
	ADD FOREIGN KEY (CONTENTID)
	REFERENCES PLACES (CONTENTID)
;


ALTER TABLE PLACESINFO
	ADD FOREIGN KEY (CONTENTID)
	REFERENCES PLACES (CONTENTID)
;


ALTER TABLE ROUTE
	ADD FOREIGN KEY (CONTENTID)
	REFERENCES PLACES (CONTENTID)
;


ALTER TABLE DINERINFO
	ADD FOREIGN KEY (CONTENTID)
	REFERENCES PLACESINFO (CONTENTID)
;


ALTER TABLE RATEREVIEW
	ADD FOREIGN KEY (RBN)
	REFERENCES ROUTEBBS (RBN)
;


ALTER TABLE ROUTEBBSPHOTO
	ADD FOREIGN KEY (RBN)
	REFERENCES ROUTEBBS (RBN)
;


ALTER TABLE ROUTE
	ADD FOREIGN KEY (RBN)
	REFERENCES ROUTEBOARD (RBN)
;


ALTER TABLE ROUTEBBS
	ADD FOREIGN KEY (RBN)
	REFERENCES ROUTEBOARD (RBN)
;


ALTER TABLE AAMUINTER
	ADD FOREIGN KEY (THEMEID)
	REFERENCES THEME (THEMEID)
;


ALTER TABLE ROUTEBBS
	ADD FOREIGN KEY (THEMEID)
	REFERENCES THEME (THEMEID)
;


ALTER TABLE AAMUINTER
	ADD FOREIGN KEY (ID)
	REFERENCES USERS (ID)
;


ALTER TABLE AUTHORITY_SECURITY
	ADD FOREIGN KEY (ID)
	REFERENCES USERS (ID)
;


ALTER TABLE COMMUCOMMENT
	ADD FOREIGN KEY (ID)
	REFERENCES USERS (ID)
;


ALTER TABLE COMMUNITY
	ADD FOREIGN KEY (ID)
	REFERENCES USERS (ID)
;


ALTER TABLE LIKEBOARD
	ADD FOREIGN KEY (ID)
	REFERENCES USERS (ID)
;


ALTER TABLE RATEREVIEW
	ADD FOREIGN KEY (ID)
	REFERENCES USERS (ID)
;


ALTER TABLE ROUTEBOARD
	ADD FOREIGN KEY (ID)
	REFERENCES USERS (ID)
;



