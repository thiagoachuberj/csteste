package message; 

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class PropertiesMessage {
	protected Properties props;
	
	protected PropertiesMessage (Properties props) {
		this.props = props;
	}
	
	protected PropertiesMessage () {
		try {
			InputStream inStream = getInputStream ();
			props = new Properties ();
			props.load (inStream);
		}
		catch (IOException ex) {
			throw new RuntimeException (ex);
		}
	}
	
	public String getValueKey (String key) {
		return props.getProperty (key);
	}
	
	protected abstract InputStream getInputStream ();
}
