package dbManagement;

import java.sql.*;

import java.util.Scanner;

import rtoManagement.Owner;

public class OwnerDAO {
	
	static Scanner input = new Scanner(System.in);
	public static void ownerAccess(int id) throws SQLException {
		System.out.println("Choose any option:\n"
				+ "1. Register new Vehicle\n"
				+ "2. View Registered vehicles\n"
				+ "3. View Registration details\n"
				+ "4. Renew Registration\n"
				+ "5. Transfer OwnerShip");
		System.out.print("Your choice: ");
		int option = input.nextInt();
		input.nextLine();
		if(option == 1) {
			VehicleDAO.registerNewVehicle(id);
		}
	}
	
	public static Owner fetchOwnerDetails(int id) throws SQLException {
		String query = QueriesList.getUserDetails;
		Connection con = DBconnect.connectDB();
		PreparedStatement pst = con.prepareStatement(query);
		pst.setInt(1, id);
		ResultSet rs = pst.executeQuery();
		rs.next();
		Owner owner = new Owner( rs.getString(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
		return owner;
	}
	
	
}
