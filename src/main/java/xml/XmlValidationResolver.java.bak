package xml;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XmlValidationResolver implements EntityResolver, LSResourceResolver, Serializable {
	
	private static final long serialVersionUID = -1295625353829960138L;
	final Map<String, DomInputSource> schemas;
	final Map<String, DomInputSource> dtds = null;

	public XmlValidationResolver(final String xsdPath) throws IOException, XMLStreamException {
		schemas = new HashMap<String, DomInputSource>();
		List<File> list = new ArrayList<File>(128);
//		String theString = IOUtils.toString(XmlValidationResolver.class.getResourceAsStream(xsdPath), "UTF-8");
		listFiles(new File(xsdPath), list, ".xsd" );
		
		File[] files = new File[list.size()];
		files = list.toArray(files);
		final XMLInputFactory xmlf = XMLInputFactory.newInstance();
		xmlf.setProperty(XMLInputFactory.IS_COALESCING, true);
		xmlf.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, false);
		xmlf.setProperty(XMLInputFactory.SUPPORT_DTD, false);

		for (int i = 0; i < files.length; i++) {
			try {
				final RandomAccessFile in = new RandomAccessFile(files[i], "r");
				final byte[] schema = new byte[(int) in.length()];
				try { in.read(schema); }
				finally { in.close(); }
				
				schemas.put(getNamespace(xmlf, schema), new DomInputSource(schema));
			}
			catch (XMLStreamException e) { 
				throw new XMLStreamException("Could not parse file: " + files[i].getAbsolutePath(), e); 
			}
			catch (IOException e) { 
				throw  new IOException("Could not read file: " + files[i].getAbsolutePath(), e);
			}
		}
		
		//dtds = new HashMap<String, DomInputSource>();
		//addDTD("csesocial/xml/XMLSchema.dtd", "-//W3C//DTD XMLSchema 200102//EN");
		//addDTD("csesocial/xml/datatypes.dtd", "datatypes");
	}
	
	private void addDTD(String dtdfile, String PublicID) throws IOException	{
		final InputStream dtdstream = this.getClass().getClassLoader().getResourceAsStream(dtdfile);
		byte buf[] = new byte[1024];
		ByteArrayOutputStream bout = new ByteArrayOutputStream(16*1024); 
		int read = 0;
		
		try {
			while((read = dtdstream.read(buf)) > 0) {
				bout.write(buf, 0,read);
			}
			final byte[] dtd = bout.toByteArray();
			dtds.put(PublicID, new DomInputSource(dtd));
		}
		catch (IOException e) { 
			throw  new IOException("Could not read file: " + dtdfile, e);
		}
		finally { 
			dtdstream.close(); 
		}
	}
	
	private void listFiles(final File from, final List<File> to, String ext) {
		if (!from.isDirectory()) return;
		final File[] list = from.listFiles();
		for (final File f : list) {
			if (f.isDirectory()) {
				listFiles(f, to, ext);
			}
			else if (f.getName().toLowerCase().endsWith(ext)) {
				to.add(f);
			}
		}
	}
	
	
	private String getNamespace(final XMLInputFactory xmlf, final byte[] schema) throws XMLStreamException {
		final XMLEventReader reader = xmlf.createXMLEventReader(new ByteArrayInputStream(schema));
		
		while (reader.hasNext()) {
			XMLEvent evt = reader.nextEvent();
			
			if (evt.getEventType() == XMLEvent.START_ELEMENT) {
				StartElement start = evt.asStartElement();
				
				if (!"schema".equals(start.getName().getLocalPart())) {
					throw new XMLStreamException("Unexpected schema file");
				}
				
				Attribute attr = start.getAttributeByName(new QName("targetNamespace"));
				if (attr == null) {
					throw new XMLStreamException("Unexpected schema file");
				}
				
				return attr.getValue();
			}
		}
		
		throw new XMLStreamException("Unexpected schema file");
	}

	public LSInput resolveResource	(
		final String type,
		final String namespaceURI,
		final String publicId,
		final String systemId,
		final String baseURI
	) { 
		DomInputSource resource = schemas.get(namespaceURI); 
		if(resource == null) resource = dtds.get(publicId);
		return resource;
	}
	
	public InputSource resolveEntity(final String publicId, final String systemId) throws SAXException, IOException {
		DomInputSource source = schemas.get(systemId);
		
		if (source == null) {
			source = schemas.get(publicId);
		}
		
		if (source == null) {
			source = dtds.get(publicId);
		}
		
		if (source != null) {
			return new InputSource(source.getByteStream());
		}
		
		return null;
	}
	
}