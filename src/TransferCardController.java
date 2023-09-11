package application;

import java.sql.SQLException;

import dbManagement.OwnerDAO;
import dbManagement.VehicleRequestsDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import rtoManagement.Owner;
import rtoManagement.Registration;
import rtoManagement.TransferRequest;
import rtoManagement.Vehicle;

public class TransferCardController {
    @FXML
    private Label expDateLabel;

    @FXML
    private Label fuelTypeLabel;

    @FXML
    private Label manufactureYearLabel;

    @FXML
    private Label regDateLabel;

    @FXML
    private Label regNoLabel;

    @FXML
    private Label tranferStatusLabel;

    @FXML
    private Label userAddressLabel;

    @FXML
    private Label userAgeLabel;

    @FXML
    private Label userContactLabel;

    @FXML
    private Label userDistrictLabel;

    @FXML
    private Label userIdLabel;

    @FXML
    private Label userNameLabel;

    @FXML
    private Label userStateLabel;

    @FXML
    private Label vehicleTypeLabel;
    
    public void setData(TransferRequest transferRequest) throws SQLException {
    	
    	Owner owner = OwnerDAO.fetchOwnerDetails(transferRequest.getNewOwnerID());
    	Registration registration = VehicleRequestsDAO.getRegistrationDetails(transferRequest.getRegNo());
		Vehicle vehicle = VehicleRequestsDAO.getVehicleDetails(transferRequest.getRegNo());
    	
    	userIdLabel.setText(new Integer(owner.getOwnerID()).toString());
		userNameLabel.setText(owner.getName());
		userAgeLabel.setText(""+owner.getAge());
		userContactLabel.setText(owner.getContactNo());
		userStateLabel.setText(owner.getState());
		userDistrictLabel.setText(owner.getDistrict());
		userAddressLabel.setText(owner.getAddress());
		
		regDateLabel.setText(registration.getDateOfreg().toString());
    	expDateLabel.setText(registration.getExpiryDate().toString());
    	vehicleTypeLabel.setText(vehicle.getVehicleType());
    	fuelTypeLabel.setText(vehicle.getFuelType());
    	manufactureYearLabel.setText(""+vehicle.getManufactureYear());
    	regNoLabel.setText(registration.getRegNo());
		
    	
    	
    }
    
}
