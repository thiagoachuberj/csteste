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
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
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
		String str = evento.replaceAll(" xmlns:ns2=\"http://www.w3.org/2000/09/xmldsig#\"", "");
		str = str.replaceAll(":ns3", "");
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
	
	public static String obterValorElemento(Element elemento, String noPrincipal, String noFilho) {
		String valor = null;
		NodeList identificadorEmpregador = elemento.getElementsByTagName(noPrincipal);
		Node primeiroNo = identificadorEmpregador.item(0).getFirstChild();

		while (primeiroNo != null) {
			if (primeiroNo.getNodeName() == null) {
				continue;
			}

			if (noFilho.equals(primeiroNo.getNodeName())) {
				valor = primeiroNo.getTextContent();
			}
			primeiroNo = primeiroNo.getNextSibling();
		}
		return valor;
	}

	public static String nodeToString(Node node, final boolean omitirDeclaracaoXml) throws IOException {
    	StringWriter sw = new StringWriter();

    	try {
    		Transformer t = TransformerFactory.newInstance().newTransformer();
    		if (omitirDeclaracaoXml) {
    			t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
    		}
    		t.setOutputProperty(OutputKeys.INDENT, "no");
    		t.transform(new DOMSource(node), new StreamResult(sw));

    	} catch (final TransformerException e) {
			throw new IOException(e);
    	}
    	return sw.toString();
    }
	
	public static void main(String []args) {
		String str = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?> <ns2:eSocial xmlns:ns2=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns3=\"http://www.esocial.gov.br/schema/evt/evtInfoEmpregador/v02_04_02\">     <ns3:evtInfoEmpregador Id=\"ID1103687170001442018041713182000000\">         <ns3:ideEvento>             <ns3:tpAmb>2</ns3:tpAmb>             <ns3:procEmi>0</ns3:procEmi>             <ns3:verProc>0</ns3:verProc>         </ns3:ideEvento>         <ns3:ideEmpregador>             <ns3:tpInsc>1</ns3:tpInsc>             <ns3:nrInsc>123456789</ns3:nrInsc>         </ns3:ideEmpregador>         <ns3:infoEmpregador>             <ns3:inclusao>                 <ns3:idePeriodo>                     <ns3:iniValid>2017-01</ns3:iniValid>                     <ns3:fimValid>2017-02</ns3:fimValid>                 </ns3:idePeriodo>                 <ns3:infoCadastro>                     <ns3:nmRazao>Teste 123</ns3:nmRazao>                     <ns3:classTrib>10</ns3:classTrib>                     <ns3:natJurid>0000</ns3:natJurid>                     <ns3:indCoop>0</ns3:indCoop>                     <ns3:indConstr>0</ns3:indConstr>                     <ns3:indDesFolha>0</ns3:indDesFolha>                     <ns3:indOptRegEletron>0</ns3:indOptRegEletron>                     <ns3:indEntEd>N</ns3:indEntEd>                     <ns3:indEtt>N</ns3:indEtt>                     <ns3:nrRegEtt>12345678910</ns3:nrRegEtt>                     <ns3:dadosIsencao>                         <ns3:ideMinLei>ok</ns3:ideMinLei>                         <ns3:nrCertif>numero</ns3:nrCertif>                         <ns3:dtEmisCertif>2018-05-15</ns3:dtEmisCertif>                         <ns3:dtVencCertif>2018-05-15</ns3:dtVencCertif>                         <ns3:nrProtRenov>numero</ns3:nrProtRenov>                         <ns3:dtProtRenov>2018-05-15</ns3:dtProtRenov>                         <ns3:dtDou>2018-05-15</ns3:dtDou>                         <ns3:pagDou>10</ns3:pagDou>                     </ns3:dadosIsencao>                     <ns3:contato>                         <ns3:nmCtt>testeContato</ns3:nmCtt>                         <ns3:cpfCtt>09063372752</ns3:cpfCtt>                         <ns3:foneFixo>2122222222</ns3:foneFixo>                         <ns3:foneCel>21999999999</ns3:foneCel>                         <ns3:email>thiago@thiago.com</ns3:email>                     </ns3:contato>                     <ns3:infoOP>                         <ns3:nrSiafi>789</ns3:nrSiafi>                         <ns3:infoEFR>                             <ns3:ideEFR>S</ns3:ideEFR>                             <ns3:cnpjEFR>12345678912345</ns3:cnpjEFR>                         </ns3:infoEFR>                         <ns3:infoEnte>                             <ns3:nmEnte>ENTE</ns3:nmEnte>                             <ns3:uf>RJ</ns3:uf>                             <ns3:codMunic>10</ns3:codMunic>                             <ns3:indRPPS>S</ns3:indRPPS>                             <ns3:subteto>0</ns3:subteto>                             <ns3:vrSubteto>10</ns3:vrSubteto>                         </ns3:infoEnte>                     </ns3:infoOP>                     <ns3:infoOrgInternacional>                         <ns3:indAcordoIsenMulta>0</ns3:indAcordoIsenMulta>                     </ns3:infoOrgInternacional>                     <ns3:infoComplementares>                         <ns3:situacaoPJ>                             <ns3:indSitPJ>0</ns3:indSitPJ>                         </ns3:situacaoPJ>                         <ns3:situacaoPF>                             <ns3:indSitPF>0</ns3:indSitPF>                         </ns3:situacaoPF>                     </ns3:infoComplementares>                 </ns3:infoCadastro>             </ns3:inclusao>         </ns3:infoEmpregador>     </ns3:evtInfoEmpregador> </ns2:eSocial>";
		
		System.out.println(Util.formataNameSpace(str));
	}
}