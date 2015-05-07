-- заблокированные номера телефонов
CREATE TABLE phone_lock
(
  phone_lock_id NUMBER(8) NOT NULL CONSTRAINT phone_lock_pk PRIMARY KEY,
  phone_number VARCHAR2(11) NOT NULL
);

CREATE SEQUENCE phone_lock_seq;