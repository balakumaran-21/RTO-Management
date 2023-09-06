package rtoManagement;

import java.util.Random;

import java.util.Scanner;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;


public class Registration {
	private Date dateOfreg;
	private Vehicle regVehicle;
	private Owner owner;
	private Date expiryDate;
	private int ownerID;
	private String reg_no;

	public Date getDateOfreg() {
		return dateOfreg;
	}
	
	public void setDateOfreg(Date dateOfreg) {
		this.dateOfreg = dateOfreg;
	}
	
	public Date getExpiryDate() {
		return expiryDate;
	}
	
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	
//	private static Map<String,String> tnDistrictCodes = new HashMap<>();
//	private static Map<String,String> klDistrictCodes = new HashMap<>();
//	private static Map<String,String> apDistrictCodes = new HashMap<>();
//	private static Map<String,String> tsDistrictCodes = new HashMap<>();
//	private static Map<String,String> kaDistrictCodes = new HashMap<>();
//	private static Map<String,String> mhDistrictCodes = new HashMap<>();
//	static Scanner input = new Scanner(System.in);
//	private int genRegNo(int min, int max) {
//		Random rand = new Random();
//		int id = rand.nextInt(min,max);
//		return id;
//	}
	
	public Registration() {
		LocalDate date = null;
		this.dateOfreg = Date.valueOf(date.now());
		expiryDate = Date.valueOf(date.now().plusYears(2));
	}
	
	public Registration(Owner owner, Vehicle vehicle) {
		LocalDate date = null;
		this.dateOfreg = Date.valueOf(date.now());
		this.owner = owner;
		this.regVehicle = vehicle;
		expiryDate = Date.valueOf(date.now().plusYears(2));
	}
	
	public Registration(int ownerID) {
		LocalDate date = null;
		this.dateOfreg = Date.valueOf(date.now());
		this.setOwnerID(ownerID);
//		this.setRegNo(regNo);
		expiryDate = Date.valueOf(date.now().plusYears(2));
	}

	public Registration(int ownerID, Date dateOfReg, Date expiryDate, String reg_no) {
		this.ownerID = ownerID;
		this.dateOfreg = dateOfReg;
		this.expiryDate = expiryDate;
		this.reg_no = reg_no;
	}
	
	public Registration(int ownerID, String reg_no) {
		this.ownerID = ownerID;
		this.reg_no = reg_no;
	}

	public int getOwnerID() {
		return ownerID;
	}

	public void setOwnerID(int ownerID) {
		this.ownerID = ownerID;
	}

	public String getRegNo() {
		return reg_no;
	}

	public void setRegNo(String regNo) {
		this.reg_no = regNo;
	}

//	private String getDistrictCode(Map<String,String>districtCodes, String district){
//		String districtCode;
//		Integer districtCount = (districtCodes.isEmpty())?1:districtCodes.size()+1;
//		if(!districtCodes.containsKey(district)) {
//			districtCodes.put( district, (districtCount < 10) ? "0"+districtCount.toString(): districtCount.toString());
//		}
//			districtCode = districtCodes.get(district);
//			System.out.println("Code generated: "+districtCode);
//		return districtCode;
//		
//	}
//	public void generateRegistrationNumber(String state,String district) {
//		String regNo = "";
//		String stateCode = null,districtCode = null;
//		state = state.toLowerCase();
//
//		if(state.compareToIgnoreCase("tamil nadu") == 0) {
//			stateCode = "TN";
//			districtCode = getDistrictCode(tnDistrictCodes, district);
//		}else if(state.compareToIgnoreCase("kerala") == 0) {
//			stateCode = "KL";
//			districtCode = getDistrictCode(klDistrictCodes, district);
//		}else if(state.compareToIgnoreCase("andhra pradesh") == 0) {
//			stateCode = "AP";
//			districtCode = getDistrictCode(apDistrictCodes, district);
//		}else if(state.compareToIgnoreCase("telangana") == 0){
//			stateCode = "TS";
//			districtCode = getDistrictCode(tsDistrictCodes, district);
//		}else if(state.compareToIgnoreCase("karnataka") == 0) {
//			stateCode = "KA";
//			districtCode = getDistrictCode(kaDistrictCodes, district);
//		}else if(state.compareToIgnoreCase("maharashtra") == 0) {
//			stateCode = "MH";
//			districtCode = getDistrictCode(mhDistrictCodes, district);
//		}else {
//			System.out.println("The entered state doesn't exist.");
//		}
//		char randomChar = (char)genRegNo(65, 91);
//		int randomNum = genRegNo(1000, 9999);
//		regNo = stateCode + districtCode + randomChar + randomNum;
//		System.out.println("Your register number: "+regNo+"\n"
//				+ "Would you like to continue registering:\n"
//				+ "1. Yes\n"
//				+ "2. No");
//		System.out.println("Your Choice: ");
//		
//	}
	
	public void getInfo() {
		System.out.println("---------------------------------------------------");
		System.out.println("Registration details:");
		System.out.println("---------------------------------------------------");
		System.out.println("Registration date: "+ dateOfreg.toString());
		System.out.println("Owner ID: "+ownerID);
		System.out.println("Register Number: "+ reg_no);
		System.out.println("Expiry date: "+expiryDate.toString());
		System.out.println("---------------------------------------------------");
	}
	
}
