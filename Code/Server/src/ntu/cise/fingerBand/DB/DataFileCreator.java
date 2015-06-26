package ntu.cise.fingerBand.DB;

import java.io.File;
import java.io.IOException;

public class DataFileCreator {
	
	int sessionId = -1;
	
	public DataFileCreator(int SessionId){
		this.sessionId = SessionId;
	}
	
	public String createDataFile(){
	
		//String filePath = "C:\\Users\\Cliff\\FingerBandRecordDataDir\\" + this.sessionId + ".txt";
		String filePath = "C:\\FingerBand\\dataFileDir\\" + this.sessionId + ".txt";
		
		File file = new File(filePath);	
		try {
			file.createNewFile();	//create a new file if it doesn't exist already
			System.out.println("�w�إ߷s��");
			return filePath;
			
		} catch (IOException e) {
			e.printStackTrace();
			
			return null;
		}
	}
}