create table options (
	id bigint not null auto_increment,
	coordinates varchar(255) not null,
	time_added datetime not null,
	description varchar(255),
	time_last_usage datetime,
	name varchar(255) not null,
	phone varchar(255),
	url varchar(255),
	primary key (id)
) ENGINE=InnoDB;
