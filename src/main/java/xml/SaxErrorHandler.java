package xml;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class SaxErrorHandler implements org.xml.sax.ErrorHandler {
	public void warning(final SAXParseException e) throws SAXException {}
	
	public void error(final SAXParseException e) throws SAXException { 
		throw new SAXException(e.getMessage() + " (col=" + e.getColumnNumber() + ", line=" + e.getLineNumber() + ")", e); 
	}
	
	public void fatalError(SAXParseException e) throws SAXException {
		throw new SAXException(e.getMessage() + " (col=" + e.getColumnNumber() + ", line=" + e.getLineNumber() + ")", e); 
	}
}