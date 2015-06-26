package ntu.cise.fingerBand.DB;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class RecordDataIOTest {

	public static void main(String[] args) {
		
		int sessionId = 305;
		String oldRecordData = "W=408\r\nW=445\r\nW=499";
		
		DataFileCreator c = new DataFileCreator(sessionId);
		String filePath = c.createDataFile();
		DataFileWriter w = new DataFileWriter(filePath, oldRecordData);
		Boolean result = w.writeDataFile();
		if (result == true){
			DataFileReader r = new DataFileReader(sessionId);
			String newRecordData = r.readDataFile();
			System.out.println("-------------------------");
			System.out.println(newRecordData);
		}
	}	
		

		
//		// WriterTest
//		String filePath = "C:\\devPj\\FingerBand\\dataFileDir" + 3 + ".txt";
//		File file = new File(filePath);
//		int status = 0;
//		
//		try {
//			//create a new file if it doesn't exist already
//			file.createNewFile();
//			status = 1;
//	
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		if (status != 1){
//			System.out.println("Can NOT create file.");
//		}
//		else {
//			try {
//				System.out.println("1");
//				PrintWriter writer = new PrintWriter(filePath, "US-ASCII");
//				System.out.println("2");
//				writer.println("A = 0\r\nB = 1\r\nC = 2\r\n");
//				System.out.println("3");
//				writer.close();
//				System.out.println("檔案寫入成功");
//			
//			} catch (IOException e) {
//				e.printStackTrace();
//				System.out.println("檔案寫入錯誤(I/O 問題)");
//			}
//		}
}
