package dbManagement;

import java.sql.*;

import java.util.ArrayList;

import rtoManagement.Registration;
import rtoManagement.Vehicle;

public class RegistrationDAO {
	
	public static ArrayList<Registration> viewOwnerRegistrations(int id) throws SQLException {
		ArrayList<Registration> registrations = new ArrayList<Registration>();
		String query = "select ownerID, reg_date, expiry_date, reg_no from vehicle_registration_status where ownerID = ? and reg_status = 'Accepted'";
		Connection con = DBconnect.connectDB();
		PreparedStatement pst = con.prepareStatement(query);
		pst.setInt(1, id);
		ResultSet rs = pst.executeQuery();
		while(rs.next()) {
			registrations.add(new Registration(rs.getInt(1), rs.getDate(2), rs.getDate(3),rs.getString(4)));
		}
		return registrations;
	}
	
	public static ArrayList<Vehicle> viewVehicleRegistrations(int id) throws SQLException {
		ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
		String query = "select vehicle_type, fuel_type, man_year from vehicle_registration_status where ownerID = ? and reg_status = 'Accepted'";
		Connection con = DBconnect.connectDB();
		PreparedStatement pst = con.prepareStatement(query);
		pst.setInt(1, id);
		ResultSet rs = pst.executeQuery();
		while(rs.next()) {
			vehicles.add(new Vehicle(rs.getString(1), rs.getString(2), rs.getInt(3)));
		}
		return vehicles;
	}
	
	public static ArrayList<Registration> viewOwnerRejects(int id) throws SQLException {
		ArrayList<Registration> registrations = new ArrayList<Registration>();
		String query = "select ownerID, reg_date, expiry_date, reg_no from vehicle_registration_status where ownerID = ? and reg_status = 'Rejected'";
		Connection con = DBconnect.connectDB();
		PreparedStatement pst = con.prepareStatement(query);
		pst.setInt(1, id);
		ResultSet rs = pst.executeQuery();
		while(rs.next()) {
			registrations.add(new Registration(rs.getInt(1), rs.getDate(2), rs.getDate(3),rs.getString(4)));
		}
		return registrations;
	}
	
	public static ArrayList<Vehicle> viewVehicleRejects(int id) throws SQLException {
		ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
		String query = "select vehicle_type, fuel_type, man_year from vehicle_registration_status where ownerID = ? and reg_status = 'Rejected'";
		Connection con = DBconnect.connectDB();
		PreparedStatement pst = con.prepareStatement(query);
		pst.setInt(1, id);
		ResultSet rs = pst.executeQuery();
		while(rs.next()) {
			vehicles.add(new Vehicle(rs.getString(1), rs.getString(2), rs.getInt(3)));
		}
		return vehicles;
	}
	
	public static void registrationDetails(Vehicle vehicle, int ownerID) throws SQLException {
		Registration registration = new Registration();
		
		//cheking for generation of register number 
		String query = QueriesList.getStateAndCity;
		Connection con = DBconnect.connectDB();
		PreparedStatement pst =con.prepareStatement(query);
		pst.setInt(1, ownerID);
		ResultSet rs = pst.executeQuery();
		rs.next();

		String state = rs.getString(1);
		String district = rs.getString(2);
		String regNo = DistrictCodesDAO.generateRegNo(state,district);
		vehicle.setRegNo(regNo);
		con.close();
		
		// Inserting registration details into registration table
		query = QueriesList.insertRegistrations;
		con = DBconnect.connectDB();
		pst = con.prepareStatement(query);
		pst.setInt(1, ownerID);
		pst.setDate(2, registration.getDateOfreg());
		pst.setDate(3, registration.getExpiryDate());
		pst.setString(4, regNo);
		int rows = pst.executeUpdate();
		con.close();
	}
}
