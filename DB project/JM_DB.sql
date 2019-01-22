/* --------------------------------------------------------
			������ �α���
 -------------------------------------------------------- */
SELECT * FROM admins WHERE admin_id = 'id';  -- ������ ���̺��� �ش� ������ID�� ��� ������ �˻�.


/* --------------------------------------------------------
			����� �α���
 -------------------------------------------------------- */
 SELECT user_pw FROM users WHERE user_id = 'id';	-- ����� ���̺��� �ش� �����ID�� password�� �˻�.
 
 
 /* --------------------------------------------------------
			������ ���� ����
 -------------------------------------------------------- */
 /* ��ȸ */
 SELECT * FROM admins;	-- ������ ���̺��� ��� ���� �˻�
 SELECT * FROM admins WHERE admin_id = 'id';	-- ������ ���̺��� ID �˻�
 SELECT * FROM admins WHERE admin_Fname = 'fname' AND admin_Lname = 'lname';	-- ������ ���̺��� ���� �˻�
 SELECT * FROM admins WHERE admin_Fname = 'fname';	-- ������ ���̺��� �̸� �˻�
 SELECT * FROM admins WHERE admin_Lname = 'lname+';	-- ������ ���̺��� �� �˻�
 
 /* ��� */
 SELECT * FROM admins WHERE admin_id = 'id';	-- ����� ������ID �ߺ� Ȯ���� ID�˻�
 INSERT INTO admins(admin_id, admin_pw, admin_Fname, admin_Lname) values('id','pw','fname','lname');	-- ������ ���̺� ���� �߰�
 
 /* ���� */
 DELETE FROM admins WHERE admin_id = 'id';	-- ������ ���̺��� �ش� ID�� ������ ���� ���� ����
 
 /* ���� */
 SELECT * FROM admins WHERE admin_id = 'id';	-- ������ ������ID ���� Ȯ���� ID�˻�
 UPDATE admins SET admin_pw = 'pw' WHERE admin_id = 'id';	-- �ش� ������ID�� ���� ������ password ����
 UPDATE admins SET admin_Fname = 'fname', admin_Lname = 'lname' WHERE admin_id = 'id';	-- �ش� ������ID�� ���� ������ ���� ����
 
 
 /* --------------------------------------------------------
			����� ���� ����
 -------------------------------------------------------- */
 /* ��ȸ */
 SELECT * FROM users;	-- ����� ���̺��� ��� ���� �˻�
 SELECT * FROM users WHERE user_id = 'id';	-- ����� ���̺��� ID �˻�
 SELECT * FROM users WHERE user_Fname = 'fname' AND user_Lname = 'lname';	-- ����� ���̺��� ���� �˻�
 SELECT * FROM users WHERE user_Fname = 'fname';	-- ����� ���̺��� �̸� �˻�
 SELECT * FROM users WHERE user_Lname = 'lname+';	-- ����� ���̺��� �� �˻�
 SELECT * FROM users WHERE jumin_no = 'jno';	-- ����� ���̺��� �ֹι�ȣ �˻�
 SELECT * FROM users WHERE bdate = 'bdate';	-- ����� ���̺��� ������� �˻�
 SELECT * FROM users WHERE bdate LIKE 'bdate%';	-- ����� ���̺��� ������Ͽ��� ���� �˻�
 SELECT * FROM users WHERE bdate LIKE '____-bdate-__';	-- ����� ���̺��� ������Ͽ��� �� �˻� (1995-10-14, ____-10-__)
 SELECT * FROM users WHERE e_mail LIKE '%email%';	-- ����� ���̺��� e-mail�ּ� �˻�
 SELECT * FROM users WHERE sex = 'M';	-- ����� ���̺��� ���� �˻�
 SELECT * FROM users WHERE sex = 'F';	-- ����� ���̺��� ���� �˻�
 SELECT * FROM users WHERE postcode = 'pcode';	-- ����� ���̺��� �����ȣ �˻�
 SELECT * FROM users WHERE address LIKE '%addr%';	-- ����� ���̺��� �ּ� �˻�
 SELECT * FROM users WHERE job = 'job';	-- ����� ���̺��� ���� �˻�
 
 /* ��� */
 SELECT * FROM users WHERE user_id = 'id';	-- ����� �����ID �ߺ� Ȯ���� ID�˻�
 INSERT INTO users(user_id, user_pw, user_Fname, user_Lname, jumin_no, bdate, e_mail, sex, postcode, address, job) 
 		values('id', 'pw', 'fname', 'lname', 'jno', 'bdate', 'email', 'sex', 'pcode', 'addr', 'job');	-- ����� ���̺� ���� �߰�
 
 
 /* ���� */
 SELECT* FROM playlists WHERE user_id = 'id';	-- �÷��̸���Ʈ ���̺���� ���� Ȯ��
 DELETE FROM users WHERE user_id = 'id';	-- ����� ���̺��� �ش� ID�� ������ ���� ���� ����
 
 /* ���� */
 SELECT * FROM users WHERE user_id = 'id';	-- ������ �����ID ���� Ȯ���� ID�˻�
 UPDATE users SET user_pw = 'pw' WHERE user_id = 'id';	-- �ش� �����ID�� ���� ������ password ����
 UPDATE users SET user_Fname = 'fname', user_Lname = 'lname' WHERE user_id = 'id';	-- �ش� �����ID�� ���� ������ ���� ����
 UPDATE users SET jumin_no = 'jno' WHERE user_id = 'id';	-- �ش� �����ID�� ���� ������ �ֹι�ȣ ����
 UPDATE users SET bdate = 'bdate' WHERE user_id = 'id';	-- �ش� �����ID�� ���� ������ ������� ����
 UPDATE users SET e_mail = 'email' WHERE user_id = 'id';	-- �ش� �����ID�� ���� ������ e-mail ����
 UPDATE users SET sex = 'sex' WHERE user_id = 'id';	-- �ش� �����ID�� ���� ������ ���� ����
 UPDATE users SET postcode = 'pcode' WHERE user_id = 'id';	-- �ش� �����ID�� ���� ������ ���� ����
 UPDATE users SET address = 'addr'  WHERE user_id = 'id';	-- �ش� �����ID�� ���� ������ �ּ� ����
 UPDATE users SET job = 'job'  WHERE user_id = 'id';	-- �ش� �����ID�� ���� ������ ���� ����


