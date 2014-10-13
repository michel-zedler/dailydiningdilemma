alter table user_device add column apikey varchar(128);

update user_device set apikey = (select apikey from users u where u.id = user_device.user_id);

delete from user_device where apikey is null;
alter table user_device CHANGE apikey apikey varchar(128) unique not null;

alter table users drop column apikey;