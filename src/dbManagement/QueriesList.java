package dbManagement;

public class QueriesList {
/************************ View All Owner details *********************/
	public static String ownerDetails = "select * from owners";
	
/************************* View All vehicle details *******************/
	public static String vehicleDetails = "select * from veicles";
	
/************************ View All registrations *********************/	
	public static String registrationDetails = "select * from registrations";
	
/*********************** Register:  Insert info owners table *********************/
	public static String insertOwner = "insert into owners values(?,?,?,?,?,?,?)";
	
/********************** Registration: Getting the userID generated for the user **************************/	
	public static String getUserID = "select user_id from users where user_name = ? and user_password = ?";
	
/*********************** ValidateLogin: Check wether password and username match ********************/
	public static String checkLoginValidity = "select count(*) from users where user_name = ? and user_password = ?";
	
/*********************** Registration: Insert admin info int admins table ******************************/
	public static String insertAdmin = "insert into admins values(?,?)";

/******************** Register: Insert into users table***********************/
	public static String insertUser = "insert into users(user_name,user_password,user_type) values(?,?,?)";
	
/********************** Register: Username validity checking *******************/
	public static String checkUserInTable = "select user_name from users where user_name = ?";
	
/********************** Login: Getting userType to check user or owner ****************/
	public static String decidingUser = "select user_type from users where user_id = ?";
	
/********************** Logged in: Getting name from owner class ******************/	
	public static String getOwnerName = "select owner_name from owners where owner_id = ?";

/********************** Logged in: Getting name from owner class ******************/	
	public static String getAdminName = "select admin_name from admins where admin_id = ?";
	
/********************* Logged in: Registering a new vehicle ***********************/
	public static String insertVehicles = "insert into vehicles(ownerId, vehicle_type, fuel_type, man_year, reg_no) values(?,?,?,?,?)";
	
/********************* Logged in: Insert unpdates into registrations table **************/
	public static String insertRegistrations = "insert into registrations values(?,?,?,?)";
	
/*************** Logged in: get user state and user city from table ********************/
	public static String getStateAndCity = "select owner_state,owner_district from owners where owner_id = ?";
	
/*************** Logged in: Check for district code in the districtCodes table ************/
	public static String checkDistrictCode = "select district, districtCode from districtCodes where state = ?";
	
/*************** Logged in: Add district code to the **************/	
	public static String insertDistrictCodes = "insert into districtCodes values(?,?,?,?)";
	
/*************** Logged in: User registration request of new vehicle to admin ************/
	public static String reqNewRegVehicle = "insert into vehicle_registration_status(ownerID, reg_date, expiry_date, vehicle_type, fuel_type, man_year, reg_no) values(?,?,?,?,?,?,?)";

/**************** Logged in:  get user details on profile section *************************/
	public static String getUserDetails = "select owner_name, owner_age, owner_contactNo, owner_state, owner_district, owner_address from owners where owner_id = ?" ;
	
}
