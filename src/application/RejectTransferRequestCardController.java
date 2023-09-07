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

public class RejectTransferRequestCardController {
	
	
	@FXML
    private Label newOwnerDetailsLabel, registrationDetailsLabel, remarksLabel;
	
	Owner newOwner = null;
	Registration registration;
	Vehicle vehicle;
	String remark;
	
	public void setData(TransferRequest transferRequest, String remark) throws SQLException {
		
		this.newOwner = OwnerDAO.fetchOwnerDetails(transferRequest.getNewOwnerID());
		this.registration = VehicleRequestsDAO.getRegistrationDetails(transferRequest.getRegNo());
		this.vehicle = VehicleRequestsDAO.getVehicleDetails(transferRequest.getRegNo());
		this.remark = remark;
		
		newOwnerDetailsLabel.setText(
				"Name: "+newOwner.getName()+
				"\nAge: "+newOwner.getAge()+
				"\nContact Number: "+newOwner.getContactNo()+
				"\nState: "+newOwner.getState()+
				"\nDistrict: "+newOwner.getDistrict()+
				"\nAddress: "+newOwner.getAddress()
				);
		
		registrationDetailsLabel.setText(
				"Registration Date: "+registration.getDateOfreg()+
				"\nVehicle Type: "+vehicle.getVehicleType()+
				"\nFuel Type: "+vehicle.getFuelType()+
				"\nManufactured year: "+vehicle.getManufactureYear()+
				"\nRegister Number: "+registration.getRegNo()+
				"\nExpiry Date: "+registration.getExpiryDate()
				);
		
		remarksLabel.setText(remark);
		
	}
	
}
