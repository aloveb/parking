
INSERT INTO P_user(id,OPEN_ID,USER_NAME,CARD_ID,PLATE_NUM,PURSE)
VALUES (1,'LUCIA_WU','LUCIA','LUCIA667','A567B',155);

INSERT INTO P_user(id,OPEN_ID,USER_NAME,CARD_ID,PLATE_NUM,PURSE)
VALUES (2,'XAVIER_WU','XAVIER','XAVIER123','3B876',145);

INSERT INTO P_user(id,OPEN_ID,USER_NAME,CARD_ID,PLATE_NUM,PURSE)
VALUES (3,'SYLVIA_PENG','SYLVIA','SYLVIA233','89AA6',150);



INSERT INTO P_order(ORDER_ID,RENT_ID,TENANT_ID,PARK_AREA,PARK_BUILD,PARK_NUM,PRICE,RELEASE_DATE,CONFIRM_DATE,ORDER_DATE,ORDER_STATE,ORDER_LOCK)
VALUES (1,1,2,'A','3','63',5,'2018-03-22','2018-03-22','2018-03-23',2,0);

INSERT INTO P_order(ORDER_ID,RENT_ID,TENANT_ID,PARK_AREA,PARK_BUILD,PARK_NUM,PRICE,RELEASE_DATE,CONFIRM_DATE,ORDER_DATE,ORDER_STATE,ORDER_LOCK)
VALUES (2,1,3,'A','3','63',0,'2018-03-23','2018-03-24','2018-03-28',2,0);

INSERT INTO P_order(ORDER_ID,RENT_ID,TENANT_ID,PARK_AREA,PARK_BUILD,PARK_NUM,PRICE,RELEASE_DATE,CONFIRM_DATE,ORDER_DATE,ORDER_STATE,ORDER_LOCK)
VALUES (3,2,null,'C','7','10',0,'2018-03-15',null,'2018-03-17',0,0);

INSERT INTO P_order(ORDER_ID,RENT_ID,TENANT_ID,PARK_AREA,PARK_BUILD,PARK_NUM,PRICE,RELEASE_DATE,CONFIRM_DATE,ORDER_DATE,ORDER_STATE,ORDER_LOCK)
VALUES (4,2,3,'C','7','10',10,'2018-03-13','2018-03-14','2018-03-15',2,0);

INSERT INTO P_order(ORDER_ID,RENT_ID,TENANT_ID,PARK_AREA,PARK_BUILD,PARK_NUM,PRICE,RELEASE_DATE,CONFIRM_DATE,ORDER_DATE,ORDER_STATE,ORDER_LOCK)
VALUES (5,2,3,'C','7','10',20,'2018-03-10','2018-03-13','2018-03-13',2,0);

INSERT INTO P_order(ORDER_ID,RENT_ID,TENANT_ID,PARK_AREA,PARK_BUILD,PARK_NUM,PRICE,RELEASE_DATE,CONFIRM_DATE,ORDER_DATE,ORDER_STATE,ORDER_LOCK)
VALUES (6,3,null,'F','4','42',30,'2018-03-26',null,'2018-04-01',1,0);