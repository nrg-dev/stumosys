
---- Master Data -----
--Script 1
CREATE TABLE inventory.login  
	(
	login_id INT(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,  
	login_user VARCHAR(20) NOT NULL UNIQUE,
	login_password VARCHAR(50)
	)
ENGINE=InnoDB;
insert into inventory.login (login_user,login_password) values('Admin','Admin');



--Script 2 Static Table
CREATE TABLE inventory.rights (
		  rights_ID  INT(20)  NOT NULL AUTO_INCREMENT PRIMARY KEY , 
		  rights  VARCHAR(50) NOT NULL, 
		  mode  VARCHAR(100) 
		    )   
		ENGINE=InnoDB;
insert into inventory.rights (rights,mode) values ('Product','777');
insert into inventory.rights (rights,mode) values ('Stock','777');
insert into inventory.rights (rights,mode)  values ('Sales','777');
insert into inventory.rights (rights,mode)  values ('Invoice','777');
insert into inventory.rights (rights,mode)  values ('Reports','777');

--Script 3
CREATE TABLE inventory.login_rights  (
		  login_rights_ID  INT(10)  NOT NULL AUTO_INCREMENT PRIMARY KEY , 
		  login_id  INT(10) NOT NULL, 
		  rights_ID  INT(10) NOT NULL , 
		  foreign key (login_id) references inventory.login (login_id),
		  foreign key (rights_ID) references inventory.rights (rights_ID)
		  )   
		ENGINE=InnoDB;
insert into inventory.login_rights (login_id,rights_ID)values(1,1);
