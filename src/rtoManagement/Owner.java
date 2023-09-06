package rtoManagement;

import java.util.Random;

import java.util.Scanner;


public class Owner {
	private int ownerID;
	private String name;
	private int age;
	private String contactNo;
	private String address;
	private String state;
	private String district;
	

	static Scanner input = new Scanner(System.in);
	
	
	
	public Owner( String name, int age, String contactNo, String state , String district,String address) {
		this.name = name;
		this.age = age;
		this.contactNo = contactNo;
		this.state = state;
		this.district = district; 
		this.address = address;
	}
	
	public int getOwnerID() {
		return ownerID;
	}

	public void setOwnerID(int ownerID) {
		this.ownerID = ownerID;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public String getContactNo() {
		return contactNo;
	}
	
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	
	public String getAddress() {
		return address;
	}
	
	public String getState() {
		return state;
	}
	
	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}
	
	public void getInfo() {
		System.out.println("----------------------------------------------------------------------------------------------------------------");
		System.out.println("Owner Details:");
		System.out.println("----------------------------------------------------------------------------------------------------------------");
		System.out.println("ID: "+ownerID);
		System.out.println("Name: "+name);
		System.out.println("Age: "+age);
		System.out.println("Contact number: "+contactNo);
		System.out.println("State :"+state);
		System.out.println("District: "+district);
		System.out.println("Address: "+address);
		System.out.println("-----------------------------------------------------------------------------------------------------------------");
	}
}