/* --------------------------------------------------------
			��Ƽ��Ʈ ���� ����
 -------------------------------------------------------- */
 /* ��ȸ */
 SELECT * FROM artists;	-- ��Ƽ��Ʈ ���̺��� ��� ���� �˻�
 SELECT * FROM artists WHERE artist_id = 'id';	-- ��Ƽ��Ʈ ���̺��� ID �˻�
 SELECT * FROM artists WHERE artist_name LIKE '%name%';	-- ��Ƽ��Ʈ ���̺��� �̸� �˻�
 SELECT * FROM artists WHERE division = 'S';	-- ��Ƽ��Ʈ ���̺��� �ַ� �˻�
 SELECT * FROM artists WHERE division = 'G';	-- ��Ƽ��Ʈ ���̺��� �׷� �˻�
 SELECT * FROM artists WHERE agency LIKE '%agency%';	-- ��Ƽ��Ʈ ���̺��� �Ҽӻ� �˻�
 
 /* ��� */
 SELECT id_no FROM id_mng WHERE id_name = 'artist_id';	-- id���� ���̺��� ��Ƽ��ƮID ����
 UPDATE id_mng SET id_no = id_no + 1 WHERE id_name = 'artist_id';	-- id���� ���̺��� ��Ƽ��ƮID ����
 INSERT INTO artists(artist_id, artist_name, division, agency)
 		values('id', 'name', 'division', 'agency');	-- ��Ƽ��Ʈ ���̺� ���� �߰�
 		
 /* ���� */
 SELECT* FROM musics WHERE artist_id = 'id';	-- �� ���̺���� ���� Ȯ��
 UPDATE musics SET artist_id = NULL WHERE artist_id = 'id';	-- �� ���̺��� ��Ƽ��ƮID�� NULL�� ����
 DELETE FROM artists WHERE artist_id = 'id';	-- ��Ƽ��Ʈ ���̺��� �ش� ID�� ������ ���� ���� ����
 
 /* ���� */
 SELECT * FROM artists WHERE artist_id = 'id';	-- ������ ��Ƽ��ƮID ���� Ȯ���� ID�˻�
 UPDATE artists SET artist_name = 'name' WHERE artist_id = 'id';	-- �ش� ��Ƽ��ƮID�� ���� ������ �̸� ����
 UPDATE artists SET division = 'division' WHERE artist_id = 'id';	-- �ش� ��Ƽ��ƮID�� ���� ������ �ַ�/�׷� ���� ����
 UPDATE artists SET agency = 'agency' WHERE artist_id = 'id';	-- �ش� ��Ƽ��ƮID�� ���� ������ �Ҽӻ� ����
 
 
 /* --------------------------------------------------------
			�۰ ���� ����
 -------------------------------------------------------- */
 /* ��ȸ */
 SELECT * FROM composers;	-- �۰ ���̺��� ��� ���� �˻�
 SELECT * FROM composers WHERE comp_id = 'id';	-- �۰ ���̺��� ID �˻�
 SELECT * FROM composers WHERE comp_name LIKE '%name%';	-- �۰ ���̺��� �̸� �˻�
 SELECT * FROM composers WHERE agency LIKE '%agency%';	-- �۰ ���̺��� �Ҽӻ� �˻�
 
 /* ��� */
 SELECT id_no FROM id_mng WHERE id_name = 'comp_id';	-- id���� ���̺��� �۰ID ����
 UPDATE id_mng SET id_no = id_no + 1 WHERE id_name = 'comp_id';	-- id���� ���̺��� �۰ID ����
 INSERT INTO composers(comp_id, comp_name, agency)
 		values('id', 'name', 'agency');	-- �۰ ���̺� ���� �߰�
 
 /* ���� */
 SELECT* FROM musics WHERE comp_id = 'id';	-- �� ���̺���� ���� Ȯ��
 UPDATE musics SET comp_id = NULL WHERE comp_id = 'id';	-- �� ���̺��� �۰ID�� NULL�� ����
 DELETE FROM composers WHERE comp_id = 'id';	-- �۰ ���̺��� �ش� ID�� ������ ���� ���� ����
 
 /* ���� */
 SELECT * FROM composers WHERE comp_id = 'id';	-- ������ �۰ID ���� Ȯ���� ID�˻�
 UPDATE composers SET comp_name = 'name' WHERE comp_id = 'id';	-- �ش� �۰ID�� ���� ������ �̸� ����
 UPDATE composers SET agency = 'agency' WHERE comp_id = 'id';	-- �ش� �۰ID�� ���� ������ �Ҽӻ� ����


