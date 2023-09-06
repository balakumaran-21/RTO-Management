package dbManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import rtoManagement.Registration;
import rtoManagement.Vehicle;

public class VehicleRequestsDAO {
	
	public static void setRequestfromUser(Registration registration, Vehicle vehicle) throws SQLException{
		String getQuery = QueriesList.getStateAndCity;
		String regNo = null;
		Connection con = DBconnect.connectDB();
		PreparedStatement pst =con.prepareStatement(getQuery);
		pst.setInt(1, registration.getOwnerID());
		ResultSet rs = pst.executeQuery();
		rs.next();
		String state = rs.getString(1);
		String district = rs.getString(2);
		regNo = DistrictCodesDAO.generateRegNo(state,district);			
		con.close();
		vehicle.setRegNo(regNo);
		registration.setRegNo(regNo);
	}
	
	public static void updateAdminSideRequests(Registration registration,Vehicle vehicle) throws SQLException {
		String query = QueriesList.reqNewRegVehicle;
		Connection con = DBconnect.connectDB();
		PreparedStatement pst =con.prepareStatement(query);
		pst.setInt(1, registration.getOwnerID());
		pst.setDate(2, registration.getDateOfreg());
		pst.setDate(3, registration.getExpiryDate());
		pst.setString(4, vehicle.getVehicleType());
		pst.setString(5, vehicle.getFuelType());
		pst.setInt(6, vehicle.getManufactureYear());
		pst.setString(7, registration.getRegNo());
		int rows = pst.executeUpdate();
		con.close();
		System.out.println("Inserted rows in Vehicle requests: "+rows);
	}
	
	public static void updateAcceptVehicleRequest(Registration registration) throws SQLException {
		String query = "update vehicle_registration_status set reg_status = 'Accepted' where reg_no = ?";
		Connection con = DBconnect.connectDB();
		PreparedStatement pst = con.prepareStatement(query);
		pst.setString(1, registration.getRegNo());
		int rows = pst.executeUpdate();
		con.close();
		System.out.println("Status Updated: Accepted");
	}
	
	public static void updateRejectVehicleRequest(Registration registration, String remarks) throws SQLException {
		String query = "update vehicle_registration_status set reg_status = 'Rejected', remarks = ? where reg_no = ?;";
		Connection con = DBconnect.connectDB();
		PreparedStatement pst = con.prepareStatement(query);
		pst.setString(1, remarks);
		pst.setString(2, registration.getRegNo());
		int rows = pst.executeUpdate();
		con.close();
		System.out.println("Status Updated: Rejected");
	}
	
	 public static ArrayList<Registration> registrationsRequests() throws SQLException{
		 
		 ArrayList<Registration> registrations = new ArrayList<>();
		 String query = "select ownerID, reg_date, expiry_date, reg_no from vehicle_registration_status where reg_status = 'Pending'";
		 Connection con = DBconnect.connectDB();
		 PreparedStatement pst = con.prepareStatement(query);
		 ResultSet rs = pst.executeQuery();
		 while(rs.next()) {
			 registrations.add(new Registration( rs.getInt(1), rs.getDate(2), rs.getDate(3), rs.getString(4) ));
			 
		 }
		 
		 return registrations;
	 }
	 
	 public static ArrayList<Vehicle> vehiclesRequests() throws SQLException{
		 ArrayList<Vehicle> vehicles = new ArrayList<>();
		 String query = "select vehicle_type, fuel_type, man_year from vehicle_registration_status where reg_status = 'Pending'";
		 Connection con = DBconnect.connectDB();
		 PreparedStatement pst = con.prepareStatement(query);
		 ResultSet rs = pst.executeQuery();
		 while(rs.next()) {
			 vehicles.add(new Vehicle( rs.getString(1), rs.getString(2), rs.getInt(3)));			 
		 }
		 return vehicles;
	 }
	 
	 public static int countOfRegisteredVehicles(int id) throws SQLException {
		 int count = 0;
		 String query  = "select count(*) from vehicle_registration_status where ownerid = ? and reg_status = 'Accepted'";
		 Connection con = DBconnect.connectDB();
		 PreparedStatement pst = con.prepareStatement(query); 
		 pst.setInt(1, id);
		 ResultSet rs = pst.executeQuery();
		 rs.next();
		 count = rs.getInt(1);
		 return count;
	 }
	
	 
	 
}
