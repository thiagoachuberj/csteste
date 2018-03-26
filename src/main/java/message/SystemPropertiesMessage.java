package message;

import java.io.InputStream;
import java.util.Properties;

public class SystemPropertiesMessage extends PropertiesMessage {
	private static String PROPERTIES_FILE_NAME = "system.properties";
	
	private SystemPropertiesMessage() {}
	
	private SystemPropertiesMessage(Properties props) {
		super(props);
	}
	
	private static SystemPropertiesMessage handle;
	
	public static synchronized SystemPropertiesMessage getInstance() {
		if (handle == null) {
			handle = new SystemPropertiesMessage();
		}
		return handle;
	}
	
	public static synchronized SystemPropertiesMessage getInstance(Properties props) {
		if (handle == null)	{
			handle = new SystemPropertiesMessage(props);
		}
		return handle;
	}
	
	protected InputStream getInputStream() {
		InputStream is = null;

		is = SystemPropertiesMessage.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME);
		
		if (is == null) {
			is = SystemPropertiesMessage.class.getResourceAsStream(PROPERTIES_FILE_NAME);
		}
		
		return is;
	}
	
	public static String getSystemEnvOrProperty(String nmVariavel) {
		return System.getProperty(nmVariavel) != null
	         ? System.getProperty(nmVariavel)
	         : System.getenv(nmVariavel);
	}
}