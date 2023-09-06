package rtoManagement;

import java.util.Scanner;

public class Vehicle {
	private String regNo;
	private Owner owner;
	private String fuelType;
	private int manufactureYear;
	private String vehicleType;
	

	static Scanner input =  new Scanner(System.in);

	public Vehicle() {
		
		System.out.print("Enter fuel type of the vehicle: ");
		fuelType = input.nextLine();
		
		System.out.print("Enter manufacture Year of the vehicle: ");
		manufactureYear = input.nextInt();
		
		System.out.println("Choose vehicle type:\n"
				+ "1. Two Wheeler\n"
				+ "2. Four Wheeler\n"
				+ "3. Heavy load Trucks\n");
		int choice = input.nextInt();
		input.nextLine();
		if(choice == 1){
			vehicleType = "Two Wheeler";
		}else if(choice == 2) {
			vehicleType = "Four Wheeler";
		}else if(choice == 3) {
			vehicleType = "Heavy load Trucks";
		}		
	}
	
	public Vehicle(String vehicleType, String fuelType, int manufactureYear) {
		this.vehicleType = vehicleType;
		this.fuelType = fuelType;
		this.manufactureYear = manufactureYear;
	}
	
	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}
	
	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public String getFuelType() {
		return fuelType;
	}

	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}

	public int getManufactureYear() {
		return manufactureYear;
	}

	public void setManufactureYear(int manufactureYear) {
		this.manufactureYear = manufactureYear;
	}

	public void getInfo() {
		System.out.println("----------------------------------------------------------------------------------------------------------------");
		System.out.println("Vehicle Details:");
		System.out.println("----------------------------------------------------------------------------------------------------------------");
		System.out.println("Fuel Type: "+fuelType);
		System.out.println("Year of Manufacture: "+manufactureYear);
		System.out.println("Vehicle Type: "+vehicleType);
		System.out.println("Registration Number: "+regNo);
		System.out.println("----------------------------------------------------------------------------------------------------------------");
	}
	
}
