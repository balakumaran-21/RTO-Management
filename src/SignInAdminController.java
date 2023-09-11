package application;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import rtoManagement.Owner;
import rtoManagement.Registration;
import rtoManagement.TransferRequest;
import rtoManagement.Vehicle;
import dbManagement.OwnerDAO;
import dbManagement.RenewalRequestDAO;
import dbManagement.TransferRequestsDAO;
import dbManagement.VehicleRequestsDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SignInAdminController implements Initializable{
	
	Parent root;
	Scene scene;
	Stage stage;

    @FXML
    private Button logoutButton, renewalRequestsButton, transferRequestsButton, vehicleRequestsButton, ownersListButton;

    @FXML
    private Label welcomeLabel;
    
    @FXML
    private Pane vehicleRequestsPane, transferRequestsPane, renewalRequestsPane, ownersListPane;
    
    @FXML 
    VBox vehicleRegVbox, renewalReqsVbox, tranferRequestsVBox, ownersListVBox;
    
    int id = 0;

    
    @Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
    	try {
    		renderVehicleReqInitially();
    		renderRenewalRequests();
    		renderTransferRequests();
		} catch (IOException e) {
			System.out.println("Something went wrong rendering details");
		} catch (SQLException e) {
			System.out.println("Something went wrong fetching details");
		}
	}
    
	public void displayWelcomeMessage(String name, int user_id){
		welcomeLabel.setText("Welcome Admin "+name+" 🤗");
		id = user_id;
	}
    
	/*********************************** Rendering panes based on button clicks ******************/
    public void handleClicks(ActionEvent event) throws SQLException, IOException {
    	
    	if(event.getSource() == vehicleRequestsButton) {
    		vehicleRequestsButton.getStyleClass().add("button-clicked");
    		renewalRequestsButton.getStyleClass().remove("button-clicked");
    		transferRequestsButton.getStyleClass().remove("button-clicked");
    		ownersListButton.getStyleClass().remove("button-clicked");
    		vehicleRequestsPane.toFront();
    		renderVehicleRequests(event);
    	}
    	else if(event.getSource() == renewalRequestsButton) {
    		renewalRequestsButton.getStyleClass().add("button-clicked");
    		vehicleRequestsButton.getStyleClass().remove("button-clicked");
    		transferRequestsButton.getStyleClass().remove("button-clicked");
    		ownersListButton.getStyleClass().remove("button-clicked");
    		renewalRequestsPane.toFront();
    		renderRenewalRequests(event);
    	}
    	else if(event.getSource() == transferRequestsButton) {
    		transferRequestsButton.getStyleClass().add("button-clicked");
    		vehicleRequestsButton.getStyleClass().remove("button-clicked");
    		renewalRequestsButton.getStyleClass().remove("button-clicked");
    		ownersListButton.getStyleClass().remove("button-clicked");
    		transferRequestsPane.toFront();
    		renderTransferRequests(event);
    	}
    	else if(event.getSource() == ownersListButton) {
    		ownersListButton.getStyleClass().add("button-clicked");
    		transferRequestsButton.getStyleClass().remove("button-clicked");
    		vehicleRequestsButton.getStyleClass().remove("button-clicked");
    		renewalRequestsButton.getStyleClass().remove("button-clicked");
    		ownersListPane.toFront();
    		renderGenerateReportCard(event);
    	}

    }
    /***********************************************************************************************/
    
    /****************************** Vehicle Request details  initially and during actionevent ********************/
    public void renderVehicleReqInitially() throws IOException, SQLException {
    	ArrayList<Registration> registrationRequests = VehicleRequestsDAO.registrationsRequests();
    	ArrayList<Vehicle> vehicleRequests = VehicleRequestsDAO.vehiclesRequests();
    	vehicleRegVbox.getChildren().clear();
    	
    	for(int i = 0; i < registrationRequests.size(); i++) {
    		Registration registration = registrationRequests.get(i);
    		Vehicle vehicle = vehicleRequests.get(i);
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("VehicleRegRequestCard.fxml"));
    		HBox vehicleRegCard = loader.load();
    		VehicleRegRequestCardController vehicleRequestCardController = loader.getController();
    		vehicleRequestCardController.setData(registration,vehicle);
    		vehicleRegVbox.getChildren().add(vehicleRegCard);
    	}
    }
    
    public void renderVehicleRequests(ActionEvent event) throws SQLException, IOException {
    	
    	ArrayList<Registration> registrationRequests = VehicleRequestsDAO.registrationsRequests();
    	ArrayList<Vehicle> vehicleRequests = VehicleRequestsDAO.vehiclesRequests();
    	vehicleRegVbox.getChildren().clear();
    	
    	for(int i = 0; i < registrationRequests.size(); i++) {
    		Registration registration = registrationRequests.get(i);
    		Vehicle vehicle = vehicleRequests.get(i);
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("VehicleRegRequestCard.fxml"));
    		HBox vehicleRegCard = loader.load();
    		VehicleRegRequestCardController vehicleRequestCardController = loader.getController();
    		vehicleRequestCardController.setData(registration,vehicle);
    		vehicleRegVbox.getChildren().add(vehicleRegCard);
    	}
    	
    }
    /*************************************************************************************************************/
    
    /***************************************** Renewal Requests **************************************************/
    
    public void renderRenewalRequests() throws SQLException, IOException {
    	ArrayList<Registration> renewalRegsreqs = RenewalRequestDAO.showRenewableRegistrationsToAdmin();
    	ArrayList<Vehicle> renewalvehicleRequests = RenewalRequestDAO.showRenewableVehiclesToAdmin();
    	renewalReqsVbox.getChildren().clear();
    	
    	for(int i = 0; i < renewalRegsreqs.size(); i++) {
    		
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("RenewRegAdminCard.fxml"));
    		HBox renewRegAdmincard = loader.load();
    		RenewRegAdminCardController renewRegAdminCardController = loader.getController();
    		renewRegAdminCardController.setData(renewalRegsreqs.get(i),renewalvehicleRequests.get(i));
    		renewalReqsVbox.getChildren().add(renewRegAdmincard);
    		
    	}
    }
    
    public void renderRenewalRequests(ActionEvent event) throws SQLException, IOException {
    	
    	ArrayList<Registration> renewalRegsreqs = RenewalRequestDAO.showRenewableRegistrationsToAdmin();
    	ArrayList<Vehicle> renewalvehicleRequests = RenewalRequestDAO.showRenewableVehiclesToAdmin();
    	renewalReqsVbox.getChildren().clear();
    	
    	for(int i = 0; i < renewalRegsreqs.size(); i++) {
    		
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("RenewRegAdminCard.fxml"));
    		HBox renewRegAdmincard = loader.load();
    		RenewRegAdminCardController renewRegAdminCardController = loader.getController();
    		renewRegAdminCardController.setData(renewalRegsreqs.get(i),renewalvehicleRequests.get(i));
    		renewalReqsVbox.getChildren().add(renewRegAdmincard);
    	}
    	
    }
    
    /*************************************************************************************************************/
    
    /************************************ render transfer requests ***********************************************/
    
    public void renderTransferRequests() throws SQLException, IOException {
    	
    	ArrayList<TransferRequest> transferRequests = TransferRequestsDAO.showAllTransferRequests();
    	tranferRequestsVBox.getChildren().clear();
    	for(TransferRequest transferRequest: transferRequests) {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("TransferRequestCard.fxml"));
    		HBox transferReqcard = loader.load();
    		TransferRequestsCardController transferRequestsCardController = loader.getController();
    		transferRequestsCardController.setData(transferRequest);
    		tranferRequestsVBox.getChildren().add(transferReqcard);
    	}
    
    }
    
    public void renderTransferRequests(ActionEvent event) throws SQLException, IOException {
    	
    	ArrayList<TransferRequest> transferRequests = TransferRequestsDAO.showAllTransferRequests();
    	tranferRequestsVBox.getChildren().clear();
    	for(TransferRequest transferRequest: transferRequests) {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("TransferRequestCard.fxml"));
    		HBox transferReqcard = loader.load();
    		TransferRequestsCardController transferRequestsCardController = loader.getController();
    		transferRequestsCardController.setData(transferRequest);
    		tranferRequestsVBox.getChildren().add(transferReqcard);
    	}
    
    }
    
    /*************************************************************************************************************/
    
    /************************************************ Generate report *********************************************/
    
    public void renderGenerateReportCard(ActionEvent event) throws IOException, SQLException {
    	
    	ArrayList<Owner> owners = OwnerDAO.getAllOwners();
    	ownersListVBox.getChildren().clear();
    	for(Owner owner:owners) {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("GenerateReportCard.fxml"));
    		HBox reportCard = loader.load();
    		GenerateReportCardController generateReportCardController = loader.getController();
    		generateReportCardController.setData(owner);
    		ownersListVBox.getChildren().add(reportCard);
    	}
    	
    }
    
    /*************************************************************************************************************/
    
    
    /************************************************* Logout ****************************************************/
    public void logout(ActionEvent event) throws IOException {

		Alert logoutAlert = new Alert(AlertType.CONFIRMATION);
		logoutAlert.setTitle("Logout");
		logoutAlert.setHeaderText("You are about to logout");
		logoutAlert.setContentText("Your information might not be saved");
		
		if(logoutAlert.showAndWait().get() == ButtonType.OK) {
			root = FXMLLoader.load(getClass().getResource("SignIn.fxml"));
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();			
		}
	/************************************************************************************************************/
	}

	
    
}