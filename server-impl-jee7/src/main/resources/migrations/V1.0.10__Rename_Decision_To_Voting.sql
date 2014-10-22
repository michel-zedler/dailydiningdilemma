RENAME TABLE decisions TO votings, decision_option_mapping TO voting_option_mapping;
ALTER TABLE voting_option_mapping CHANGE decision_id voting_id bigint;
ALTER TABLE votes CHANGE decision_option_id voting_option_id bigint;