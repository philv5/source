USE jimin;
/* --------------------------------------------------------
			DB Creation
 -------------------------------------------------------- */
DROP DATABASE jimin;

CREATE DATABASE IF NOT EXISTS jimin ;

USE jimin;

/* --------------------------------------------------------
		관리자
 -------------------------------------------------------- */
DROP TABLE IF EXISTS admins;

CREATE TABLE IF NOT EXISTS admins (
	admin_id		varchar(16)	NOT NULL ,
	admin_pw		varchar(16)	NOT NULL ,
	admin_Fname	varchar(16) 	NOT NULL ,
	admin_Lname	varchar(16) 	NOT NULL ,
	CONSTRAINT admin_PK 
		PRIMARY KEY (admin_id)
);

INSERT INTO admins(admin_id, admin_pw, admin_Fname, admin_Lname) values ('philv5','opop12','Jimin','Lee');
INSERT INTO admins(admin_id, admin_pw, admin_Fname, admin_Lname) values ('root','password','Root','Root');

/* --------------------------------------------------------
		사용자
 -------------------------------------------------------- */
DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS users(
	user_id			varchar(16)	NOT NULL ,
	user_pw		varchar(16)	NOT NULL ,
	user_Fname	varchar(16) 	NOT NULL ,
	user_Lname	varchar(16) 	NOT NULL ,
	jumin_no		char(13)	NOT NULL ,
	bdate			date, /* YYYY-MM-DD */
	e_mail			varchar(32),
	sex			char, 	/* M or F */
	postcode		char(5),
	address		varchar(64),
	job				varchar(16),
	CONSTRAINT user_PK 
		PRIMARY KEY (user_id)
);


INSERT INTO users(user_id, user_pw, user_Fname, user_Lname, jumin_no, bdate, sex, postcode, address,job) values ('user1','qwer123','Jisoo','Lee', '0501081048567', '2005-01-08', 'F', '12308', 'Tokyo Sinden Adachi 3-37-12-710', 'student');
INSERT INTO users(user_id, user_pw, user_Fname, user_Lname, jumin_no, bdate, sex, postcode, address,job) values ('user2','qwer123','Jiwoong','Lee', '9510141048127', '1995-10-14', 'M', '26202', 'Mangwon 1-dong, Mapo-gu, Seoul, Korea', 'student');
INSERT INTO users(user_id, user_pw, user_Fname, user_Lname, jumin_no, bdate, sex, postcode, address,job) values ('user3','qwer123','Unjin','Kim', '7304057364538', '1973-04-05', 'M', '12194', '121, Mullae-ro, Yeongdeungpo-gu, Seoul', 'businessman');
INSERT INTO users(user_id, user_pw, user_Fname, user_Lname,jumin_no) values ('user4','qwer123','Hyunu','Kim', '8801011048345');
INSERT INTO users(user_id, user_pw, user_Fname, user_Lname,jumin_no, bdate, sex, postcode, address, job) values ('user5','qwer123','Jiunko','Lee', '7710191048789', '1977-10-19', 'F', '07293', '125, Sejong-daero, Jung-gu, Seoul', 'office worker');
INSERT INTO users(user_id, user_pw, user_Fname, user_Lname,jumin_no, bdate, sex, postcode, address, job) values ('user6','qwer123','Sihyung','Bae', '9209081048908', '1992-09-08', 'M', '07225', '48-74, Namyangju-si, Gyeonggi-do', 'accountant');
INSERT INTO users(user_id, user_pw, user_Fname, user_Lname,jumin_no, bdate, sex, postcode, address, job) values ('user7','qwer123','Sehwang','Kim', '8912121048456', '1989-12-12', 'F', '04519', '6, Sopa-ro 4-gil, Jung-gu, Seoul', 'student');
INSERT INTO users(user_id, user_pw, user_Fname, user_Lname,jumin_no) values ('user8','qwer123','Songhun','Bae', '8610141048123');
INSERT INTO users(user_id, user_pw, user_Fname, user_Lname,jumin_no) values ('user9','qwer123','Yongyun','Lim', '9810141038456');
INSERT INTO users(user_id, user_pw, user_Fname, user_Lname,jumin_no) values ('user10','qwer123','Donghyun','Lee', '9903041098345');

/* --------------------------------------------------------
		아티스트
 -------------------------------------------------------- */
DROP TABLE IF EXISTS artists;

CREATE TABLE IF NOT EXISTS artists (
	artist_id			varchar(16)	NOT NULL ,
	artist_name		varchar(16) 	NOT NULL ,
	division			char , /* S(solo) or G(group) */
	agency			varchar(16),
	CONSTRAINT artist_PK 
		PRIMARY KEY (artist_id)
);