/* --------------------------------------------------------
			�ۻ簡 ���� ����
 -------------------------------------------------------- */
 /* ��ȸ */
 SELECT * FROM songwriters;	-- �ۻ簡 ���̺��� ��� ���� �˻�
 SELECT * FROM songwriters WHERE swriter_id = 'id';	-- �ۻ簡 ���̺��� ID �˻�
 SELECT * FROM songwriters WHERE swriter_name LIKE '%name%';	-- �ۻ簡 ���̺��� �̸� �˻�
 SELECT * FROM songwriters WHERE agency LIKE '%agency%';	-- �ۻ簡 ���̺��� �Ҽӻ� �˻�
 
 /* ��� */
 SELECT id_no FROM id_mng WHERE id_name = 'swriter_id';	-- id���� ���̺��� �ۻ簡ID ����
 UPDATE id_mng SET id_no = id_no + 1 WHERE id_name = 'swriter_id';	-- id���� ���̺��� �ۻ簡ID ����
 INSERT INTO songwriters(swriter_id, swriter_name, agency)
 		values('id', 'name', 'agency');	-- �ۻ簡 ���̺� ���� �߰�
 		
 /* ���� */
 SELECT* FROM musics WHERE swriter_id = 'id';	-- �� ���̺���� ���� Ȯ��
 UPDATE musics SET swriter_id = NULL WHERE swriter_id = 'id';	-- �� ���̺��� �ۻ簡ID�� NULL�� ����
 DELETE FROM songwriters WHERE swriter_id = 'id';	-- �ۻ簡 ���̺��� �ش� ID�� ������ ���� ���� ����
 
 /* ���� */
 SELECT * FROM songwriters WHERE swriter_id = 'id';	-- ������ �ۻ簡ID ���� Ȯ���� ID�˻�
 UPDATE songwriters SET swriter_name = 'name' WHERE swriter_id = 'id';	-- �ش� �ۻ簡ID�� ���� ������ �̸� ����
 UPDATE songwriters SET agency = 'agency' WHERE swriter_id = 'id';	-- �ش� �ۻ簡ID�� ���� ������ �Ҽӻ� ����


