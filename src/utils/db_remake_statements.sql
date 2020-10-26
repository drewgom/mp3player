# Adding the artist table

create table artist(
	id integer not null generated always as identity (start with 1, increment by 1),
	name varchar(32) not null,
	constraint arist_pk primary key(id)
);

# Adding the song table
create table song(
	id integer not null generated always as identity (start with 1, increment by 1),
	title varchar(64) not null,
	album varchar(64) not null,
	yr varchar(8) not null,
	comments varchar(256),
	genre varchar(32) not null,
    constraint song_pk primary key (id)
);

# Adding artist-song pairing table

create table song_artist(
	song_pk integer not null,
	artist_pk integer not null,
	constraint song_artist_pk primary key(song_pk, artist_pk)
);