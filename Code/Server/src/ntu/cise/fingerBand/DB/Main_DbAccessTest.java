package ntu.cise.fingerBand.DB;

import java.sql.SQLException;
import java.util.ArrayList;

public class Main_DbAccessTest {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		
// ��եΪ��Ѽ� (�i�ۭq)
		
		String myFbId_1 = "fid1";
		String myFbId_1_name = "fid1_fName";
		
		String myFbId_2 = "fid2";
		String myFbId_2_name = "fid2_fName";
		
		String myFbId_3 = "fid3";
		String myFbId_3_name = "fid3_fName";
		
		String myFbId_4 = "fid4";
		String myFbId_4_name = "fid4_fName";
		
		String fbId_69 = "fbId_69";
		String fbId_NoGood = "v302q34sedrftgnyhmujp,i[ko.p";
		
		int myFbId_1_sessionId = 12;
		String myFbId_1_sessionName = "�ͪ���";
		String myFbId_1_recordName = "�Ⱖ�Ѫ�";
		String myFbId_1_recordData = "Do~ Re~ Mi~";
		
		int myFbId_4_sessionId = 13;		
		String myFbId_4_sessionName = "�i����";
		String myFbId_4_recordName = "�T���p��";
		String myFbId_4_recordData = "Fa~ Mi~ Re~Do~";
		
