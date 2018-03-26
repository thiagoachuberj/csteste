package xml;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Reader;
import java.io.Serializable;

import org.w3c.dom.ls.LSInput;

public class DomInputSource implements LSInput, Serializable
{
	private static final long serialVersionUID = 8479206522160817236L;
	private byte[] source;
	
	public DomInputSource(byte[] source) {
		if (source == null || source.length == 0) {
			throw new NullPointerException("Array argument must not be null nor empty");
		}
		
		this.source = source;
	}
	
	public Reader getCharacterStream() { return null; }
	public void setCharacterStream(Reader characterStream) {}
	public void setByteStream(InputStream byteStream) {}
	public String getStringData() { return null; }
	public void setStringData(String stringData) {}
	public String getSystemId() { return null; }
	public void setSystemId(String systemId) {}
	public String getPublicId() { return null; }
	public void setPublicId(String publicId) {}
	public String getBaseURI() { return null; }
	public void setBaseURI(String baseURI) {}
	public String getEncoding() { return null; }
	public void setEncoding(String encoding) {}
	public boolean getCertifiedText() { return false; }
	public void setCertifiedText(boolean certifiedText) {}
	public InputStream getByteStream() { return new ByteArrayInputStream(source); }
}