INSERT INTO artists(artist_id, artist_name, division, agency) values ('001','artist1','G','Gyanees');
INSERT INTO artists(artist_id, artist_name, division, agency) values ('002','artist2','G','YG');
INSERT INTO artists(artist_id, artist_name, division, agency) values ('003','artist3','G','JYP');
INSERT INTO artists(artist_id, artist_name, division, agency) values ('004','artist4','S','LeeKimBakery');
INSERT INTO artists(artist_id, artist_name, division, agency) values ('005','artist5','S','LeeKimBakery');

/* --------------------------------------------------------
		작곡가
 -------------------------------------------------------- */
DROP TABLE IF EXISTS composers;

CREATE TABLE IF NOT EXISTS composers (
	comp_id		varchar(16)	NOT NULL ,
	comp_name	varchar(16) 	NOT NULL ,
	agency			varchar(16),
	CONSTRAINT comp_PK 
		PRIMARY KEY (comp_id)
);

INSERT INTO composers(comp_id, comp_name, agency) values ('001','comp1', 'Maria');
INSERT INTO composers(comp_id, comp_name, agency) values ('002','comp2', 'MySQL');
INSERT INTO composers(comp_id, comp_name, agency) values ('003','comp3', 'Oracle');
INSERT INTO composers(comp_id, comp_name, agency) values ('004','comp4', 'Oracle');
INSERT INTO composers(comp_id, comp_name, agency) values ('005','comp5', 'Maria');

/* --------------------------------------------------------
		작사가
 -------------------------------------------------------- */
DROP TABLE IF EXISTS songwriters;

CREATE TABLE IF NOT EXISTS songwriters (
	swriter_id		varchar(16)	NOT NULL ,
	swriter_name	varchar(16) 	NOT NULL ,
	agency			varchar(16),
	CONSTRAINT swrite_PK 
		PRIMARY KEY (swriter_id)
);

INSERT INTO songwriters(swriter_id, swriter_name, agency) values ('001','swriter1', 'Maria');
INSERT INTO songwriters(swriter_id, swriter_name, agency) values ('002','swriter2', 'MySQL');
INSERT INTO songwriters(swriter_id, swriter_name, agency) values ('003','swriter3', 'Maria');
INSERT INTO songwriters(swriter_id, swriter_name, agency) values ('004','swriter4', 'Oracle');
INSERT INTO songwriters(swriter_id, swriter_name, agency) values ('005','swriter5', 'SQLsever');

/* --------------------------------------------------------
		앨범
 -------------------------------------------------------- */
DROP TABLE IF EXISTS albums;

CREATE TABLE IF NOT EXISTS albums (
	album_id		varchar(16)	NOT NULL ,
	album_title		varchar(16) 	NOT NULL ,
	agency			varchar(16),
	album_rdate		date,
	CONSTRAINT album_PK 
		PRIMARY KEY (album_id)
);

INSERT INTO albums(album_id, album_title, agency,album_rdate) values ('001','album1', 'Maria', '1995-10-14');
INSERT INTO albums(album_id, album_title, agency) values ('002','album2', 'Maria');
INSERT INTO albums(album_id, album_title, agency) values ('003','album3', 'SQLsever');

/* --------------------------------------------------------
		음악
 -------------------------------------------------------- */
DROP TABLE IF EXISTS musics;

CREATE TABLE IF NOT EXISTS musics (
	music_id		varchar(16)	NOT NULL ,
	music_title		varchar(16) 	NOT NULL ,
	artist_id			varchar(16),
	comp_id		varchar(16),
	swriter_id		varchar(16),
	album_id		varchar(16),
	genre			varchar(16),

	CONSTRAINT music_PK 
		PRIMARY KEY (music_id),
	CONSTRAINT music_FK_artistid 
		FOREIGN KEY (artist_id) REFERENCES artists(artist_id),
	CONSTRAINT music_FK_compid 
		FOREIGN KEY (comp_id) REFERENCES composers(comp_id),
	CONSTRAINT music_FK_swriterid
		FOREIGN KEY (swriter_id) REFERENCES songwriters(swriter_id),
	CONSTRAINT music_FK_albumid 
		FOREIGN KEY (album_id) REFERENCES albums(album_id)
);

INSERT INTO musics(music_id,music_title, artist_id,comp_id, swriter_id, album_id, genre) values ('001','music1', '001', '001', '001', '001', 'Rock');
INSERT INTO musics(music_id,music_title, artist_id,comp_id, swriter_id, album_id, genre) values ('002','music2', '002', '002', '002', '002', 'Dance');
INSERT INTO musics(music_id,music_title, artist_id,comp_id, swriter_id, album_id, genre) values ('003','music3', '003', '003', '003', '003', 'Hip Hop');
INSERT INTO musics(music_id,music_title, artist_id,comp_id, swriter_id, album_id, genre) values ('004','music4', '004', '004', '004', '003', 'Pop');
INSERT INTO musics(music_id,music_title, artist_id,comp_id, swriter_id, album_id, genre) values ('005','music5', '005', '005', '005', '002', 'Classical');

/* --------------------------------------------------------
		플레이리스트
 -------------------------------------------------------- */
DROP TABLE IF EXISTS playlists;

