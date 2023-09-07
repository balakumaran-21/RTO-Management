package application;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import dbManagement.TransferRequestsDAO;
import dbManagement.VehicleRequestsDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import rtoManagement.*;

public class MoreDetailsController {

	Owner currOwner, newOwner;
	Registration registration;
	Vehicle vehicle;
	
	@FXML
    private Label currentUserLabel, newUserLabel, registrationLabel;
	
	public void setData( Owner curr_owner, Owner new_owner, Registration registration, Vehicle vehicle) {
		
		this.currOwner = curr_owner;
		this.newOwner = new_owner;
		this.registration = registration;
		this.vehicle = vehicle;
		
		System.out.println("Current Owner details: ");
		curr_owner.getInfo();
		System.out.println("New Owner details: ");
		new_owner.getInfo();
		registration.getInfo();
		vehicle.setRegNo(registration.getRegNo());
		vehicle.getInfo();
		
		currentUserLabel.setText(
				"Name: "+curr_owner.getName()+
				"\nAge: "+curr_owner.getAge()+
				"\nContact Number: "+curr_owner.getContactNo()+
				"\nState: "+curr_owner.getState()+
				"\nDistrict: "+curr_owner.getDistrict()+
				"\nAddress: "+curr_owner.getAddress()
				);
		
		newUserLabel.setText(
				"Name: "+new_owner.getName()+
				"\nAge: "+new_owner.getAge()+
				"\nContact Number: "+new_owner.getContactNo()+
				"\nState: "+new_owner.getState()+
				"\nDistrict: "+new_owner.getDistrict()+
				"\nAddress: "+new_owner.getAddress()
				);
		registrationLabel.setText(
				"Registration Date: "+registration.getDateOfreg()+
				"\nVehicle Type: "+vehicle.getVehicleType()+
				"\nFuel Type: "+vehicle.getFuelType()+
				"\nManufactured year: "+vehicle.getManufactureYear()+
				"\nRegister Number: "+registration.getRegNo()+
				"\nExpiry Date: "+registration.getExpiryDate()
				);	
	}
	
	public void accept(ActionEvent event) throws SQLException, IOException {
		
		newOwner.getInfo();
		registration.getInfo();
		
		Alert acceptConfirmation = new Alert(AlertType.CONFIRMATION);
		acceptConfirmation.setTitle("Transfer Request Confirmation");
		acceptConfirmation.setHeaderText("Do you want to accept this registration");
		if(acceptConfirmation.showAndWait().get() == ButtonType.OK) {
			TransferRequestsDAO.acceptTransferRequest(newOwner,registration);
			acceptConfirmation = new Alert(AlertType.INFORMATION);
			acceptConfirmation.setTitle("Transfer successfull");
			acceptConfirmation.setHeaderText("Transfer of Ownership successfull");
			acceptConfirmation.showAndWait();
		}
		exit(event);
	}
	
	public void reject(ActionEvent event) throws SQLException, IOException {
		
		Alert acceptConfirmation = new Alert(AlertType.CONFIRMATION);
		acceptConfirmation.setTitle("Transfer Request Rejection");
		acceptConfirmation.setHeaderText("Do you want to reject this registration");
		
		if(acceptConfirmation.showAndWait().get() == ButtonType.OK) {
			TextInputDialog dialog = new TextInputDialog();
			dialog.setTitle("Remarks required");
			dialog.setHeaderText("Provide reason for rejection in brief ");
			dialog.setContentText("Remark: ");
			Optional<String> remark = dialog.showAndWait();
			if(remark.isPresent()) {
				String remarks = remark.toString();
				remarks = remarks.substring(9,remarks.length()-1);
				System.out.println(remarks);
				 TransferRequestsDAO.rejectTransferRequest(registration,remarks);
				exit(event);
			}else {
				System.out.println("No remark given");
			}
		}
	}
	
	public void exit(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("SignInAdmin.fxml"));
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	
}
