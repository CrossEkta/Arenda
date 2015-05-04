-- заблокированные номера телефонов
CREATE TABLE phone_lock
(
  phone_lock NUMBER(8) NOT NULL CONSTRAINT phone_lock_pk PRIMARY KEY,
  phone_number VARCHAR2(11) NOT NULL
);