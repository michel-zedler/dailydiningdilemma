alter table votes 
	drop foreign key fk_votes_decision_option_id;
drop index idx_votes_decision_option_id on votes;

alter table votes 
	add index idx_votes_decision_option_id (decision_option_id), 
	add constraint fk_votes_decision_option_id foreign key (decision_option_id) references decision_option_mapping (id);