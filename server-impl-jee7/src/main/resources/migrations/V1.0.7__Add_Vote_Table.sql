create table votes (
	id bigint not null auto_increment,
	decision_option_id bigint not null,
	user_id bigint not null,
	value bigint not null,	
	create_date datetime,	
	primary key (id)
) ENGINE=InnoDB;

alter table votes 
	add index idx_votes_user_id (user_id), 
	add constraint fk_votes_user_id foreign key (user_id) references users (id);
	
alter table votes 
	add index idx_votes_decision_option_id (decision_option_id), 
	add constraint fk_votes_decision_option_id foreign key (decision_option_id) references options (id);