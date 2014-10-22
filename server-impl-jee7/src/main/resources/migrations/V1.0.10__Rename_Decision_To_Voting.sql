RENAME TABLE decisions TO votings, decision_option_mapping TO voting_option_mapping;

ALTER TABLE voting_option_mapping DROP FOREIGN KEY FK67500DC7CBADE601;
ALTER TABLE voting_option_mapping CHANGE decision_id voting_id bigint;
alter table voting_option_mapping add constraint FK67500DC7CBADE601 foreign key (voting_id) references votings (id);

ALTER TABLE votes DROP FOREIGN KEY fk_votes_decision_option_id;
ALTER TABLE votes DROP INDEX idx_votes_decision_option_id;
ALTER TABLE votes CHANGE decision_option_id voting_option_id bigint;
alter table votes 
	add index idx_votes_voting_option_id (voting_option_id), 
	add constraint fk_votes_voting_option_id foreign key (voting_option_id) references options (id);