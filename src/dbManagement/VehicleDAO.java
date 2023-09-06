package dbManagement;

import java.sql.*;

import rtoManagement.Vehicle;

public class VehicleDAO {
	public static void registerNewVehicle(int id) throws SQLException{
		System.out.println("-------------------------------------------------------");
		System.out.println("Register your vehicle by filling in the details below");
		System.out.println("-------------------------------------------------------");
		String reg_no = null;
		Vehicle vehicle = new Vehicle();
		
		RegistrationDAO.registrationDetails(vehicle,id);
		
		//Inserting values into the vehicles table
		String query = QueriesList.insertVehicles;
		Connection con = DBconnect.connectDB();
		PreparedStatement pst = con.prepareStatement(query);
		pst.setInt(1,id);
		pst.setString(2, vehicle.getVehicleType());
		pst.setString(3,vehicle.getFuelType());
		pst.setInt(4,vehicle.getManufactureYear());
		pst.setString(5, vehicle.getRegNo());
		int rows = pst.executeUpdate();
		System.out.println("Updated vehicles rows: "+rows);
		con.close();
	}
}
