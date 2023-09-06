package application;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import dbManagement.VehicleRequestsDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import rtoManagement.Owner;
import rtoManagement.Registration;
import rtoManagement.Vehicle;

public class ViewMoreDetailsController{
	
	Parent root;
	Scene scene;
	Stage stage;
	
	Owner owner;
	Registration registration;
	Vehicle vehicle;
	
	@FXML
	Label ownerDetails, registrationDetails, vehiclesCount;
	
	public void setDetails(Owner owner, Registration registration, Vehicle vehicle) throws SQLException {
		
		this.owner = owner;
		this.registration = registration;
		this.vehicle = vehicle;
		System.out.println("Printed from setDetails");
		this.owner.getInfo();
		this.registration.getInfo();
		this.vehicle.getInfo();
		
		ownerDetails.setText(
				"ID: "+owner.getOwnerID()+
				"\nName: "+owner.getName()+
				"\nAge: "+owner.getAge()+
				"\nContact Number: "+owner.getContactNo()+
				"\nState: "+owner.getState()+
				"\nDistrict: "+owner.getDistrict()+
				"\nAddress: "+owner.getAddress()
				);
		registrationDetails.setText(
				"Registration Date: "+registration.getDateOfreg()+
				"\nVehicle Type: "+vehicle.getVehicleType()+
				"\nFuel Type: "+vehicle.getFuelType()+
				"\nManufactured year: "+vehicle.getManufactureYear()+
				"\nRegister Number: "+registration.getRegNo()+
				"\nExpiry Date: "+registration.getExpiryDate()
				);
		vehiclesCount.setText("Total number of vehicles registered by "+owner.getName()+": "+VehicleRequestsDAO.countOfRegisteredVehicles(registration.getOwnerID()));
	}
	
	public void accept(ActionEvent event) throws SQLException, IOException {
		
		VehicleRequestsDAO.updateAcceptVehicleRequest(registration);
		Alert acceptInfo = new Alert(AlertType.INFORMATION);
		acceptInfo.setTitle("Registration Approved");
		acceptInfo.setHeaderText("The user "+owner.getOwnerID()+" has been approved");
		if(acceptInfo.showAndWait().get() == ButtonType.OK) {
			exit(event);
		}
		
		
	}
	
	public void reject(ActionEvent event) throws SQLException, IOException {
		
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Remarks required");
		dialog.setHeaderText("Provide reason for rejection in brief ");
		dialog.setContentText("Remark: ");
		Optional<String> remark = dialog.showAndWait();
		if(remark.isPresent()) {
			String remarks = remark.toString();
			remarks = remarks.substring(9,remarks.length()-1);
			System.out.println(remarks);
			VehicleRequestsDAO.updateRejectVehicleRequest(registration,remarks);
			exit(event);
		}else {
			System.out.println("No remark given");
			
		}
	}
	
	public void exit(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("SignInAdmin.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
}
