package application;

import java.io.IOException;
import java.sql.SQLException;

import dbManagement.OwnerDAO;
import dbManagement.VehicleRequestsDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import rtoManagement.Owner;
import rtoManagement.Registration;
import rtoManagement.TransferRequest;
import rtoManagement.Vehicle;

public class TransferRequestsCardController {
	
	@FXML
    private Label current_id_label, new_owner_id_label, reg_no_label;
	
	public void setData(TransferRequest transferRequest) {
		current_id_label.setText(new Integer(transferRequest.getCurrOwnerID()).toString());
		new_owner_id_label.setText(new Integer(transferRequest.getNewOwnerID()).toString());
		reg_no_label.setText(transferRequest.getRegNo());
	}
	
	public TransferRequest getData() {
		
		int curr_id = new Integer(current_id_label.getText());
		int new_id = new Integer(new_owner_id_label.getText());
		String reg_no = reg_no_label.getText();
		TransferRequest transferRequest = new TransferRequest(curr_id, new_id, reg_no);
		return transferRequest;
		
	}
	
	public void moreDetails(ActionEvent event) throws SQLException, IOException {
		
		TransferRequest transferRequest = getData();
		Owner currOwner = OwnerDAO.fetchOwnerDetails(transferRequest.getCurrOwnerID());
		Owner newOwner = OwnerDAO.fetchOwnerDetails(transferRequest.getNewOwnerID());
		Registration registration = VehicleRequestsDAO.getRegistrationDetails(transferRequest.getRegNo());
		Vehicle vehicle = VehicleRequestsDAO.getVehicleDetails(transferRequest.getRegNo());
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource( "MoreDetails.fxml" ));
		Parent root = loader.load();
		MoreDetailsController moreDetailsController = loader.getController();
		moreDetailsController.setData(currOwner, newOwner, registration, vehicle);
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
}
