create table decision_option_mapping (
	id bigint not null auto_increment,
	decision_id bigint not null,
	option_id bigint not null,
	primary key (id)
) ENGINE=InnoDB;

create table decisions (
	id bigint not null auto_increment,
	title varchar(255) not null,
	voting_planned_closing_date datetime not null,
	voting_planned_opening_date datetime not null,
	primary key (id)
) ENGINE=InnoDB;

alter table decision_option_mapping 
	add index FK67500DC7CBADE601 (decision_id), 
	add constraint FK67500DC7CBADE601 foreign key (decision_id) references decisions (id);

alter table decision_option_mapping
	add index FK67500DC7E4FC3921 (option_id),
	add constraint FK67500DC7E4FC3921 foreign key (option_id) references options (id);
