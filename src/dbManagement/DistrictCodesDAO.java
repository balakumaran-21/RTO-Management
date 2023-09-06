package dbManagement;

import java.sql.*; 
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DistrictCodesDAO {
	static Random random = new Random();
	
	public static String generateRegNo(String state, String district) throws SQLException {
		String stateCode = getStatecode(state);
		String districtCode = getDistrictCode(state,district);
		char alphabet = randomChar();
		String number = randomNum();
		String regNo = stateCode + districtCode + alphabet + number;
		
		return regNo;
	}
	
	private static String randomNum() {
		Integer num = random.nextInt(1000,9999);
		return num.toString();
	}
	
	private static char randomChar() {
		int num = random.nextInt(65,91);
		return (char)num;
	} 
	
	private static String getDistrictCode(String state, String district) throws SQLException {
		Integer districtCode = 1;
		// Cheking district code in the table
		String query = QueriesList.checkDistrictCode;
		Connection con = DBconnect.connectDB();
		PreparedStatement pst = con.prepareStatement(query);
		pst.setString(1, state);
		ResultSet rs = pst.executeQuery();
		
		HashMap<String,Integer> districtCodesMap = new HashMap<String, Integer>();
		int max = 0;
		while(rs.next()) {
				districtCode = rs.getInt(2);
				districtCodesMap.put(rs.getString(1), districtCode);
				if(max < districtCode) {
					districtCode = max;
				}
		}
		
		for(Map.Entry<String,Integer> m:districtCodesMap.entrySet()) {
			System.out.println("----------------------------------------------------------------------------------------------------------------");
			System.out.println(m.getKey()+":"+m.getValue());
			System.out.println("----------------------------------------------------------------------------------------------------------------");
		}System.out.println(districtCode);
		
		if(districtCodesMap.containsKey(district)) {
			districtCode = districtCodesMap.get(district);
		}else {
			String insertQuery = QueriesList.insertDistrictCodes;
			Connection conn = DBconnect.connectDB();
			PreparedStatement ipst = conn.prepareStatement(insertQuery);
			ipst.setString(1, state);
			ipst.setString(2, getStatecode(state));
			ipst.setString(3, district);
			ipst.setInt(4, districtCode);
			int rows = ipst.executeUpdate();
			System.out.println("Updated districtCodes rows: "+rows);
			conn.close();
		}
		
		String distCode = (districtCode < 10)?'0'+districtCode.toString():districtCode.toString();
		return distCode;
	}

	private static String getStatecode(String state) {
		
		String statecode = "";
		if(state.equalsIgnoreCase("andhra pradesh")) statecode = "AP";
		else if(state.equalsIgnoreCase("arunachal pradesh")) statecode = "AR";
		else if(state.equalsIgnoreCase("assam")) statecode = "AS";
		else if(state.equalsIgnoreCase("bihar")) statecode = "BR";
		else if(state.equalsIgnoreCase("chandigarh")) statecode = "CH";
		else if(state.equalsIgnoreCase("chhattisgarh")) statecode = "CT";
		else if(state.equalsIgnoreCase("delhi")) statecode = "DL";
		else if(state.equalsIgnoreCase("goa")) statecode = "GA";
		else if(state.equalsIgnoreCase("gujarat")) statecode = "GJ";
		else if(state.equalsIgnoreCase("haryana")) statecode = "HR";
		else if(state.equalsIgnoreCase("himachal pradesh")) statecode = "HP";
		else if(state.equalsIgnoreCase("jammu and kashmir")) statecode = "JK";
		else if(state.equalsIgnoreCase("jharkhand")) statecode = "JH";
		else if(state.equalsIgnoreCase("karnataka")) statecode = "KA";
		else if(state.equalsIgnoreCase("kerala")) statecode = "KL";
		else if(state.equalsIgnoreCase("lakshadweep")) statecode = "LD";
		else if(state.equalsIgnoreCase("madhya pradesh")) statecode = "MP";
		else if(state.equalsIgnoreCase("maharashtra")) statecode = "MH";
		else if(state.equalsIgnoreCase("manipur")) statecode = "MN";
		else if(state.equalsIgnoreCase("meghalaya")) statecode = "ML";
		else if(state.equalsIgnoreCase("mizoram")) statecode = "MZ";
		else if(state.equalsIgnoreCase("nagaland")) statecode = "NL";
		else if(state.equalsIgnoreCase("odisha")) statecode = "OR";
		else if(state.equalsIgnoreCase("puducherry")) statecode = "PY";
		else if(state.equalsIgnoreCase("punjab")) statecode = "PB";
		else if(state.equalsIgnoreCase("rajasthan")) statecode = "RJ";
		else if(state.equalsIgnoreCase("sikkim")) statecode = "SK";
		else if(state.equalsIgnoreCase("tamil nadu")) statecode = "TN";
		else if(state.equalsIgnoreCase("telangana")) statecode = "TG";
		else if(state.equalsIgnoreCase("tripura")) statecode = "TR";
		else if(state.equalsIgnoreCase("uttar pradesh")) statecode = "UP";
		else if(state.equalsIgnoreCase("uttarkhand")) statecode = "UT";
		else if(state.equalsIgnoreCase("west bengal")) statecode = "WB";
		else {
			statecode = null;
			System.out.println("The state doesn't exist");
		} 
		return statecode;
		
	}
	
}
