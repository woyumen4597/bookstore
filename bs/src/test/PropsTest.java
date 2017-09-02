package test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.Test;

public class PropsTest {
	@Test
	public void getProps() throws IOException{
		Properties props = new Properties();
		InputStream inputStream = PropsTest.class.getClassLoader().getResourceAsStream("resource.properties");
		props.load(inputStream);
		String string = props.getProperty("FTP_ADDRESS");
		System.out.println(string);
	}
}
