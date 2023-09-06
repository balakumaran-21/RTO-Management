package application;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Optional;

import dbManagement.RenewalRequestDAO;
import dbManagement.VehicleRequestsDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import rtoManagement.Registration;
import rtoManagement.Vehicle;

public class RenewRegAdminCardController {
	
	Parent root;
	Stage stage;
	Scene scene;
	
	@FXML
	private Label idLabel, regDateLabel, expDateLabel, regNoLabel, vehicleTypeLabel, fuelTypeLabel, manYearLabel;
	
	public void setData(Registration registration,Vehicle vehicle) {
    	
		idLabel.setText(new Integer(registration.getOwnerID()).toString());
    	regDateLabel.setText(registration.getDateOfreg().toString());
    	expDateLabel.setText(registration.getExpiryDate().toString());
    	regNoLabel.setText(registration.getRegNo());
    	vehicleTypeLabel.setText(vehicle.getVehicleType());
    	fuelTypeLabel.setText(vehicle.getFuelType());
    	manYearLabel.setText(new Integer(vehicle.getManufactureYear()).toString());
    	
    }
    
    private Registration getRegistrationData() {
    	
    	int ownerID = Integer.parseInt(idLabel.getText());
    	Date regDate = Date.valueOf(regDateLabel.getText());
    	Date expDate = Date.valueOf(expDateLabel.getText());
    	String regNo = regNoLabel.getText();
    	Registration registration = new Registration(ownerID, regDate, expDate, regNo);
    	return registration;
    	
    }
    
    private Vehicle getVehicleData() {
    	
    	int manYear = Integer.parseInt(manYearLabel.getText());
    	String vehicleType = vehicleTypeLabel.getText();
    	String fuelType = fuelTypeLabel.getText();		
    	Vehicle vehicle = new Vehicle(vehicleType,fuelType,manYear);
    	return vehicle;
    	
    }
    
    public void updateUserSide(ActionEvent event) throws SQLException, IOException {
    	Alert renewalAlert = new Alert(AlertType.CONFIRMATION);
    	renewalAlert.setTitle("Renewal request Confirmation");
    	renewalAlert.setHeaderText("Do you want to accept the renewal request");
    	if(renewalAlert.showAndWait().get() == ButtonType.OK) {
    		RenewalRequestDAO.updateStatusAccepted(getRegistrationData(),getVehicleData());
    	}else {
    		
    		renewalAlert = new Alert(AlertType.CONFIRMATION);
    		renewalAlert.setTitle("Reject Renewal");
    		renewalAlert.setHeaderText("Do you want to reject the renewal");
    		
    		if(renewalAlert.showAndWait().get() == ButtonType.OK) {
    			
    			TextInputDialog dialog = new TextInputDialog();
    			dialog.setTitle("Remarks required");
    			dialog.setHeaderText("Provide reason for rejection in brief ");
    			dialog.setContentText("Remark: ");
    			Optional<String> remark = dialog.showAndWait();
    			if(remark.isPresent()) {
    				String remarks = remark.toString();
    				remarks = remarks.substring(9,remarks.length()-1);
    				System.out.println(remarks);
    				RenewalRequestDAO.updateStatusRejected(getRegistrationData(), remarks);
    				exit(event);
    			}
    		}
    			
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
