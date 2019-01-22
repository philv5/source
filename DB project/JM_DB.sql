/* --------------------------------------------------------
			관리자 로그인
 -------------------------------------------------------- */
SELECT * FROM admins WHERE admin_id = 'id';  -- 관리자 테이블에서 해당 관리자ID의 모든 정보를 검색.


/* --------------------------------------------------------
			사용자 로그인
 -------------------------------------------------------- */
 SELECT user_pw FROM users WHERE user_id = 'id';	-- 사용자 테이블에서 해당 사용자ID의 password를 검색.
 
 
 /* --------------------------------------------------------
			관리자 정보 관리
 -------------------------------------------------------- */
 /* 조회 */
 SELECT * FROM admins;	-- 관리자 테이블의 모든 정보 검색
 SELECT * FROM admins WHERE admin_id = 'id';	-- 관리자 테이블의 ID 검색
 SELECT * FROM admins WHERE admin_Fname = 'fname' AND admin_Lname = 'lname';	-- 관리자 테이블의 성명 검색
 SELECT * FROM admins WHERE admin_Fname = 'fname';	-- 관리자 테이블의 이름 검색
 SELECT * FROM admins WHERE admin_Lname = 'lname+';	-- 관리자 테이블의 성 검색
 
 /* 등록 */
 SELECT * FROM admins WHERE admin_id = 'id';	-- 등록전 관리자ID 중복 확인차 ID검색
 INSERT INTO admins(admin_id, admin_pw, admin_Fname, admin_Lname) values('id','pw','fname','lname');	-- 관리자 테이블에 투플 추가
 
 /* 삭제 */
 DELETE FROM admins WHERE admin_id = 'id';	-- 관리자 테이블에서 해당 ID에 정보를 가진 투플 삭제
 
 /* 수정 */
 SELECT * FROM admins WHERE admin_id = 'id';	-- 수정전 관리자ID 존재 확인차 ID검색
 UPDATE admins SET admin_pw = 'pw' WHERE admin_id = 'id';	-- 해당 관리자ID를 가진 투플의 password 수정
 UPDATE admins SET admin_Fname = 'fname', admin_Lname = 'lname' WHERE admin_id = 'id';	-- 해당 관리자ID를 가진 투플의 성명 수정
 
 
 /* --------------------------------------------------------
			사용자 정보 관리
 -------------------------------------------------------- */
 /* 조회 */
 SELECT * FROM users;	-- 사용자 테이블의 모든 정보 검색
 SELECT * FROM users WHERE user_id = 'id';	-- 사용자 테이블의 ID 검색
 SELECT * FROM users WHERE user_Fname = 'fname' AND user_Lname = 'lname';	-- 사용자 테이블의 성명 검색
 SELECT * FROM users WHERE user_Fname = 'fname';	-- 사용자 테이블의 이름 검색
 SELECT * FROM users WHERE user_Lname = 'lname+';	-- 사용자 테이블의 성 검색
 SELECT * FROM users WHERE jumin_no = 'jno';	-- 사용자 테이블의 주민번호 검색
 SELECT * FROM users WHERE bdate = 'bdate';	-- 사용자 테이블의 생년월일 검색
 SELECT * FROM users WHERE bdate LIKE 'bdate%';	-- 사용자 테이블의 생년월일에서 연도 검색
 SELECT * FROM users WHERE bdate LIKE '____-bdate-__';	-- 사용자 테이블의 생년월일에서 월 검색 (1995-10-14, ____-10-__)
 SELECT * FROM users WHERE e_mail LIKE '%email%';	-- 사용자 테이블의 e-mail주소 검색
 SELECT * FROM users WHERE sex = 'M';	-- 사용자 테이블의 남성 검색
 SELECT * FROM users WHERE sex = 'F';	-- 사용자 테이블의 여성 검색
 SELECT * FROM users WHERE postcode = 'pcode';	-- 사용자 테이블의 우편번호 검색
 SELECT * FROM users WHERE address LIKE '%addr%';	-- 사용자 테이블의 주소 검색
 SELECT * FROM users WHERE job = 'job';	-- 사용자 테이블의 직업 검색
 
 /* 등록 */
 SELECT * FROM users WHERE user_id = 'id';	-- 등록전 사용자ID 중복 확인차 ID검색
 INSERT INTO users(user_id, user_pw, user_Fname, user_Lname, jumin_no, bdate, e_mail, sex, postcode, address, job) 
 		values('id', 'pw', 'fname', 'lname', 'jno', 'bdate', 'email', 'sex', 'pcode', 'addr', 'job');	-- 사용자 테이블에 투플 추가
 
 
 /* 삭제 */
 SELECT* FROM playlists WHERE user_id = 'id';	-- 플레이리스트 테이블과의 참조 확인
 DELETE FROM users WHERE user_id = 'id';	-- 사용자 테이블에서 해당 ID에 정보를 가진 투플 삭제
 
 /* 수정 */
 SELECT * FROM users WHERE user_id = 'id';	-- 수정전 사용자ID 존재 확인차 ID검색
 UPDATE users SET user_pw = 'pw' WHERE user_id = 'id';	-- 해당 사용자ID를 가진 투플의 password 수정
 UPDATE users SET user_Fname = 'fname', user_Lname = 'lname' WHERE user_id = 'id';	-- 해당 사용자ID를 가진 투플의 성명 수정
 UPDATE users SET jumin_no = 'jno' WHERE user_id = 'id';	-- 해당 사용자ID를 가진 투플의 주민번호 수정
 UPDATE users SET bdate = 'bdate' WHERE user_id = 'id';	-- 해당 사용자ID를 가진 투플의 생년월일 수정
 UPDATE users SET e_mail = 'email' WHERE user_id = 'id';	-- 해당 사용자ID를 가진 투플의 e-mail 수정
 UPDATE users SET sex = 'sex' WHERE user_id = 'id';	-- 해당 사용자ID를 가진 투플의 성별 수정
 UPDATE users SET postcode = 'pcode' WHERE user_id = 'id';	-- 해당 사용자ID를 가진 투플의 성별 수정
 UPDATE users SET address = 'addr'  WHERE user_id = 'id';	-- 해당 사용자ID를 가진 투플의 주소 수정
 UPDATE users SET job = 'job'  WHERE user_id = 'id';	-- 해당 사용자ID를 가진 투플의 직업 수정


