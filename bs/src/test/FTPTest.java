package test;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

public class FTPTest {
	@Test
	public void testFTP() throws Exception{
		FTPClient ftpClient = new FTPClient();
		ftpClient.connect("server",21);
		ftpClient.login("ubuntu", "woyumen4597");
		FileInputStream inputStream = new FileInputStream(new File("C:\\Users\\Administrator\\pic\\1.jpg"));
		ftpClient.changeWorkingDirectory("/var/www/images");
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		boolean b = ftpClient.storeFile("6.jpg", inputStream);
		if(!b){
			System.out.println("失败了");
		}
		ftpClient.logout();
	}
}
