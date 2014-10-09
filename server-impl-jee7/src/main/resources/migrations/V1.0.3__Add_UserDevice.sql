create table user_device (
	id bigint not null auto_increment, 
	model varchar(255) not null,
	platform varchar(255) not null,
	uuid varchar(255) not null,
	user_id bigint not null,
	primary key (id)
) ENGINE=InnoDB;

alter table user_device 
	add index FKDCA4654A5F66E1A4 (user_id), 
	add constraint FKDCA4654A5F66E1A4 foreign key (user_id) references users (id);

alter table decisions add column voting_actual_closing_date datetime;
alter table decisions add column description varchar(255);