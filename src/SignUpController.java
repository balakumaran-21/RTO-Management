package application;

import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import rtoManagement.Owner;
import dbManagement.UserAuthentication;

public class SignUpController {
	private Stage stage;
	private Scene scene;
	private Parent root;
	@FXML
	TextField userNameField, ownerNameField, ownerAgeField, ownerContactNoField, ownerStateField, ownerDistrictField , ownerAddressField, adminNameField;
	String userName, password, confirmPassword,user_type, owner_name ,contactNo, state, district, address, admin_name;
	@FXML
	PasswordField passField,confirmPassField;
	int age;
	
	@FXML
	Button signUpOwner, signUpAdmin;
	
	@FXML 
	ToggleGroup userType;
	
	@FXML
	AnchorPane userDetails;
	@FXML
	AnchorPane adminDetails;
	
	@FXML
	RadioButton userRadioButton;
	
	@FXML
	RadioButton adminRadioButton;
	
	@FXML
	Label warningLabel;
	
	private void clearFieldsUser() {
		userNameField.setText("");
		passField.setText("");
		confirmPassField.setText("");
		ownerNameField.setText("");
		ownerAgeField.setText("");
		ownerContactNoField.setText("");
		ownerDistrictField.setText("");
		ownerStateField.setText("");
		ownerAddressField.setText("");
	}
	
	private void clearFieldsAdmin() {
		userNameField.setText("");
		passField.setText("");
		confirmPassField.setText("");
		adminNameField.setText("");
	}
	
	public void switchToSignInPage(ActionEvent event) throws IOException {
		System.out.println("Clicked sign in from sign up page");
		root = FXMLLoader.load(getClass().getResource("SignIn.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}
	
	public String selectedUserType() {
		if(userRadioButton.isSelected()) {
			user_type = userRadioButton.getText();
		}
		else{
			user_type = adminRadioButton.getText();
		}
		return user_type;
	}
	
	public void getUserDetails(ActionEvent event) throws SQLException {
		int id = 0;
		boolean isRegistered = false;
		userName = userNameField.getText();
		
		if(UserAuthentication.checkUserName(userName)) {
			warningLabel.setText("The username already exists");
			return;
		}else {
			warningLabel.setText(" ");
		}
		
		password = passField.getText();
		confirmPassword = confirmPassField.getText();
		if(!password.equals(confirmPassword)) {
			warningLabel.setText("The password entered doesn't match");
			return;
		}else {
			warningLabel.setText(" ");
			UserAuthentication.registerUser(userName, confirmPassword, selectedUserType());
			id = UserAuthentication.getUserID(userName, confirmPassword);
		}
		
		System.out.println("UserName: " +userNameField.getText());
		System.out.println("Password: " +passField.getText());
		System.out.println("Confirm pass: "+confirmPassField.getText());
		System.out.print("User Type: "+selectedUserType());;
		
		owner_name = ownerNameField.getText();
		age = Integer.parseInt(ownerAgeField.getText());
		if(age < 18) {
			warningLabel.setText("Age must be above 18");
			return;
		}
		else {
			warningLabel.setText(" ");
		}
		contactNo = ownerContactNoField.getText();
		state = ownerStateField.getText();
		district = ownerDistrictField.getText();
		address = ownerAddressField.getText();
		Owner owner = new Owner(id, owner_name, age, contactNo, state, district, address);
		owner.setOwnerID(id);
		owner.getInfo();
		UserAuthentication.registerOwner(owner);
		isRegistered = true;
		if(isRegistered) {
			Alert signedUpDialogBox = new Alert(AlertType.INFORMATION);
			signedUpDialogBox.setTitle("Registration successful");
			signedUpDialogBox.setHeaderText("You have sucessfully signed up");
			signedUpDialogBox.setContentText("Please sign in to continue");
			signedUpDialogBox.showAndWait();
		}
		clearFieldsUser();
	}
	
	
	
	public void getAdminDetails(ActionEvent event) throws SQLException {
		int id = 0;
		boolean isRegistered = false;
		userName = userNameField.getText();
		
		if(UserAuthentication.checkUserName(userName)) {
			warningLabel.setText("The username already exists");
			return;
		}else {
			warningLabel.setText(" ");
		}
		
		password = passField.getText();
		confirmPassword = confirmPassField.getText();
		if(!password.equals(confirmPassword)) {
			warningLabel.setText("The password entered doesn't match");
			return;
		}else {
			warningLabel.setText(" ");
			UserAuthentication.registerUser(userName, confirmPassword, selectedUserType());
			id = UserAuthentication.getUserID(userName, confirmPassword);
		}
		admin_name = adminNameField.getText();
		UserAuthentication.registerAdmin(id, admin_name);
		isRegistered = true;
		if(isRegistered) {
			Alert signedUpDialogBox = new Alert(AlertType.INFORMATION);
			signedUpDialogBox.setTitle("Registration successful");
			signedUpDialogBox.setHeaderText("You have sucessfully signed up");
			signedUpDialogBox.setContentText("Please sign in to continue");
			signedUpDialogBox.showAndWait();
		}
		clearFieldsAdmin();
	}
	
	public void toggleGetUserInfo() {
		System.out.println("User radio button clicked");
		userDetails.setVisible(true);
		adminDetails.setVisible(false);
	}
	
	public void toggleGetAdminInfo() {
		System.out.println("Admin radio button clicked");
		adminDetails.setVisible(true);
		userDetails.setVisible(false);
	}
	
}
