package ntu.cise.fingerBand.DB;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
//import java.io.Writer;

public class DataFileWriter{

	String filePath = "";
	String recordData = "";
	
	public DataFileWriter(String FilePath, String RecordData){
		this.filePath = FilePath;
		this.recordData = RecordData;
	}
	
	protected Boolean writeDataFile(){
		
		//String filePath = "C:\\Users\\Cliff\\FingerBandRecordDataDir" + SessionId + ".txt";
		//String filePath = "C:\\devPj\\FingerBand\\dataFileDir" + 3 + ".txt";
				
		File file = new File(filePath);
		
		try {
			System.out.println("1");
			PrintWriter writer = new PrintWriter(filePath, "US-ASCII");
			System.out.println("2");
			writer.println(this.recordData);
			System.out.println("3");
			writer.close();
			System.out.println("w");
			
			return true;
		
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("�ɮ׼g�J��~(I/O ���D)");
			
			return false;
		}
	}
}

