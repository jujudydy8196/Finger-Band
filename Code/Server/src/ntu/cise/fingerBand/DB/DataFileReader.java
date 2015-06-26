package ntu.cise.fingerBand.DB;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DataFileReader {

	int sessionId = -1;
	
	public DataFileReader(int SessionId){
		this.sessionId = SessionId;
	}
	
	protected String readDataFile(){
		
		try {
			String filePath ="C:\\FingerBand\\dataFileDir\\" + this.sessionId + ".txt"; 
			FileReader fr = new FileReader(filePath);
			BufferedReader br = new BufferedReader(fr);
			
			String line = "";
			String recordData = "";
			while((line = br.readLine())!=null){
				recordData = recordData + line;
			}
			br.close();
			fr.close();
			
			return recordData;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("�䤣���ɮ� (File not found)");
			
			return null;
			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("�ɮ�Ū���~(I/O ���D)");
			
			return null;
		}
	}
}

