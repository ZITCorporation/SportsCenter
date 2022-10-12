CREATE TABLE SportsCenter_USER(
user_id number(10) primary key,
user_name VARCHAR2(50) not null,
email VARCHAR2(256) not null,
pass VARCHAR2(30) not null,
phone_number VARCHAR2(11) not null,
post number(7),
domicile VARCHAR2(200),
authority NUMBER(1) not null
);