package ntu.cise.fingerBand.DB;

/**
 * 嚙誕用歹蕭k嚙瘦
 * 
 * 
 */

//############################## START OF DbConnector CLASS #######################################
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DbConnector {

////ATTRIBUTES FOR CONNECTION /////////////////////////////////////////////////////////////////////
	
	public Connection conn = null;
	private ArrayList<Integer> sessionIdList = null;
	
////CONSTRUCTOR ///////////////////////////////////////////////////////////////////////////////////
	public DbConnector() {
	}
	
////METHODS FOR DB CONNECTION TO DB ///////////////////////////////////////////////////////////////	
	
	// METHOD: 嚙罷嚙課〔嚙踝蕭w嚙篆嚙編嚙踝蕭
	public void getConnection() throws ClassNotFoundException, SQLException {
		
		try {
			this.conn = DriverManager.getConnection("jdbc:mysql://140.112.90.150:3306/finger2","root","root");
			System.out.println("You're now connected to FingerBand DB.");
		}
		catch (SQLException e) {
			//e.printStackTrace();
			System.out.println("[Warning] dbConnection CANNOT be built");
		}
	}
	
	// METHOD: 嚙踝蕭嚙踝蕭嚙箴嚙踝蕭w嚙篆嚙編嚙踝蕭
	public void endConnection() /*throws SQLException*/ {
	
		try {	
			this.conn.close();
			this.conn = null;
		}
		catch (SQLException e) {
			//e.printStackTrace();
			System.out.println("[Warning] CANNOT close connection to DB correctly");
		}
	}

////METHODS FOR FINGER BAND ACTIVITIES ////////////////////////////////////////////////////////////
	
	// 嚙編嚙磕 member 嚙編嚙踝蕭, member FB id, member FB name
	public int createAccount(String FbId, String FbName){	//DONE
		
		try {
			// 嚙編嚙磕嚙瑾嚙踝蕭 member 嚙踝蕭嚙�
			String query = "INSERT INTO accountTable (fbId, fbName) VALUES (?, ?)";
						
			PreparedStatement pStm1 = this.conn.prepareStatement(query);
			pStm1.setString(1, FbId);
			pStm1.setString(2, FbName);
			pStm1.executeUpdate();
			
			return 1; 
		}
		catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	// 嚙編嚙磕嚙瑾嚙踝蕭 session 嚙踝蕭嚙� 
	public int createSession(String FbId, String SessionName){	//DONE
		
		/**/
		Boolean existance = checkAccountExist(FbId);
		if (existance == true){
		/**/
		
			System.out.println(FbId);
			System.out.println(SessionName);
			int last_inserted_sessionId = 0;
			try {
				String query = "INSERT INTO sessionTable (sessionName) VALUES (?)";
				
				PreparedStatement pStm1 = this.conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				pStm1.setString(1, SessionName);
				System.out.println(pStm1);
				pStm1.executeUpdate();	
				
				// 嚙踝蕭oMySQL嚙諛動生佗蕭嚙踝蕭 sessionId 
				ResultSet rs = pStm1.getGeneratedKeys();
				if(rs.next()){
					last_inserted_sessionId = rs.getInt(1);
					System.out.println(last_inserted_sessionId);
				}
				
				// 嚙編嚙磕嚙瑾嚙踝蕭 SessionMemberTable 嚙踝蕭A嚙衛將嚙踝蕭 FbId 嚙稽嚙踝蕭 isSessionCreator嚙瘠
				setSessionCreator(last_inserted_sessionId, FbId);
				return last_inserted_sessionId;
			}
			catch (SQLException e) {
				e.printStackTrace();
				return -1;
			}
		/**/
		}
		else {
			System.out.println("Account Does not exist");
			return -1;
		}
		/**/
	}
	
	// 嚙編嚙磕嚙瑾嚙踝蕭 SessionMemberTable 嚙踝蕭A嚙衛將嚙踝蕭 member 嚙稽嚙踝蕭 notCreator 	(0 = notCreator)
	public int addSessionMember(int SessionId, String FbId){	//DONE
		
		try {
			String query = "INSERT INTO sessionMemberTable (sessionId, fbId, isCreator) VALUES (?, ?, ?)";
			
			PreparedStatement pStm1 = this.conn.prepareStatement(query);
			pStm1.setInt(1, SessionId);
			pStm1.setString(2, FbId);
			pStm1.setInt(3, 0);	// 1 = isCreator, 0 = notCreator
			pStm1.executeUpdate();
			
			return 1;
		
		}
		catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	// 嚙踝蕭o嚙磐嚙瘡嚙踝蕭 Record List
	public ArrayList<Record> getRecordList(String FbId){
		
		ArrayList<Record> recordList = new ArrayList<Record>();
		try {	
				
			// 嚙踝蕭嚙緻嚙踝蕭嚙瘡嚙踝蕭 Session ID List
			ArrayList<Integer> sesIdList = getSessionIdListByFbId(FbId);
			
			// 嚙璀嚙緩嚙踝蕭 Session ID List 嚙踝蕭嚙踝蕭嚙瘠嚙瑾嚙踝蕭 Session ID 嚙踝蕭嚙� Record Name 嚙瞎 Record Data
			for (int sesId:sesIdList){
				
				String queryForRecord = "SELECT recordName, recordData FROM sessionTable WHERE sessionId = ?";
				
				PreparedStatement pStm1 = conn.prepareStatement(queryForRecord);
				pStm1.setInt(1, sesId);
				ResultSet rsForRecord = pStm1.executeQuery();
				
				// 嚙瞇嚙瘠嚙瑾嚙踝蕭嚙緻嚙踝蕭 Record Name 嚙瞎 Record Data 嚙踝蕭嚙稽嚙踝蕭嚙瑾嚙踝蕭 Record 嚙踝蕭嚙踝蕭A嚙踝蕭i  Record List 嚙踝蕭嚙瘠
				while (rsForRecord.next()){
					Record record = new Record(rsForRecord.getString("recordName"), rsForRecord.getInt("sessionId"));
					recordList.add(record);
				}
			}
			return recordList;
		}	
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// (嚙諛伐蕭) 嚙瞎嚙踝蕭Y嚙瘡 嚙踝蕭 Session ID List 
	private ArrayList<Integer> getSessionIdListByFbId(String FbId){
		
		ArrayList<Integer> sesIdList = new ArrayList<Integer>();
		
		try {
			String queryForSessionIdList = "SELECT sessionId FROM sessionMemberTable WHERE fbId = ?";
			
			PreparedStatement pStm1 = conn.prepareStatement(queryForSessionIdList);
			pStm1.setString(1, FbId);
			ResultSet rsOfSessionIdList = pStm1.executeQuery();
			while (rsOfSessionIdList.next()){
				sesIdList.add( rsOfSessionIdList.getInt("sessionId") );
			}
			this.sessionIdList = sesIdList;
			
//			for (int i = 0; i < sesIdList.size(); i++){
//				System.out.println("sessionId : " + sesIdList.get(i));
//			}
			
			return sessionIdList;
		} 
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// (嚙諛伐蕭) 嚙編嚙磕嚙瑾嚙踝蕭 SessionMemberTable 嚙踝蕭A嚙衛將嚙踝蕭 member 嚙稽嚙踝蕭 isCreator (1 = isCreator)
	private int setSessionCreator(int SessionId, String FbId){
		
		try {
			String query = "INSERT INTO sessionMemberTable (sessionId, fbId, isCreator) VALUES (?, ?, ?)";
			
			PreparedStatement pStm1 = this.conn.prepareStatement(query);
			pStm1.setInt(1, SessionId);
			pStm1.setString(2, FbId);
			pStm1.setInt(3, 1);	// 1 = isCreator, 0 = notCreator
			pStm1.executeUpdate();
			
			return 1;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 嚙編嚙磕 sessionRecordData (Version 2)
	public int setSessionRecord(int SessionId, String RecordName, String RecordData) /*throws SQLException*/ {	//DONE
		
		/**/
		Boolean existance = checkSessionExist(SessionId);
		if (existance == true){
		/**/
		
			DataFileCreator c = new DataFileCreator(SessionId);
			String filePath = c.createDataFile();
			DataFileWriter w = new DataFileWriter(filePath, RecordData);
			Boolean result = w.writeDataFile();
			if (result == false){
				System.out.println("false");
				return -1;
			}
			else {
				try {
					String query = "UPDATE sessionTable SET recordName = ?, recordData = ? WHERE sessionId = ?";
					
					PreparedStatement pStm = conn.prepareStatement(query);
					pStm.setString(1, RecordName);
					pStm.setString(2, filePath);
					pStm.setInt(3, SessionId);
					pStm.executeUpdate();
					
					return 1;
				}
				catch (SQLException e) {
					e.printStackTrace();
					return -1;
				}
			}
		/**/
		} else {
			System.out.println("Account Does not exist");
			return -1;
		}
		/**/
	}

	// 嚙踝蕭o嚙磐嚙瑾嚙踝蕭嚙踝蕭 Record Data (Version 2)
	public String getRecordData(int SessionId){
			
		DataFileReader reader = new DataFileReader(SessionId);
		String recordData = reader.readDataFile();
		
		return recordData;
	}
	

 	// (嚙諛伐蕭) 嚙誼查 account 嚙瞌嚙稻嚙緩嚙編嚙箭 (嚙踝蕭嚙確嚙緹嚙踝蕭嚙踝蕭)
	private Boolean checkAccountExist(String FbId){
		
		int checkResult = 3; 
		try{
			String query = "SELECT EXISTS(SELECT 1 FROM accountTable WHERE fbId = ?)";
			
			PreparedStatement pStm = conn.prepareStatement(query);
			pStm.setString(1, FbId);
			ResultSet rs = pStm.executeQuery();
			if(rs.next()) {
			    checkResult = rs.getInt(1);		// (1) 嚙踝蕭嚙瞑嚙踝蕭 rs 嚙諒迎蕭嚙衝一嚙踝蕭嚙踝蕭嚙磕嚙誶。	
			}									// IF:	 pStm.setInt(1, FbId);		// 1 = FbId
												// THEN: res.getInt(1) 嚙瞇嚙罵嚙稷嚙踝蕭 FbId
												// THEN: "SELECT EXISTS ()" 嚙踝蕭 query 嚙踝蕭O嚙瞑嚙稻 FbId 嚙瞌嚙稻嚙編嚙箭嚙瘠
			if (checkResult == 1){
				return true;
			}
			else {
				return false;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	

 	// (嚙諛伐蕭) 嚙誼查session 嚙瞌嚙稻嚙緩嚙編嚙箭 (嚙踝蕭嚙確嚙緹嚙踝蕭嚙踝蕭)
	private Boolean checkSessionExist(int SessionId){
		
		int checkResult = 3; 
		try{
			String query = "SELECT EXISTS(SELECT 1 FROM sessionTable WHERE sessionId = ?)";
			
			PreparedStatement pStm = conn.prepareStatement(query);
			pStm.setInt(1, SessionId);
			ResultSet rs = pStm.executeQuery();
			if(rs.next()) {
			    checkResult = rs.getInt(1);		// (1) 嚙踝蕭嚙瞑嚙踝蕭 rs 嚙諒迎蕭嚙衝一嚙踝蕭嚙踝蕭嚙磕嚙誶。	
			}									// IF:	 pStm.setInt(1, SessionId);		// 1 = SessinId
												// THEN: res.getInt(1) 嚙瞇嚙罵嚙稷嚙踝蕭 sessionId
												// THEN: "SELECT EXISTS ()" 嚙踝蕭 query 嚙踝蕭O嚙瞑嚙稻 sessionId 嚙瞌嚙稻嚙編嚙箭嚙瘠
			if (checkResult == 1){
				return true;
			}
			else {
				return false;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	
/*
	// 嚙踝蕭o FbName (嚙褓無嚙踝蕭嚙豎求)
	private String getMemberFbName(String MemberFbId){
		
		String memberFbId = "";
		try {	
			String query = "SELECT * FROM MemberTable WHERE memberFbId = ?";
			PreparedStatement pStm = conn.prepareStatement(query);
			pStm.setString(1, MemberFbId);
			ResultSet rs = pStm.executeQuery();
			while (rs.next()){
				memberFbId = rs.getString("memberFbId");
			}
			return memberFbId;
		}
		catch (SQLException e) {
			//e.printStackTrace();
			return null;
		}
	}
*/
/////////////////////////////////////////// FingerBand ///// (end) //////////////////////////////////	
	
}	