/* --------------------------------------------------------
			아티스트 정보 관리
 -------------------------------------------------------- */
 /* 조회 */
 SELECT * FROM artists;	-- 아티스트 테이블의 모든 정보 검색
 SELECT * FROM artists WHERE artist_id = 'id';	-- 아티스트 테이블의 ID 검색
 SELECT * FROM artists WHERE artist_name LIKE '%name%';	-- 아티스트 테이블의 이름 검색
 SELECT * FROM artists WHERE division = 'S';	-- 아티스트 테이블의 솔로 검색
 SELECT * FROM artists WHERE division = 'G';	-- 아티스트 테이블의 그룹 검색
 SELECT * FROM artists WHERE agency LIKE '%agency%';	-- 아티스트 테이블의 소속사 검색
 
 /* 등록 */
 SELECT id_no FROM id_mng WHERE id_name = 'artist_id';	-- id관리 테이블에서 아티스트ID 발행
 UPDATE id_mng SET id_no = id_no + 1 WHERE id_name = 'artist_id';	-- id관리 테이블에서 아티스트ID 갱신
 INSERT INTO artists(artist_id, artist_name, division, agency)
 		values('id', 'name', 'division', 'agency');	-- 아티스트 테이블에 투플 추가
 		
 /* 삭제 */
 SELECT* FROM musics WHERE artist_id = 'id';	-- 곡 테이블과의 참조 확인
 UPDATE musics SET artist_id = NULL WHERE artist_id = 'id';	-- 곡 테이블의 아티스트ID를 NULL로 수정
 DELETE FROM artists WHERE artist_id = 'id';	-- 아티스트 테이블에서 해당 ID에 정보를 가진 투플 삭제
 
 /* 수정 */
 SELECT * FROM artists WHERE artist_id = 'id';	-- 수정전 아티스트ID 존재 확인차 ID검색
 UPDATE artists SET artist_name = 'name' WHERE artist_id = 'id';	-- 해당 아티스트ID를 가진 투플의 이름 수정
 UPDATE artists SET division = 'division' WHERE artist_id = 'id';	-- 해당 아티스트ID를 가진 투플의 솔로/그룹 구분 수정
 UPDATE artists SET agency = 'agency' WHERE artist_id = 'id';	-- 해당 아티스트ID를 가진 투플의 소속사 수정
 
 
 /* --------------------------------------------------------
			작곡가 정보 관리
 -------------------------------------------------------- */
 /* 조회 */
 SELECT * FROM composers;	-- 작곡가 테이블의 모든 정보 검색
 SELECT * FROM composers WHERE comp_id = 'id';	-- 작곡가 테이블의 ID 검색
 SELECT * FROM composers WHERE comp_name LIKE '%name%';	-- 작곡가 테이블의 이름 검색
 SELECT * FROM composers WHERE agency LIKE '%agency%';	-- 작곡가 테이블의 소속사 검색
 
 /* 등록 */
 SELECT id_no FROM id_mng WHERE id_name = 'comp_id';	-- id관리 테이블에서 작곡가ID 발행
 UPDATE id_mng SET id_no = id_no + 1 WHERE id_name = 'comp_id';	-- id관리 테이블에서 작곡가ID 갱신
 INSERT INTO composers(comp_id, comp_name, agency)
 		values('id', 'name', 'agency');	-- 작곡가 테이블에 투플 추가
 
 /* 삭제 */
 SELECT* FROM musics WHERE comp_id = 'id';	-- 곡 테이블과의 참조 확인
 UPDATE musics SET comp_id = NULL WHERE comp_id = 'id';	-- 곡 테이블의 작곡가ID를 NULL로 수정
 DELETE FROM composers WHERE comp_id = 'id';	-- 작곡가 테이블에서 해당 ID에 정보를 가진 투플 삭제
 
 /* 수정 */
 SELECT * FROM composers WHERE comp_id = 'id';	-- 수정전 작곡가ID 존재 확인차 ID검색
 UPDATE composers SET comp_name = 'name' WHERE comp_id = 'id';	-- 해당 작곡가ID를 가진 투플의 이름 수정
 UPDATE composers SET agency = 'agency' WHERE comp_id = 'id';	-- 해당 작곡가ID를 가진 투플의 소속사 수정


