package xml;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

public class XmlDocumentBuildFactory implements Serializable {

	private static final long serialVersionUID = -3044486156403115472L;
	private final XmlValidationResolver resolver;
	private final DocumentBuilderFactory dbf;
	
	public XmlDocumentBuildFactory(XmlValidationResolver resolver) throws SAXException {
		if (resolver == null) { 
			throw new NullPointerException("Argument must not be null");
		}
		
		this.resolver = resolver;
		
		SchemaFactory sch = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		sch.setResourceResolver(resolver);
		
		List<Source> srclist = new ArrayList<Source>();
		Iterator<DomInputSource> it = resolver.schemas.values().iterator();
		
		while (it.hasNext()) {
			srclist.add(new StreamSource(it.next().getByteStream()));
		}
		
		Schema schema = sch.newSchema(srclist.toArray(new Source[srclist.size()]));
		dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		dbf.setSchema(schema);
	}
	
	public DocumentBuilder newDocumentBuilder() throws ParserConfigurationException {
		final DocumentBuilder builder = dbf.newDocumentBuilder();
		builder.setEntityResolver(resolver);
		builder.setErrorHandler(new SaxErrorHandler());
		return builder;
	}
}
