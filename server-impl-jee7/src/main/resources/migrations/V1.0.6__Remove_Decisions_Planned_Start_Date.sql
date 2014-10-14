alter table decisions drop column voting_planned_opening_date;
alter table decisions change voting_planned_closing_date planned_closing_date datetime not null;
alter table decisions change voting_actual_closing_date actual_closing_date datetime;

alter table decisions add index (actual_closing_date);
