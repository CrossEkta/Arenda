CREATE TABLE apartment
(
  apartment_id NUMBER(8) NOT NULL CONSTRAINT apartment_pk PRIMARY KEY,
  url          VARCHAR2(100) NOT NULL,
  address      VARCHAR2(50) NOT NULL,
  description  VARCHAR2(50) NOT NULL,
  rental       VARCHAR2(50) NOT NULL,
  ownerName    VARCHAR2(50) NOT NULL,
  storey       NUMBER(2)    NOT NULL,
  rooms        NUMBER(2)    NOT NULL,
  area         NUMBER(3,1)  NOT NULL
);