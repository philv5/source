//-slocalhost/jimin -uroot -popop12

import java.util.Scanner;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DBProj {
	
	private static Scanner scanner = new Scanner(System.in);
	private static int loginLevel; // 0: user, 1: admin, 2: root
	private static String user_id = null;	//사용자 로그인을 한 유저의 ID를 기억하는 변수
	private static Statement stmt = null;
	private static Connection conn = null;
    
	public static void main(String[] args) throws Exception {
		String servername = null;
		String userid = null;
		String password = null;
		String url = null;
		int cmd;
		
		try {
			if(args.length != 3) {
				System.out.println("Parameter mismnatch.\nInput 3 arguments.");
				System.out.println("Goodbye!");
				System.exit(1);	
			}
			
			
			
			for(int i = 0; i < args.length; i++) {
				char[] c = args[i].toCharArray();
				if(c[0] == '-') {
					if(c[1] == 's') 
						servername = args[i].substring(2);
					
					if(c[1] == 'u') 
						userid = args[i].substring(2);
					
					if(c[1] == 'p') 
						password = args[i].substring(2);
					
				}
				else continue;
			}
			
			if((servername == null) || (userid == null) || (password == null)) {
				System.out.println("Parameter mismnatch.\nArguments are severname, use id, password.");
				System.out.println("Goodbye!");
				System.exit(1);	
				
			}
			
			url = "jdbc:mysql://"+servername;//localhost:3306/jimin
			
			//Connect to mariaDB
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url,userid,password);
			System.out.println("connected to "+url+" ....\n");
			
			//LOGIN 
			while(true) {
				System.out.println("***** Login Menu *****");
				System.out.println("0. Exit");
				System.out.println("1. User Login");
				System.out.println("2. Admin Login");
				
				try {
					System.out.print("Input: ");
					cmd = scanner.nextInt();
					System.out.print("\n");
				}catch(Exception e){
					cmd = -1;
					scanner.nextLine();
				}
				
				
				if(cmd == 0) {
					System.out.println("Goodbye!");
					System.exit(1);	
				}
				else if(cmd == 1) {
					if(user_login()) {
						System.out.println("Success!\n");
						break;
					}
					else {
						System.out.println("User login error!\n");
						continue;
					}
				}	
				else if(cmd == 2) {
					if(admin_login()) {
						System.out.println("Success!\n");
						break;
					}
					else {
						System.out.println("Admin login error!\n");
						continue;
					}
				}	
				else {
					System.out.println("Unknown commend\n");
					continue;
				}
			}
			
			//각 loginlevel에 따라 보이는 메뉴가 다르다.
			while(true) {
				System.out.println("***** Top Menu *****");
				System.out.println("0. Exit");
				if (loginLevel >= 2)
					System.out.println("1. Admn info management");
				if (loginLevel >= 1) {
					System.out.println("2. User info management");
					System.out.println("3. Artist info management");
					System.out.println("4. Composer info management");
					System.out.println("5. Songwriter info management");
					System.out.println("6. Album info management");
					System.out.println("7. Music info management");
				}
				System.out.println("8. Playlist management");
				System.out.println("9. Music in playlist management");
				System.out.print("Input: ");
				try {
					cmd = scanner.nextInt();
					System.out.print("\n");
				}catch(Exception e){
					cmd = -1;
					scanner.nextLine();
				}
				
				if(cmd == 0) {
					System.out.println("Goodbye!");
					System.exit(1);	
				}
				else if(cmd == 1 && loginLevel >= 2) {
					while(true) {
						System.out.println("***** Admin Info Management Menu *****");
						System.out.println("0. Return to previous menu");
						System.out.println("1. Registration");
						System.out.println("2. Deletion");
						System.out.println("3. Modification");
						System.out.println("4. Search");
						System.out.print("Input: ");
						try {
							cmd = scanner.nextInt();
							System.out.print("\n");
						}catch(Exception e){
							cmd = -1;
							scanner.nextLine();
						}
						
						if(cmd == 0)
							break;
						else if(cmd == 1) {
							admin_registration();
						}
						else if(cmd == 2) {
							admin_deletion();
						}
						else if(cmd == 3) {
							admin_modification();
						}
						else if(cmd == 4) {
							admin_search();
						}
						else {
							System.out.println("Unknown commend");
							continue;
						}
					}								
				}
				else if(cmd == 2 && loginLevel >= 1) {
					while(true) {
						System.out.println("***** User Info Management Menu *****");
						System.out.println("0. Return to previous menu");
						System.out.println("1. Registration");
						System.out.println("2. Deletion");
						System.out.println("3. Modification");
						System.out.println("4. Search");
						System.out.print("Input: ");
						try {
							cmd = scanner.nextInt();
							System.out.print("\n");
						}catch(Exception e){
							cmd = -1;
							scanner.nextLine();
						}
						
						if(cmd == 0)
							break;
						else if(cmd == 1) {
							user_registration();
						}
						else if(cmd == 2) {
							user_deletion();
						}
						else if(cmd == 3) {
							user_modification();
						}
						else if(cmd == 4) {
							user_search();
						}
						else {
							System.out.println("Unknown commend");
							continue;
						}
					}							
				}
				else if(cmd == 3 && loginLevel >= 1) {
					while(true) {
						System.out.println("***** Artist Info Management Menu *****");
						System.out.println("0. Return to previous menu");
						System.out.println("1. Registration");
						System.out.println("2. Deletion");
						System.out.println("3. Modification");
						System.out.println("4. Search");
						System.out.print("Input: ");
						try {
							cmd = scanner.nextInt();
							System.out.print("\n");
						}catch(Exception e){
							cmd = -1;
							scanner.nextLine();
						}
						
						if(cmd == 0)
							break;
						else if(cmd == 1) {
							artist_registration();
						}
						else if(cmd == 2) {
							artist_deletion();
						}
						else if(cmd == 3) {
							artist_modification();
						}
						else if(cmd == 4) {
							artist_search();
						}
						else {
							System.out.println("Unknown commend");
							continue;
						}
					}
				}
				else if(cmd == 4 && loginLevel >= 1) {
					while(true) {
						System.out.println("***** Composer Info Management Menu *****");
						System.out.println("0. Return to previous menu");
						System.out.println("1. Registration");
						System.out.println("2. Deletion");
						System.out.println("3. Modification");
						System.out.println("4. Search");
						System.out.print("Input: ");
						try {
							cmd = scanner.nextInt();
							System.out.print("\n");
						}catch(Exception e){
							cmd = -1;
							scanner.nextLine();
						}
						
						if(cmd == 0)
							break;
						else if(cmd == 1) {
							comp_registration();
						}
						else if(cmd == 2) {
							comp_deletion();
						}
						else if(cmd == 3) {
							comp_modification();
						}
						else if(cmd == 4) {
							comp_search();
						}
						else {
							System.out.println("Unknown commend");
							continue;
						}
					}
				}
				else if(cmd == 5 && loginLevel >= 1) {
					while(true) {
						System.out.println("***** Songwrirer Info Management Menu *****");
						System.out.println("0. Return to previous menu");
						System.out.println("1. Registration");
						System.out.println("2. Deletion");
						System.out.println("3. Modification");
						System.out.println("4. Search");
						System.out.print("Input: ");
						try {
							cmd = scanner.nextInt();
							System.out.print("\n");
						}catch(Exception e){
							cmd = -1;
							scanner.nextLine();
						}
						
						if(cmd == 0)
							break;
						else if(cmd == 1) {
							swriter_registration();
						}
						else if(cmd == 2) {
							swriter_deletion();
						}
						else if(cmd == 3) {
							swriter_modification();
						}
						else if(cmd == 4) {
							swriter_search();
						}
						else {
							System.out.println("Unknown commend");
							continue;
						}
					}
				}
				else if(cmd == 6 && loginLevel >= 1) {
					while(true) {
						System.out.println("***** Album Info Management Menu *****");
						System.out.println("0. Return to previous menu");
						System.out.println("1. Registration");
						System.out.println("2. Deletion");
						System.out.println("3. Modification");
						System.out.println("4. Search");
						System.out.print("Input: ");
						try {
							cmd = scanner.nextInt();
							System.out.print("\n");
						}catch(Exception e){
							cmd = -1;
							scanner.nextLine();
						}
						
						if(cmd == 0)
							break;
						else if(cmd == 1) {
							album_registration();
						}
						else if(cmd == 2) {
							album_deletion();
						}
						else if(cmd == 3) {
							album_modification();
						}
						else if(cmd == 4) {
							album_search();
						}
						else {
							System.out.println("Unknown commend");
							continue;
						}
					}
				}
				else if(cmd == 7 && loginLevel >= 1) {
					while(true) {
						System.out.println("***** Music Info Management Menu *****");
						System.out.println("0. Return to previous menu");
						System.out.println("1. Registration");
						System.out.println("2. Deletion");
						System.out.println("3. Modification");
						System.out.println("4. Search");
						System.out.print("Input: ");
						try {
							cmd = scanner.nextInt();
							System.out.print("\n");
						}catch(Exception e){
							cmd = -1;
							scanner.nextLine();
						}
						
						if(cmd == 0)
							break;
						else if(cmd == 1) {
							music_registration();
						}
						else if(cmd == 2) {
							music_deletion();
						}
						else if(cmd == 3) {
							music_modification();
						}
						else if(cmd == 4) {
							music_search();
						}
						else {
							System.out.println("Unknown commend");
							continue;
						}
					}
				}
				else if(cmd == 8) {
					while(true) {
						System.out.println("***** Playlist Info Management Menu *****");
						System.out.println("0. Return to previous menu");
						System.out.println("1. Creation");
						System.out.println("2. Deletion");
						System.out.println("3. Modification (Title)");
						System.out.println("4. Search");
						System.out.print("Input: ");
						try {
							cmd = scanner.nextInt();
							System.out.print("\n");
						}catch(Exception e){
							cmd = -1;
							scanner.nextLine();
						}
						
						if(cmd == 0)
							break;
						else if(cmd == 1) {
							plist_creation();
						}
						else if(cmd == 2) {
							plist_deletion();
						}
						else if(cmd == 3) {
							plist_modification();
						}
						else if(cmd == 4) {
							plist_search();
						}
						else {
							System.out.println("Unknown commend");
							continue;
						}
					}
				}
				else if(cmd == 9) {
					while(true) {
						System.out.println("***** Music in playlist Info Menu *****");
						System.out.println("0. Return to previous menu");
						System.out.println("1. Registration");
						System.out.println("2. Deletion");
						System.out.println("3. Search");
						System.out.print("Input: ");
						try {
							cmd = scanner.nextInt();
							System.out.print("\n");
						}catch(Exception e){
							cmd = -1;
							scanner.nextLine();
						}
						
						if(cmd == 0)
							break;
						else if(cmd == 1) {
							musicInplist_registration();
						}
						else if(cmd == 2) {
							musicInplist_deletion();
						}
						else if(cmd == 3) {
							musicInplist_search();
						}
						else {
							System.out.println("Unknown commend\n");
							continue;
						}
					}
				}
				else {
					System.out.println("Unknown commend\n");
					continue;
				}
			}
			
		}catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
		   }catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		   }finally{
		      //finally block used to close resources
		      try{
		         if(stmt!=null)
		            stmt.close();
		      }catch(SQLException se2){
		    	  se2.printStackTrace();
		      }
		      try{
				if(conn!=null)
		            conn.close();
		      }catch(SQLException se3){
		         se3.printStackTrace();
		      }
		   }
		
		
	}
	
	
	/************ Show Function **********/
	
	
	private static void showRS(String[] attr, int size, ResultSet rs, String tname) {
		String record, sql;
		ResultSet rs2 = null;
		try {
			if(!rs.next() && !tname.contains("ID:")) {
				System.out.println("There is no search result.\n");
				return;
			}
			else 
				rs.beforeFirst();
			
			//print users table
			if(tname.equals("users")) {
				System.out.println("\n< "+tname+" info >");
				for(int i = 0; i < size; i++)
					System.out.print("----------------------------");
				System.out.print("\n");
				
				for(int i = 0; i <= 5; i++)
					System.out.printf("%-16s\t",attr[i]);
				System.out.printf("%-32s\t",attr[6]);
				System.out.printf("%-10s\t",attr[7]);
				System.out.printf("%-10s\t",attr[8]);
				System.out.printf("%-64s\t",attr[9]);
				System.out.printf("%-16s\t",attr[10]);
				
				System.out.print("\n");
				for(int i = 0; i < size; i++)
					System.out.print("----------------------------");
				System.out.println("\n");
				
				while(rs.next()) {
					for(int i = 0; i <= 5; i++) 
						System.out.printf("%-16s\t",(rs.getString(attr[i]) == null ? "" : rs.getString(attr[i])));
					System.out.printf("%-32s\t",(rs.getString(attr[6]) == null ? "" : rs.getString(attr[6])));
					System.out.printf("%-10s\t",(rs.getString(attr[7]) == null ? "" : rs.getString(attr[7])));
					System.out.printf("%-10s\t",(rs.getString(attr[8]) == null ? "" : rs.getString(attr[8])));
					System.out.printf("%-64s\t",(rs.getString(attr[9]) == null ? "" : rs.getString(attr[9])));
					System.out.printf("%-16s\t",(rs.getString(attr[10]) == null ? "" : rs.getString(attr[10])));
					
					System.out.print("\n");
				}
				System.out.print("\n");
				for(int i = 0; i < size; i++)
					System.out.print("----------------------------");
			}
			
			
			//print musics table
			else if(tname.equals("musics") || tname.contains("ID:")) {
				System.out.println("\n< "+tname+" info  >");
				for(int i = 0; i < size; i++)
					System.out.print("-----------------------");
				System.out.print("\n");
				for(int i = 0; i < size; i++)
					System.out.printf("%-16s\t",attr[i]);
				System.out.print("\n");
				for(int i = 0; i < size; i++)
					System.out.print("-----------------------");
				System.out.println("\n");
			
				while(rs.next()) {
					System.out.printf("%-16s\t", rs.getString("music_id"));
					System.out.printf("%-16s\t", rs.getString("music_title"));
					
					record = rs.getString("artist_id");
					if(record == null) {
						System.out.printf("%-16s\t","");
						System.out.printf("%-16s\t","");
					}
					else {
						stmt = conn.createStatement();
						sql = "SELECT artist_name FROM artists WHERE artist_id = '"+record+"'";
						rs2 = stmt.executeQuery(sql);
						rs2.next();
						System.out.printf("%-16s\t", record);
						System.out.printf("%-16s\t", rs2.getString("artist_name"));
						rs2.close();
						stmt.close();
					}
					
					record = rs.getString("comp_id");
					if(record == null) {
						System.out.printf("%-16s\t","");
						System.out.printf("%-16s\t","");
					}
					else {
						stmt = conn.createStatement();
						sql = "SELECT comp_name FROM composers WHERE comp_id = '"+record+"'";
						rs2 = stmt.executeQuery(sql);
						rs2.next();
						System.out.printf("%-16s\t", record);
						System.out.printf("%-16s\t", rs2.getString("comp_name"));
						rs2.close();
						stmt.close();
					}
					
					record = rs.getString("swriter_id");
					if(record == null) {
						System.out.printf("%-16s\t","");
						System.out.printf("%-16s\t","");
					}
					else {
						stmt = conn.createStatement();
						sql = "SELECT swriter_name FROM songwriters WHERE swriter_id = '"+record+"'";
						rs2 = stmt.executeQuery(sql);
						rs2.next();
						System.out.printf("%-16s\t", record);
						System.out.printf("%-16s\t", rs2.getString("swriter_name"));
						rs2.close();
						stmt.close();
					}
					
					record = rs.getString("album_id");
					if(record == null) {
						System.out.printf("%-16s\t","");
						System.out.printf("%-16s\t","");
					}
					else {
						stmt = conn.createStatement();
						sql = "SELECT album_title FROM albums WHERE album_id = '"+record+"'";
						rs2 = stmt.executeQuery(sql);
						rs2.next();
						System.out.printf("%-16s\t", record);
						System.out.printf("%-16s\t", rs2.getString("album_title"));
						rs2.close();
						stmt.close();
					}
					
					System.out.printf("%-16s\t", (rs.getString("genre") == null ? "" : rs.getString("genre")));
					
					System.out.print("\n");
				}
				System.out.print("\n");
				for(int i = 0; i < size; i++)
					System.out.print("-----------------------");
			}
			//print other table
			else {
				System.out.println("\n< "+tname+" info >");
				for(int i = 0; i < size; i++)
					System.out.print("-----------------------");
				System.out.print("\n");
				for(int i = 0; i < size; i++)
					System.out.printf("%-16s\t",attr[i]);
				System.out.print("\n");
				for(int i = 0; i < size; i++)
					System.out.print("-----------------------");
				System.out.println("\n");
			
				while(rs.next()) {
					for(int i = 0; i < size; i++) {
						record = (rs.getString(attr[i]) == null ? "" : rs.getString(attr[i]));
						System.out.printf("%-16s\t",record);
					}
					System.out.print("\n");
				}
				System.out.print("\n");
				for(int i = 0; i < size; i++)
					System.out.print("-----------------------");
			}
			
			System.out.print("\n");
			
			//입력 버퍼 플러쉬
			scanner.nextLine();
			//아무거나 입력 받으면 끝난다
			System.out.print("Input any key..... ");
			scanner.nextLine();
			
			System.out.print("\n");
			
		}catch(SQLException se){
			se.printStackTrace();
		}
	}
	
	
	/************ Login Function **********/
	
	
	private static boolean admin_login() {
		String id, pw, sql;
		
		System.out.print("ID: ");
		id = scanner.next();
		System.out.print("PW: ");
		pw = scanner.next();
		ResultSet rs = null;
		
		try {
			
			stmt = conn.createStatement();
			sql = "SELECT * "
					+ "FROM admins "
					+ "WHERE admin_id = "+"'"+id+"'";
			rs = stmt.executeQuery(sql);
			
			if(!rs.next()) {
				System.out.println("Input ID does not exist.");
				rs.close();
			    stmt.close();
				return false;
			}
			else {
				if(!pw.equals(rs.getString("admin_pw"))){
					System.out.println("Input password does not exist.");
					rs.close();
				    stmt.close();
					return false;
				} 
			}
			
			loginLevel = 1;
			
			//root 로그인
			if("root".equals(rs.getString("admin_id"))) {
				System.out.println("\nroot login");
				loginLevel++;
			}
			
			rs.close();
		    stmt.close();
		    return true;
		    
		}catch(SQLException se){
			se.printStackTrace();
			return false;
		}
	}
	
	private static boolean user_login() {
		String id, pw, sql;
		
		System.out.print("ID: ");
		id = scanner.next();
		System.out.print("PW: ");
		pw = scanner.next();
		ResultSet rs = null;
		
		try {
			
			stmt = conn.createStatement();
			sql = "SELECT user_pw "
					+ "FROM users "
					+ "WHERE user_id = "+"'"+id+"'";
			rs = stmt.executeQuery(sql);
			
			if(!rs.next()) {
				System.out.println("Input ID does not exist.");
				rs.close();
			    stmt.close();
				return false;
			}
			else {
				if(!pw.equals(rs.getString("user_pw"))){
					System.out.println("Input password does not exist.");
					rs.close();
				    stmt.close();
					return false;
				} 
			}
			
			loginLevel = 0;
			user_id = id;
			
			rs.close();
		    stmt.close();
		    return true;
		    
		}catch(SQLException se){
			se.printStackTrace();
			return false;
		}
	}
	
	
	/************ Admin Info Mangemenet Function **********/
	
	
	private static void admin_registration() {
		String id, pw, fname, lname, sql;
		ResultSet rs = null;
		
		System.out.println("< Admin Registration >");
		
		try {
			
			//ID 입력
			System.out.println("--- Step of ID input ---");
			while(true) {
				System.out.print("ID(essential): ");
				id = scanner.next();
				//admin_id가 Varchar(16) 이므로 문자열 길이 확인 
				if(id.length() > 16) {
					System.out.println("Maximum is 16 characters");
					continue;
				}
				
				stmt = conn.createStatement();
				sql = "SELECT * "
						+ "FROM admins "
						+ "WHERE admin_id = "+"'"+id+"'";
				rs = stmt.executeQuery(sql);
				
				//ID중복 확인
				if(rs.next()) {
					System.out.println("It is a invalid ID (Duplicate)\n");
					rs.close();
					stmt.close();
					continue;
				}
				else {
					System.out.println("It is a valid ID\n");
					rs.close();
					stmt.close();
					break;
				}
			}
			System.out.print("\n");
			
			//PW입력
			System.out.println("--- Step of password input ---");
			while(true) {
				System.out.print("PW(essential): ");
				pw = scanner.next();
				// admin_pw가 Varchar(16) 이므로 문자열 길이 확인 
				if(pw.length() > 16) {
					System.out.println("Maximum is 16 characters\n");
					continue;
				}
				else
					break;
			}
			System.out.print("\n");
			
			//Fname입력
			System.out.println("--- Step of Fname input ---");
			while(true) {
				System.out.print("Fname(essential): ");
				fname = scanner.next();
				//admin_Fname가 Varchar(16) 이므로 문자열 길이 확인 
				if(fname.length() > 16) {
					System.out.println("Maximum is 16 characters\n");
					continue;
				}
				else
					break;
			}
			System.out.print("\n");
			
			//Lname입력
			System.out.println("--- Step of Lname input ---");
			while(true) {
				System.out.print("Lname(essential): ");
				lname = scanner.next();
				//admin_Lname가 Varchar(16) 이므로 문자열 길이 확인 
				if(lname.length() > 16) {
					System.out.println("Maximum is 16 characters\n");
					continue;
				}
				else
					break;
			}
			System.out.print("\n");
			
			//Insert
			stmt = conn.createStatement();
			sql = "INSERT INTO admins(admin_id, admin_pw, admin_Fname, admin_Lname) "
					+ "values("+"'"+id+"',"+"'"+pw+"',"+"'"+fname+"',"+"'"+lname+"'"+")";
			stmt.executeUpdate(sql);
			stmt.close();
			
			System.out.println("Success!\n");
			
		}catch(SQLException se){
			se.printStackTrace();
		}
	}
	
	private static void admin_deletion() {
		String id, sql;
		int flag;
		
		System.out.println("Input admin ID to delete ");
		System.out.print("ID: ");
		id = scanner.next();
		
		try {
			if(id.equals("root")) {
				System.out.println("Cannot delete root\n");
				return;
			}
			
			stmt = conn.createStatement();
			sql = "DELETE FROM admins "
					+ "WHERE admin_id = "+"'"+id+"'";
					
			flag = stmt.executeUpdate(sql);
			if(flag == 0) 
				System.out.println("It does not exist.\n");
			
			else
				System.out.println("Success!\n");
			
			stmt.close();
		}catch(SQLException se){
			se.printStackTrace();
		}
	}
	
	private static void admin_modification() {
		String id, pw, fname, lname, sql;
		ResultSet rs = null;
		int cmd;
		
		System.out.println("Input admin ID to modify\n");
		System.out.print("ID: ");
		id = scanner.next();
		System.out.print("\n");
		
		try {
			stmt = conn.createStatement();
			sql = "SELECT * "
					+ "FROM admins "
					+ "WHERE admin_id = "+"'"+id+"'";
			rs = stmt.executeQuery(sql);
			
			//ID존재 확인
			if(!rs.next()) {
				System.out.println("It does not exist.\n");
				rs.close();
			    stmt.close();
				return;
			}
			else {
				stmt.close();
				
				while(true) {
					System.out.println("***** ["+id+"] modification menu *****");
					System.out.println("0. Return to previous menu");
					System.out.println("1. Change password");
					System.out.println("2. Change name");
					System.out.print("Input: ");
					try {
						cmd = scanner.nextInt();
						System.out.print("\n");
					}catch(Exception e){
						cmd = -1;
						scanner.nextLine();
					}
					if(cmd == 0)
						break;
					else if(cmd == 1) {
						//PW입력
						System.out.println("Input new password");
						while(true) {
							System.out.print("PW: ");
							pw = scanner.next();
							// admin_pw가 Varchar(16) 이므로 문자열 길이 확인 
							if(pw.length() > 16) {
								System.out.println("Maximum is 16 characters\n");
								continue;
							}
							else
								break;
						}
						stmt = conn.createStatement();
						sql = "UPDATE admins "
								+ "SET admin_pw = "+"'"+pw+"' "
								+ "WHERE admin_id = "+"'"+id+"'";
						stmt.executeUpdate(sql);
						stmt.close();
						System.out.println("Success!\n");
					}
					else if(cmd == 2){
						System.out.println("Input new name");
						//Fname 입력
						while(true) {
							System.out.print("Fname: ");
							fname = scanner.next();
							//admin_Fname가 Varchar(16) 이므로 문자열 길이 확인 
							if(fname.length() > 16) {
								System.out.println("Maximum is 16 characters\n");
								continue;
							}
							else
								break;
						}
						//Lname입력
						while(true) {
							System.out.print("Lname: ");
							lname = scanner.next();
							//admin_Lname가 Varchar(16) 이므로 문자열 길이 확인 
							if(lname.length() > 16) {
								System.out.println("Maximum is 16 characters\n");
								continue;
							}
							else
								break;
						}
						stmt = conn.createStatement();
						sql = "UPDATE admins "
								+ "SET admin_Fname = "+"'"+fname+"', "+"admin_Lname = "+"'"+lname+"' "
								+ "WHERE admin_id = "+"'"+id+"'";
						stmt.executeUpdate(sql);
						stmt.close();
						System.out.println("Success!\n");
					}
					else {
						System.out.println("Unknown commend\n");
						continue;
					}
				}
			}
		}catch(SQLException se){
			se.printStackTrace();
		}finally{
		      try{
			         if(stmt!=null)
			            stmt.close();
			      }catch(SQLException se2){
			    	  se2.printStackTrace();
			      }
			      try{
			         if(rs!=null)
			            rs.close();
			      }catch(SQLException se){
			         se.printStackTrace();
			      }
			}
	}
	
	private static void admin_search() {
		String id, fname, lname, sql;
		ResultSet rs = null;
		int cmd;
		
		try {			
			while(true) {
				System.out.println("***** Search Menu *****");
				System.out.println("0. Return to previous menu");
				System.out.println("1. All items");
				System.out.println("2. ID search");
				System.out.println("3. Name search");
				System.out.print("Input: ");
				try {
					cmd = scanner.nextInt();
					System.out.print("\n");
				}catch(Exception e){
					cmd = -1;
					scanner.nextLine();
				}
				
				if(cmd == 0)
					break;
				else if(cmd == 1) {
					stmt = conn.createStatement();
					sql = "SELECT * "
							+ "FROM admins";
					rs = stmt.executeQuery(sql);
				}
				else if(cmd == 2){
					System.out.print("ID: ");
					id = scanner.next();
					stmt = conn.createStatement();
					sql = "SELECT * "
							+ "FROM admins "
							+ "WHERE admin_id = "+"'"+id+"'";
					rs = stmt.executeQuery(sql);
				}
				else if(cmd == 3) {
					System.out.println("1. Full name search");
					System.out.println("2. Fname search");
					System.out.println("3. Lname search");
					System.out.print("Input: ");
					try {
						cmd = scanner.nextInt();
						System.out.print("\n");
					}catch(Exception e){
						cmd = -1;
						scanner.nextLine();
					}
					if(cmd == 1) {
						System.out.print("Fname: ");
						fname = scanner.next();
						System.out.print("Lname: ");
						lname = scanner.next();
						stmt = conn.createStatement();
						sql = "SELECT * "
								+ "FROM admins "
								+ "WHERE admin_Fname = "+"'"+fname+"' "+"AND "+"admin_Lname = "+"'"+lname+"'";
						rs = stmt.executeQuery(sql);
					}
					else if(cmd == 2) {
						System.out.print("Fname: ");
						fname = scanner.next();
						stmt = conn.createStatement();
						sql = "SELECT * "
								+ "FROM admins "
								+ "WHERE admin_Fname = "+"'"+fname+"'";
						rs = stmt.executeQuery(sql);
					}
					else if(cmd == 3) {
						System.out.print("Lname: ");
						lname = scanner.next();
						stmt = conn.createStatement();
						sql = "SELECT * "
								+ "FROM admins "
								+ "WHERE admin_Lname = "+"'"+lname+"'";
						rs = stmt.executeQuery(sql);
					}
					else {
						System.out.println("Unknown commend\n");
						continue;
					}
				}
				else {
					System.out.println("Unknown commend\n");
					continue;
				}
				
				String[] attr = {"admin_id", "admin_pw", "admin_Fname", "admin_Lname"};
				showRS(attr, 4, rs, "admins");
				rs.close();
				stmt.close();
			}
			
		}catch(SQLException se){
			se.printStackTrace();
		}finally{
		      try{
		         if(stmt!=null)
		            stmt.close();
		      }catch(SQLException se2){
		    	  se2.printStackTrace();
		      }
		      try{
		         if(rs!=null)
		            rs.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }
		}
	}
	
	
	/************ User Info Mangemenet Function **********/
	
	
	private static void user_registration() {
		String id, pw, fname, lname, jno, bdate, email, sex, pcode, addr, job, sql ;
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		ResultSet rs = null;
		
		//System.out.println("Input info to register ");
		System.out.println("< User Registration >");
		
		try {
			//ID 입력
			System.out.println("--- Step of ID input ---");
			while(true) {
				System.out.print("ID(essential): ");
				id = scanner.next();
				//user_id가 Varchar(16) 이므로 문자열 길이 확인 
				if(id.length() > 16) {
					System.out.println("Maximum is 16 characters\n");
					continue;
				}
				
				stmt = conn.createStatement();
				sql = "SELECT * "
						+ "FROM users "
						+ "WHERE user_id = "+"'"+id+"'";
				rs = stmt.executeQuery(sql);
				
				//ID중복 확인
				if(rs.next()) {
					System.out.println("It is a invalid ID (Duplicate)\n");
					rs.close();
					stmt.close();
					continue;	//return
				}
				else {
					System.out.println("It is a valid ID\n");
					rs.close();
					stmt.close();
					break;
				}
			}
			System.out.print("\n");
			
			//PW입력
			System.out.println("--- Step of Password input ---");
			while(true) {
				System.out.print("PW(essential): ");
				pw = scanner.next();
				// user_pw가 Varchar(16) 이므로 문자열 길이 확인 
				if(pw.length() > 16) {
					System.out.println("Maximum is 16 characters\n");
					continue;
				}
				else
					break;
			}
			System.out.print("\n");
			
			//Fname입력
			System.out.println("--- Step of Fname input ---");
			while(true) {
				System.out.print("Fname(essential): ");
				fname = scanner.next();
				//user_Fname가 Varchar(16) 이므로 문자열 길이 확인 
				if(fname.length() > 16) {
					System.out.println("Maximum is 16 characters\n");
					continue;
				}
				else
					break;
			}
			System.out.print("\n");
			
			//Lname입력
			System.out.println("--- Step of Lname input ---");
			while(true) {
				System.out.print("Lname(essential): ");
				lname = scanner.next();
				//user_Lname가 Varchar(16) 이므로 문자열 길이 확인 
				if(lname.length() > 16) {
					System.out.println("Maximum is 16 characters\n");
					continue;
				}
				else
					break;
			}
			System.out.print("\n");
			
			//jumin_no입력
			System.out.println("--- Step of Jumin number input ---");
			while(true) {
				System.out.print("Jumin no(essential): ");
				jno = scanner.next();
				//jumin_no가 char(13) 이므로 문자열 길이 확인 
				if(jno.length() != 13) {
					System.out.println("You need to input 13 numbers\n");
					continue;
				}
				else
					break;
			}
			System.out.print("\n");
			
			//입력 버퍼 플러쉬
			scanner.nextLine();
			
			//Bdate입력
			System.out.println("--- Step of Birthdate input ---");
			while(true) {
				System.out.print("Bdate(press enter to skip): ");
				bdate = scanner.nextLine();
				//skip
				if(bdate.equals("")) {
					System.out.println("skip!\n");
					bdate = null;
					break;
				}
				//bdate가 date형식 이므로 문자열의 날짜가 유효한지 확인
				try {
					format.setLenient(false);
					format.parse(bdate);
				}catch(ParseException e){
					System.out.println("It is an incorrect date.");
					System.out.println("You need to input like YYYY-MM-DD\n");
					continue;
				}
				break;
			}
			System.out.print("\n");
			
			//e-mail입력
			System.out.println("--- Step of e-mail input ---");
			while(true) {
				System.out.print("E-mail(press enter to skip): ");
				email = scanner.nextLine();
				//skip
				if(email.equals("")){
					System.out.println("skip!\n");
					email = null;
					break;
				}
				//e_mail가 varchar(32) 이므로 문자열 길이 확인 
				if(email.length() > 32) {
					System.out.println("Maximum is 32 characters\n");
					continue;
				}
				else
					break;
			}
			System.out.print("\n");
			
			//sex입력
			System.out.println("--- Step of sex input ---");
			while(true) {
				System.out.print("Sex(press enter to skip): ");
				sex = scanner.nextLine();
				//skip
				if(sex.equals("")){
					System.out.println("skip!\n");
					sex = null;
					break;
				}
				//sex은 M or F로 구분 
				if(!sex.equals("M") && !sex.equals("F")) {
					System.out.println("You need to input 1 character ('M' or 'F')\n");
					continue;
				}
				else
					break;
			}
			System.out.print("\n");
			
			//postcode입력
			System.out.println("--- Step of postcode input ---");
			while(true) {
				System.out.print("Postcode(press enter to skip): ");
				pcode = scanner.nextLine();
				//skip
				if(pcode.equals("")){
					System.out.println("skip!\n");
					pcode = null;
					break;
				}
				//postcode가 char(5) 이므로 문자열 길이 확인 
				if(pcode.length() != 5) {
					System.out.println("You need to input 5 numbers\n");
					continue;
				}
				else
					break;
			}
			System.out.print("\n");
			
			//address입력
			System.out.println("--- Step of address input ---");
			while(true) {
				System.out.print("Address(press enter to skip): ");
				addr = scanner.nextLine();
				//skip
				if(addr.equals("")){
					System.out.println("skip!\n");
					addr = null;
					break;
				}
				//address가 varchar(64) 이므로 문자열 길이 확인 
				if(addr.length() > 64) {
					System.out.println("Maximum is 64 characters\n");
					continue;
				}
				else
					break;
			}
			System.out.print("\n");
			
			//job입력
			System.out.println("--- Step of job input ---");
			while(true) {
				System.out.print("Job(press enter to skip): ");
				job = scanner.nextLine();
				//skip
				if(job.equals("")){
					System.out.println("skip!\n");
					job = null;
					break;
				}
				//job가 varchar(16) 이므로 문자열 길이 확인 
				if(job.length() > 16) {
					System.out.println("Maximum is 16 characters\n");
					continue;
				}
				else
					break;
			}
			System.out.print("\n");
			
			//Insert
			stmt = conn.createStatement();
			sql = "INSERT INTO users(user_id, user_pw, user_Fname, user_Lname, "
					+ "jumin_no, bdate, e_mail, sex, postcode, address, job) "
					+ "values("+"'"+id+"',"+"'"+pw+"',"+"'"+fname+"',"+"'"+lname+"',"+"'"+jno+"',"
					+(bdate == null ? "NULL": "'"+bdate+"'") +","
					+(email == null ? "NULL": "'"+email+"'") +","
					+(sex == null ? "NULL": "'"+sex+"'") +","
					+(pcode == null ? "NULL": "'"+pcode+"'") +","
					+(addr == null ? "NULL": "'"+addr+"'") +","
					+(job == null ? "NULL": "'"+job+"'") +")";
					
			
			stmt.executeUpdate(sql);
			stmt.close();
			
			System.out.println("Success!\n");
			
		}catch(SQLException se){
			se.printStackTrace();
		}
	}
	
	private static void user_deletion() {
		String id, sql;
		int flag;
		ResultSet rs = null;
		
		System.out.println("Input user ID to delete ");
		System.out.print("ID: ");
		id = scanner.next();
		
		try {
			stmt = conn.createStatement();
			sql = "SELECT* FROM playlists "
					+ "WHERE user_id = "+"'"+id+"'";
			rs = stmt.executeQuery(sql);
			
			//참조 확인
			if(rs.next()) {
				System.out.println("Cannot delete : a foreign key constraint fails (playlists table)\n");
				rs.close();
				stmt.close();
				return;
			}
			rs.close();
			stmt.close();
			
			stmt = conn.createStatement();
			sql = "DELETE FROM users "
					+ "WHERE user_id = "+"'"+id+"'";
					
			flag = stmt.executeUpdate(sql);
			if(flag == 0) 
				System.out.println("It does not exist.\n");
			
			else
				System.out.println("Success!\n");
			
			stmt.close();
		}catch(SQLException se){
			se.printStackTrace();
		}
	}
	
	private static void user_modification() {
		String id, pw, fname, lname, jno, bdate, email, sex, pcode, addr, job, sql;
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		ResultSet rs = null;
		int cmd;
		
		System.out.println("Input user ID to modify\n");
		System.out.print("ID: ");
		id = scanner.next();
		System.out.print("\n");
		
		try {
			stmt = conn.createStatement();
			sql = "SELECT * "
					+ "FROM users "
					+ "WHERE user_id = "+"'"+id+"'";
			rs = stmt.executeQuery(sql);
			
			//ID존재 확인
			if(!rs.next()) {
				System.out.println("It does not exist.\n");
				rs.close();
			    stmt.close();
				return;
			}
			else {
				stmt.close();
				
				while(true) {
					System.out.println("***** ["+id+"] modification menu *****");
					System.out.println("0. Return to previous menu");
					System.out.println("1. Change password");
					System.out.println("2. Change name");
					System.out.println("3. Change jimin no");
					System.out.println("4. Change bdate");
					System.out.println("5. Change e-mail");
					System.out.println("6. Change sex");
					System.out.println("7. Change postcode");
					System.out.println("8. Change address");
					System.out.println("9. Change job");
					System.out.print("Input: ");
					try {
						cmd = scanner.nextInt();
						System.out.print("\n");
					}catch(Exception e){
						cmd = -1;
						scanner.nextLine();
					}
					if(cmd == 0)
						break;
					//PW입력
					else if(cmd == 1) {
						System.out.println("Input new password");
						while(true) {
							System.out.print("PW: ");
							pw = scanner.next();
							// user_pw가 Varchar(16) 이므로 문자열 길이 확인 
							if(pw.length() > 16) {
								System.out.println("Maximum is 16 characters\n");
								continue;
							}
							else
								break;
						}
						stmt = conn.createStatement();
						sql = "UPDATE users "
								+ "SET user_pw = "+"'"+pw+"' "
								+ "WHERE user_id = "+"'"+id+"'";
						stmt.executeUpdate(sql);
						stmt.close();
						System.out.println("Success!\n");
					}
					//Name입력
					else if(cmd == 2){
						System.out.println("Input new name");
						//Fname 입력
						while(true) {
							System.out.print("Fname: ");
							fname = scanner.next();
							//user_Fname가 Varchar(16) 이므로 문자열 길이 확인 
							if(fname.length() > 16) {
								System.out.println("Maximum is 16 characters\n");
								continue;
							}
							else
								break;
						}
						//Lname입력
						while(true) {
							System.out.print("Lname: ");
							lname = scanner.next();
							//user_Lname가 Varchar(16) 이므로 문자열 길이 확인 
							if(lname.length() > 16) {
								System.out.println("Maximum is 16 characters\n");
								continue;
							}
							else
								break;
						}
						stmt = conn.createStatement();
						sql = "UPDATE users "
								+ "SET user_Fname = "+"'"+fname+"', "+"user_Lname = "+"'"+lname+"' "
								+ "WHERE user_id = "+"'"+id+"'";
						stmt.executeUpdate(sql);
						stmt.close();
						System.out.println("Success!\n");
					}
					//jumin_no입력
					else if(cmd == 3) {
						System.out.println("Input new number");
						while(true) {
							System.out.print("Jumin no: ");
							jno = scanner.next();
							//jumin_no가 char(13) 이므로 문자열 길이 확인 
							if(jno.length() != 13) {
								System.out.println("You need to input 13 numbers\n");
								continue;
							}
							else
								break;
						}
						stmt = conn.createStatement();
						sql = "UPDATE users "
								+ "SET jumin_no = "+"'"+jno+"' "
								+ "WHERE user_id = "+"'"+id+"'";
						stmt.executeUpdate(sql);
						stmt.close();
						System.out.println("Success!\n");
					}
					//bdate입력
					else if(cmd == 4) {
						System.out.println("Input new date");
						while(true) {
							System.out.print("Bdate: ");
							bdate = scanner.next();
							//bdate가 date형식 이므로 문자열에 날짜가 유효한지 확인
							try {
								format.setLenient(false);
								format.parse(bdate);
							}catch(ParseException e){
								System.out.println("It is an incorrect date.");
								System.out.println("You need to input like YYYY-MM-DD\n");
								continue;
							}
							break;
						}
						stmt = conn.createStatement();
						sql = "UPDATE users "
								+ "SET bdate = "+"'"+bdate+"' "
								+ "WHERE user_id = "+"'"+id+"'";
						stmt.executeUpdate(sql);
						stmt.close();
						System.out.println("Success!\n");
					}
					//e-mail입력
					else if(cmd == 5) {
						System.out.println("Input new e-mail");
						while(true) {
							System.out.print("E-mail: ");
							email = scanner.next();
							//e_mail가 varchar(32) 이므로 문자열 길이 확인 
							if(email.length() > 32) {
								System.out.println("Maximum is 32 characters\n");
								continue;
							}
							else
								break;
						}
						stmt = conn.createStatement();
						sql = "UPDATE users "
								+ "SET e_mail = "+"'"+email+"' "
								+ "WHERE user_id = "+"'"+id+"'";
						stmt.executeUpdate(sql);
						stmt.close();
						System.out.println("Success!\n");
					}
					//sex입력
					else if(cmd == 6) {
						System.out.println("Input new info");
						while(true) {
							System.out.print("Sex: ");
							sex = scanner.next();
							//sex가 char 이므로 문자열 길이 확인 
							if(!sex.equals("M") && !sex.equals("F")) {
								System.out.println("You need to input 1 character ('M' ofr 'F')\n");
								continue;
							}
							else
								break;
						}
						stmt = conn.createStatement();
						sql = "UPDATE users "
								+ "SET sex = "+"'"+sex+"' "
								+ "WHERE user_id = "+"'"+id+"'";
						stmt.executeUpdate(sql);
						stmt.close();
						System.out.println("Success!\n");
					}
					//postcode입력
					else if(cmd == 7) {
						System.out.println("Input new postcode");
						while(true) {
							System.out.print("Postcode: ");
							pcode = scanner.next();
							//postcode가 char(5) 이므로 문자열 길이 확인 
							if(pcode.length() != 5) {
								System.out.println("You need to input 5 numbers\n");
								continue;
							}
							else
								break;
						}
						stmt = conn.createStatement();
						sql = "UPDATE users "
								+ "SET postcode = "+"'"+pcode+"' "
								+ "WHERE user_id = "+"'"+id+"'";
						stmt.executeUpdate(sql);
						stmt.close();
						System.out.println("Success!\n");
					}
					//address입력
					else if(cmd == 8) {
						System.out.println("Input new address");
						while(true) {
							System.out.print("Address: ");
							addr = scanner.next();
							//address가 varchar(64) 이므로 문자열 길이 확인 
							if(addr.length() > 64) {
								System.out.println("Maximum is 64 characters\n");
								continue;
							}
							else
								break;
						}
						stmt = conn.createStatement();
						sql = "UPDATE users "
								+ "SET address = "+"'"+addr+"' "
								+ "WHERE user_id = "+"'"+id+"'";
						stmt.executeUpdate(sql);
						stmt.close();
						System.out.println("Success!\n");
					}
					//job
					else if(cmd == 9) {
						System.out.println("Input new job");
						while(true) {
							System.out.print("Job: ");
							job = scanner.next();
							//job가 varchar(16) 이므로 문자열 길이 확인 
							if(job.length() > 16) {
								System.out.println("Maximum is 16 characters\n");
								continue;
							}
							else
								break;
						}
						stmt = conn.createStatement();
						sql = "UPDATE users "
								+ "SET job = "+"'"+job+"' "
								+ "WHERE user_id = "+"'"+id+"'";
						stmt.executeUpdate(sql);
						stmt.close();
						System.out.println("Success!\n");
					}
					else {
						System.out.println("Unknown commend\n");
						continue;
					}
				}
			}
		}catch(SQLException se){
			se.printStackTrace();
		}finally{
		      try{
			         if(stmt!=null)
			            stmt.close();
			      }catch(SQLException se2){
			    	  se2.printStackTrace();
			      }
			      try{
			         if(rs!=null)
			            rs.close();
			      }catch(SQLException se){
			         se.printStackTrace();
			      }
			}
	}
	
	private static void user_search() {
		String id, fname, lname, jno, bdate, email, pcode, addr, job, sql;
		ResultSet rs = null;
		int cmd;
		
		try {
			while(true) {
				System.out.println("***** Search Menu *****");
				System.out.println("0. Return to previous menu");
				System.out.println("1. All items");
				System.out.println("2. ID search");
				System.out.println("3. Name search");
				System.out.println("4. Jumin number search");
				System.out.println("5. Birthdate search");
				System.out.println("6. E-mail search");
				System.out.println("7. Sex search");
				System.out.println("8. Postcode search");
				System.out.println("9. Address search");
				System.out.println("10. Job search");
				System.out.print("Input: ");
				try {
					cmd = scanner.nextInt();
					System.out.print("\n");
				}catch(Exception e){
					cmd = -1;
					scanner.nextLine();
				}
				
				if(cmd == 0)
					break;
				else if(cmd == 1) {
					stmt = conn.createStatement();
					sql = "SELECT * "
							+ "FROM users";
					rs = stmt.executeQuery(sql);
				}
				else if(cmd == 2){
					System.out.print("ID: ");
					id = scanner.next();
					stmt = conn.createStatement();
					sql = "SELECT * "
							+ "FROM users "
							+ "WHERE user_id = "+"'"+id+"'";
					rs = stmt.executeQuery(sql);
				}
				else if(cmd == 3) {
					System.out.println("1. Full name search");
					System.out.println("2. Fname search");
					System.out.println("3. Lname search");
					System.out.print("Input: ");
					try {
						cmd = scanner.nextInt();
						System.out.print("\n");
					}catch(Exception e){
						cmd = -1;
						scanner.nextLine();
					}
					if(cmd == 1) {
						System.out.print("Fname: ");
						fname = scanner.next();
						System.out.print("Lname: ");
						lname = scanner.next();
						stmt = conn.createStatement();
						sql = "SELECT * "
								+ "FROM users "
								+ "WHERE user_Fname = "+"'"+fname+"' "+"AND "+"user_Lname = "+"'"+lname+"'";
						rs = stmt.executeQuery(sql);
					}
					else if(cmd == 2) {
						System.out.print("Fname: ");
						fname = scanner.next();
						stmt = conn.createStatement();
						sql = "SELECT * "
								+ "FROM users "
								+ "WHERE user_Fname = "+"'"+fname+"'";
						rs = stmt.executeQuery(sql);
					}
					else if(cmd == 3) {
						System.out.print("Lname: ");
						lname = scanner.next();
						stmt = conn.createStatement();
						sql = "SELECT * "
								+ "FROM users "
								+ "WHERE user_Lname = "+"'"+lname+"'";
						rs = stmt.executeQuery(sql);
					}
					else {
						System.out.println("Unknown commend\n");
						stmt.close();
						return;
					}
				}
				else if(cmd == 4) {
					System.out.print("Jumin no: ");
					jno = scanner.next();
					stmt = conn.createStatement();
					sql = "SELECT * "
							+ "FROM users "
							+ "WHERE jumin_no = "+"'"+jno+"'";
					rs = stmt.executeQuery(sql);
				}
				else if(cmd == 5) {
					System.out.println("1. YYYY-MM-DD search");
					System.out.println("2. YYYY search");
					System.out.println("3. MM search");
					System.out.print("Input: ");
					try {
						cmd = scanner.nextInt();
						System.out.print("\n");
					}catch(Exception e){
						cmd = -1;
						scanner.nextLine();
					}
					if(cmd == 1) {
						System.out.print("Bdate: ");
						bdate = scanner.next();
						stmt = conn.createStatement();
						sql = "SELECT * "
								+ "FROM users "
								+ "WHERE bdate = "+"'"+bdate+"'";
						rs = stmt.executeQuery(sql);
					}
					else if(cmd == 2) {
						System.out.print("Byear: ");
						bdate = scanner.next();
						stmt = conn.createStatement();
						sql = "SELECT * "
								+ "FROM users "
								+ "WHERE bdate LIKE "+"'"+bdate+"%'";
						rs = stmt.executeQuery(sql);
					}
					else if(cmd == 3) {
						System.out.print("Bmonth: ");
						bdate = scanner.next();
						stmt = conn.createStatement();
						sql = "SELECT * "
								+ "FROM users "
								+ "WHERE bdate LIKE "+"'____-"+bdate+"-__'"; //"1995-10-14 ____-10-__"
						rs = stmt.executeQuery(sql);
					}
					else {
						System.out.println("Unknown commend\n");
						continue;
					}
					
				}
				else if(cmd == 6) {
					System.out.print("E-mail address: ");
					email = scanner.next();
					//e_mail에 입력 받은 email가 포함되어 있으면 hit
					stmt = conn.createStatement();
					sql = "SELECT * "
							+ "FROM users "
							+ "WHERE e_mail LIKE "+"'%"+email+"%'";
					rs = stmt.executeQuery(sql);
				}
				else if(cmd == 7) {
					System.out.println("1. Male search");
					System.out.println("2. Female search");
					System.out.print("Input: ");
					try {
						cmd = scanner.nextInt();
						System.out.print("\n");
					}catch(Exception e){
						cmd = -1;
						scanner.nextLine();
					}
					if(cmd == 1) {
						stmt = conn.createStatement();
						sql = "SELECT * "
								+ "FROM users "
								+ "WHERE sex = 'M'";
						rs = stmt.executeQuery(sql);
					}
					else if(cmd == 2) {
						stmt = conn.createStatement();
						sql = "SELECT * "
								+ "FROM users "
								+ "WHERE sex = 'F'";
						rs = stmt.executeQuery(sql);
					}
					else {
						System.out.println("Unknown commend\n");
						continue;
					}
				}
				else if(cmd == 8) {
					System.out.print("Postcod: ");
					pcode = scanner.next();
					stmt = conn.createStatement();
					sql = "SELECT * "
							+ "FROM users "
							+ "WHERE postcode = "+"'"+pcode+"'";
					rs = stmt.executeQuery(sql);
				}
				else if(cmd == 9) {
					System.out.print("Address: ");
					addr = scanner.next();
					//address에 입력 받은 addr가 포함되어 있으면 hit
					stmt = conn.createStatement();
					sql = "SELECT * "
							+ "FROM users "
							+ "WHERE address LIKE "+"'%"+addr+"%'";
					rs = stmt.executeQuery(sql);
				}
				else if(cmd == 10) {
					System.out.print("Job: ");
					job = scanner.next();
					stmt = conn.createStatement();
					sql = "SELECT * "
							+ "FROM users "
							+ "WHERE job = "+"'"+job+"'";
					rs = stmt.executeQuery(sql);
				}
				else {
					System.out.println("Unknown commend\n");
					continue;
				}
				
				String[] attr = {"user_id", "user_pw", "user_Fname", "user_Lname",
						"jumin_no", "bdate", "e_mail","sex", "postcode","address", "job"};
				showRS(attr, 11, rs, "users");
				rs.close();
				stmt.close();
			}
			
		}catch(SQLException se){
			se.printStackTrace();
		}finally{
		      try{
		         if(stmt!=null)
		            stmt.close();
		      }catch(SQLException se2){
		    	  se2.printStackTrace();
		      }
		      try{
		         if(rs!=null)
		            rs.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }
		}
	}
	
	
	/************ Artist Info Mangemenet Function **********/
	
	
	private static void artist_registration() {
		String id, name, division, agency, sql;
		ResultSet rs = null;
		
		System.out.println("< Artist Registration >");
		
		try {
			//artist_id 발행 
			stmt = conn.createStatement();
			sql = "SELECT id_no "
					+ "FROM id_mng "
					+ "WHERE id_name = 'artist_id'";
			rs = stmt.executeQuery(sql);
			rs.next();
			id = rs.getString(1);
			rs.close();
			stmt.close();
		
			//artist_id 갱신
			stmt = conn.createStatement();
			sql = "UPDATE id_mng "
					+ "SET id_no = id_no + 1 "
					+ "WHERE id_name = 'artist_id'";
			stmt.executeUpdate(sql);
			stmt.close();
			
			//Name입력
			System.out.println("--- Step of name input ---");
			while(true) {
				System.out.print("Name(essential): ");
				name = scanner.next();
				// artist_name가 Varchar(16) 이므로 문자열 길이 확인 
				if(name.length() > 16) {
					System.out.println("Maximum is 16 characters\n");
					continue;
				}
				else
					break;
			}
			System.out.print("\n");
			
			//입력 버퍼 플러쉬
			scanner.nextLine();
			
			//division입력
			System.out.println("--- Step of (Solo:'S' or Group:'G') input ---");
			while(true) {
				System.out.print("Division(press enter to skip): ");
				division = scanner.nextLine();
				//skip
				if(division.equals("")){
					System.out.println("skip!\n");
					division = null;
					break;
				}
				//division은 S, G로 구분 
				if(!division.equals("S") && !division.equals("G")) {
					System.out.println("You need to input 1 character (Solo:'S' or Group:'G')\n");
					continue;
				}
				else {
					break;
				}
			}
			System.out.print("\n");
			
			//agency입력
			System.out.println("--- Step of agency input ---");
			while(true) {
				System.out.print("Agency(press enter to skip): ");
				agency = scanner.nextLine();
				//skip
				if(agency.equals("")){
					System.out.println("skip!\n");
					agency = null;
					break;
				}
				//agency가 varchar(16) 이므로 문자열 길이 확인 
				if(agency.length() > 16) {
					System.out.println("Maximum is 16 characters\n");
					continue;
				}
				else
					break;
			}
			System.out.print("\n");
			
			//Insert
			stmt = conn.createStatement();
			sql = "INSERT INTO artists(artist_id, artist_name, division, agency) "
					+ "values("+"'"+id+"',"+"'"+name+"',"
					+(division == null ? "NULL": "'"+division+"'") +","
					+(agency == null ? "NULL": "'"+agency+"'") +")";
					
			stmt.executeUpdate(sql);
			stmt.close();
			
			System.out.println("Success!\n");
			
		}catch(SQLException se){
			se.printStackTrace();
		}
	}
	
	private static void artist_deletion() {
		String id, sql;
		int flag;
		ResultSet rs = null;
		
		System.out.println("Input artist ID to delete ");
		System.out.print("ID: ");
		id = scanner.next();
		
		try {
			stmt = conn.createStatement();
			sql = "SELECT* FROM musics "
					+ "WHERE artist_id = "+"'"+id+"'";
			rs = stmt.executeQuery(sql);
			
			//참조 확인
			if(rs.next()) {
				sql = "UPDATE musics "
						+ "SET artist_id = NULL "
						+ "WHERE artist_id = "+"'"+id+"'";
				stmt.executeUpdate(sql);
			}
			rs.close();
			stmt.close();
			
			stmt = conn.createStatement();
			sql = "DELETE FROM artists "
					+ "WHERE artist_id = "+"'"+id+"'";
					
			flag = stmt.executeUpdate(sql);
			if(flag == 0) 
				System.out.println("It does not exist.\n");
			
			else
				System.out.println("Success!\n");
			
			stmt.close();
		}catch(SQLException se){
			se.printStackTrace();
		}
	}
	
	private static void artist_modification() {
		String id, name, division, agency, sql;
		ResultSet rs = null;
		int cmd;
		
		System.out.println("Input artist ID to modify\n");
		System.out.print("ID: ");
		id = scanner.next();
		System.out.print("\n");
		
		try {
			stmt = conn.createStatement();
			sql = "SELECT * "
					+ "FROM artists "
					+ "WHERE artist_id = "+"'"+id+"'";
			rs = stmt.executeQuery(sql);
			
			//ID존재 확인
			if(!rs.next()) {
				System.out.println("It does not exist.\n");
				rs.close();
			    stmt.close();
				return;
			}
			else {
				stmt.close();
				
				while(true) {
					System.out.println("***** ["+id+"] modification menu *****");
					System.out.println("0. Return to previous menu");
					System.out.println("1. Change name");
					System.out.println("2. Change division");
					System.out.println("3. Change agency");
					System.out.print("Input: ");
					try {
						cmd = scanner.nextInt();
						System.out.print("\n");
					}catch(Exception e){
						cmd = -1;
						scanner.nextLine();
					}
					if(cmd == 0)
						break;
					else if(cmd == 1) {
						//Name입력
						System.out.println("Input new name");
						while(true) {
							System.out.print("Name: ");
							name = scanner.next();
							// artist_name가 Varchar(16) 이므로 문자열 길이 확인 
							if(name.length() > 16) {
								System.out.println("Maximum is 16 characters\n");
								continue;
							}
							else
								break;
						}
						stmt = conn.createStatement();
						sql = "UPDATE artists "
								+ "SET artist_name = "+"'"+name+"' "
								+ "WHERE artist_id = "+"'"+id+"'";
						stmt.executeUpdate(sql);
						stmt.close();
						System.out.println("Success!\n");
					}
					else if(cmd == 2){
						//division 입력
						System.out.println("Input new division");
						while(true) {
							System.out.print("Division: ");
							division = scanner.next();
							if(!division.equals("S") && !division.equals("G")) {
								System.out.println("You need to input 1 character (Solo:'S' or Group:'G')\n");
								continue;
							}
							else {
								break;
							}
						}
						stmt = conn.createStatement();
						sql = "UPDATE artists "
								+ "SET division = "+"'"+division+"' "
								+ "WHERE artist_id = "+"'"+id+"'";
						stmt.executeUpdate(sql);
						stmt.close();
						System.out.println("Success!\n");
					}
					else if(cmd == 3) {
						//Name입력
						System.out.println("Input new agency");
						while(true) {
							System.out.print("Agency: ");
							agency = scanner.next();
							// artist_name가 Varchar(16) 이므로 문자열 길이 확인 
							if(agency.length() > 16) {
								System.out.println("Maximum is 16 characters\n");
								continue;
							}
							else
								break;
						}
						stmt = conn.createStatement();
						sql = "UPDATE artists "
								+ "SET agency = "+"'"+agency+"' "
								+ "WHERE artist_id = "+"'"+id+"'";
						stmt.executeUpdate(sql);
						stmt.close();
						System.out.println("Success!\n");
					}
					else {
						System.out.println("Unknown commend\n");
						continue;
					}
				}
			}
		}catch(SQLException se){
			se.printStackTrace();
		}finally{
		      try{
			         if(stmt!=null)
			            stmt.close();
			      }catch(SQLException se2){
			    	  se2.printStackTrace();
			      }
			      try{
			         if(rs!=null)
			            rs.close();
			      }catch(SQLException se){
			         se.printStackTrace();
			      }
			}
	}
	
	private static void artist_search() {
		String id, name, agency, sql;
		ResultSet rs = null;
		int cmd;
		
		try {
			while(true) {
				System.out.println("***** Search Menu *****");
				System.out.println("0. Return to previous menu");
				System.out.println("1. All items");
				System.out.println("2. ID search");
				System.out.println("3. Name search");
				System.out.println("4. Solo/Group search");
				System.out.println("5. Agency search");
				System.out.print("Input: ");
				try {
					cmd = scanner.nextInt();
					System.out.print("\n");
				}catch(Exception e){
					cmd = -1;
					scanner.nextLine();
				}
				
				if(cmd == 0)
					break;
				else if(cmd == 1) {
					stmt = conn.createStatement();
					sql = "SELECT * "
							+ "FROM artists";
					rs = stmt.executeQuery(sql);
				}
				else if(cmd == 2){
					System.out.print("ID: ");
					id = scanner.next();
					stmt = conn.createStatement();
					sql = "SELECT * "
							+ "FROM artists "
							+ "WHERE artist_id = "+"'"+id+"'";
					rs = stmt.executeQuery(sql);
				}
				else if(cmd == 3) {
					System.out.print("Name: ");
					name = scanner.next();
					//artist_name에 입력 받은 name가 포함되어 있으면 hit
					stmt = conn.createStatement();
					sql = "SELECT * "
							+ "FROM artists "
							+ "WHERE artist_name LIKE "+"'%"+name+"%'";
					rs = stmt.executeQuery(sql);
				}
				else if(cmd == 4) {
					System.out.println("1. Solo search");
					System.out.println("2. Group search");
					System.out.print("Input: ");
					try {
						cmd = scanner.nextInt();
						System.out.print("\n");
					}catch(Exception e){
						cmd = -1;
						scanner.nextLine();
					}
					if(cmd == 1) {
						stmt = conn.createStatement();
						sql = "SELECT * "
								+ "FROM artists "
								+ "WHERE division = 'S'";
						rs = stmt.executeQuery(sql);
					}
					else if(cmd == 2) {
						stmt = conn.createStatement();
						sql = "SELECT * "
								+ "FROM artists "
								+ "WHERE division = 'G'";
						rs = stmt.executeQuery(sql);
					}
					else {
						System.out.println("Unknown commend\n");
						continue;
					}
				}
				else if(cmd == 5) {
					System.out.print("Agency: ");
					agency = scanner.next();
					//agency에 입력 받은 agency가 포함되어 있으면 hit
					stmt = conn.createStatement();
					sql = "SELECT * "
							+ "FROM artists "
							+ "WHERE agency LIKE "+"'%"+agency+"%'";
					rs = stmt.executeQuery(sql);
				}
				else {
					System.out.println("Unknown commend\n");
					continue;
				}
				
				String[] attr = {"artist_id", "artist_name", "division", "agency"};
				showRS(attr, 4, rs, "artists");
				rs.close();
				stmt.close();
			}
			
		}catch(SQLException se){
			se.printStackTrace();
		}finally{
		      try{
		         if(stmt!=null)
		            stmt.close();
		      }catch(SQLException se2){
		    	  se2.printStackTrace();
		      }
		      try{
		         if(rs!=null)
		            rs.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }
		}
	}
	
	
	/************ Composer Info Mangemenet Function **********/
	
	
	private static void comp_registration() {
		String id, name, agency, sql;
		ResultSet rs = null;
		
		System.out.println("< Composer Registration >");
		
		try {
			//comp_id 발행 
			stmt = conn.createStatement();
			sql = "SELECT id_no "
					+ "FROM id_mng "
					+ "WHERE id_name = 'comp_id'";
			rs = stmt.executeQuery(sql);
			rs.next();
			id = rs.getString(1);
			rs.close();
			stmt.close();
		
			//comp_id 갱신
			stmt = conn.createStatement();
			sql = "UPDATE id_mng "
					+ "SET id_no = id_no + 1 "
					+ "WHERE id_name = 'comp_id'";
			stmt.executeUpdate(sql);
			stmt.close();
			
			//Name입력
			System.out.println("--- Step of name input ---");
			while(true) {
				System.out.print("Name(essential): ");
				name = scanner.next();
				// comp_name가 Varchar(16) 이므로 문자열 길이 확인 
				if(name.length() > 16) {
					System.out.println("Maximum is 16 characters\n");
					continue;
				}
				else
					break;
			}
			System.out.print("\n");
			
			//입력 버퍼 플러쉬
			scanner.nextLine();
			
			//agency입력
			System.out.println("--- Step of agency input ---");
			while(true) {
				System.out.print("Agency(press enter to skip): ");
				agency = scanner.nextLine();
				//skip
				if(agency.equals("")){
					System.out.println("skip!\n");
					agency = null;
					break;
				}
				//agency가 varchar(16) 이므로 문자열 길이 확인 
				if(agency.length() > 16) {
					System.out.println("Maximum is 16 characters\n");
					continue;
				}
				else
					break;
			}
			System.out.print("\n");
			
			//Insert
			stmt = conn.createStatement();
			sql = "INSERT INTO composers(comp_id, comp_name, agency) "
					+ "values("+"'"+id+"',"+"'"+name+"',"
					+(agency == null ? "NULL": "'"+agency+"'") +")";
					
			stmt.executeUpdate(sql);
			stmt.close();
			
			System.out.println("Success!\n");
			
		}catch(SQLException se){
			se.printStackTrace();
		}
	}
	
	private static void comp_deletion() {
		String id, sql;
		int flag;
		ResultSet rs = null;
		
		System.out.println("Input comp ID to delete ");
		System.out.print("ID: ");
		id = scanner.next();
		
		try {
			stmt = conn.createStatement();
			sql = "SELECT* FROM musics "
					+ "WHERE comp_id = "+"'"+id+"'";
			rs = stmt.executeQuery(sql);
			
			//참조 확인
			if(rs.next()) {
				sql = "UPDATE musics "
						+ "SET comp_id = NULL "
						+ "WHERE comp_id = "+"'"+id+"'";
				stmt.executeUpdate(sql);
			}
			rs.close();
			stmt.close();
			
			stmt = conn.createStatement();
			sql = "DELETE FROM composers "
					+ "WHERE comp_id = "+"'"+id+"'";
					
			flag = stmt.executeUpdate(sql);
			if(flag == 0) 
				System.out.println("It does not exist.\n");
			
			else
				System.out.println("Success!\n");
			
			stmt.close();
		}catch(SQLException se){
			se.printStackTrace();
		}
	}
	
	private static void comp_modification() {
		String id, name, agency, sql;
		ResultSet rs = null;
		int cmd;
		
		System.out.println("Input comp ID to modify\n");
		System.out.print("ID: ");
		id = scanner.next();
		System.out.print("\n");
		
		try {
			stmt = conn.createStatement();
			sql = "SELECT * "
					+ "FROM composers "
					+ "WHERE comp_id = "+"'"+id+"'";
			rs = stmt.executeQuery(sql);
			
			//ID존재 확인
			if(!rs.next()) {
				System.out.println("It does not exist.\n");
				rs.close();
			    stmt.close();
				return;
			}
			else {
				stmt.close();
				
				while(true) {
					System.out.println("***** ["+id+"] modification menu *****");
					System.out.println("0. Return to previous menu");
					System.out.println("1. Change name");
					System.out.println("2. Change agency");
					System.out.print("Input: ");
					try {
						cmd = scanner.nextInt();
						System.out.print("\n");
					}catch(Exception e){
						cmd = -1;
						scanner.nextLine();
					}
					if(cmd == 0)
						break;
					else if(cmd == 1) {
						//Name입력
						System.out.println("Input new name");
						while(true) {
							System.out.print("Name: ");
							name = scanner.next();
							// comp_name가 Varchar(16) 이므로 문자열 길이 확인 
							if(name.length() > 16) {
								System.out.println("Maximum is 16 characters\n");
								continue;
							}
							else
								break;
						}
						stmt = conn.createStatement();
						sql = "UPDATE composers "
								+ "SET comp_name = "+"'"+name+"' "
								+ "WHERE comp_id = "+"'"+id+"'";
						stmt.executeUpdate(sql);
						stmt.close();
						System.out.println("Success!\n");
					}
					else if(cmd == 2) {
						//Agency입력
						System.out.println("Input new agency");
						while(true) {
							System.out.print("Agency: ");
							agency = scanner.next();
							// agency가 Varchar(16) 이므로 문자열 길이 확인 
							if(agency.length() > 16) {
								System.out.println("Maximum is 16 characters\n");
								continue;
							}
							else
								break;
						}
						stmt = conn.createStatement();
						sql = "UPDATE composers "
								+ "SET agency = "+"'"+agency+"' "
								+ "WHERE comp_id = "+"'"+id+"'";
						stmt.executeUpdate(sql);
						stmt.close();
						System.out.println("Success!\n");
					}
					else {
						System.out.println("Unknown commend\n");
						continue;
					}
				}
			}
		}catch(SQLException se){
			se.printStackTrace();
		}finally{
		      try{
			         if(stmt!=null)
			            stmt.close();
			      }catch(SQLException se2){
			    	  se2.printStackTrace();
			      }
			      try{
			         if(rs!=null)
			            rs.close();
			      }catch(SQLException se){
			         se.printStackTrace();
			      }
			}
	}
	
	private static void comp_search() {
		String id, name, agency, sql;
		ResultSet rs = null;
		int cmd;
		
		try {
			while(true) {
				System.out.println("***** Search Menu *****");
				System.out.println("0. Return to previous menu");
				System.out.println("1. All items");
				System.out.println("2. ID search");
				System.out.println("3. Name search");
				System.out.println("4. Agency search");
				System.out.print("Input: ");
				try {
					cmd = scanner.nextInt();
					System.out.print("\n");
				}catch(Exception e){
					cmd = -1;
					scanner.nextLine();
				}
				
				if(cmd == 0)
					break;
				else if(cmd == 1) {
					stmt = conn.createStatement();
					sql = "SELECT * "
							+ "FROM composers";
					rs = stmt.executeQuery(sql);
				}
				else if(cmd == 2){
					System.out.print("ID: ");
					id = scanner.next();
					stmt = conn.createStatement();
					sql = "SELECT * "
							+ "FROM composers "
							+ "WHERE comp_id = "+"'"+id+"'";
					rs = stmt.executeQuery(sql);
				}
				else if(cmd == 3) {
					System.out.print("Name: ");
					name = scanner.next();
					//comp_name에 입력 받은 name가 포함되어 있으면 hit
					stmt = conn.createStatement();
					sql = "SELECT * "
							+ "FROM composers "
							+ "WHERE comp_name LIKE "+"'%"+name+"%'";
					rs = stmt.executeQuery(sql);
				}
				else if(cmd == 4) {
					System.out.print("Agency: ");
					agency = scanner.next();
					//agency에 입력 받은 agency가 포함되어 있으면 hit
					stmt = conn.createStatement();
					sql = "SELECT * "
							+ "FROM composers "
							+ "WHERE agency LIKE "+"'%"+agency+"%'";
					rs = stmt.executeQuery(sql);
				}
				else {
					System.out.println("Unknown commend\n");
					continue;
				}
				
				String[] attr = {"comp_id", "comp_name", "agency"};
				showRS(attr, 3, rs, "composers");
				rs.close();
				stmt.close();
			}
		}catch(SQLException se){
			se.printStackTrace();
		}finally{
		      try{
		         if(stmt!=null)
		            stmt.close();
		      }catch(SQLException se2){
		    	  se2.printStackTrace();
		      }
		      try{
		         if(rs!=null)
		            rs.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }
		}
	}
	
	
	/************ Songwriter Info Mangemenet Function **********/
	
	
	private static void swriter_registration() {
		String id, name, agency, sql;
		ResultSet rs = null;
		
		System.out.println("< Songwriter Registration >");
		
		try {
			//swriter_id 발행 
			stmt = conn.createStatement();
			sql = "SELECT id_no "
					+ "FROM id_mng "
					+ "WHERE id_name = 'swriter_id'";
			rs = stmt.executeQuery(sql);
			rs.next();
			id = rs.getString(1);
			rs.close();
			stmt.close();
		
			//swriter_id 갱신
			stmt = conn.createStatement();
			sql = "UPDATE id_mng "
					+ "SET id_no = id_no + 1 "
					+ "WHERE id_name = 'swriter_id'";
			stmt.executeUpdate(sql);
			stmt.close();
			
			//Name입력
			System.out.println("--- Step of name input ---");
			while(true) {
				System.out.print("Name(essential): ");
				name = scanner.next();
				// swriter_name가 Varchar(16) 이므로 문자열 길이 확인 
				if(name.length() > 16) {
					System.out.println("Maximum is 16 characters\n");
					continue;
				}
				else
					break;
			}
			System.out.print("\n");
			
			//입력 버퍼 플러쉬
			scanner.nextLine();
			
			//agency입력
			System.out.println("--- Step of agency input ---");
			while(true) {
				System.out.print("Agency(press enter to skip): ");
				agency = scanner.nextLine();
				//skip
				if(agency.equals("")){
					System.out.println("skip!\n");
					agency = null;
					break;
				}
				//agency가 varchar(16) 이므로 문자열 길이 확인 
				if(agency.length() > 16) {
					System.out.println("Maximum is 16 characters\n");
					continue;
				}
				else
					break;
			}
			System.out.print("\n");
			
			//Insert
			stmt = conn.createStatement();
			sql = "INSERT INTO songwriters(swriter_id, swriter_name, agency) "
					+ "values("+"'"+id+"',"+"'"+name+"',"
					+(agency == null ? "NULL": "'"+agency+"'") +")";
			
			stmt.executeUpdate(sql);
			stmt.close();
			
			System.out.println("Success!\n");
			
		}catch(SQLException se){
			se.printStackTrace();
		}
	}
	
	private static void swriter_deletion() {
		String id, sql;
		int flag;
		ResultSet rs = null;
		
		System.out.println("Input swriter ID to delete ");
		System.out.print("ID: ");
		id = scanner.next();
		
		try {
			stmt = conn.createStatement();
			sql = "SELECT* FROM musics "
					+ "WHERE swriter_id = "+"'"+id+"'";
			rs = stmt.executeQuery(sql);
			
			//참조 확인
			if(rs.next()) {
				sql = "UPDATE musics "
						+ "SET swriter_id = NULL "
						+ "WHERE swriter_id = "+"'"+id+"'";
				stmt.executeUpdate(sql);
			}
			rs.close();
			stmt.close();
			
			stmt = conn.createStatement();
			sql = "DELETE FROM songwriters "
					+ "WHERE swriter_id = "+"'"+id+"'";
					
			flag = stmt.executeUpdate(sql);
			if(flag == 0) 
				System.out.println("It does not exist.\n");
			
			else
				System.out.println("Success!\n");
			
			stmt.close();
		}catch(SQLException se){
			se.printStackTrace();
		}
	}
	
	private static void swriter_modification() {
		String id, name, agency, sql;
		ResultSet rs = null;
		int cmd;
		
		System.out.println("Input swriter ID to modify\n");
		System.out.print("ID: ");
		id = scanner.next();
		System.out.print("\n");
		
		try {
			stmt = conn.createStatement();
			sql = "SELECT * "
					+ "FROM songwriters "
					+ "WHERE swriter_id = "+"'"+id+"'";
			rs = stmt.executeQuery(sql);
			
			//ID존재 확인
			if(!rs.next()) {
				System.out.println("It does not exist.\n");
				rs.close();
			    stmt.close();
				return;
			}
			else {
				stmt.close();
				
				while(true) {
					System.out.println("***** ["+id+"] modification menu *****");
					System.out.println("0. Return to previous menu");
					System.out.println("1. Change name");
					System.out.println("2. Change agency");
					System.out.print("Input: ");
					try {
						cmd = scanner.nextInt();
						System.out.print("\n");
					}catch(Exception e){
						cmd = -1;
						scanner.nextLine();
					}
					if(cmd == 0)
						break;
					else if(cmd == 1) {
						//Name입력
						System.out.println("Input new name");
						while(true) {
							System.out.print("Name: ");
							name = scanner.next();
							// swriter_name가 Varchar(16) 이므로 문자열 길이 확인 
							if(name.length() > 16) {
								System.out.println("Maximum is 16 characters\n");
								continue;
							}
							else
								break;
						}
						stmt = conn.createStatement();
						sql = "UPDATE songwriters "
								+ "SET swriter_name = "+"'"+name+"' "
								+ "WHERE swriter_id = "+"'"+id+"'";
						stmt.executeUpdate(sql);
						stmt.close();
						System.out.println("Success!\n");
					}
					else if(cmd == 2) {
						//agency입력
						System.out.println("Input new agency");
						while(true) {
							System.out.print("Agency: ");
							agency = scanner.next();
							// agency가 Varchar(16) 이므로 문자열 길이 확인 
							if(agency.length() > 16) {
								System.out.println("Maximum is 16 characters\n");
								continue;
							}
							else
								break;
						}
						stmt = conn.createStatement();
						sql = "UPDATE songwriters "
								+ "SET agency = "+"'"+agency+"' "
								+ "WHERE swriter_id = "+"'"+id+"'";
						stmt.executeUpdate(sql);
						stmt.close();
						System.out.println("Success!\n");
					}
					else {
						System.out.println("Unknown commend\n");
						continue;
					}
				}
			}
		}catch(SQLException se){
			se.printStackTrace();
		}finally{
		      try{
			         if(stmt!=null)
			            stmt.close();
			      }catch(SQLException se2){
			    	  se2.printStackTrace();
			      }
			      try{
			         if(rs!=null)
			            rs.close();
			      }catch(SQLException se){
			         se.printStackTrace();
			      }
			}
	}
	
	private static void swriter_search() {
		String id, name, agency, sql;
		ResultSet rs = null;
		int cmd;
		
		try {
			while(true) {
				System.out.println("***** Search Menu *****");
				System.out.println("0. Return to previous menu");
				System.out.println("1. All items");
				System.out.println("2. ID search");
				System.out.println("3. Name search");
				System.out.println("4. Agency search");
				System.out.print("Input: ");
				try {
					cmd = scanner.nextInt();
					System.out.print("\n");
				}catch(Exception e){
					cmd = -1;
					scanner.nextLine();
				}
				
				if(cmd == 0)
					break;
				else if(cmd == 1) {
					stmt = conn.createStatement();
					sql = "SELECT * "
							+ "FROM songwriters";
					rs = stmt.executeQuery(sql);
				}
				else if(cmd == 2){
					System.out.print("ID: ");
					id = scanner.next();
					stmt = conn.createStatement();
					sql = "SELECT * "
							+ "FROM songwriters "
							+ "WHERE swriter_id = "+"'"+id+"'";
					rs = stmt.executeQuery(sql);
				}
				else if(cmd == 3) {
					System.out.print("Name: ");
					name = scanner.next();
					//swriter_name에 입력 받은 name가 포함되어 있으면 hit
					stmt = conn.createStatement();
					sql = "SELECT * "
							+ "FROM songwriters "
							+ "WHERE swriter_name LIKE "+"'%"+name+"%'";
					rs = stmt.executeQuery(sql);
				}
				else if(cmd == 4) {
					System.out.print("Agency: ");
					agency = scanner.next();
					//agency에 입력 받은 agency가 포함되어 있으면 hit
					stmt = conn.createStatement();
					sql = "SELECT * "
							+ "FROM songwriters "
							+ "WHERE agency LIKE "+"'%"+agency+"%'";
					rs = stmt.executeQuery(sql);
				}
				else {
					System.out.println("Unknown commend\n");
					continue;
				}
				
				String[] attr = {"swriter_id", "swriter_name", "agency"};
				showRS(attr, 3, rs, "songwriters");
				rs.close();
				stmt.close();
			}
		}catch(SQLException se){
			se.printStackTrace();
		}finally{
		      try{
		         if(stmt!=null)
		            stmt.close();
		      }catch(SQLException se2){
		    	  se2.printStackTrace();
		      }
		      try{
		         if(rs!=null)
		            rs.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }
		}
	}


	/************ Album Info Mangemenet Function **********/
	
	
	private static void album_registration() {
		String id, title, agency, rdate, sql;
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		ResultSet rs = null;
		
		System.out.println("< Album Registration >");
		
		try {
			//album_id 발행 
			stmt = conn.createStatement();
			sql = "SELECT id_no "
					+ "FROM id_mng "
					+ "WHERE id_name = 'album_id'";
			rs = stmt.executeQuery(sql);
			rs.next();
			id = rs.getString(1);
			rs.close();
			stmt.close();
		
			//album_id 갱신
			stmt = conn.createStatement();
			sql = "UPDATE id_mng "
					+ "SET id_no = id_no + 1 "
					+ "WHERE id_name = 'album_id'";
			stmt.executeUpdate(sql);
			stmt.close();
			
			//Title입력
			System.out.println("--- Step of title input ---");
			while(true) {
				System.out.print("Title(essential): ");
				title = scanner.next();
				// album_title가 Varchar(16) 이므로 문자열 길이 확인 
				if(title.length() > 16) {
					System.out.println("Maximum is 16 characters\n");
					continue;
				}
				else
					break;
			}
			System.out.print("\n");
			
			//입력 버퍼 플러쉬
			scanner.nextLine();
			
			//agency입력
			System.out.println("--- Step of agency input ---");
			while(true) {
				System.out.print("Agency(press enter to skip): ");
				agency = scanner.nextLine();
				//skip
				if(agency.equals("")){
					System.out.println("skip!\n");
					agency = null;
					break;
				}
				//agency가 varchar(16) 이므로 문자열 길이 확인 
				if(agency.length() > 16) {
					System.out.println("Maximum is 16 characters\n");
					continue;
				}
				else
					break;
			}
			System.out.print("\n");
			
			//rdate입력
			System.out.println("--- Step of Rdate input ---");
			while(true) {
				System.out.print("Rdate(press enter to skip): ");
				rdate = scanner.nextLine();
				//skip
				if(rdate.equals("")) {
					System.out.println("skip!\n");
					rdate = null;
					break;
				}
				//rdate가 date형식 이므로 문자열의 날짜가 유효한지 확인
				try {
					format.setLenient(false);
					format.parse(rdate);
				}catch(ParseException e){
					System.out.println("It is an incorrect date.");
					System.out.println("You need to input like YYYY-MM-DD\n");
					continue;
				}
				break;
			}
			System.out.print("\n");
			
			//Insert
			stmt = conn.createStatement();
			sql = "INSERT INTO albums(album_id, album_title, agency, album_rdate) "
					+ "values("+"'"+id+"',"+"'"+title+"',"
					+(agency == null ? "NULL": "'"+agency+"'") +","
					+(rdate == null ? "NULL": "'"+rdate+"'") +")";
			
			stmt.executeUpdate(sql);
			stmt.close();
			
			System.out.println("Success!\n");
			
		}catch(SQLException se){
			se.printStackTrace();
		}
	}
	
	private static void album_deletion() {
		String id, sql;
		int flag;
		ResultSet rs = null;
		
		System.out.println("Input album ID to delete ");
		System.out.print("ID: ");
		id = scanner.next();
		
		try {
			stmt = conn.createStatement();
			sql = "SELECT* FROM musics "
					+ "WHERE album_id = "+"'"+id+"'";
			rs = stmt.executeQuery(sql);
			
			//참조 확인
			if(rs.next()) {
				sql = "UPDATE musics "
						+ "SET album_id = NULL "
						+ "WHERE album_id = "+"'"+id+"'";
				stmt.executeUpdate(sql);
			}
			rs.close();
			stmt.close();
			
			stmt = conn.createStatement();
			sql = "DELETE FROM albums "
					+ "WHERE album_id = "+"'"+id+"'";
					
			flag = stmt.executeUpdate(sql);
			if(flag == 0) 
				System.out.println("It does not exist.\n");
			
			else
				System.out.println("Success!\n");
			
			stmt.close();
		}catch(SQLException se){
			se.printStackTrace();
		}
	}
	
	private static void album_modification() {
		String id, title, agency, rdate, sql;
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		ResultSet rs = null;
		int cmd;
		
		System.out.println("Input album ID to modify\n");
		System.out.print("ID: ");
		id = scanner.next();
		System.out.print("\n");
		
		try {
			stmt = conn.createStatement();
			sql = "SELECT * "
					+ "FROM albums "
					+ "WHERE album_id = "+"'"+id+"'";
			rs = stmt.executeQuery(sql);
			
			//ID존재 확인
			if(!rs.next()) {
				System.out.println("It does not exist.\n");
				rs.close();
			    stmt.close();
				return;
			}
			else {
				stmt.close();
				
				while(true) {
					System.out.println("***** ["+id+"] modification menu *****");
					System.out.println("0. Return to previous menu");
					System.out.println("1. Change title");
					System.out.println("2. Change agency");
					System.out.println("3. Change rdate");
					System.out.print("Input: ");
					try {
						cmd = scanner.nextInt();
						System.out.print("\n");
					}catch(Exception e){
						cmd = -1;
						scanner.nextLine();
					}
					if(cmd == 0)
						break;
					else if(cmd == 1) {
						//Title입력
						System.out.println("Input new title");
						while(true) {
							System.out.print("Title: ");
							title = scanner.next();
							// album_title가 Varchar(16) 이므로 문자열 길이 확인 
							if(title.length() > 16) {
								System.out.println("Maximum is 16 characters\n");
								continue;
							}
							else
								break;
						}
						stmt = conn.createStatement();
						sql = "UPDATE albums "
								+ "SET album_title = "+"'"+title+"' "
								+ "WHERE album_id = "+"'"+id+"'";
						stmt.executeUpdate(sql);
						stmt.close();
						System.out.println("Success!\n");
					}
					else if(cmd == 2) {
						//agency입력
						System.out.println("Input new agency");
						while(true) {
							System.out.print("Agency: ");
							agency = scanner.next();
							// agency가 Varchar(16) 이므로 문자열 길이 확인 
							if(agency.length() > 16) {
								System.out.println("Maximum is 16 characters\n");
								continue;
							}
							else
								break;
						}
						stmt = conn.createStatement();
						sql = "UPDATE albums "
								+ "SET agency = "+"'"+agency+"' "
								+ "WHERE album_id = "+"'"+id+"'";
						stmt.executeUpdate(sql);
						stmt.close();
						System.out.println("Success!\n");
					}
					else if(cmd == 3){
						//division 입력
						System.out.println("Input new rdate");
						while(true) {
							System.out.print("Rdate: ");
							rdate = scanner.next();
							//rdate가 date형식 이므로 문자열에 날짜가 유효한지 확인
							try {
								format.setLenient(false);
								format.parse(rdate);
							}catch(ParseException e){
								System.out.println("It is an incorrect date.");
								System.out.println("You need to input like YYYY-MM-DD\n");
								continue;
							}
							break;
						}
						stmt = conn.createStatement();
						sql = "UPDATE albums "
								+ "SET album_rdate = "+"'"+rdate+"' "
								+ "WHERE album_id = "+"'"+id+"'";
						stmt.executeUpdate(sql);
						stmt.close();
						System.out.println("Success!\n");
					}
					else {
						System.out.println("Unknown commend\n");
						continue;
					}
				}
			}
		}catch(SQLException se){
			se.printStackTrace();
		}finally{
		      try{
			         if(stmt!=null)
			            stmt.close();
			      }catch(SQLException se2){
			    	  se2.printStackTrace();
			      }
			      try{
			         if(rs!=null)
			            rs.close();
			      }catch(SQLException se){
			         se.printStackTrace();
			      }
			}
	}
	
	private static void album_search() {
		String id, title, agency, rdate, sql;
		ResultSet rs = null;
		int cmd;
		
		try {
			while(true) {
				System.out.println("***** Search Menu *****");
				System.out.println("0. Return to previous menu");
				System.out.println("1. All items");
				System.out.println("2. ID search");
				System.out.println("3. Title search");
				System.out.println("4. Agency search");
				System.out.println("5. Rdate search");
				System.out.print("Input: ");
				try {
					cmd = scanner.nextInt();
					System.out.print("\n");
				}catch(Exception e){
					cmd = -1;
					scanner.nextLine();
				}
				
				if(cmd == 0)
					break;
				else if(cmd == 1) {
					stmt = conn.createStatement();
					sql = "SELECT * "
							+ "FROM albums";
					rs = stmt.executeQuery(sql);
				}
				else if(cmd == 2){
					System.out.print("ID: ");
					id = scanner.next();
					stmt = conn.createStatement();
					sql = "SELECT * "
							+ "FROM albums "
							+ "WHERE album_id = "+"'"+id+"'";
					rs = stmt.executeQuery(sql);
				}
				else if(cmd == 3) {
					System.out.print("Title: ");
					title = scanner.next();
					//album_title에 입력 받은 title가 포함되어 있으면 hit
					stmt = conn.createStatement();
					sql = "SELECT * "
							+ "FROM albums "
							+ "WHERE album_title LIKE "+"'%"+title+"%'";
					rs = stmt.executeQuery(sql);
				}
				else if(cmd == 4) {
					System.out.print("Agency: ");
					agency = scanner.next();
					//agency에 입력 받은 agency가 포함되어 있으면 hit
					stmt = conn.createStatement();
					sql = "SELECT * "
							+ "FROM albums "
							+ "WHERE agency LIKE "+"'%"+agency+"%'";
					rs = stmt.executeQuery(sql);
				}
				else if(cmd == 5) {
					System.out.println("1. YYYY-MM-DD search");
					System.out.println("2. YYYY search");
					System.out.println("3. MM search");
					System.out.print("Input: ");
					try {
						cmd = scanner.nextInt();
						System.out.print("\n");
					}catch(Exception e){
						cmd = -1;
						scanner.nextLine();
					}
					if(cmd == 1) {
						System.out.print("Rdate: ");
						rdate = scanner.next();
						stmt = conn.createStatement();
						sql = "SELECT * "
								+ "FROM albums "
								+ "WHERE album_rdate = "+"'"+rdate+"'";
						rs = stmt.executeQuery(sql);
					}
					else if(cmd == 2) {
						System.out.print("Ryear: ");
						rdate = scanner.next();
						stmt = conn.createStatement();
						sql = "SELECT * "
								+ "FROM albums "
								+ "WHERE album_rdate LIKE "+"'"+rdate+"%'";
						rs = stmt.executeQuery(sql);
					}
					else if(cmd == 3) {
						System.out.print("Rmonth: ");
						rdate = scanner.next();
						stmt = conn.createStatement();
						sql = "SELECT * "
								+ "FROM users "
								+ "WHERE album_rdate LIKE "+"'____-"+rdate+"-__'"; //"1995-10-14 ____-10-__"
						rs = stmt.executeQuery(sql);
					}
					else {
						System.out.println("Unknown commend\n");
						continue;
					}
				}
				else {
					System.out.println("Unknown commend\n");
					continue;
				}
				
				String[] attr = {"album_id", "album_title", "agency", "album_rdate"};
				showRS(attr, 4, rs, "albums");
				rs.close();
				stmt.close();
			}
		}catch(SQLException se){
			se.printStackTrace();
		}finally{
		      try{
		         if(stmt!=null)
		            stmt.close();
		      }catch(SQLException se2){
		    	  se2.printStackTrace();
		      }
		      try{
		         if(rs!=null)
		            rs.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }
		}
	}
	
	
	/************ Music Info Mangemenet Function **********/
	
	
	private static void music_registration() {
		String id, title, a_id, c_id, s_id, al_id, genre, sql;
		int cmd;
		ResultSet rs = null;
		
		//System.out.println("Input info to register ");
		System.out.println("< Music Registration >");
		
		try {
			//music_id 발행 
			stmt = conn.createStatement();
			sql = "SELECT id_no "
					+ "FROM id_mng "
					+ "WHERE id_name = 'music_id'";
			rs = stmt.executeQuery(sql);
			rs.next();
			id = rs.getString(1);
			rs.close();
			stmt.close();
		
			//music_id 갱신
			stmt = conn.createStatement();
			sql = "UPDATE id_mng "
					+ "SET id_no = id_no + 1 "
					+ "WHERE id_name = 'music_id'";
			stmt.executeUpdate(sql);
			stmt.close();
			
			//Title입력
			System.out.println("--- Step of title input ---");
			while(true) {
				System.out.print("Title(essential): ");
				title = scanner.next();
				// artist_name가 Varchar(16) 이므로 문자열 길이 확인 
				if(title.length() > 16) {
					System.out.println("Maximum is 16 characters\n");
					continue;
				}
				else
					break;
			}
			System.out.print("\n");
			
			//artist_id입력
			System.out.println("--- Step of artist ID input ---");
			while(true) {
				System.out.println("1. Skip this step");
				System.out.println("2. Input artist ID");
				System.out.println("3. Search artist");
				System.out.print("Input: ");
				try {
					cmd = scanner.nextInt();
					System.out.print("\n");
				}catch(Exception e){
					cmd = -1;
					scanner.nextLine();
				}
				
				 if(cmd == 1) {
					a_id = null;
					System.out.println("skip!\n");
					break;
				}
				else if(cmd == 2){
					System.out.print("ID: ");
					a_id = scanner.next();
					stmt = conn.createStatement();
					sql = "SELECT * "
							+ "FROM artists "
							+ "WHERE artist_id = "+"'"+a_id+"'";
					rs = stmt.executeQuery(sql);
					//ID존재 확인
					if(!rs.next()) {
						System.out.println("It does not exist.\n");
						rs.close();
					    stmt.close();
						continue;
					}
					else {
						rs.close();
					    stmt.close();
						break;
					}
				}			
				else if(cmd == 3) {
					artist_search();
				}
				
				else {
					System.out.println("Unknown commend\n");
					continue;
				}
			}
			System.out.print("\n");
			
			//comp_id입력
			System.out.println("--- Step of composer ID input ---");
			while(true) {
				System.out.println("1. Skip this step");
				System.out.println("2. Input comp ID");
				System.out.println("3. Search composer");
				System.out.print("Input: ");
				try {
					cmd = scanner.nextInt();
					System.out.print("\n");
				}catch(Exception e){
					cmd = -1;
					scanner.nextLine();
				}
				
				 if(cmd == 1) {
					c_id = null;
					System.out.println("skip!\n");
					break;
				}
				else if(cmd == 2){
					System.out.print("ID: ");
					c_id = scanner.next();
					stmt = conn.createStatement();
					sql = "SELECT * "
							+ "FROM composers "
							+ "WHERE comp_id = "+"'"+c_id+"'";
					rs = stmt.executeQuery(sql);
					//ID존재 확인
					if(!rs.next()) {
						System.out.println("It does not exist.\n");
						rs.close();
					    stmt.close();
						continue;
					}
					else {
						rs.close();
					    stmt.close();
						break;
					}
				}			
				else if(cmd == 3) {
					comp_search();
				}
				
				else {
					System.out.println("Unknown commend\n");
					continue;
				}
			}
			System.out.print("\n");
			
			//swriter_id입력
			System.out.println("--- Step of songwriter ID input ---");
			while(true) {
				System.out.println("1. Skip this step");
				System.out.println("2. Input swriter ID");
				System.out.println("3. Search songwriter");
				System.out.print("Input: ");
				try {
					cmd = scanner.nextInt();
					System.out.print("\n");
				}catch(Exception e){
					cmd = -1;
					scanner.nextLine();
				}
				
				 if(cmd == 1) {
					s_id = null;
					System.out.println("skip!\n");
					break;
				}
				else if(cmd == 2){
					System.out.print("ID: ");
					s_id = scanner.next();
					stmt = conn.createStatement();
					sql = "SELECT * "
							+ "FROM songwriters "
							+ "WHERE swriter_id = "+"'"+s_id+"'";
					rs = stmt.executeQuery(sql);
					//ID존재 확인
					if(!rs.next()) {
						System.out.println("It does not exist.\n");
						rs.close();
					    stmt.close();
						continue;
					}
					else {
						rs.close();
					    stmt.close();
						break;
					}
				}			
				else if(cmd == 3) {
					swriter_search();
				}
				
				else {
					System.out.println("Unknown commend\n");
					continue;
				}
			}
			System.out.print("\n");
			
			//album_id입력
			System.out.println("--- Step of album ID input ---");
			while(true) {
				System.out.println("1. Skip this step");
				System.out.println("2. Input album ID");
				System.out.println("3. Search album");
				System.out.print("Input: ");
				try {
					cmd = scanner.nextInt();
					System.out.print("\n");
				}catch(Exception e){
					cmd = -1;
					scanner.nextLine();
				}
				
				 if(cmd == 1) {
					al_id = null;
					System.out.println("skip!\n");
					break;
				}
				else if(cmd == 2){
					System.out.print("ID: ");
					al_id = scanner.next();
					stmt = conn.createStatement();
					sql = "SELECT * "
							+ "FROM albums "
							+ "WHERE album_id = "+"'"+al_id+"'";
					rs = stmt.executeQuery(sql);
					//ID존재 확인
					if(!rs.next()) {
						System.out.println("It does not exist.\n");
						rs.close();
					    stmt.close();
						continue;
					}
					else {
						rs.close();
					    stmt.close();
						break;
					}
				}			
				else if(cmd == 3) {
					album_search();
				}
				
				else {
					System.out.println("Unknown commend\n");
					continue;
				}
			}
			System.out.print("\n");
			
			//입력 버퍼 플러쉬
			scanner.nextLine();
			
			//genre입력
			System.out.println("--- Step of genre input ---");
			while(true) {
				System.out.print("Genre(press enter to skip): ");
				genre = scanner.nextLine();
				//skip
				if(genre.equals("")){
					System.out.println("skip!\n");
					genre = null;
					break;
				}
				//agency가 varchar(16) 이므로 문자열 길이 확인 
				if(genre.length() > 16) {
					System.out.println("Maximum is 16 characters\n");
					continue;
				}
				else
					break;

			}
			System.out.print("\n");
			
			//Insert
			stmt = conn.createStatement();
			sql = "INSERT INTO musics(music_id, music_title, artist_id, comp_id, swriter_id, album_id, genre) "
					+ "values("+"'"+id+"',"+"'"+title+"',"
					+(a_id == null ? "NULL": "'"+a_id+"'") +","
					+(c_id == null ? "NULL": "'"+c_id+"'") +","
					+(s_id == null ? "NULL": "'"+s_id+"'") +","
					+(al_id == null ? "NULL": "'"+al_id+"'") +","
					+(genre == null ? "NULL": "'"+genre+"'") +")";
			
			stmt.executeUpdate(sql);
			stmt.close();
			
			System.out.println("Success!\n");
			
		}catch(SQLException se){
			se.printStackTrace();
		}
	}
	
	private static void music_deletion() {
		String id, sql;
		int flag;
		ResultSet rs = null;
		
		System.out.println("Input music ID to delete ");
		System.out.print("ID: ");
		id = scanner.next();
		
		try {
			stmt = conn.createStatement();
			sql = "SELECT* FROM playlist_registrations "
					+ "WHERE music_id = "+"'"+id+"'";
			rs = stmt.executeQuery(sql);
			
			//참조 확인
			if(rs.next()) {
				System.out.println("Cannot delete : a foreign key constraint fails (playlists_registrations table)\n");
				rs.close();
				stmt.close();
				return;
			}
			rs.close();
			stmt.close();
			
			stmt = conn.createStatement();
			sql = "DELETE FROM musics "
					+ "WHERE music_id = "+"'"+id+"'";
					
			flag = stmt.executeUpdate(sql);
			if(flag == 0) 
				System.out.println("It does not exist.\n");
			
			else
				System.out.println("Success!\n");
			
			stmt.close();
		}catch(SQLException se){
			se.printStackTrace();
		}
	}
	
	private static void music_modification() {
		String id, title, a_id, c_id, s_id, al_id, genre, sql;
		ResultSet rs = null;
		int cmd;
		
		System.out.println("Input music ID to modify\n");
		System.out.print("ID: ");
		id = scanner.next();
		System.out.print("\n");
		
		try {
			stmt = conn.createStatement();
			sql = "SELECT * "
					+ "FROM musics "
					+ "WHERE music_id = "+"'"+id+"'";
			rs = stmt.executeQuery(sql);
			
			//ID존재 확인
			if(!rs.next()) {
				System.out.println("It does not exist.\n");
				rs.close();
			    stmt.close();
				return;
			}
			else {
				stmt.close();
				
				while(true) {
					System.out.println("***** ["+id+"] modification menu *****");
					System.out.println("0. Return to previous menu");
					System.out.println("1. Change title");
					System.out.println("2. Change artist ID");
					System.out.println("3. Change composer ID");
					System.out.println("4. Change songwriter ID");
					System.out.println("5. Change album ID");
					System.out.println("6. Change genre");
					System.out.print("Input: ");
					try {
						cmd = scanner.nextInt();
						System.out.print("\n");
					}catch(Exception e){
						cmd = -1;
						scanner.nextLine();
					}
					if(cmd == 0)
						break;
					else if(cmd == 1) {
						//Title입력
						System.out.println("Input new title");
						while(true) {
							System.out.print("Title: ");
							title = scanner.next();
							// album_title가 Varchar(16) 이므로 문자열 길이 확인 
							if(title.length() > 16) {
								System.out.println("Maximum is 16 characters\n");
								continue;
							}
							else
								break;
						}
						stmt = conn.createStatement();
						sql = "UPDATE musics "
								+ "SET music_title = "+"'"+title+"' "
								+ "WHERE music_id = "+"'"+id+"'";
						stmt.executeUpdate(sql);
						stmt.close();
						System.out.println("Success!\n");
					}
					else if(cmd == 2) {
						//artist_id입력
						System.out.println("Input new artist ID");
						
						System.out.print("ID: ");
						a_id = scanner.next();
						// artist_id가 외래키 이므로 a_id가 참조하는 테이블의 기본 키인지 확인
						stmt = conn.createStatement();
						sql = "SELECT * "
								+ "FROM artists "
								+ "WHERE artist_id = "+"'"+a_id+"'";
						rs = stmt.executeQuery(sql);
						//ID존재 확인
						if(!rs.next()) {
							System.out.println("It does not exist.\n");
							rs.close();
						    stmt.close();
							continue;
						}
					
						rs.close();
						stmt.close();
						
						stmt = conn.createStatement();
						sql = "UPDATE musics "
								+ "SET artist_id = "+"'"+a_id+"' "
								+ "WHERE music_id = "+"'"+id+"'";
						stmt.executeUpdate(sql);
						stmt.close();
						System.out.println("Success!\n");
					}
					else if(cmd == 3){
						//comp_id입력
						System.out.println("Input new composer ID");
						
						System.out.print("ID: ");
						c_id = scanner.next();
						// comp_id가 외래키 이므로 c_id가 참조하는 테이블의 기본 키인지 확인
						stmt = conn.createStatement();
						sql = "SELECT * "
								+ "FROM composers "
								+ "WHERE comp_id = "+"'"+c_id+"'";
						rs = stmt.executeQuery(sql);
						//ID존재 확인
						if(!rs.next()) {
							System.out.println("It does not exist.\n");
							rs.close();
						    stmt.close();
							continue;
						}
						
						rs.close();
						stmt.close();
							
						stmt = conn.createStatement();
						sql = "UPDATE musics "
								+ "SET comp_id = "+"'"+c_id+"' "
								+ "WHERE music_id = "+"'"+id+"'";
						stmt.executeUpdate(sql);
						stmt.close();
						System.out.println("Success!\n");
					}
					else if(cmd == 4) {
						//swriter_id입력
						System.out.println("Input new songwriter ID");
						
						System.out.print("ID: ");
						s_id = scanner.next();
						// swriter_id가 외래키 이므로 s_id가 참조하는 테이블의 기본 키인지 확인
						stmt = conn.createStatement();
						sql = "SELECT * "
								+ "FROM songwriters "
								+ "WHERE swriter_id = "+"'"+s_id+"'";
						rs = stmt.executeQuery(sql);
						//ID존재 확인
						if(!rs.next()) {
							System.out.println("It does not exist.\n");
							rs.close();
						    stmt.close();
							continue;
						}
						
						rs.close();
					    stmt.close();
						
						stmt = conn.createStatement();
						sql = "UPDATE musics "
								+ "SET swriter_id = "+"'"+s_id+"' "
								+ "WHERE music_id = "+"'"+id+"'";
						stmt.executeUpdate(sql);
						stmt.close();
						System.out.println("Success!\n");
					}
					else if(cmd == 5) {
						//album_id입력
						System.out.println("Input new album ID");
						
						System.out.print("ID: ");
						al_id = scanner.next();
						// album_id가 외래키 이므로 al_id가 참조하는 테이블의 기본 키인지 확인
						stmt = conn.createStatement();
						sql = "SELECT * "
								+ "FROM albums "
								+ "WHERE album_id = "+"'"+al_id+"'";
						rs = stmt.executeQuery(sql);
						//ID존재 확인
						if(!rs.next()) {
							System.out.println("It does not exist.\n");
							rs.close();
						    stmt.close();
							continue;
						}
				
						rs.close();
					    stmt.close();			
						
						stmt = conn.createStatement();
						sql = "UPDATE musics "
								+ "SET album_id = "+"'"+al_id+"' "
								+ "WHERE music_id = "+"'"+id+"'";
						stmt.executeUpdate(sql);
						stmt.close();
						System.out.println("Success!\n");
					}
					else if(cmd == 6) {
						//genre입력
						System.out.println("Input new genre");
						while(true) {
							System.out.print("Genre: ");
							genre = scanner.next();
							// genre가 Varchar(16) 이므로 문자열 길이 확인 
							if(genre.length() > 16) {
								System.out.println("Maximum is 16 characters\n");
								continue;
							}
							else
								break;
						}
						stmt = conn.createStatement();
						sql = "UPDATE musics "
								+ "SET genre = "+"'"+genre+"' "
								+ "WHERE music_id = "+"'"+id+"'";
						stmt.executeUpdate(sql);
						stmt.close();
						System.out.println("Success!\n");
					}
					else {
						System.out.println("Unknown commend\n");
						continue;
					}
				}
			}
		}catch(SQLException se){
			se.printStackTrace();
		}finally{
		      try{
			         if(stmt!=null)
			            stmt.close();
			      }catch(SQLException se2){
			    	  se2.printStackTrace();
			      }
			      try{
			         if(rs!=null)
			            rs.close();
			      }catch(SQLException se){
			         se.printStackTrace();
			      }
			}
	}
	
	private static void music_search() {
		String id, title, name, genre, sql;
		ResultSet rs = null;
		int cmd;
		
		try {
			while(true) {
				System.out.println("***** Search Menu *****");
				System.out.println("0. Return to previous menu");
				System.out.println("1. All items");
				System.out.println("2. ID search");
				System.out.println("3. Title search");
				System.out.println("4. Artist search");
				System.out.println("5. Composer search");
				System.out.println("6. Songwriter search");
				System.out.println("7. Album search");
				System.out.println("8. Genre search");
				System.out.print("Input: ");
				try {
					cmd = scanner.nextInt();
					System.out.print("\n");
				}catch(Exception e){
					cmd = -1;
					scanner.nextLine();
				}
				
				if(cmd == 0)
					break;
				else if(cmd == 1) {
					stmt = conn.createStatement();
					sql = "SELECT * FROM musics";
					rs = stmt.executeQuery(sql);
				}
				else if(cmd == 2){
					System.out.print("ID: ");
					id = scanner.next();
					stmt = conn.createStatement();
					sql = "SELECT * FROM musics WHERE music_id = "+"'"+id+"'";
					rs = stmt.executeQuery(sql);
				}
				else if(cmd == 3) {
					System.out.print("Title: ");
					title = scanner.next();
					//music_title에 입력 받은 title가 포함되어 있으면 hit
					stmt = conn.createStatement();
					sql = "SELECT * FROM musics WHERE music_title LIKE "+"'%"+title+"%'";
					rs = stmt.executeQuery(sql);
				}
				else if(cmd == 4) {
					System.out.println("1. Artist ID search");
					System.out.println("2. Artist name search");
					System.out.print("Input: ");
					try {
						cmd = scanner.nextInt();
						System.out.print("\n");
					}catch(Exception e){
						cmd = -1;
						scanner.nextLine();
					}
					if(cmd == 1) {
						System.out.print("ID: ");
						id = scanner.next();
						stmt = conn.createStatement();
						sql = "SELECT * FROM musics WHERE artist_id = "+"'"+id+"'";
						rs = stmt.executeQuery(sql);
					}
					else if(cmd == 2) {
						System.out.print("Name: ");
						name = scanner.next();
						//artist_name에 입력 받은 name가 포함되어 있으면 hit
						stmt = conn.createStatement();
						sql = "SELECT * FROM musics m WHERE EXISTS "
								+ "(SELECT * FROM artists a "
								+ "WHERE m.artist_id = a.artist_id AND a.artist_name LIKE '%"+name+"%')";
						rs = stmt.executeQuery(sql);
					}
					else {
						System.out.println("Unknown commend\n");
						continue;
					}
				}
				else if(cmd == 5) {
					System.out.println("1. Composer ID search");
					System.out.println("2. Composer name search");
					System.out.print("Input: ");
					try {
						cmd = scanner.nextInt();
						System.out.print("\n");
					}catch(Exception e){
						cmd = -1;
						scanner.nextLine();
					}
					if(cmd == 1) {
						System.out.print("ID: ");
						id = scanner.next();
						stmt = conn.createStatement();
						sql = "SELECT * FROM musics WHERE comp_id = "+"'"+id+"'";
						rs = stmt.executeQuery(sql);
					}
					else if(cmd == 2) {
						System.out.print("Name: ");
						name = scanner.next();
						//comp_name에 입력 받은 name가 포함되어 있으면 hit
						stmt = conn.createStatement();
						sql = "SELECT * FROM musics m WHERE EXISTS "
								+ "(SELECT * FROM composers c "
								+ "WHERE m.comp_id = c.comp_id AND c.comp_name LIKE '%"+name+"%')";
						rs = stmt.executeQuery(sql);
					}
					else {
						System.out.println("Unknown commend\n");
						continue;
					}
				}
				else if(cmd == 6) {
					System.out.println("1. Songwriter ID search");
					System.out.println("2. Songwriter name search");
					System.out.print("Input: ");
					try {
						cmd = scanner.nextInt();
						System.out.print("\n");
					}catch(Exception e){
						cmd = -1;
						scanner.nextLine();
					}
					if(cmd == 1) {
						System.out.print("ID: ");
						id = scanner.next();
						stmt = conn.createStatement();
						sql = "SELECT * FROM musics WHERE swriter_id = "+"'"+id+"'";
						rs = stmt.executeQuery(sql);
					}
					else if(cmd == 2) {
						System.out.print("Name: ");
						name = scanner.next();
						//swriter_name에 입력 받은 name가 포함되어 있으면 hit
						stmt = conn.createStatement();
						sql = "SELECT * FROM musics m WHERE EXISTS "
								+ "(SELECT * FROM songwriters s "
								+ "WHERE m.swriter_id = s.swriter_id AND s.swriter_name LIKE '%"+name+"%')";
						rs = stmt.executeQuery(sql);
					}
					else {
						System.out.println("Unknown commend\n");
						continue;
					}
				}
				else if(cmd == 7) {
					System.out.println("1. Album ID search");
					System.out.println("2. Album title search");
					System.out.print("Input: ");
					try {
						cmd = scanner.nextInt();
						System.out.print("\n");
					}catch(Exception e){
						cmd = -1;
						scanner.nextLine();
					}
					if(cmd == 1) {
						System.out.print("ID: ");
						id = scanner.next();
						stmt = conn.createStatement();
						sql = "SELECT * FROM musics WHERE album_id = "+"'"+id+"'";
						rs = stmt.executeQuery(sql);
					}
					else if(cmd == 2) {
						System.out.print("Title: ");
						title = scanner.next();
						//album_title에 입력 받은 title가 포함되어 있으면 hit
						stmt = conn.createStatement();
						sql = "SELECT * FROM musics m WHERE EXISTS "
								+ "(SELECT * FROM albums al "
								+ "WHERE m.album_id = al.album_id AND al.album_title LIKE '%"+title+"%')";
						rs = stmt.executeQuery(sql);
					}
					else {
						System.out.println("Unknown commend\n");
						continue;
					}
				}
				else if(cmd == 8) {
					System.out.print("Genre: ");
					genre = scanner.next();
					stmt = conn.createStatement();
					sql = "SELECT * FROM musics WHERE genre = "+"'"+genre+"'";
					rs = stmt.executeQuery(sql);
				}
				
				else {
					System.out.println("Unknown commend\n");
					continue;
				}
				
				String[] attr = {"music_id", "music_title", "artist_id", "artist_name", "comp_id", "comp_name", "swriter_id", "swriter_name", "album_id", "album_title", "genre"};
				showRS(attr, 11, rs, "musics");
				rs.close();
				stmt.close();
			}
		}catch(SQLException se){
			se.printStackTrace();
		}finally{
		      try{
		         if(stmt!=null)
		            stmt.close();
		      }catch(SQLException se2){
		    	  se2.printStackTrace();
		      }
		      try{
		         if(rs!=null)
		            rs.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }
		}
	}
	
	
	/************ Playlist Info Mangemenet Function **********/
	
	
	private static void plist_creation() {
		String uid, pid,  title, sql;
		ResultSet rs = null;
		
		try {
			//loginLevel에 따라 create할 수 있는 범위가 다르다
			if(loginLevel >= 1) {
				System.out.println("Input user ID to create playlist");
				System.out.print("ID: ");
				uid = scanner.next();
				System.out.print("\n");
				
				stmt = conn.createStatement();
				sql = "SELECT * "
						+ "FROM users "
						+ "WHERE user_id = "+"'"+uid+"'";
				rs = stmt.executeQuery(sql);
				
				//ID존재 확인
				if(!rs.next()) {
					System.out.println("It does not exist.\n");
					rs.close();
				    stmt.close();
					return;
				}
				rs.close();
			    stmt.close();
			}
			else 
				uid = user_id;
			
			//plist_id 발행 
			stmt = conn.createStatement();
			sql = "SELECT id_no "
					+ "FROM id_mng "
					+ "WHERE id_name = 'plist_id'";
			rs = stmt.executeQuery(sql);
			rs.next();
			pid = rs.getString(1);
			rs.close();
			stmt.close();
		
			//plist_id 갱신
			stmt = conn.createStatement();
			sql = "UPDATE id_mng "
					+ "SET id_no = id_no + 1 "
					+ "WHERE id_name = 'plist_id'";
			stmt.executeUpdate(sql);
			stmt.close();
			
			//Title입력
			while(true) {
				System.out.print("Title(essential): ");
				title = scanner.next();
				// plist_title가 Varchar(16) 이므로 문자열 길이 확인 
				if(title.length() > 16) {
					System.out.println("Maximum is 16 characters\n");
					continue;
				}
				else {
					//plist_title 중복 확인 (UNIQUE(user_id, plist_title))
					stmt = conn.createStatement();
					sql = "SELECT * "
							+ "FROM playlists "
							+ "WHERE user_id = '"+uid+"' AND plist_title = '"+title+"'";
					rs = stmt.executeQuery(sql);
					if(rs.next()) {
						System.out.println("It is a invalid title (Duplicate)\n");
						rs.close();
						stmt.close();
						continue;
					}
					rs.close();
					stmt.close();
					break;
				}
			}
			
			//Insert
			stmt = conn.createStatement();
			sql = "INSERT INTO playlists(plist_id, user_id, plist_title) "
					+ "values("+"'"+pid+"',"+"'"+uid+"',"+"'"+title+"')";
			
			stmt.executeUpdate(sql);
			stmt.close();
			
			System.out.println("Success!\n");
			
			
		}catch(SQLException se){
			se.printStackTrace();
		}
		
	}
	
	private static void plist_deletion() {
		String pid, sql;
		int flag;
		ResultSet rs = null;
		
		try {
			System.out.println("Input playlist ID to delete ");
			System.out.print("ID: ");
			pid = scanner.next();
		
			//loginLevel에 따라 delete할 수 있는 범위가 다르다
			if(loginLevel >= 1) {
				//해당 playlist에 등록된 음악 삭제
				stmt = conn.createStatement();
				sql = "DELETE FROM playlist_registrations "
						+ "WHERE plist_id = "+"'"+pid+"'";
				stmt.executeUpdate(sql);
				stmt.close();
				
				//해당 playlist를 삭제
				stmt = conn.createStatement();
				sql = "DELETE FROM playlists "
						+ "WHERE plist_id = "+"'"+pid+"'";
				flag = stmt.executeUpdate(sql);
			}
			else {
				//해당 playlist에 등록된 음악 삭제, 로그인한 user_id의 playlist인지 확인
				stmt = conn.createStatement();
				sql = "SELECT DISTINCT pr.plist_id "
						+ "FROM playlists p, playlist_registrations pr "
						+ "WHERE p.plist_id = pr.plist_id AND p.user_id = '"+user_id+"' AND p.plist_id = '"+pid+"'";
				rs = stmt.executeQuery(sql);
				if(rs.next()) {
					sql = "DELETE FROM playlist_registrations "
							+ "WHERE plist_id = "+"'"+pid+"'";
					stmt.executeUpdate(sql);
				}
				rs.close();
				stmt.close();
				
				//해당 playlist를 삭제
				stmt = conn.createStatement();
				sql = "DELETE FROM playlists "
						+ "WHERE plist_id = "+"'"+pid+"'"+" AND "+"user_id = '"+user_id+"'";
				flag = stmt.executeUpdate(sql);
			}
			
			if(flag == 0) 
				System.out.println("It does not exist.\n");
			
			else
				System.out.println("Success!\n");
			
			stmt.close();
		}catch(SQLException se){
			se.printStackTrace();
		}
	}
	
	private static void plist_modification() {
		String uid, pid,  title, sql;
		ResultSet rs = null;
		
		try {
			System.out.println("Input playlist ID to modify");
			System.out.print("ID: ");
			pid = scanner.next();
			System.out.print("\n");
			
			stmt = conn.createStatement();
			sql = "SELECT * "
					+ "FROM playlists "
					+ "WHERE plist_id = "+"'"+pid+"'";
			rs = stmt.executeQuery(sql);
			
			//ID존재 확인
			if(!rs.next()) {
				System.out.println("It does not exist.\n");
				rs.close();
			    stmt.close();
				return;
			}
			uid = rs.getString("user_id");
			rs.close();
		    stmt.close();
		    
		    //사용자 로그인을 한 유저는 자신의 ID에 있는 playlist만 modify할 수 있따
		    if(loginLevel == 0 && !uid.equals(user_id)) {
		    	 System.out.println("It does not exist.\n");
		    	 return;
		    }
		    
		    //Title입력
			while(true) {
				System.out.println("Input new title");
				System.out.print("Title(essential): ");
				title = scanner.next();
				// plist_title가 Varchar(16) 이므로 문자열 길이 확인 
				if(title.length() > 16) {
					System.out.println("Maximum is 16 characters\n");
					continue;
				}
				else {
					//plist_title 중복 확인 (UNIQUE(user_id, plist_title))
					stmt = conn.createStatement();
					sql = "SELECT * "
							+ "FROM playlists "
							+ "WHERE user_id = '"+uid+"' AND plist_title = '"+title+"'";
					rs = stmt.executeQuery(sql);
					if(rs.next()) {
						System.out.println("It is a invalid title (Duplicate)\n");
						rs.close();
						stmt.close();
						continue;
					}
					rs.close();
					stmt.close();
					break;
				}
			}
			
			
			//Update
			stmt = conn.createStatement();
			sql = "UPDATE playlists SET plist_title = '"+title+"' "+"WHERE plist_id = '"+pid+"' AND user_id = '"+uid+"'";
			
			stmt.executeUpdate(sql);
			stmt.close();
			
			System.out.println("Success!\n");
			
			
		}catch(SQLException se){
			se.printStackTrace();
		}
	
	}
	
	private static void plist_search() {
		String uid, pid, title, sql;
		ResultSet rs = null;
		int cmd;
		
		try {
			while(true) {
				System.out.println("***** Search Menu *****");
				System.out.println("0. Return to previous menu");
				System.out.println("1. All items");
				System.out.println("2. Playlist ID search");
				System.out.println("3. Title search");
				if(loginLevel >= 1)
					System.out.println("4. User ID search");
				System.out.print("Input: ");
				try {
					cmd = scanner.nextInt();
					System.out.print("\n");
				}catch(Exception e){
					cmd = -1;
					scanner.nextLine();
				}
				
				if(cmd == 0)
					break;
				else if(cmd == 1) {
					if(loginLevel >= 1) {
						stmt = conn.createStatement();
						sql = "SELECT * "
								+ "FROM playlists";
						rs = stmt.executeQuery(sql);
					}
					else {
						stmt = conn.createStatement();
						sql = "SELECT * "
								+ "FROM playlists "
								+ "WHERE user_id = '"+user_id+"'";
						rs = stmt.executeQuery(sql);
					}
				}
				else if(cmd == 2){
					System.out.print("ID: ");
					pid = scanner.next();
					if(loginLevel >= 1) {
						stmt = conn.createStatement();
						sql = "SELECT * "
								+ "FROM playlists "
								+ "WHERE plist_id = "+"'"+pid+"'";
						rs = stmt.executeQuery(sql);
					}
					else {
						stmt = conn.createStatement();
						sql = "SELECT * "
								+ "FROM playlists "
								+ "WHERE user_id = '"+user_id+"' AND "+"plist_id = "+"'"+pid+"'";
						rs = stmt.executeQuery(sql);
					}
				}
				else if(cmd == 3) {
					System.out.print("Title: ");
					title = scanner.next();
					if(loginLevel >= 1) {
						stmt = conn.createStatement();
						sql = "SELECT * "
								+ "FROM playlists "
								+ "WHERE plist_title = "+"'"+title+"'";
						rs = stmt.executeQuery(sql);
					}
					else {
						stmt = conn.createStatement();
						sql = "SELECT * "
								+ "FROM playlists "
								+ "WHERE user_id = '"+user_id+"' AND "+"plist_title = "+"'"+title+"'";
						rs = stmt.executeQuery(sql);
					}
				}
				else if(cmd == 4 && loginLevel >= 1){
					System.out.print("ID: ");
					uid = scanner.next();
					stmt = conn.createStatement();
					sql = "SELECT * "
							+ "FROM playlists "
							+ "WHERE user_id = "+"'"+uid+"'";
					rs = stmt.executeQuery(sql);
				}
				else {
					System.out.println("Unknown commend\n");
					continue;
				}
				
				
				if(loginLevel >= 1) {
					String[] attr = {"plist_id", "user_id", "plist_title"};
					showRS(attr, 3, rs, "playlists");
				}
				else {
					String[] attr = {"plist_id", "plist_title"};
					showRS(attr, 2, rs, "playlists");
				}
				
				rs.close();
				stmt.close();
			}
		}catch(SQLException se){
			se.printStackTrace();
		}finally{
		      try{
		         if(stmt!=null)
		            stmt.close();
		      }catch(SQLException se2){
		    	  se2.printStackTrace();
		      }
		      try{
		         if(rs!=null)
		            rs.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }
		}
	}
	
	
	/************ Music in plist Info Mangemenet Function **********/
	
	
	private static void musicInplist_registration() {
		String uid, pid, mid, title, sql;
		int cmd;
		ResultSet rs = null;
		
		try {
			while(true) {
				System.out.println("***** Select playlist to register music Menu *****");
				System.out.println("0. Return to previous menu");
				System.out.println("1. Search playlist");
				System.out.println("2. Input playlist ID");
				System.out.println("3. Input playlist title");
				System.out.print("Input: ");
				try {
					cmd = scanner.nextInt();
					System.out.print("\n");
				}catch(Exception e){
					cmd = -1;
					scanner.nextLine();
				}
				
				if(cmd == 0)
					break;
				else if(cmd == 1) {
					plist_search();
					continue;
				}
				else if(cmd == 2){
					System.out.print("ID: ");
					pid = scanner.next();
					System.out.print("\n");
					
					stmt = conn.createStatement();
					sql = "SELECT * "
							+ "FROM playlists "
							+ "WHERE plist_id = "+"'"+pid+"'";
					rs = stmt.executeQuery(sql);
					
					//ID존재 확인
					if(!rs.next()) {
						System.out.println("It does not exist.\n");
						rs.close();
					    stmt.close();
						continue;
					}
					uid = rs.getString("user_id");
					title = rs.getString("plist_title");
					rs.close();
				    stmt.close();
				    //사용자 로그인을 한 유저는 자신의 ID에 있는 playlist에만 음악을 등록할 수 있다
				    if(loginLevel == 0 && !uid.equals(user_id)) {
				    	 System.out.println("It does not exist.\n");
				    	 continue;
				    }
				}
				else if(cmd == 3) {
					if(loginLevel >= 1) {
						System.out.println("Input user ID to identify the title");
						System.out.print("ID: ");
						uid = scanner.next();
						System.out.print("\n");
						stmt = conn.createStatement();
						sql = "SELECT * "
								+ "FROM users "
								+ "WHERE user_id = "+"'"+uid+"'";
						rs = stmt.executeQuery(sql);
						
						//ID존재 확인
						if(!rs.next()) {
							System.out.println("It does not exist.\n");
							rs.close();
						    stmt.close();
							continue;
						}
						rs.close();
					    stmt.close();
					}
					else
						uid = user_id;
					
					System.out.print("Title: ");
					title = scanner.next();
					System.out.print("\n");
					stmt = conn.createStatement();
					sql = "SELECT * "
							+ "FROM playlists "
							+ "WHERE plist_title LIKE "+"'%"+title+"%' AND user_id = '"+uid+"'";
					rs = stmt.executeQuery(sql);
					//존재 확인
					if(!rs.next()) {
						System.out.println("It does not exist.\n");
						rs.close();
					    stmt.close();
						continue;
					}
					pid = rs.getString("plist_id");
					rs.close();
				    stmt.close();
				}
				else {
					System.out.println("Unknown commend\n");
					continue;
				}
				
				//Register
				while(true) {
					System.out.println("< Music registration in ["+title+"(ID:"+pid+")] >");
					System.out.println("0. Return to previous menu");
					System.out.println("1. Search music");
					System.out.println("2. Input music ID");
					System.out.print("Input: ");
					try {
						cmd = scanner.nextInt();
						System.out.print("\n");
					}catch(Exception e){
						cmd = -1;
						scanner.nextLine();
					}
					if(cmd == 0)
						break;
					else if(cmd == 1) {
						music_search();
					}
					else if(cmd == 2){
						System.out.print("ID: ");
						mid = scanner.next();
						System.out.print("\n");
						
						stmt = conn.createStatement();
						sql = "SELECT * "
								+ "FROM musics "
								+ "WHERE music_id = "+"'"+mid+"'";
						rs = stmt.executeQuery(sql);
						
						//ID존재 확인
						if(!rs.next()) {
							System.out.println("It does not exist.\n");
							rs.close();
						    stmt.close();
							continue;
						}
						rs.close();
					    stmt.close();
					    
					    //Insert
						stmt = conn.createStatement();
						sql = "INSERT INTO playlist_registrations(plist_id, music_id) "
								+ "values("+"'"+pid+"',"+"'"+mid+"')";
						
						stmt.executeUpdate(sql);
						stmt.close();
						
						System.out.println("Success!\n");
					}
					else {
						System.out.println("Unknown commend\n");
						continue;
					}
				}
			}
			
		}catch(SQLException se){
			se.printStackTrace();
		}
		
	}
	
	private static void musicInplist_deletion() {
		String uid, pid, mid, title, sql;
		int cmd, flag;
		ResultSet rs = null;
		
		try {
			while(true) {
				System.out.println("***** Select playlist to delete music Menu *****");
				System.out.println("0. Return to previous menu");
				System.out.println("1. Search playlist");
				System.out.println("2. Input playlist ID");
				System.out.println("3. Input playlist title");
				System.out.print("Input: ");
				try {
					cmd = scanner.nextInt();
					System.out.print("\n");
				}catch(Exception e){
					cmd = -1;
					scanner.nextLine();
				}
				if(cmd == 0)
					break;
				else if(cmd == 1) {
					plist_search();
					continue;
				}
				else if(cmd == 2){
					System.out.print("ID: ");
					pid = scanner.next();
					System.out.print("\n");
					
					stmt = conn.createStatement();
					sql = "SELECT * "
							+ "FROM playlists "
							+ "WHERE plist_id = "+"'"+pid+"'";
					rs = stmt.executeQuery(sql);
					
					//ID존재 확인
					if(!rs.next()) {
						System.out.println("It does not exist.\n");
						rs.close();
					    stmt.close();
						continue;
					}
					uid = rs.getString("user_id");
					title = rs.getString("plist_title");
					rs.close();
				    stmt.close();
				    //사용자 로그인을 한 유저는 자신의 ID에 있는 playlist에만 음악을 등록할 수 있다
				    if(loginLevel == 0 && !uid.equals(user_id)) {
				    	 System.out.println("It does not exist.\n");
				    	 continue;
				    }
				}
				else if(cmd == 3) {
					if(loginLevel >= 1) {
						System.out.println("Input user ID to identify the title");
						System.out.print("ID: ");
						uid = scanner.next();
						System.out.print("\n");
						stmt = conn.createStatement();
						sql = "SELECT * "
								+ "FROM users "
								+ "WHERE user_id = "+"'"+uid+"'";
						rs = stmt.executeQuery(sql);
						
						//ID존재 확인
						if(!rs.next()) {
							System.out.println("It does not exist.\n");
							rs.close();
						    stmt.close();
							continue;
						}
						rs.close();
					    stmt.close();
					}
					else
						uid = user_id;
					
					System.out.print("Title: ");
					title = scanner.next();
					System.out.print("\n");
					stmt = conn.createStatement();
					sql = "SELECT * "
							+ "FROM playlists "
							+ "WHERE plist_title LIKE "+"'%"+title+"%' AND user_id = '"+uid+"'";
					rs = stmt.executeQuery(sql);
					//존재 확인
					if(!rs.next()) {
						System.out.println("It does not exist.\n");
						rs.close();
					    stmt.close();
						continue;
					}
					pid = rs.getString("plist_id");
					rs.close();
				    stmt.close();
				}
				else {
					System.out.println("Unknown commend\n");
					continue;
				}
				//Deletion
				while(true) {
					System.out.println("< Music deletion in ["+title+"(ID:"+pid+")] >");
					System.out.println("0. Return to previous menu");
					System.out.println("1. Search music in the playlist");
					System.out.println("2. Input music ID");
					System.out.print("Input: ");
					try {
						cmd = scanner.nextInt();
						System.out.print("\n");
					}catch(Exception e){
						cmd = -1;
						scanner.nextLine();
					}
					if(cmd == 0)
						break;
					else if(cmd == 1) {
						stmt = conn.createStatement();
						sql = "SELECT pr.music_id, music_title, artist_id, comp_id, swriter_id, album_id, genre "  
								+"FROM playlist_registrations pr, musics m " 
								+"WHERE pr.plist_id = '"+pid+"' AND pr.music_id = m.music_id";
						rs = stmt.executeQuery(sql);
						String[] attr = {"music_id", "music_title", "artist_id", "artist_name", "comp_id", "comp_name", "swriter_id", "swriter_name", "album_id", "album_title", "genre"};
						String tname = "["+title+"(ID:"+pid+")]";
						showRS(attr, 11, rs, tname);
						rs.close();
						stmt.close();
					}
					else if(cmd == 2){
						System.out.print("ID: ");
						mid = scanner.next();
						System.out.print("\n");
						
						stmt = conn.createStatement();
						sql = "DELETE FROM playlist_registrations "
								+ "WHERE music_id = "+"'"+mid+"' AND "+"plist_id = '"+pid+"'";
								
						flag = stmt.executeUpdate(sql);
						if(flag == 0) 
							System.out.println("It does not exist.\n");
						
						else
							System.out.println("Success!\n");
						
						stmt.close();
					}
					else {
						System.out.println("Unknown commend\n");
						continue;
					}
				}
			}
		}catch(SQLException se){
			se.printStackTrace();
		}
	}
	
	private static void musicInplist_search() {
		String uid, pid, title, sql;
		int cmd;
		ResultSet rs = null;
		ResultSet rs2 = null;
		
		try {
			while(true) {
				System.out.println("***** Search registered music Menu *****");
				System.out.println("0. Return to previous menu");
				System.out.println("1. All playlists");
				System.out.println("2. Playlist ID search");
				System.out.println("3. Playlist title search");
				if(loginLevel >= 1)
					System.out.println("4. User ID search");
				System.out.print("Input: ");
				try {
					cmd = scanner.nextInt();
					System.out.print("\n");
				}catch(Exception e){
					cmd = -1;
					scanner.nextLine();
				}
				
				if(cmd == 0)
					break;
				else if(cmd == 1) {
					stmt = conn.createStatement();
					if(loginLevel >= 1) {
						sql = "SELECT * "
								+ "FROM playlists ";
						rs = stmt.executeQuery(sql);
					}
					else {
						sql = "SELECT * "
								+ "FROM playlists "
								+ "WHERE user_id = '"+user_id+"'";
						rs = stmt.executeQuery(sql);
					}
					while(rs.next()) {
						pid = rs.getString("plist_id");
						title = rs.getString("plist_title");
						stmt = conn.createStatement();
						sql = "SELECT pr.music_id, music_title, artist_id, comp_id, swriter_id, album_id, genre "  
								+"FROM playlist_registrations pr, musics m " 
								+"WHERE pr.plist_id = '"+pid+"' AND pr.music_id = m.music_id";
						rs2 = stmt.executeQuery(sql);
						String[] attr = {"music_id", "music_title", "artist_id", "artist_name", "comp_id", "comp_name", "swriter_id", "swriter_name", "album_id", "album_title", "genre"};
						String tname = "["+title+"(ID:"+pid+")]";
						showRS(attr, 11, rs2, tname);
						rs2.close();
						stmt.close();
					}
					rs.close();
					stmt.close();
				}
				else if(cmd == 2){
					System.out.print("ID: ");
					pid = scanner.next();
					System.out.print("\n");
					
					stmt = conn.createStatement();
					sql = "SELECT * "
							+ "FROM playlists "
							+ "WHERE plist_id = "+"'"+pid+"'";
					rs = stmt.executeQuery(sql);
					
					//ID존재 확인
					if(!rs.next()) {
						System.out.println("It does not exist.\n");
						rs.close();
					    stmt.close();
						continue;
					}
					uid = rs.getString("user_id");
					title = rs.getString("plist_title");
					rs.close();
				    stmt.close();
				    //사용자 로그인을 한 유저는 자신의 ID에 있는 playlist에만 음악을 등록할 수 있다
				    if(loginLevel == 0 && !uid.equals(user_id)) {
				    	 System.out.println("It does not exist.\n");
				    	 continue;
				    }
				    //Show
				    stmt = conn.createStatement();
					sql = "SELECT pr.music_id, music_title, artist_id, comp_id, swriter_id, album_id, genre "  
							+"FROM playlist_registrations pr, musics m " 
							+"WHERE pr.plist_id = '"+pid+"' AND pr.music_id = m.music_id";
					rs = stmt.executeQuery(sql);
					String[] attr = {"music_id", "music_title", "artist_id", "artist_name", "comp_id", "comp_name", "swriter_id", "swriter_name", "album_id", "album_title", "genre"};
					String tname = "["+title+"(ID:"+pid+")]";
					showRS(attr, 11, rs, tname);
					rs.close();
					stmt.close();
				}
				else if(cmd == 3) {
					if(loginLevel >= 1) {
						System.out.println("Input user ID to identify the title");
						System.out.print("ID: ");
						uid = scanner.next();
						System.out.print("\n");
						stmt = conn.createStatement();
						sql = "SELECT * "
								+ "FROM users "
								+ "WHERE user_id = "+"'"+uid+"'";
						rs = stmt.executeQuery(sql);
						
						//ID존재 확인
						if(!rs.next()) {
							System.out.println("It does not exist.\n");
							rs.close();
						    stmt.close();
							continue;
						}
						rs.close();
					    stmt.close();
					}
					else
						uid = user_id;
					
					System.out.print("Title: ");
					title = scanner.next();
					System.out.print("\n");
					stmt = conn.createStatement();
					sql = "SELECT * "
							+ "FROM playlists "
							+ "WHERE plist_title LIKE "+"'%"+title+"%' AND user_id = '"+uid+"'";
					rs = stmt.executeQuery(sql);
					//존재 확인
					if(!rs.next()) {
						System.out.println("It does not exist.\n");
						rs.close();
					    stmt.close();
						continue;
					}
					pid = rs.getString("plist_id");
					rs.close();
				    stmt.close();
				    //Show
				    stmt = conn.createStatement();
					sql = "SELECT pr.music_id, music_title, artist_id, comp_id, swriter_id, album_id, genre "  
							+"FROM playlist_registrations pr, musics m " 
							+"WHERE pr.plist_id = '"+pid+"' AND pr.music_id = m.music_id";
					rs = stmt.executeQuery(sql);
					String[] attr = {"music_id", "music_title", "artist_id", "artist_name", "comp_id", "comp_name", "swriter_id", "swriter_name", "album_id", "album_title", "genre"};
					String tname = "["+title+"(ID:"+pid+")]";
					if(loginLevel >= 1)
				    	tname = "["+title+"(ID:"+pid+" UID:"+uid+")]";
					showRS(attr, 11, rs, tname);
					rs.close();
					stmt.close();
				}
				else if(cmd == 4 && loginLevel >= 1){
					System.out.print("ID: ");
					uid = scanner.next();
					stmt = conn.createStatement();
					sql = "SELECT * "
							+ "FROM playlists "
							+ "WHERE user_id = "+"'"+uid+"'";
					rs = stmt.executeQuery(sql);
					//존재 확인
					if(!rs.next()) {
						System.out.println("It does not exist.\n");
						rs.close();
					    stmt.close();
						continue;
					}
					else 
						rs.beforeFirst();
					
					
					//Show
					while(rs.next()) {
						pid = rs.getString("plist_id");
						title = rs.getString("plist_title");
						stmt = conn.createStatement();
						sql = "SELECT pr.music_id, music_title, artist_id, comp_id, swriter_id, album_id, genre "  
								+"FROM playlist_registrations pr, musics m " 
								+"WHERE pr.plist_id = '"+pid+"' AND pr.music_id = m.music_id";
						rs2 = stmt.executeQuery(sql);
						String[] attr = {"music_id", "music_title", "artist_id", "artist_name", "comp_id", "comp_name", "swriter_id", "swriter_name", "album_id", "album_title", "genre"};
						String tname = "["+title+"(ID:"+pid+" UID:"+uid+")]";
						showRS(attr, 11, rs2, tname);
						rs2.close();
						stmt.close();
					}
					rs.close();
					stmt.close();
				    
				}
				else {
					System.out.println("Unknown commend\n");
					continue;
				}
			}
			
		}catch(SQLException se){
			se.printStackTrace();
		}
	}
	
	
}
