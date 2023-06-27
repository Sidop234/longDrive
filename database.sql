use ld;
-- user table
create table if not exists users(registrationNo int primary key, firstName varchar(15),lastName
 varchar(15), emailID varchar(50),password varchar(50), lastActive
 datetime);
 -- files table
 create table if not exists files(fileID int primary key auto_increment, registrationNo int, filename varchar(70), filetype
 varchar(70), fileblob longblob,foreign key(registrationNo) references users(registrationNo) on delete cascade);