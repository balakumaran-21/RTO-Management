package dbManagement;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import com.mysql.cj.xdevapi.Result;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import rtoManagement.Owner;
import rtoManagement.Registration;
import rtoManagement.TransferRequest;

public class TransferRequestsDAO {
	
	public static ArrayList<Owner> showAllOwners(int id) throws SQLException {
		
		ArrayList<Owner> ownersList = new ArrayList<Owner>();
		String query = "select * from owners where owner_id <> ?";
		Connection con = DBconnect.connectDB();
		PreparedStatement pst = con.prepareStatement(query);
		pst.setInt(1, id);
		ResultSet rs = pst.executeQuery();
		while(rs.next()) {
			ownersList.add(new Owner(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
		}

		return ownersList;
	}
	
	public static ArrayList<String> showAllRegistrations(int id) throws SQLException {
		
		ArrayList<String> regNoList = new ArrayList<String>();
		String query = "select reg_no from vehicle_registration_status where ownerID = ? and reg_status = 'Accepted'";
		Connection con = DBconnect.connectDB();
		PreparedStatement pst = con.prepareStatement(query);
		pst.setInt(1, id);
		ResultSet rs = pst.executeQuery();
		while(rs.next()) {
			regNoList.add(rs.getString(1));
		}

		return regNoList;
	}

	public static void requestTranferToAdmin(TransferRequest transferRequest) throws SQLException {
		
		String query = "insert into transfer_requests(current_owner_id, new_owner_id, reg_no) values(?,?,?)";
		Connection con = DBconnect.connectDB();
		PreparedStatement pst = con.prepareStatement(query);
		pst.setInt(1, transferRequest.getCurrOwnerID());
		pst.setInt(2, transferRequest.getNewOwnerID());
		pst.setString(3, transferRequest.getRegNo());
		int rows = pst.executeUpdate();
		System.out.println("Inserted rows in transfer_requests table: "+rows);
		
	}
	
	public static ArrayList<TransferRequest> showAllTransferRequests() throws SQLException {
		
		ArrayList<TransferRequest> transferRequests = new ArrayList<TransferRequest>();
		String query = "select current_owner_id, new_owner_id, reg_no from transfer_requests where transfer_status = 'Pending'";
		Connection con = DBconnect.connectDB();
		PreparedStatement pst = con.prepareStatement(query);
		ResultSet rs = pst.executeQuery();
		while(rs.next()) {
			transferRequests.add(new TransferRequest(rs.getInt(1),rs.getInt(2), rs.getString(3)));
		}
		return transferRequests;
		
	}
	
	public static void acceptTransferRequest(Owner newOwner, Registration registration) throws SQLException {
		
		String query = "update transfer_requests set transfer_status = 'Accepted' where reg_no = ?";
		Connection conn = DBconnect.connectDB();
		PreparedStatement pst = conn.prepareStatement(query);
		pst.setString(1, registration.getRegNo());
		int rows = pst.executeUpdate();
		System.out.println("Updated status : Accpeted");
		conn.close();
		
		String updateQuery = "update vehicle_registration_status set ownerID = ? where reg_no = ?;";
		Connection con = DBconnect.connectDB();
		PreparedStatement upst = con.prepareStatement(updateQuery);
		upst.setInt(1, newOwner.getOwnerID());
		upst.setString(2, registration.getRegNo());
		rows = upst.executeUpdate();
		System.out.println("Updated Status: Transferred Registration");
		con.close();
		
	}
	
	public static void rejectTransferRequest(Registration registration, String remark) throws SQLException {
			
			String query = "update transfer_requests set remarks = ? , transfer_status = 'Rejected' where reg_no = ?";
			Connection con = DBconnect.connectDB();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, remark);
			pst.setString(2, registration.getRegNo());
			int rows = pst.executeUpdate();
			System.out.println("Updated status : Rejected");
			con.close();
			
	}
	
	public static ArrayList<TransferRequest> showAllRejectedTransferRequests(int id) throws SQLException {
		ArrayList<TransferRequest> transferRequests = new ArrayList<TransferRequest>();
		String query = "select current_owner_id, new_owner_id, reg_no from transfer_requests where current_owner_id = ? and transfer_status = 'Rejected'";
		Connection con = DBconnect.connectDB();
		PreparedStatement pst = con.prepareStatement(query);
		pst.setInt(1, id);
		ResultSet rs = pst.executeQuery();
		while(rs.next()) {
			transferRequests.add(new TransferRequest(rs.getInt(1), rs.getInt(2), rs.getString(3)));
		}
		return transferRequests;
	}
	
	public static ArrayList<String> showAllRejectedRemarksTransferRequests(int id) throws SQLException{
		ArrayList<String> remarks = new ArrayList<String>();
		String query = "select remarks from transfer_requests where current_owner_id = ? and transfer_status = 'Rejected'";
		Connection con = DBconnect.connectDB();
		PreparedStatement pst = con.prepareStatement(query);
		pst.setInt(1, id);
		ResultSet rs = pst.executeQuery();
		while(rs.next()) {
			remarks.add(rs.getString(1));
		}
		return remarks;
	}
	
}	

