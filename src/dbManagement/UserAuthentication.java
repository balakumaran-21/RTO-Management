package dbManagement;

import java.sql.*;



import java.util.Scanner;

import rtoManagement.Owner;

public class UserAuthentication {
	static Scanner input = new Scanner(System.in);
	
	// Check for already present usernames in the users table
	public static boolean checkUserName(String username) throws SQLException {
		
		String query = QueriesList.checkUserInTable;
		Connection con = DBconnect.connectDB();
		PreparedStatement pst = con.prepareStatement(query);
		pst.setString(1, username);
		ResultSet rs = pst.executeQuery();
		if(rs.next()) {
			return true;
		}
		con.close();
		return false;
		
	}
	
	// Inserting data into users table 
	public static void registerUser(String username, String password, String usertype) throws SQLException {
		String query = QueriesList.insertUser;
		Connection con = DBconnect.connectDB();
		PreparedStatement pst = con.prepareStatement(query);
		pst.setString(1, username);
		pst.setString(2, password);
		pst.setString(3, usertype);
		int rows = pst.executeUpdate();
		System.out.println("Rows updated in users table: "+ rows);
		con.close();
	}
	
	// Getting id of user while signing up
	public static int getUserID(String username, String password) throws SQLException {
		int userID = 0;
		String query = QueriesList.getUserID;
		Connection con = DBconnect.connectDB();
		PreparedStatement pst = con.prepareStatement(query);
		pst.setString(1, username);
		pst.setString(2, password);
		ResultSet rs = pst.executeQuery();
		if(rs.next())
		userID = rs.getInt(1);
		con.close();
		return userID;
	}
	
	public static void registerOwner(Owner owner) throws SQLException {
		
		String query = QueriesList.insertOwner;
		Connection con = DBconnect.connectDB();
		PreparedStatement pst = con.prepareStatement(query);
		pst.setInt(1,owner.getOwnerID());
		pst.setString(2, owner.getName());
		pst.setInt(3, owner.getAge());
		pst.setString(4, owner.getContactNo());
		pst.setString(5, owner.getState());
		pst.setString(6, owner.getDistrict());
		pst.setString(7, owner.getAddress());
		int rows = pst.executeUpdate();
		System.out.println("Rows updated in owners table: "+ rows);
		con.close();
	}
	
	// Inserting information into the admin table
	public static void registerAdmin(int id, String adminName) throws SQLException {
		
		String query = QueriesList.insertAdmin;
		Connection con = DBconnect.connectDB();
		PreparedStatement pst = con.prepareStatement(query);
		pst.setInt(1, id);
		pst.setString(2, adminName);
		int rows = pst.executeUpdate();
		System.out.println("Rows updated in admins table: "+rows);
		con.close();
		
	}
	
//	 Check username and password to proceed login
	public static boolean ValidateUser(String userName,String password) throws SQLException {
		String query = QueriesList.checkLoginValidity;
		Connection con = DBconnect.connectDB();
		PreparedStatement pst = con.prepareStatement(query);
		pst.setString(1, userName);
		pst.setString(2, password);
		ResultSet rs = pst.executeQuery();
		rs.next();
		if(rs.getInt(1) == 1) return true;
		con.close();
		return false;
	}
	
	//Get user type to render based on their acess
	public static String getUserType(int userID) throws SQLException {
		String query = QueriesList.decidingUser;
		Connection con = DBconnect.connectDB();
		PreparedStatement pst = con.prepareStatement(query);
		pst.setInt(1, userID);
		ResultSet rs = pst.executeQuery();
		rs.next();
		String userType = rs.getString(1);
		return userType;
	}

	public static String getOwnerName(int id) throws SQLException {
		String query = QueriesList.getOwnerName;
		Connection con = DBconnect.connectDB();
		PreparedStatement pst = con.prepareStatement(query);
		pst.setInt(1, id);
		ResultSet rs = pst.executeQuery();
		rs.next();
		String name = rs.getString(1);
		con.close();
		return name;
	}

	public static String getAdminName(int id) throws SQLException {
		String query = QueriesList.getAdminName;
		Connection con = DBconnect.connectDB();
		PreparedStatement pst = con.prepareStatement(query);
		pst.setInt(1, id);
		ResultSet rs = pst.executeQuery();
		rs.next();
		String name = rs.getString(1);
		con.close();
		return name;
	}

	
	
}
