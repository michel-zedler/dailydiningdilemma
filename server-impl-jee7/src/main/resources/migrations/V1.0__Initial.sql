create table configuration (
	id bigint not null auto_increment, 
	name varchar(255) not null unique, 
	value varchar(255), 
	primary key (id)
) ENGINE=InnoDB;

create table user_oauth_service (
	id bigint not null auto_increment,
	service_id varchar(255),
	service_name varchar(255),
	user_id bigint,
	primary key (id),
	unique (user_id, service_id)
) ENGINE=InnoDB;

ALTER TABLE user_oauth_service ADD CONSTRAINT ucon_user_oauth_service_1 UNIQUE (service_id, user_id);

create table users (
	id bigint not null auto_increment,
	apikey varchar(128),
	displayName varchar(64) not null,
	primary key (id)
) ENGINE=InnoDB;

alter table user_oauth_service add index FK3817B3995F66E1A4 (user_id),
	add constraint FK3817B3995F66E1A4 foreign key (user_id) references users (id);