/* --------------------------------------------------------
			작사가 정보 관리
 -------------------------------------------------------- */
 /* 조회 */
 SELECT * FROM songwriters;	-- 작사가 테이블의 모든 정보 검색
 SELECT * FROM songwriters WHERE swriter_id = 'id';	-- 작사가 테이블의 ID 검색
 SELECT * FROM songwriters WHERE swriter_name LIKE '%name%';	-- 작사가 테이블의 이름 검색
 SELECT * FROM songwriters WHERE agency LIKE '%agency%';	-- 작사가 테이블의 소속사 검색
 
 /* 등록 */
 SELECT id_no FROM id_mng WHERE id_name = 'swriter_id';	-- id관리 테이블에서 작사가ID 발행
 UPDATE id_mng SET id_no = id_no + 1 WHERE id_name = 'swriter_id';	-- id관리 테이블에서 작사가ID 갱신
 INSERT INTO songwriters(swriter_id, swriter_name, agency)
 		values('id', 'name', 'agency');	-- 작사가 테이블에 투플 추가
 		
 /* 삭제 */
 SELECT* FROM musics WHERE swriter_id = 'id';	-- 곡 테이블과의 참조 확인
 UPDATE musics SET swriter_id = NULL WHERE swriter_id = 'id';	-- 곡 테이블의 작사가ID를 NULL로 수정
 DELETE FROM songwriters WHERE swriter_id = 'id';	-- 작사가 테이블에서 해당 ID에 정보를 가진 투플 삭제
 
 /* 수정 */
 SELECT * FROM songwriters WHERE swriter_id = 'id';	-- 수정전 작사가ID 존재 확인차 ID검색
 UPDATE songwriters SET swriter_name = 'name' WHERE swriter_id = 'id';	-- 해당 작사가ID를 가진 투플의 이름 수정
 UPDATE songwriters SET agency = 'agency' WHERE swriter_id = 'id';	-- 해당 작사가ID를 가진 투플의 소속사 수정


