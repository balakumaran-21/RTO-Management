package dbManagement;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

import rtoManagement.Registration;
import rtoManagement.Vehicle;

public class RenewalRequestDAO {
	
	
	public static ArrayList<Registration> showExpiredRegistrations(int id) throws SQLException {
		
		ArrayList<Registration> renewableRegistrations = new ArrayList<Registration>();
		String query = "select ownerID, reg_date, expiry_date, reg_no, year(expiry_date) from vehicle_registration_status where ownerid = ? and reg_status = 'Accepted'";
		Connection con = DBconnect.connectDB();
		PreparedStatement pst = con.prepareStatement(query);
		pst.setInt(1, id);
		ResultSet rs = pst.executeQuery();
		
		while(rs.next()){
			
			LocalDate date = null;
			int exp_year = Integer.parseInt(rs.getString(5));
			int curr_year = date.now().getYear();
			if(curr_year > exp_year) {
				renewableRegistrations.add(new Registration(rs.getInt(1), rs.getDate(2), rs.getDate(3), rs.getString(4)));
			}
			
			for(Registration registration: renewableRegistrations) {
				registration.getInfo();
			}
		}
		
		return renewableRegistrations;
		
	}
	
	public static ArrayList<Vehicle> showExpiredVehicles(int id) throws SQLException {
		
		ArrayList<Vehicle> renewableVehicles = new ArrayList<Vehicle>();
		String query = "select  vehicle_type, fuel_type, man_year,year(expiry_date) from vehicle_registration_status where ownerid = ? and reg_status = 'Accepted'";
		Connection con = DBconnect.connectDB();
		PreparedStatement pst = con.prepareStatement(query);
		pst.setInt(1, id);
		ResultSet rs = pst.executeQuery();
		
		while(rs.next()){
			
			LocalDate date = null;
			int exp_year = Integer.parseInt(rs.getString(4));
			int curr_year = date.now().getYear();
			if(curr_year > exp_year) {
				renewableVehicles.add(new Vehicle(rs.getString(1), rs.getString(2), rs.getInt(3)));
			}
			
		}
		
		for(Vehicle vehicle: renewableVehicles) {
			vehicle.getInfo();
		}
		
		return renewableVehicles;
		
	}
	
	public static void updateRenewalRequestsToAdmin(Registration registration, Vehicle vehicle) throws SQLException {
		
		String query = "insert into registration_renewal_requests(ownerID, reg_date, expiry_date, vehicle_type, fuel_type, man_year, reg_no) values(?,?,?,?,?,?,?)";
		Connection con = DBconnect.connectDB();
		PreparedStatement pst = con.prepareStatement(query);
		pst.setInt(1, registration.getOwnerID());
		pst.setDate(2, registration.getDateOfreg());
		pst.setDate(3, registration.getExpiryDate());
		pst.setString(4, vehicle.getVehicleType());
		pst.setString(5, vehicle.getFuelType());
		pst.setInt(6, vehicle.getManufactureYear());
		pst.setString(7, registration.getRegNo());
		int rows = pst.executeUpdate();
		System.out.println("No of rows updated in renewa registration table: "+rows);
	}
	
	
	// Admin side table
	public static ArrayList<Registration> showRenewableRegistrationsToAdmin() throws SQLException {
		ArrayList<Registration> renewableRegistrationsInfo = new ArrayList<Registration>();
		String query = "select ownerID, reg_date, expiry_date, reg_no from registration_renewal_requests where renewal_status = 'Pending';";
		Connection con = DBconnect.connectDB();
		PreparedStatement pst = con.prepareStatement(query);
		ResultSet rs = pst.executeQuery();
		while(rs.next()) {
			int ownerID = rs.getInt(1);
			Date regDate = rs.getDate(2);
			Date expDate = rs.getDate(3);
			String regNo = rs.getString(4);
			renewableRegistrationsInfo.add(new Registration(ownerID,regDate,expDate,regNo));
		}

		return renewableRegistrationsInfo;	
	}
	
	public static ArrayList<Vehicle> showRenewableVehiclesToAdmin() throws SQLException {
		ArrayList<Vehicle> renewableVehiclesInfo = new ArrayList<Vehicle>();
		String query = "select vehicle_type, fuel_type, man_year from registration_renewal_requests where renewal_status = 'Pending';";
		Connection con = DBconnect.connectDB();
		PreparedStatement pst = con.prepareStatement(query);
		ResultSet rs = pst.executeQuery();
		while(rs.next()) {
			String vehicleType = rs.getString(1);
			String fuelType = rs.getString(2);
			int manYear = rs.getInt(3);
			renewableVehiclesInfo.add(new Vehicle(vehicleType,fuelType,manYear));
		}

		return renewableVehiclesInfo;	
	}
	
	public static void updateStatusAccepted(Registration registration, Vehicle vehicle) throws SQLException {
		
		Registration updatedRegistration = new Registration();
		registration.setDateOfreg(updatedRegistration.getDateOfreg());
		registration.setExpiryDate(updatedRegistration.getExpiryDate());
		System.out.println("Reg Date: "+registration.getDateOfreg());
		System.out.println("Exp date: "+registration.getExpiryDate());
		
		String query = "update registration_renewal_requests set reg_date = ?, expiry_date = ? ,renewal_status = 'Accepted' where reg_no = ?;";
		Connection con = DBconnect.connectDB();
		PreparedStatement pst = con.prepareStatement(query);
		pst.setDate(1, registration.getDateOfreg());
		pst.setDate(2, registration.getExpiryDate());
		pst.setString(3, registration.getRegNo());
		int rows = pst.executeUpdate();
		System.out.println("Registration status to be set to renewed");
		
		String queryUpdate = "update vehicle_registration_status set reg_date = ?, expiry_date = ? where reg_no = ? ";
		PreparedStatement pstUpdate = con.prepareStatement(queryUpdate);
		pstUpdate.setDate(1,registration.getDateOfreg());
		pstUpdate.setDate(2, registration.getExpiryDate());
		pstUpdate.setString(3,registration.getRegNo());
		int rowsUpdate = pstUpdate.executeUpdate();
		System.out.println("Registration renewed");
		
	}
	
	public static void updateStatusRejected(Registration registration, String remarks) throws SQLException {
		
		String query = "update registration_renewal_requests set renewal_status = 'Rejected', remarks = ? where reg_no = ?;";
		Connection con = DBconnect.connectDB();
		PreparedStatement pst = con.prepareStatement(query);
		pst.setString(1,remarks);
		pst.setString(2, registration.getRegNo());
		int rows = pst.executeUpdate();
		System.out.println("Renewal rejected");
		
	}
	
}
