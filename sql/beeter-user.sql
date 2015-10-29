drop user 'beeter'@'localhost';
create user 'beeter'@'localhost' identified by 'beeter';
grant all privileges on grouptalkdb.* to 'beeter'@'localhost';
flush privileges; 
