package util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
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
	
	public static String createTemplateSoapMessage() throws SOAPException, IOException {
		/*sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		sb.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
		sb.append(" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"");
		sb.append(" xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\">");
		sb.append(" <soap:Header/>");
		sb.append(" <soap:Body>");*/
		String soapMessage = "";
		
		try {
			MessageFactory factory = MessageFactory.newInstance();
            SOAPMessage message = factory.createMessage();
            SOAPPart soapPart = message.getSOAPPart();
            SOAPEnvelope envelope = soapPart.getEnvelope();
            
            envelope.addNamespaceDeclaration("xsi", "http://www.w3.org/2001/XMLSchema-instance");
            envelope.addNamespaceDeclaration("xsd", "http://www.w3.org/2001/XMLSchema");

            message.setProperty(SOAPMessage.CHARACTER_SET_ENCODING, "UTF-8");
            message.setProperty(SOAPMessage.WRITE_XML_DECLARATION, "true");
            
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			message.writeTo(out);
			soapMessage = new String(out.toByteArray());
			
			System.out.println("cabecalho soap: "+soapMessage);
			
			return soapMessage;
		}
		catch (SOAPException e) {
			throw new SOAPException(e);
		} 
		catch (IOException e) {
			throw new IOException(e);
		}
	}
	
	public static String getTipoDocumento(String documento) {
		String tipoDocumento = "";
		
		if(!StringUtils.isEmpty(documento)){
			tipoDocumento = documento.length() > 11 ? "1" : "2";
		}
		
		return tipoDocumento;
	}

	public static Element convertStringToElement(String xml) throws IOException {
		return convertStringToElement(xml,false);
	}
	
	public static Element convertStringToElement(String xml, boolean aware) throws IOException {
		ByteArrayInputStream sbis = new ByteArrayInputStream(xml.getBytes());
		javax.xml.parsers.DocumentBuilderFactory b = javax.xml.parsers.DocumentBuilderFactory.newInstance();
		Document doc = null;
		try {
			b.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, Boolean.TRUE);
			b.setNamespaceAware(aware);

			javax.xml.parsers.DocumentBuilder db = b.newDocumentBuilder();
			doc = db.parse(sbis);

		} catch (final ParserConfigurationException | SAXException | IOException e) {
			throw new IOException(e);
		}
		return doc.getDocumentElement();
	}

	public static Object xmlToObject(Class<?> clazz, String xml) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
		Unmarshaller unmarshal = jaxbContext.createUnmarshaller();
		return unmarshal.unmarshal(new StreamSource(new StringReader(xml)));
	}
	
	public static Object xmlToObject(Class<?> clazz, Element xml) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
		Unmarshaller unmarshal = jaxbContext.createUnmarshaller();
		return unmarshal.unmarshal(new DOMSource(xml));
	}

}