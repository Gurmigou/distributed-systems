drop schema lab_7;
create schema lab_7;
use lab_7;

create table `group` (
                         id int not null auto_increment primary key,
                         name varchar(255) not null
);

create table `student` (
                           id int not null auto_increment primary key,
                           first_name varchar(255) not null,
                           last_name varchar(255) not null,
                           group_id int not null,
                           foreign key (group_id) references `group`(id) on delete cascade
);
