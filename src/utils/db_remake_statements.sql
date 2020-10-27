create table song(
	id integer not null generated always as identity (start with 1, increment by 1),
	title varchar(64) not null,
	artist varchar(64) not null, 
	album varchar(64) not null,
	yr varchar(8),
	comments varchar(256),
	genre varchar(32) not null,
	pth varchar(512) not null,
	constraint song_pk primary key (id),
	constraint song_uk_1 unique (pth)
);