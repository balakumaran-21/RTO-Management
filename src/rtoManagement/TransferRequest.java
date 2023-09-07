package rtoManagement;

public class TransferRequest {

	int currOwnerID;
	int newOwnerID;
	String regNo;
	
	public TransferRequest(int currOwnerID, int newOwnerID, String regNo) {
		
		this.currOwnerID = currOwnerID;
		this.newOwnerID = newOwnerID;
		this.regNo = regNo;
		
	}
	
	public int getCurrOwnerID() {
		
		return currOwnerID;
	
	}

	public void setCurrOwnerID(int currOwnerID) {
	
		this.currOwnerID = currOwnerID;
	
	}

	public int getNewOwnerID() {
		
		return newOwnerID;
	
	}

	public void setNewOwnerID(int newOwnerID) {
		
		this.newOwnerID = newOwnerID;
		
	}

	public String getRegNo() {
		
		return regNo;
		
	}

	public void setRegNo(String regNo) {
		
		this.regNo = regNo;
		
	}

	public void getInfo() {
		System.out.println("-----------------------------------------");
		System.out.println("Transfer Request Details");
		System.out.println("-----------------------------------------");
		System.out.println("Current owner ID: "+currOwnerID);
		System.out.println("New owner ID: "+newOwnerID);
		System.out.println("Register number: "+regNo);
		System.out.println("-----------------------------------------");		
	}
	
}