/* --------------------------------------------------------
			앨범 정보 관리
 -------------------------------------------------------- */
 /* 조회 */
 SELECT * FROM albums;	-- 앨범 테이블의 모든 정보 검색
 SELECT * FROM albums WHERE album_id = 'id';	-- 앨범 테이블의 ID 검색
 SELECT * FROM albums WHERE album_title LIKE '%title%';	-- 앨범 테이블의 제목 검색
 SELECT * FROM albums WHERE agency LIKE '%agency%';	-- 앨범 테이블의 소속사 검색
 SELECT * FROM albums WHERE album_rdate = 'rdate';	-- 앨범 테이블의 제작년도 검색
 SELECT * FROM albums WHERE album_rdate LIKE 'rdate%';	-- 앨범 테이블의 제작년 검색
 SELECT * FROM users  WHERE album_rdate LIKE '____-rdate-__';	-- 앨범 테이블의 제작월 검색
 
 /* 등록 */
 SELECT id_no FROM id_mng WHERE id_name = 'album_id';	-- id관리 테이블에서 앨범ID 발행
 UPDATE id_mng SET id_no = id_no + 1 WHERE id_name = 'album_id';	-- id관리 테이블에서 앨범ID 갱신
 INSERT INTO albums(album_id, album_title, agency, album_rdate)
 		values('id', 'title', 'agency', 'rdate');	-- 앨범 테이블에 투플 추가
 
 /* 삭제 */
 SELECT* FROM musics WHERE album_id = 'id';	-- 곡 테이블과의 참조 확인
 UPDATE musics SET album_id = NULL WHERE album_id = 'id';	-- 곡 테이블의 앨범ID를 NULL로 수정
 DELETE FROM albums WHERE album_id = 'id';	-- 앨범 테이블에서 해당 ID에 정보를 가진 투플 삭제
 
 /* 수정 */
 SELECT * FROM albums WHERE album_id = 'id';	-- 수정전 앨범ID 존재 확인차 ID검색
 UPDATE albums SET album_title = 'title' WHERE album_id = 'id';	-- 해당 앨범ID를 가진 투플의 제목 수정
 UPDATE albums SET agency = 'agency' WHERE album_id = 'id';	-- 해당 앨범ID를 가진 투플의 소속사 수정
 UPDATE albums SET album_rdate = 'rdate' WHERE album_id = 'id';	-- 해당 앨범ID를 가진 투플의 제작년도 수정
 
 
 /* --------------------------------------------------------
			곡 정보 관리
 -------------------------------------------------------- */
 /* 조회 */
 SELECT * FROM musics; -- 곡 테이블의 모든 정보 검색
 SELECT * FROM musics WHERE music_id = 'id';	-- 곡 테이블의 ID 검색
 SELECT * FROM musics WHERE music_title LIKE '%title%';	-- 곡 테이블의 제목 검색
 SELECT * FROM musics WHERE artist_id = 'id';	-- 곡 테이블의 아티스트ID 검색
 SELECT * FROM musics m WHERE EXISTS (SELECT * 
 									  FROM artists a 
 									  WHERE m.artist_id = a.artist_id AND a.artist_name LIKE '%name%');	-- 곡 테이블의 아티스트 이름 검색
 									  
 SELECT * FROM musics WHERE comp_id = 'id';	-- 곡 테이블의 작곡가ID 검색
 SELECT * FROM musics m WHERE EXISTS (SELECT * 
 									  FROM composers c 
 									  WHERE m.comp_id = c.comp_id AND c.comp_name LIKE '%name%');	-- 곡 테이블의 작곡가 이름 검색
 SELECT * FROM musics WHERE swriter_id = 'id';	-- 곡 테이블의 작사가ID 검색
 SELECT * FROM musics m WHERE EXISTS (SELECT * 
 									  FROM songwriters s 
 									  WHERE m.swriter_id = s.swriter_id AND s.swriter_name LIKE '%name%');	-- 곡 테이블의 작사가 이름 검색
 SELECT * FROM musics WHERE album_id = 'id';	-- 곡 테이블의 앨범ID 검색
 SELECT * FROM musics m WHERE EXISTS (SELECT * 
 									  FROM albums al 
 									  WHERE m.album_id = al.album_id AND al.album_title LIKE '%title%');	-- 곡 테이블의 앨범 제목 검색
 SELECT * FROM musics WHERE genre = 'genre';	-- 곡 테이블의 장르 검색
 
 /* 등록 */
 SELECT id_no FROM id_mng WHERE id_name = 'music_id';	-- id관리 테이블에서 곡ID 발행
 UPDATE id_mng SET id_no = id_no + 1 WHERE id_name = 'music_id';	-- id관리 테이블에서 곡ID 갱신
 INSERT INTO musics(music_id, music_title, artist_id, comp_id, swriter_id, album_id, genre) "
 		values('id', 'title', 'a_id', 'c_id', 's_id', 'al_id', 'genre');	-- 곡 테이블에 투플 추가
 
 /* 삭제 */
 SELECT* FROM playlist_registrations WHERE music_id = 'id';	-- 플레이리스트 등록 상세 테이블과의 참조 확인
 DELETE FROM musics WHERE music_id = 'id';	-- 곡 테이블에서 해당 ID에 정보를 가진 투플 삭제
 
 /* 수정 */
 SELECT * FROM musics WHERE music_id = 'id';	-- 수정전 곡ID 존재 확인차 ID검색
 UPDATE musics SET music_title = 'title' WHERE music_id = 'id';	-- 해당 곡ID를 가진 투플의 제목 수정
 SELECT * FROM artists WHERE artist_id = 'a_id';	-- 아티스트ID 존재 확인
 UPDATE musics SET artist_id = 'a_id' WHERE music_id = 'id';	-- 해당 곡ID를 가진 투플의 아티스트ID 수정
 SELECT * FROM composers WHERE comp_id = 'c_id';	-- 작곡가ID 존재 확인
 UPDATE musics SET comp_id = 'c_id' WHERE music_id = 'id';	-- 해당 곡ID를 가진 투플의 작곡가ID 수정
 SELECT * FROM songwriters WHERE swriter_id = 's_id';	-- 작사가ID 존재 확인
 UPDATE musics SET swriter_id = 's_id' WHERE music_id = 'id';	-- 해당 곡ID를 가진 투플의 작사가ID 수정
 SELECT * FROM albums WHERE album_id = 'al_id';	-- 앨범ID 존재 확인
 UPDATE musics SET album_id = 'al_id' WHERE music_id = 'id';	-- 해당 곡ID를 가진 투플의 앨범ID 수정
 UPDATE musics SET genre = 'genre' WHERE music_id = 'id';	-- 해당 곡ID를 가진 투플의 장르 수정
 
 
 
 /* --------------------------------------------------------
			플레이리스트 정보 관리
 -------------------------------------------------------- */
 /* 조회 */
 SELECT * FROM playlists	-- 플레이리스트 테이블의 모든 정보 검색
 SELECT * FROM playlists WHERE user_id = 'user_id';	-- 해당 사용자ID의 모든 플레이리스트 정보 검색
 SELECT * FROM playlists WHERE plist_id = 'pid';	-- 플레이리스트 테이블의 ID 검색
 SELECT * FROM playlists WHERE user_id = 'user_id' AND plist_id = 'pid';	-- 해당 사용자ID의  플레이리스트 정보에서 ID 검색
 SELECT * FROM playlists WHERE plist_title = 'title';	-- 플레이리스트 테이블의 제목 검색
 SELECT * FROM playlists WHERE user_id = 'user_id' AND plist_title = 'title';	-- 해당 사용자ID의  플레이리스트 정보에서 제목 검색
 SELECT * FROM playlists WHERE user_id = 'uid';	-- 플레이리스트 테이블의 사용자ID 검색
 
 /* 생성 */
 SELECT * FROM users WHERE user_id = 'uid';	-- 사용자ID 존재 확인
 SELECT id_no FROM id_mng WHERE id_name = 'plist_id';	-- id관리 테이블에서 플레이리스트ID 발행
 UPDATE id_mng SET id_no = id_no + 1 WHERE id_name = 'plist_id';	-- id관리 테이블에서 플레이리스트ID 갱신
 INSERT INTO playlists(plist_id, user_id, plist_title) 
 		values('pid', 'uid', 'title');	-- 플레이리스트 테이블에 투플 추가
 
 /* 삭제 */
 DELETE FROM playlist_registrations WHERE plist_id = 'pid';	-- 해당 플레이리스트ID에 등록된 모든 음악 삭제
 DELETE FROM playlists WHERE plist_id = 'pid';	-- 플레이리스트 테이블에서 해당 ID에 정보를 가진 투플 삭제
 SELECT DISTINCT pr.plist_id 
 	FROM playlists p, playlist_registrations pr 
	WHERE p.plist_id = pr.plist_id AND p.user_id = 'user_id' AND p.plist_id = 'pid';	-- 해당 사용자ID의 음악이 등록된 플레이리스트ID 확인
 DELETE FROM playlist_registrations WHERE plist_id = 'pid';	-- 해당 플레이리스트ID에 등록된 모든 음악 삭제
 DELETE FROM playlists WHERE plist_id = 'pid' AND user_id = 'user_id'	-- 해당 사용자ID의 플레이리스트중에서 해당 pid를 가진 플레이리스트 삭제
 
 /* 수정 */
 SELECT * FROM playlists WHERE plist_id = 'pid';	-- 수정전 플레이리스트ID 존재 확인차 ID검색
 SELECT * FROM playlists WHERE user_id = 'uid' AND plist_title = 'title';	-- 해당 사용자ID의 플레이리스트 제목 중복 확인
 UPDATE playlists SET plist_title = 'title' WHERE plist_id = 'pid' AND user_id = 'uid';	-- 해당 플레이리스트ID와 사용자ID를 가진 투플의 제목 수정
 
 
 /* --------------------------------------------------------
			플레이리스트 곡 등록 관리
 -------------------------------------------------------- */
 /* 조회 */
 SELECT * FROM playlists;	-- 플레이리스트 테이블의 모든 정보 검색
 SELECT * FROM playlists WHERE user_id = 'user_id';	-- 해당 사용자ID의 모든 플레이리스트 정보 검색
 SELECT pr.music_id, music_title, artist_id, comp_id, swriter_id, album_id, genre   
 	FROM playlist_registrations pr, musics m  
	WHERE pr.plist_id = 'pid' AND pr.music_id = m.music_id;	-- 해당 플레이리스트에 등록된 음악 정보 검색
 SELECT * FROM playlists WHERE plist_id = 'pid';	-- 플레이리스트 테이블의 플레이리스트ID 검색
 SELECT pr.music_id, music_title, artist_id, comp_id, swriter_id, album_id, genre  
 	FROM playlist_registrations pr, musics m  
	WHERE pr.plist_id = 'pid' AND pr.music_id = m.music_id;	-- 해당 플레이리스트에 등록된 음악 정보 검색
 SELECT * FROM users WHERE user_id = 'uid';	-- 해당 사용자ID 존재 확인
 SELECT * FROM playlists WHERE plist_title LIKE '%title%' AND user_id = 'uid';	-- 해당 사용자ID의 플레이리스트중 해당 제목을 가진 플레이리스트 검색
 SELECT pr.music_id, music_title, artist_id, comp_id, swriter_id, album_id, genre  
 	FROM playlist_registrations pr, musics m  
	WHERE pr.plist_id = 'pid' AND pr.music_id = m.music_id;	-- 해당 플레이리스트에 등록된 음악 정보 검색
 SELECT * FROM playlists WHERE user_id = 'uid';	-- 해당 사용자ID의 모든 플레이리스트 검색
 SELECT pr.music_id, music_title, artist_id, comp_id, swriter_id, album_id, genre  
 	FROM playlist_registrations pr, musics m  
	WHERE pr.plist_id = 'pid' AND pr.music_id = m.music_id;	-- 해당 플레이리스트에 등록된 음악 정보 검색
	
 /* 등록 */
 SELECT * FROM playlists WHERE plist_id = 'pid';	-- 플레이리스트ID 존재 확인
 SELECT * FROM users WHERE user_id = 'uid';	-- 사용자ID 존재 확인
 SELECT * FROM playlists WHERE plist_title LIKE '%title%' AND user_id = 'uid';	-- 해당 사용자ID의 플레이리스트 제목 중복 확인
 INSERT INTO playlist_registrations(plist_id, music_id) values('pid','mid');	-- 해당 플레이리스트에 음악 등록
 
 /* 삭제 */
 SELECT * FROM playlists WHERE plist_id = 'pid';	-- 플레이리스트ID 존재 확인
 SELECT * FROM users WHERE user_id = 'uid';	-- 사용자ID 존재 확인
 SELECT * FROM playlists WHERE plist_title LIKE '%title%' AND user_id = 'uid';	-- 해당 사용자ID의 해당 제목 플레이리스트 존재 확인
 SELECT pr.music_id, music_title, artist_id, comp_id, swriter_id, album_id, genre  
 	FROM playlist_registrations pr, musics m  
	WHERE pr.plist_id = 'pid' AND pr.music_id = m.music_id;	-- 해당 플레이리스트에 등록된 음악 정보 검색
 DELETE FROM playlist_registrations WHERE music_id = 'mid' AND plist_id = 'pid';	-- 해당  플레이리스트중에서 해당 mid를 가진 음악 삭제