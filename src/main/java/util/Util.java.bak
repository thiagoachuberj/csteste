package util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import xml.XmlDocumentBuildFactory;

public final class Util {
	
	public static Document convertObjectInDocument(Object object) throws ParserConfigurationException, JAXBException {
		Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		JAXBContext context = JAXBContext.newInstance(object.getClass().getPackage().getName());
		Marshaller m = context.createMarshaller();
		m.marshal(object, document);
		
		return document;
	}
	
	public static StringWriter convertObjectInXML(Object object) {
		StringWriter sw = null;
		
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass().getPackage().getName());
			JAXBElement<?> element = new JAXBElement(new QName("http://www.w3.org/2000/09/xmldsig#", "eSocial"), object.getClass(), object);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			sw = new StringWriter();
			marshaller.marshal(element, sw);
		}
		catch(JAXBException ex) {
			ex.printStackTrace();
		}
		
		return sw;
	}
	
	public static String formataNameSpace(String evento) {
		String str = evento.replaceAll("\"http://www.w3.org/2000/09/xmldsig#\"", "");
		str = str.replaceAll("\"http://www.esocial.gov.br/schema/evt/evtInfoEmpregador/v02_04_01\"", "");
		str = str.replaceAll("xmlns:ns3=", "").replaceAll("xmlns:ns2=", "");
		str = str.replaceAll("ns2:", "").replaceAll("ns3:", "");
		
		str = str.replace("<eSocial  >", "<eSocial xmlns=\"http://www.esocial.gov.br/schema/evt/evtInfoEmpregador/v02_04_01\">");
		str = str.replaceAll("ns2:", "").replaceAll("ns3:", "");
		
		return str;
	}
	
	/**.
	 * Efetua validacao de xml
	 * @param factory
	 * @param bs
	 * @throws IOException
	 */
	public static void validarXml(final XmlDocumentBuildFactory factory, final byte[] bs) throws IOException {
		final ByteArrayInputStream in = new ByteArrayInputStream(bs);

		try {
			final DocumentBuilder builder = factory.newDocumentBuilder();
			builder.parse(in);

		} 
		catch (final ParserConfigurationException | SAXException e) {
			throw new IOException(e);

		} 
		catch (final IOException e) {
			throw e;

		} 
		finally {
			in.close();
		}
	}
}