CREATE TABLE IF NOT EXISTS playlists (
	plist_id			varchar(16)	NOT NULL ,
	user_id			varchar(16) 	NOT NULL ,
	plist_title		varchar(16)	NOT NULL ,
	CONSTRAINT playlist_PK 
		PRIMARY KEY (plist_id),
	CONSTRAINT playlist_FK_userid 
		FOREIGN KEY (user_id) REFERENCES users(user_id)
);
	
CREATE UNIQUE INDEX playlists_Idx1 ON playlists  (
	 user_id
	,plist_title 
) ;

INSERT INTO playlists(plist_id, user_id, plist_title) values ('001','user1', 'playlist1');
INSERT INTO playlists(plist_id, user_id, plist_title) values ('002','user1', 'playlist2');
INSERT INTO playlists(plist_id, user_id, plist_title) values ('003','user1', 'playlist3');
INSERT INTO playlists(plist_id, user_id, plist_title) values ('004','user2', 'playlist1');
INSERT INTO playlists(plist_id, user_id, plist_title) values ('005','user2', 'playlist2');
INSERT INTO playlists(plist_id, user_id, plist_title) values ('006','user3', 'playlist1');
INSERT INTO playlists(plist_id, user_id, plist_title) values ('007','user3', 'playlist2');
INSERT INTO playlists(plist_id, user_id, plist_title) values ('008','user3', 'playlist3');

/* --------------------------------------------------------
		플레이리스트 등록 상세
 -------------------------------------------------------- */
DROP TABLE IF EXISTS playlist_registrations;

CREATE TABLE IF NOT EXISTS playlist_registrations (
	plist_id			varchar(16) 	NOT NULL ,
	music_id		varchar(16)	NOT NULL,
	CONSTRAINT playlist_registration_PK 
		PRIMARY KEY (plist_id, music_id),
	CONSTRAINT playlist_FK_musicid 
		FOREIGN KEY (music_id) REFERENCES musics(music_id),
	CONSTRAINT playlist_FK_plistid 
		FOREIGN KEY (plist_id) REFERENCES playlists(plist_id)
);

INSERT INTO playlist_registrations(plist_id,music_id) values ('001','001');
INSERT INTO playlist_registrations(plist_id,music_id) values ('001','002');
INSERT INTO playlist_registrations(plist_id,music_id) values ('001','003');
INSERT INTO playlist_registrations(plist_id,music_id) values ('002','004');
INSERT INTO playlist_registrations(plist_id,music_id) values ('002','005');
INSERT INTO playlist_registrations(plist_id,music_id) values ('003','001');
INSERT INTO playlist_registrations(plist_id,music_id) values ('003','002');
INSERT INTO playlist_registrations(plist_id,music_id) values ('003','003');
INSERT INTO playlist_registrations(plist_id,music_id) values ('003','004');
INSERT INTO playlist_registrations(plist_id,music_id) values ('004','002');
INSERT INTO playlist_registrations(plist_id,music_id) values ('004','003');
INSERT INTO playlist_registrations(plist_id,music_id) values ('004','004');
INSERT INTO playlist_registrations(plist_id,music_id) values ('004','005');
INSERT INTO playlist_registrations(plist_id,music_id) values ('005','001');
INSERT INTO playlist_registrations(plist_id,music_id) values ('005','002');
INSERT INTO playlist_registrations(plist_id,music_id) values ('005','003');
INSERT INTO playlist_registrations(plist_id,music_id) values ('006','001');
INSERT INTO playlist_registrations(plist_id,music_id) values ('006','004');
INSERT INTO playlist_registrations(plist_id,music_id) values ('006','005');
INSERT INTO playlist_registrations(plist_id,music_id) values ('007','003');
INSERT INTO playlist_registrations(plist_id,music_id) values ('007','005');
INSERT INTO playlist_registrations(plist_id,music_id) values ('008','002');
INSERT INTO playlist_registrations(plist_id,music_id) values ('008','004');
INSERT INTO playlist_registrations(plist_id,music_id) values ('008','005');

/* --------------------------------------------------------
		ID발행 번호 관리
 -------------------------------------------------------- */
DROP TABLE IF EXISTS id_mng;

CREATE TABLE IF NOT EXISTS id_mng (
	id_name		varchar(16)	NOT NULL,
	id_no			integer	NOT NULL,
	CONSTRAINT id_mng_PK 
		PRIMARY KEY (id_name)
);
/* 각 ID번호는 100에서 시작한다. */
INSERT INTO id_mng(id_name,id_no) values ('artist_id', 100);
INSERT INTO id_mng(id_name,id_no) values ('comp_id', 100);
INSERT INTO id_mng(id_name,id_no) values ('swriter_id', 100);
INSERT INTO id_mng(id_name,id_no) values ('album_id', 100);
INSERT INTO id_mng(id_name,id_no) values ('music_id', 100);
INSERT INTO id_mng(id_name,id_no) values ('plist_id', 100);