		int player_6_sessionId = 24;

		
// ��o Database Access Object
		DbConnector dbConn = new DbConnector();

//// ��աGif session exist
//		dbConn.getConnection();
//		System.out.println(dbConn.checkSessionExist(1002));
//		dbConn.endConnection();
		
//// ��աGif account exist
//		dbConn.getConnection();
//		System.out.println(dbConn.checkAccountExist("myFbId_1002"));
//		dbConn.endConnection();
		
//// ���b��աG�չϨ�o�쥻���s�b�� DB �������G
//		dbConn.getConnection();
//		ArrayList<Record> myRecordList_NoGood = dbConn.getRecordList(myFbId_NoGood);
//		for (int i = 0; i < myRecordList_NoGood.size(); i++){
//			String rName = myRecordList_NoGood.get(i).getRecordName();
//			System.out.println(rName);
//			String rData = myRecordList_NoGood.get(i).getRecordData();
//			System.out.println(rData);
//		 }
//		System.out.println("---------------------------");
//		dbConn.endConnection();
//		
//// ���Ҵ��(1)�}�l�G�إ� account
//		
//		//�إ߲Ĥ@�� account 
//		dbConn.getConnection();
//		dbConn.createAccount(myFbId_1, myFbId_1_name);
//		dbConn.endConnection();
//		
//		//�إ߲ĤG�� account
//		dbConn.getConnection();
//		dbConn.createAccount(myFbId_2, myFbId_2_name);
//		dbConn.endConnection();
//		
//		//�إ߲ĤT�� account
//		dbConn.getConnection();
//		dbConn.createAccount(myFbId_3, myFbId_3_name);
//		dbConn.endConnection();
//		
//		//�إ߲ĥ|�� account
//		dbConn.getConnection();
//		dbConn.createAccount(myFbId_4, myFbId_4_name);
//		dbConn.endConnection();
//		
//// ���Ҵ��(1)�����C
//
//// ���Ҵ��(2)�}�l�G�إߤ@�ӦW�s SessionByMyFbId_1 �� session
//	
//		// Player #1 �Ыؤ@�� session (�s�W�@�� session �� session table ���A�æP�B�s�W�@�� session member �� session member table ���A�� Creator = myFbId_1)
//		dbConn.getConnection();
//		myFbId_1_sessionId = dbConn.createSession(myFbId_1, myFbId_1_sessionName);
//		dbConn.endConnection();
//		
//		// Player #2  �[�J Player #1 �� session �� (�s�W�@�� session member �� session member table ��)
//		dbConn.getConnection();
//		dbConn.addSessionMember(myFbId_1_sessionId, myFbId_2);
//		dbConn.endConnection();
//		
//		// Player #3  �[�J Player #1  �� session �� (�A�s�W�@�� session member �� session table ��)
//		dbConn.getConnection();
//		dbConn.addSessionMember(12, myFbId_3);
//		dbConn.endConnection();
//
//// ���Ҵ��(3)�}�l�GPlayer #4 �إߤ@�ӦW�s SessionByMyFbId_4 �� session
//
//		// myFbId_4 �Ыؤ@�� session (�s�W�@�� session �� session table ���A�æP�B�s�W�@�� session member �� session member table ���A�� Creator = myFbId_1)
//		dbConn.getConnection();
//		myFbId_4_sessionId = dbConn.createSession(myFbId_4, myFbId_4_sessionName);
//		dbConn.endConnection();
//		System.out.println("sessionId for myFbId_4's session :" + myFbId_4_sessionId);
//
//// ���Ҵ��(3)�Ȱ�
//
//// ���Ҵ��(2)�����G	~~~~ SessionByMyFbId_1 �����֪����F�A�{�b�ݭn�x�s  Record ~~~~
//		
		// �إ� record (��s recordName �P recordData �� session table ��)
//		dbConn.getConnection();
//		dbConn.setSessionRecord(12, myFbId_1_recordName, myFbId_1_recordData);
//		//dbConn.addSessionRecord(10, "RecordName_of_myFbId_1", "RecordData_of_myFbId_1 ... Do~ Re~ Mi~");
//		dbConn.endConnection();
//		
//// ���Ҵ��(2)�����C
//
//// ���Ҵ��(3)���ҡG���s�i�J  SessionByMyFbId_4
//		
//		// myFbId_3 �[�J SessionByMyFbId_4 (�s�W�@�� session member �� session table ��)
//		dbConn.getConnection();
//		dbConn.addSessionMember(14, myFbId_3);
//		dbConn.endConnection();
//
//// ���Ҵ��(3)�Ȱ�
//
//// ���Ҵ��(4)�����GPlayer #2 �ݭn��o Record�C
//		
//		// myFbId_2 �ݭn��o record list�C
//		dbConn.getConnection();		
//		ArrayList<Record> myRecordList2 = dbConn.getRecordList(myFbId_2);
//		for (int i = 0; i < myRecordList2.size(); i++){
//			String rName = myRecordList2.get(i).getRecordName();
//			System.out.println(rName);
//			String rData = myRecordList2.get(i).getRecordData();
//			System.out.println(rData);
//		 }
//		System.out.println("---------------------------");
//		dbConn.endConnection();
//
//// ���Ҵ��(3)���ҡG���s�i�J  SessionByMyFbId_4

//		// myFbId_2 �[�J SessionByMyFbId_4 (�A�W�@�� session member �� session member table ��)
//		dbConn.getConnection();
//		dbConn.addSessionMember(14, myFbId_2);
//		dbConn.endConnection();
//		
//		// myFbId_1 �[�J SessionByMyFbId_4 (�S�A�s�W�@�� session member �� session table ��)
//		dbConn.getConnection();
//		dbConn.addSessionMember(14, myFbId_1);
//		dbConn.endConnection();

//		// ~~~~ SessionByMyFbId_4 �����֪����F�A�{�b�ݭn�x�s  Record ~~~~
	
//		// myFbId_4 �� session �����A�إ� record (��s recordName �P recordData �� session table ��)
//		dbConn.getConnection();
//		dbConn.setSessionRecord(14, myFbId_4_recordName, myFbId_4_recordData);
//		dbConn.endConnection();
//
//// ���Ҵ��(3)����
	
//// ���Ҵ��(4)�}�l�G�Ҧ� Player ��o Record
		
//		// myFbId_1 �ݭn��o record list�C
//		dbConn.getConnection();
//		ArrayList<Record> myRecordList1 = dbConn.getRecordList(myFbId_1);
//		for (int i = 0; i < myRecordList1.size(); i++){
//			String rName = myRecordList1.get(i).getRecordName();
//			System.out.println(rName);
//			String rData = myRecordList1.get(i).getRecordData();
//			System.out.println(rData);
//		 }
//		System.out.println("---------------------------");
//		dbConn.endConnection();
		
//		// myFbId_3 �ݭn��o record list�C
//		dbConn.getConnection();
//		ArrayList<Record> myRecordList3 = dbConn.getRecordList(myFbId_3);
//		for (int i = 0; i < myRecordList3.size(); i++){
//			String rName = myRecordList3.get(i).getRecordName();
//			System.out.println(rName);
//			String rData = myRecordList3.get(i).getRecordData();
//			System.out.println(rData);
//		 }
//		System.out.println("---------------------------");
//		dbConn.endConnection();
		
//		// myFbId_4 �ݭn��o record list�C
//		dbConn.getConnection();
//		ArrayList<Record> myRecordList4 = dbConn.getRecordList(myFbId_4);
//		for (int i = 0; i < myRecordList4.size(); i++){
//			String rName = myRecordList4.get(i).getRecordName();
//			System.out.println(rName);
//			String rData = myRecordList4.get(i).getRecordData();
//			System.out.println(rData);
//		 }
//		System.out.println("---------------------------");
//		dbConn.endConnection();
		
//		// sessionId 5 ���ѻP�̷Q�n��o session 4 �� record
//		dbConn.getConnection();
//		String recordData = dbConn.getRecordData(5);
//		dbConn.endConnection();
//		System.out.println(recordData);
		
//// ���Ҵ��(5)�G�إ� account # 5
	
//		//�إ߲Ĥ@�� account 
//		dbConn.getConnection();
//		dbConn.createAccount("PlayerId5", "PlayerId5_name");
//		dbConn.endConnection();
		
//		// Player #5 �Ыؤ@�� session
//		dbConn.getConnection();
//		myFbId_1_sessionId = dbConn.createSession("PlayerId5", "PlayerId5_sessionName");
//		dbConn.endConnection();
		
