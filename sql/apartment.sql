CREATE TABLE apartment
(
  apartment_id NUMBER(8) NOT NULL CONSTRAINT apartment_pk PRIMARY KEY,
  url          VARCHAR2(100) NOT NULL, -- ссылка на сайте
  address      VARCHAR2(50) NOT NULL, -- адрес
  description  VARCHAR2(500) NOT NULL, -- описание
  rental       NUMBER(5)    NOT NULL, -- стоимость аренды
  ownerName    VARCHAR2(50) NOT NULL, -- заявитель
  storey       NUMBER(2)    NOT NULL, -- этаж
  rooms        NUMBER(2)    NOT NULL, -- кол-во комнат
  area         NUMBER(3,1)  NOT NULL, -- площадь
  
  stamp           DATE      NOT NULL  -- время создания
);

CREATE TABLE apartment_tried
(
  apartment_tried NUMBER(8) NOT NULL CONSTRAINT apartment_tried_pk PRIMARY KEY, 
  apartment_id    NUMBER(8) NOT NULL CONSTRAINT apartment_tried_fk_apart REFERENCES apartment,
  
  stamp           DATE      NOT NULL,  -- время создания
  user_name       VARCHAR2(20) NOT NULL  -- кто создал
);

