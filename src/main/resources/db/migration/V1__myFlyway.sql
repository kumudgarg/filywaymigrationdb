CREATE TABLE users(
     id int(20) NOT NULL AUTO_INCREMENT,
     username varchar(100) ,
     firstname varchar(50) ,
     lastname varchar(50) DEFAULT NULL,
     PRIMARY KEY (id),
     UNIQUE KEY UK_username (username)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;