		// Player # 5 �إ� record
//		dbConn.getConnection();
//		dbConn.setSessionRecord(15, "PlayerId5_recordName", "Note = 60\r\nInstrument = 61\r\nPitch = 62\r\n");
//		dbConn.endConnection();
		
//		// Player # root �إ� record
//		dbConn.getConnection();
//		dbConn.createAccount("root", "root_name");
//		dbConn.endConnection();
		
//// ���Ҵ��(2)�����G	~~~~ SessionByMyFbId_1 �����֪����F�A�{�b�ݭn�x�s  Record ~~~~
//			
//		// �إ� record (��s recordName �P recordData �� session table ��)
//		dbConn.getConnection();
//		dbConn.setSessionRecord(12, myFbId_1_recordName, myFbId_1_recordData);
//		//dbConn.addSessionRecord(10, "RecordName_of_myFbId_1", "RecordData_of_myFbId_1 ... Do~ Re~ Mi~");
//		dbConn.endConnection();
		
		// Player # 6  �إ� record
		//public int createAccount(String FbId, String FbName){	//DONE
		dbConn.getConnection();
		dbConn.createAccount("p555", "p555_name");
		dbConn.endConnection();
		
		// p50  �Ыؤ@�� session
		//public int createSession(String FbId, String SessionName){	//DONE
		dbConn.getConnection();
		int p555_sessionId = dbConn.createSession("xyz", "p555_sessionName");
		dbConn.endConnection();
		System.out.println(p555_sessionId);
		
//		// p50  �g�J record
//		//public int setSessionRecord(int SessionId, String RecordName, String RecordData){	//DONE
//		dbConn.getConnection();
//		dbConn.setSessionRecord(p555_sessionId, "p555_recordName", "A=X\r\nB=Y\r\nC=Z\r\n");
//		dbConn.endConnection();
		
//		// p49  �n��o session 24  �� record
//		dbConn.getConnection();
//		String recordData = dbConn.getRecordData(27);
//		dbConn.endConnection();
//		System.out.println(recordData);
	
//// ���Ҵ��  END
	}
}