/* --------------------------------------------------------
			�ٹ� ���� ����
 -------------------------------------------------------- */
 /* ��ȸ */
 SELECT * FROM albums;	-- �ٹ� ���̺��� ��� ���� �˻�
 SELECT * FROM albums WHERE album_id = 'id';	-- �ٹ� ���̺��� ID �˻�
 SELECT * FROM albums WHERE album_title LIKE '%title%';	-- �ٹ� ���̺��� ���� �˻�
 SELECT * FROM albums WHERE agency LIKE '%agency%';	-- �ٹ� ���̺��� �Ҽӻ� �˻�
 SELECT * FROM albums WHERE album_rdate = 'rdate';	-- �ٹ� ���̺��� ���۳⵵ �˻�
 SELECT * FROM albums WHERE album_rdate LIKE 'rdate%';	-- �ٹ� ���̺��� ���۳� �˻�
 SELECT * FROM users  WHERE album_rdate LIKE '____-rdate-__';	-- �ٹ� ���̺��� ���ۿ� �˻�
 
 /* ��� */
 SELECT id_no FROM id_mng WHERE id_name = 'album_id';	-- id���� ���̺��� �ٹ�ID ����
 UPDATE id_mng SET id_no = id_no + 1 WHERE id_name = 'album_id';	-- id���� ���̺��� �ٹ�ID ����
 INSERT INTO albums(album_id, album_title, agency, album_rdate)
 		values('id', 'title', 'agency', 'rdate');	-- �ٹ� ���̺� ���� �߰�
 
 /* ���� */
 SELECT* FROM musics WHERE album_id = 'id';	-- �� ���̺���� ���� Ȯ��
 UPDATE musics SET album_id = NULL WHERE album_id = 'id';	-- �� ���̺��� �ٹ�ID�� NULL�� ����
 DELETE FROM albums WHERE album_id = 'id';	-- �ٹ� ���̺��� �ش� ID�� ������ ���� ���� ����
 
 /* ���� */
 SELECT * FROM albums WHERE album_id = 'id';	-- ������ �ٹ�ID ���� Ȯ���� ID�˻�
 UPDATE albums SET album_title = 'title' WHERE album_id = 'id';	-- �ش� �ٹ�ID�� ���� ������ ���� ����
 UPDATE albums SET agency = 'agency' WHERE album_id = 'id';	-- �ش� �ٹ�ID�� ���� ������ �Ҽӻ� ����
 UPDATE albums SET album_rdate = 'rdate' WHERE album_id = 'id';	-- �ش� �ٹ�ID�� ���� ������ ���۳⵵ ����
 
 
 /* --------------------------------------------------------
			�� ���� ����
 -------------------------------------------------------- */
 /* ��ȸ */
 SELECT * FROM musics; -- �� ���̺��� ��� ���� �˻�
 SELECT * FROM musics WHERE music_id = 'id';	-- �� ���̺��� ID �˻�
 SELECT * FROM musics WHERE music_title LIKE '%title%';	-- �� ���̺��� ���� �˻�
 SELECT * FROM musics WHERE artist_id = 'id';	-- �� ���̺��� ��Ƽ��ƮID �˻�
 SELECT * FROM musics m WHERE EXISTS (SELECT * 
 									  FROM artists a 
 									  WHERE m.artist_id = a.artist_id AND a.artist_name LIKE '%name%');	-- �� ���̺��� ��Ƽ��Ʈ �̸� �˻�
 									  
 SELECT * FROM musics WHERE comp_id = 'id';	-- �� ���̺��� �۰ID �˻�
 SELECT * FROM musics m WHERE EXISTS (SELECT * 
 									  FROM composers c 
 									  WHERE m.comp_id = c.comp_id AND c.comp_name LIKE '%name%');	-- �� ���̺��� �۰ �̸� �˻�
 SELECT * FROM musics WHERE swriter_id = 'id';	-- �� ���̺��� �ۻ簡ID �˻�
 SELECT * FROM musics m WHERE EXISTS (SELECT * 
 									  FROM songwriters s 
 									  WHERE m.swriter_id = s.swriter_id AND s.swriter_name LIKE '%name%');	-- �� ���̺��� �ۻ簡 �̸� �˻�
 SELECT * FROM musics WHERE album_id = 'id';	-- �� ���̺��� �ٹ�ID �˻�
 SELECT * FROM musics m WHERE EXISTS (SELECT * 
 									  FROM albums al 
 									  WHERE m.album_id = al.album_id AND al.album_title LIKE '%title%');	-- �� ���̺��� �ٹ� ���� �˻�
 SELECT * FROM musics WHERE genre = 'genre';	-- �� ���̺��� �帣 �˻�
 
 /* ��� */
 SELECT id_no FROM id_mng WHERE id_name = 'music_id';	-- id���� ���̺��� ��ID ����
 UPDATE id_mng SET id_no = id_no + 1 WHERE id_name = 'music_id';	-- id���� ���̺��� ��ID ����
 INSERT INTO musics(music_id, music_title, artist_id, comp_id, swriter_id, album_id, genre) "
 		values('id', 'title', 'a_id', 'c_id', 's_id', 'al_id', 'genre');	-- �� ���̺� ���� �߰�
 
 /* ���� */
 SELECT* FROM playlist_registrations WHERE music_id = 'id';	-- �÷��̸���Ʈ ��� �� ���̺���� ���� Ȯ��
 DELETE FROM musics WHERE music_id = 'id';	-- �� ���̺��� �ش� ID�� ������ ���� ���� ����
 
 /* ���� */
 SELECT * FROM musics WHERE music_id = 'id';	-- ������ ��ID ���� Ȯ���� ID�˻�
 UPDATE musics SET music_title = 'title' WHERE music_id = 'id';	-- �ش� ��ID�� ���� ������ ���� ����
 SELECT * FROM artists WHERE artist_id = 'a_id';	-- ��Ƽ��ƮID ���� Ȯ��
 UPDATE musics SET artist_id = 'a_id' WHERE music_id = 'id';	-- �ش� ��ID�� ���� ������ ��Ƽ��ƮID ����
 SELECT * FROM composers WHERE comp_id = 'c_id';	-- �۰ID ���� Ȯ��
 UPDATE musics SET comp_id = 'c_id' WHERE music_id = 'id';	-- �ش� ��ID�� ���� ������ �۰ID ����
 SELECT * FROM songwriters WHERE swriter_id = 's_id';	-- �ۻ簡ID ���� Ȯ��
 UPDATE musics SET swriter_id = 's_id' WHERE music_id = 'id';	-- �ش� ��ID�� ���� ������ �ۻ簡ID ����
 SELECT * FROM albums WHERE album_id = 'al_id';	-- �ٹ�ID ���� Ȯ��
 UPDATE musics SET album_id = 'al_id' WHERE music_id = 'id';	-- �ش� ��ID�� ���� ������ �ٹ�ID ����
 UPDATE musics SET genre = 'genre' WHERE music_id = 'id';	-- �ش� ��ID�� ���� ������ �帣 ����
 
 
 
 /* --------------------------------------------------------
			�÷��̸���Ʈ ���� ����
 -------------------------------------------------------- */
 /* ��ȸ */
 SELECT * FROM playlists	-- �÷��̸���Ʈ ���̺��� ��� ���� �˻�
 SELECT * FROM playlists WHERE user_id = 'user_id';	-- �ش� �����ID�� ��� �÷��̸���Ʈ ���� �˻�
 SELECT * FROM playlists WHERE plist_id = 'pid';	-- �÷��̸���Ʈ ���̺��� ID �˻�
 SELECT * FROM playlists WHERE user_id = 'user_id' AND plist_id = 'pid';	-- �ش� �����ID��  �÷��̸���Ʈ �������� ID �˻�
 SELECT * FROM playlists WHERE plist_title = 'title';	-- �÷��̸���Ʈ ���̺��� ���� �˻�
 SELECT * FROM playlists WHERE user_id = 'user_id' AND plist_title = 'title';	-- �ش� �����ID��  �÷��̸���Ʈ �������� ���� �˻�
 SELECT * FROM playlists WHERE user_id = 'uid';	-- �÷��̸���Ʈ ���̺��� �����ID �˻�
 
 /* ���� */
 SELECT * FROM users WHERE user_id = 'uid';	-- �����ID ���� Ȯ��
 SELECT id_no FROM id_mng WHERE id_name = 'plist_id';	-- id���� ���̺��� �÷��̸���ƮID ����
 UPDATE id_mng SET id_no = id_no + 1 WHERE id_name = 'plist_id';	-- id���� ���̺��� �÷��̸���ƮID ����
 INSERT INTO playlists(plist_id, user_id, plist_title) 
 		values('pid', 'uid', 'title');	-- �÷��̸���Ʈ ���̺� ���� �߰�
 
 /* ���� */
 DELETE FROM playlist_registrations WHERE plist_id = 'pid';	-- �ش� �÷��̸���ƮID�� ��ϵ� ��� ���� ����
 DELETE FROM playlists WHERE plist_id = 'pid';	-- �÷��̸���Ʈ ���̺��� �ش� ID�� ������ ���� ���� ����
 SELECT DISTINCT pr.plist_id 
 	FROM playlists p, playlist_registrations pr 
	WHERE p.plist_id = pr.plist_id AND p.user_id = 'user_id' AND p.plist_id = 'pid';	-- �ش� �����ID�� ������ ��ϵ� �÷��̸���ƮID Ȯ��
 DELETE FROM playlist_registrations WHERE plist_id = 'pid';	-- �ش� �÷��̸���ƮID�� ��ϵ� ��� ���� ����
 DELETE FROM playlists WHERE plist_id = 'pid' AND user_id = 'user_id'	-- �ش� �����ID�� �÷��̸���Ʈ�߿��� �ش� pid�� ���� �÷��̸���Ʈ ����
 
 /* ���� */
 SELECT * FROM playlists WHERE plist_id = 'pid';	-- ������ �÷��̸���ƮID ���� Ȯ���� ID�˻�
 SELECT * FROM playlists WHERE user_id = 'uid' AND plist_title = 'title';	-- �ش� �����ID�� �÷��̸���Ʈ ���� �ߺ� Ȯ��
 UPDATE playlists SET plist_title = 'title' WHERE plist_id = 'pid' AND user_id = 'uid';	-- �ش� �÷��̸���ƮID�� �����ID�� ���� ������ ���� ����
 
 
 /* --------------------------------------------------------
			�÷��̸���Ʈ �� ��� ����
 -------------------------------------------------------- */
 /* ��ȸ */
 SELECT * FROM playlists;	-- �÷��̸���Ʈ ���̺��� ��� ���� �˻�
 SELECT * FROM playlists WHERE user_id = 'user_id';	-- �ش� �����ID�� ��� �÷��̸���Ʈ ���� �˻�
 SELECT pr.music_id, music_title, artist_id, comp_id, swriter_id, album_id, genre   
 	FROM playlist_registrations pr, musics m  
	WHERE pr.plist_id = 'pid' AND pr.music_id = m.music_id;	-- �ش� �÷��̸���Ʈ�� ��ϵ� ���� ���� �˻�
 SELECT * FROM playlists WHERE plist_id = 'pid';	-- �÷��̸���Ʈ ���̺��� �÷��̸���ƮID �˻�
 SELECT pr.music_id, music_title, artist_id, comp_id, swriter_id, album_id, genre  
 	FROM playlist_registrations pr, musics m  
	WHERE pr.plist_id = 'pid' AND pr.music_id = m.music_id;	-- �ش� �÷��̸���Ʈ�� ��ϵ� ���� ���� �˻�
 SELECT * FROM users WHERE user_id = 'uid';	-- �ش� �����ID ���� Ȯ��
 SELECT * FROM playlists WHERE plist_title LIKE '%title%' AND user_id = 'uid';	-- �ش� �����ID�� �÷��̸���Ʈ�� �ش� ������ ���� �÷��̸���Ʈ �˻�
 SELECT pr.music_id, music_title, artist_id, comp_id, swriter_id, album_id, genre  
 	FROM playlist_registrations pr, musics m  
	WHERE pr.plist_id = 'pid' AND pr.music_id = m.music_id;	-- �ش� �÷��̸���Ʈ�� ��ϵ� ���� ���� �˻�
 SELECT * FROM playlists WHERE user_id = 'uid';	-- �ش� �����ID�� ��� �÷��̸���Ʈ �˻�
 SELECT pr.music_id, music_title, artist_id, comp_id, swriter_id, album_id, genre  
 	FROM playlist_registrations pr, musics m  
	WHERE pr.plist_id = 'pid' AND pr.music_id = m.music_id;	-- �ش� �÷��̸���Ʈ�� ��ϵ� ���� ���� �˻�
	
 /* ��� */
 SELECT * FROM playlists WHERE plist_id = 'pid';	-- �÷��̸���ƮID ���� Ȯ��
 SELECT * FROM users WHERE user_id = 'uid';	-- �����ID ���� Ȯ��
 SELECT * FROM playlists WHERE plist_title LIKE '%title%' AND user_id = 'uid';	-- �ش� �����ID�� �÷��̸���Ʈ ���� �ߺ� Ȯ��
 INSERT INTO playlist_registrations(plist_id, music_id) values('pid','mid');	-- �ش� �÷��̸���Ʈ�� ���� ���
 
 /* ���� */
 SELECT * FROM playlists WHERE plist_id = 'pid';	-- �÷��̸���ƮID ���� Ȯ��
 SELECT * FROM users WHERE user_id = 'uid';	-- �����ID ���� Ȯ��
 SELECT * FROM playlists WHERE plist_title LIKE '%title%' AND user_id = 'uid';	-- �ش� �����ID�� �ش� ���� �÷��̸���Ʈ ���� Ȯ��
 SELECT pr.music_id, music_title, artist_id, comp_id, swriter_id, album_id, genre  
 	FROM playlist_registrations pr, musics m  
	WHERE pr.plist_id = 'pid' AND pr.music_id = m.music_id;	-- �ش� �÷��̸���Ʈ�� ��ϵ� ���� ���� �˻�
 DELETE FROM playlist_registrations WHERE music_id = 'mid' AND plist_id = 'pid';	-- �ش�  �÷��̸���Ʈ�߿��� �ش� mid�� ���� ���� ����