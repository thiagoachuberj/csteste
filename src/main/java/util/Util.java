package util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import bs.MontaXmlConsulta;
import consulta.envioConsulta.ESocial;
import consulta.envioConsulta.ESocial.ConsultaLoteEventos;
import exception.BusinessException;
import exception.CertificadoException;
import xml.XmlDocumentBuildFactory;

public final class Util {
	
	public static Document convertObjectInDocument(Object object) throws ParserConfigurationException, JAXBException {
		Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		JAXBContext context = JAXBContext.newInstance(object.getClass().getPackage().getName());
		Marshaller m = context.createMarshaller();
		m.marshal(object, document);
		
		return document;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static StringWriter convertObjectInXML(Object object) throws JAXBException {
		StringWriter sw = null;
		
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass().getPackage().getName());
			JAXBElement<?> element = new JAXBElement(new QName("eSocial"), object.getClass(), object);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);
			marshaller.setProperty("jaxb.fragment", Boolean.TRUE);
			sw = new StringWriter();
			marshaller.marshal(element, sw);
			
			return sw;
		}
		catch(JAXBException ex) {
			throw ex;
		}
	}
	
	public static String formataNameSpaceEvento(String evento) {		
		String str = evento.replaceAll(" xmlns:ns2=\"http://www.w3.org/2000/09/xmldsig#\"", "");
		str = str.replaceAll(" xmlns:n3=\"http://www.w3.org/2000/09/xmldsig#\"", "");
		str = str.replaceAll(" xmlns=\"http://www.w3.org/2000/09/xmldsig#\"", "");
		str = str.replaceAll(":ns2", "").replaceAll(":ns3", "");
		str = str.replaceAll("ns2:", "").replaceAll("ns3:", "");
		str = str.replace("xmlns=\"http://www.w3.org/2000/09/xmldsig#\"", "");
		
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
		String soapMessage = "";
		
		try {
			MessageFactory factory = MessageFactory.newInstance();
            SOAPMessage message = factory.createMessage();
            SOAPPart soapPart = message.getSOAPPart();
            SOAPEnvelope envelope = soapPart.getEnvelope();
            
            envelope.addNamespaceDeclaration("xsi", "http://www.w3.org/2001/XMLSchema-instance");
            envelope.addNamespaceDeclaration("xsd", "http://www.w3.org/2001/XMLSchema");

            message.setProperty(SOAPMessage.WRITE_XML_DECLARATION, "true");
            message.setProperty(SOAPMessage.CHARACTER_SET_ENCODING, "UTF-8");
            
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			message.writeTo(out);
			soapMessage = new String(out.toByteArray());
			
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
			//b.setNamespaceAware(true);
			b.setFeature("http://xml.org/sax/features/namespaces", false);
			b.setFeature("http://xml.org/sax/features/validation", false);
			b.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
			b.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

			javax.xml.parsers.DocumentBuilder db = b.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xml));
			//doc = db.parse(sbis);
			doc = db.parse(is);
			
			return doc.getDocumentElement();
		} 
		catch (final ParserConfigurationException | SAXException | IOException e) {
			throw new IOException(e);
		}
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
	
	public static NodeList getNodeList(Element element, String tagName) {
		NodeList nodeValue = element.getElementsByTagName(tagName);
		return nodeValue;
	}

	public static String omitirDeclaracaoXml(Element elem, String omitirDeclaracaoXml, String identXml) throws TransformerException, IOException {
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		StringWriter writer = new StringWriter();

		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, omitirDeclaracaoXml);
		transformer.setOutputProperty(OutputKeys.INDENT, identXml);
		transformer.transform(new DOMSource(elem), new StreamResult(writer));

		return writer.toString();
	}
	
	public static String removeDeclaracaoXML(String decaracao, String xml) {
		String novoXML = null;
		if(decaracao != null) {
			novoXML = xml.replaceAll(decaracao, "");
		}
		else {
			novoXML = xml.replaceAll("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "");
		}
		
		return novoXML;
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
	
	public static String formataNameSpaceLoteCompleto(String lote) {
		//String str = formataNameSpaceEvento(lote);
		String str = lote.replaceAll(" xmlns:ns2", " xmlns");
		str = str.replaceAll("ns2:", "");
		str = str.replaceAll("eSocial:eSocial", "eSocial");
		Pattern pattern = Pattern.compile("xmlns:eSocial=\"([^\"]*)\"");
		Matcher m = pattern.matcher(str);
		str = m.replaceAll("");
		Pattern p2 = Pattern.compile("(xmlns=\"http://www.w3.org/2000/09/xmldsig#\")+");
		Matcher m2 = p2.matcher(str);
		if(m2.find()) {
			str = str.replace("xmlns=\"http://www.w3.org/2000/09/xmldsig#\"", "");
		}		
		str = str.replaceAll("Signature:Signature", "Signature");
		str = str.replaceAll("xmlns:Signature", "xmlns");
		
		return str;
	}
	
	public static void main(String []args) throws IOException, SOAPException, JAXBException, BusinessException {
		
		String str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" 	xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"> 	<soapenv:Body> 		<eSocial xmlns=\"http://www.esocial.gov.br/schema/lote/eventos/envio/v1_1_1\"> 			<envioLoteEventos grupo=\"1\"> 				<ideEmpregador> 					<tpInsc>1</tpInsc> 					<nrInsc>10368717000144</nrInsc> 				</ideEmpregador> 				<ideTransmissor> 					<tpInsc>1</tpInsc> 					<nrInsc>10368717000144</nrInsc> 				</ideTransmissor> 				<eventos> 					<evento Id=\"ID1103687170001442018041713182000000\"> 						<eSocial:eSocial 							xmlns=\"http://www.esocial.gov.br/schema/evt/evtInfoEmpregador/v02_04_02\" 							xmlns:eSocial=\"http://www.esocial.gov.br/schema/evt/evtInfoEmpregador/v02_04_02\"> 							<evtInfoEmpregador Id=\"ID1103687170001442018041713182000000\"> 								<ideEvento> 									<tpAmb>2</tpAmb> 									<procEmi>0</procEmi> 									<verProc>0</verProc> 								</ideEvento> 								<ideEmpregador> 									<tpInsc>1</tpInsc> 									<nrInsc>123456789</nrInsc> 								</ideEmpregador> 								<infoEmpregador> 									<inclusao> 										<idePeriodo> 											<iniValid>2017-01</iniValid> 											<fimValid>2017-02</fimValid> 										</idePeriodo> 										<infoCadastro> 											<nmRazao>Teste 123</nmRazao> 											<classTrib>10</classTrib> 											<natJurid>0000</natJurid> 											<indCoop>0</indCoop> 											<indConstr>0</indConstr> 											<indDesFolha>0</indDesFolha> 											<indOptRegEletron>0</indOptRegEletron> 											<indEntEd>N</indEntEd> 											<indEtt>N</indEtt> 											<nrRegEtt>12345678910</nrRegEtt> 											<dadosIsencao> 												<ideMinLei>ok</ideMinLei> 												<nrCertif>numero</nrCertif> 												<dtEmisCertif>2018-05-28</dtEmisCertif> 												<dtVencCertif>2018-05-28</dtVencCertif> 												<nrProtRenov>numero</nrProtRenov> 												<dtProtRenov>2018-05-28</dtProtRenov> 												<dtDou>2018-05-28</dtDou> 												<pagDou>10</pagDou> 											</dadosIsencao> 											<contato> 												<nmCtt>testeContato</nmCtt> 												<cpfCtt>09063372752</cpfCtt> 												<foneFixo>2122222222</foneFixo> 												<foneCel>21999999999</foneCel> 												<email>thiago@thiago.com</email> 											</contato> 											<infoOP> 												<nrSiafi>789</nrSiafi> 												<infoEFR> 													<ideEFR>S</ideEFR> 													<cnpjEFR>12345678912345</cnpjEFR> 												</infoEFR> 												<infoEnte> 													<nmEnte>ENTE</nmEnte> 													<uf>RJ</uf> 													<codMunic>10</codMunic> 													<indRPPS>S</indRPPS> 													<subteto>0</subteto> 													<vrSubteto>10</vrSubteto> 												</infoEnte> 											</infoOP> 											<infoOrgInternacional> 												<indAcordoIsenMulta>0</indAcordoIsenMulta> 											</infoOrgInternacional> 											<infoComplementares> 												<situacaoPJ> 													<indSitPJ>0</indSitPJ> 												</situacaoPJ> 												<situacaoPF> 													<indSitPF>0</indSitPF> 												</situacaoPF> 											</infoComplementares> 										</infoCadastro> 									</inclusao> 								</infoEmpregador> 							</evtInfoEmpregador> 							<Signature:Signature xmlns:Signature=\"http://www.w3.org/2000/09/xmldsig#\"> 								<SignedInfo> 									<CanonicalizationMethod 										Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\" /> 									<SignatureMethod 										Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#rsa-sha256\" /> 									<Reference URI=\"\"> 										<Transforms> 											<Transform 												Algorithm=\"http://www.w3.org/2000/09/xmldsig#enveloped-signature\" /> 										</Transforms> 										<DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmlenc#sha256\" /> 										<DigestValue>oXidazlcP4KEP2NwkOlRprm/+yhJ77Yh/jVrWSaeJgc= 										</DigestValue> 									</Reference> 								</SignedInfo> 								<SignatureValue>LbiuwsX5+ObfNovnimJ19DaH2CR6obRBZTXvcnwyUgHN3x2X/AV44kXBvexLzq/Hye+tCiwovJun 									Rj/T+TDkw+PiWVOhvS9ozrzzGDDUDW8iS7xHuJB2Z7m3TR4ul8cdMeEy/FATEwXWhhawu9wPrAAM 									QumBRGR1jyTOG6q+Q1GYngaqJm07c+MmVqp53J9jSFiC5MdzZBhnvi3exZK21c+YysANZcXHPA+7 									DmZz2oUAUA1utaH19OfHQBtO74v+vD53nY0t1CS/S1FExVJBrTW0t+XhZKUFlGc9Wgb22hFaRJIH 									wen6SA/fN4lhlqgTjyPiVqg1EgTnHjRft+HSsw== 								</SignatureValue> 								<KeyInfo> 									<X509Data> 										<X509SubjectName>CN=FRANCISVALDO ALVES DA 											SILVA:10986785830,OU=AC Icptestes Sub PJ v2,OU=AC Icptestes 											Sub v2,O=ICP-Icptestes,C=BR</X509SubjectName> 										<X509Certificate>MIIF3TCCBMWgAwIBAgIIW87wOAtxBmYwDQYJKoZIhvcNAQELBQAwXTELMAkGA1UEBhMCQlIxFjAU 											BgNVBAoMDUlDUC1JY3B0ZXN0ZXMxFTATBgNVBAsMDEFDIEljcHRlc3RlczEfMB0GA1UEAwwWQUMg 											SWNwdGVzdGVzIFN1YiBQSiB2MjAeFw0xNzA5MjUxMjU0MTRaFw0xODA5MjUxMjU0MTRaMIGWMQsw 											CQYDVQQGEwJCUjEWMBQGA1UECgwNSUNQLUljcHRlc3RlczEcMBoGA1UECwwTQUMgSWNwdGVzdGVz 											IFN1YiB2MjEfMB0GA1UECwwWQUMgSWNwdGVzdGVzIFN1YiBQSiB2MjEwMC4GA1UEAwwnRlJBTkNJ 											U1ZBTERPIEFMVkVTIERBIFNJTFZBOjEwOTg2Nzg1ODMwMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8A 											MIIBCgKCAQEAsiZOSPJGJhnD2kDgodHBvzayyjRdW+3gUoSvYQ7q27BAE4D8GLE5dgDMHixgRoOH 											TRGBYtrHPvAfKlNa4I4+DCKmuOCpsKWQ4QyrHnumkSWrZtQLqEhkiQR6JGVTVpo6kRpD2wqTVeeM 											10FmssjRv2WrKEekX2ykihW3n0EX8chqkO0bRr21sO05KymhU9EIXrzr6G7/XJIPMRWXOfwzV5Sv 											RP8zl9JZ0QQ2HpHi2UamcsLPgei9pRPw5yK+UbIjkTwGGhmcbNOXq13vuERzd/Z4sNkySN6q7/kR 											dl+IfJhk1XzpHN3N27Uig1QhSQ85YGWx4C8vAlCYs0MkCRwi6QIDAQABo4ICZTCCAmEwDgYDVR0P 											AQH/BAQDAgXgMCkGA1UdJQQiMCAGCCsGAQUFBwMCBggrBgEFBQcDBAYKKwYBBAGCNxQCAjAdBgNV 											HQ4EFgQU69kfdgMh3H2fjUuDDyoF8JZovKMwHwYDVR0jBBgwFoAU42YL1AnaM0yaj802QvL8npWp 											MEwwgaYGA1UdEQSBnjCBm6AXBgVgTAEDB6AOBAwwMDAwMDAwMDAwMDCgPQYFYEwBAwSgNAQyMTEx 											MTE5ODcxMDk4Njc4NTgzMDAwMDAwMDAwMDAwMDAwMDAwODczMjgzNzgySUZQUkqgGQYFYEwBAwOg 											EAQOMDAzNjAzMDUwMDAxMDSgJgYFYEwBAwKgHQQbRlJBTkNJU1ZBTERPIEFMVkVTIERBIFNJTFZB 											MHAGA1UdIARpMGcwZQYGYEwBAgEJMFswWQYIKwYBBQUHAgEWTWh0dHA6Ly93d3cucmVpY3AuY29y 											ZWRmLmNhaXhhL3JlcG9zaXRvcmlvL2NhZGVpYXYyL2RvY3VtZW50b3MvZHBjYWMtc3VicGoucGRm 											MF4GA1UdHwRXMFUwU6BRoE+GTWh0dHA6Ly93d3cucmVpY3AuY29yZWRmLmNhaXhhL3JlcG9zaXRv 											cmlvL2NhZGVpYXYyL2xjci9hY2ljcHRlc3Rlc3N1YnBqdjIuY3JsMGkGCCsGAQUFBwEBBF0wWzBZ 											BggrBgEFBQcwAoZNaHR0cDovL3d3dy5yZWljcC5jb3JlZGYuY2FpeGEvcmVwb3NpdG9yaW8vY2Fk 											ZWlhdjIvYWlhL2FjaWNwdGVzdGVzc3VicGp2Mi5wN2IwDQYJKoZIhvcNAQELBQADggEBAAUIBsGj 											aSnQwKvQTT40978kj702F9qRdddZwhQW3BR/3sY5KQIcP7e3u7jXhRALA/61jo3NfizD+NOod57A 											Ts73tKEF//8IUNW/ns+5Lg/+M0NsojdjOeHqm+LiBvvadJhDiyX5L48vUY1yjVFIhWrj0+CEHpEd 											XJDVzROjjf98bxbKd57RsTjk8O06ckBbFT+IURBYupNbpUzOoyZb7qOwtENgJ8beLpDyNzvtXZhy 											wkw0u/7QvwAWuAGSkrl6A/FnZuZrmCkwBKdYUKRG0VaNKHo0FEq7AIPBH1nQ8WIL756zG6Xxc8aP 											zGaBr1lsKWfJqKp5a6KbNQXXfHnV8q4= 										</X509Certificate> 									</X509Data> 								</KeyInfo> 							</Signature:Signature> 						</eSocial:eSocial> 					</evento> 				</eventos> 			</envioLoteEventos> 		</eSocial> 	</soapenv:Body> </soapenv:Envelope>";
//		String str = "<eSocial xmlns=\"http://www.esocial.gov.br/schema/evt/evtTabLotacao/v02_04_02\">   <evtTabLotacao Id=\"ID1074196560000002018040210071600001\">     <ideEvento>       <tpAmb>2</tpAmb>       <procEmi>1</procEmi>       <verProc>v02_04_01</verProc>     </ideEvento>     <ideEmpregador>       <tpInsc>1</tpInsc>       <nrInsc>07419656</nrInsc>     </ideEmpregador>     <infoLotacao>       <inclusao>         <ideLotacao>           <codLotacao>1</codLotacao>           <iniValid>2016-01</iniValid>         </ideLotacao>         <dadosLotacao>           <tpLotacao>01</tpLotacao>           <fpasLotacao>             <fpas>566</fpas>             <codTercs>0099</codTercs>           </fpasLotacao>         </dadosLotacao>       </inclusao>     </infoLotacao>   </evtTabLotacao> <Signature xmlns=\"http://www.w3.org/2000/09/xmldsig#\"><SignedInfo><CanonicalizationMethod Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\"/><SignatureMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#rsa-sha1\"/><Reference URI=\"\"><Transforms><Transform Algorithm=\"http://www.w3.org/2000/09/xmldsig#enveloped-signature\"/></Transforms><DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\"/><DigestValue>wkpF2dJqLhvEfF5QPtweR8aN2so=</DigestValue></Reference></SignedInfo><SignatureValue>W5F4nA+aWEQsuuZhM6Oo1eqwcPeiiA44yqJC7rNvzvdby+d1u4WEqvGFGNgih4fJEJRkEHwquYTs e+7oU4GuTojD2bHmyxosW9SJz8jRtK7dDXnaJIqXd5Hq7pekuPfbKSQ3Zcx438DxxTzCp3OAejHE 3Hx2fmATJyQ4QFY81TrwOUbUVSVWwZLQMX6C0lne0deOTAMoCnaRcQqzVLshVJS9iu72maHzEZ37 qpuEnIcVv9klMuugt2srfngqWXsebwLhejjQEpW0VXdd1ubZ81HCOAUVYeAVx5VoTuFtRaHs4NPX waK31K9U1XLMgV6SQLw4oOxzlMefhmnesimmIQ==</SignatureValue><KeyInfo><X509Data><X509SubjectName>CN=FRANCISVALDO ALVES DA SILVA:10986785830,OU=AC Icptestes Sub PJ v2,OU=AC Icptestes Sub v2,O=ICP-Icptestes,C=BR</X509SubjectName><X509Certificate>MIIF3TCCBMWgAwIBAgIIW87wOAtxBmYwDQYJKoZIhvcNAQELBQAwXTELMAkGA1UEBhMCQlIxFjAU BgNVBAoMDUlDUC1JY3B0ZXN0ZXMxFTATBgNVBAsMDEFDIEljcHRlc3RlczEfMB0GA1UEAwwWQUMg SWNwdGVzdGVzIFN1YiBQSiB2MjAeFw0xNzA5MjUxMjU0MTRaFw0xODA5MjUxMjU0MTRaMIGWMQsw CQYDVQQGEwJCUjEWMBQGA1UECgwNSUNQLUljcHRlc3RlczEcMBoGA1UECwwTQUMgSWNwdGVzdGVz IFN1YiB2MjEfMB0GA1UECwwWQUMgSWNwdGVzdGVzIFN1YiBQSiB2MjEwMC4GA1UEAwwnRlJBTkNJ U1ZBTERPIEFMVkVTIERBIFNJTFZBOjEwOTg2Nzg1ODMwMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8A MIIBCgKCAQEAsiZOSPJGJhnD2kDgodHBvzayyjRdW+3gUoSvYQ7q27BAE4D8GLE5dgDMHixgRoOH TRGBYtrHPvAfKlNa4I4+DCKmuOCpsKWQ4QyrHnumkSWrZtQLqEhkiQR6JGVTVpo6kRpD2wqTVeeM 10FmssjRv2WrKEekX2ykihW3n0EX8chqkO0bRr21sO05KymhU9EIXrzr6G7/XJIPMRWXOfwzV5Sv RP8zl9JZ0QQ2HpHi2UamcsLPgei9pRPw5yK+UbIjkTwGGhmcbNOXq13vuERzd/Z4sNkySN6q7/kR dl+IfJhk1XzpHN3N27Uig1QhSQ85YGWx4C8vAlCYs0MkCRwi6QIDAQABo4ICZTCCAmEwDgYDVR0P AQH/BAQDAgXgMCkGA1UdJQQiMCAGCCsGAQUFBwMCBggrBgEFBQcDBAYKKwYBBAGCNxQCAjAdBgNV HQ4EFgQU69kfdgMh3H2fjUuDDyoF8JZovKMwHwYDVR0jBBgwFoAU42YL1AnaM0yaj802QvL8npWp MEwwgaYGA1UdEQSBnjCBm6AXBgVgTAEDB6AOBAwwMDAwMDAwMDAwMDCgPQYFYEwBAwSgNAQyMTEx MTE5ODcxMDk4Njc4NTgzMDAwMDAwMDAwMDAwMDAwMDAwODczMjgzNzgySUZQUkqgGQYFYEwBAwOg EAQOMDAzNjAzMDUwMDAxMDSgJgYFYEwBAwKgHQQbRlJBTkNJU1ZBTERPIEFMVkVTIERBIFNJTFZB MHAGA1UdIARpMGcwZQYGYEwBAgEJMFswWQYIKwYBBQUHAgEWTWh0dHA6Ly93d3cucmVpY3AuY29y ZWRmLmNhaXhhL3JlcG9zaXRvcmlvL2NhZGVpYXYyL2RvY3VtZW50b3MvZHBjYWMtc3VicGoucGRm MF4GA1UdHwRXMFUwU6BRoE+GTWh0dHA6Ly93d3cucmVpY3AuY29yZWRmLmNhaXhhL3JlcG9zaXRv cmlvL2NhZGVpYXYyL2xjci9hY2ljcHRlc3Rlc3N1YnBqdjIuY3JsMGkGCCsGAQUFBwEBBF0wWzBZ BggrBgEFBQcwAoZNaHR0cDovL3d3dy5yZWljcC5jb3JlZGYuY2FpeGEvcmVwb3NpdG9yaW8vY2Fk ZWlhdjIvYWlhL2FjaWNwdGVzdGVzc3VicGp2Mi5wN2IwDQYJKoZIhvcNAQELBQADggEBAAUIBsGj aSnQwKvQTT40978kj702F9qRdddZwhQW3BR/3sY5KQIcP7e3u7jXhRALA/61jo3NfizD+NOod57A Ts73tKEF//8IUNW/ns+5Lg/+M0NsojdjOeHqm+LiBvvadJhDiyX5L48vUY1yjVFIhWrj0+CEHpEd XJDVzROjjf98bxbKd57RsTjk8O06ckBbFT+IURBYupNbpUzOoyZb7qOwtENgJ8beLpDyNzvtXZhy wkw0u/7QvwAWuAGSkrl6A/FnZuZrmCkwBKdYUKRG0VaNKHo0FEq7AIPBH1nQ8WIL756zG6Xxc8aP zGaBr1lsKWfJqKp5a6KbNQXXfHnV8q4=</X509Certificate></X509Data></KeyInfo></Signature></eSocial>";
		
		String strs = Util.formataNameSpaceLoteCompleto(str);
		System.out.println(strs);
		/*NharuX509Certificate cert = CertificadoUtil.validarCertificado(Util.convertStringToElement(strs));
		
		CertificadoUtil.validarAssinatura(Util.convertStringToElement(strs), cert);*/
		
		/*StringBuilder sb = new StringBuilder();
		String str = createTemplateSoapMessage();
		
		System.out.println("antes: "+str);
		sb.append(str.substring(0, str.indexOf("<soapenv:Body>")+14));
		System.out.println("depois: "+str);
		
		ESocial esocial = new ESocial();
		ConsultaLoteEventos cle = new ConsultaLoteEventos();
		cle.setProtocoloEnvio("1.2.201805.0000000000003400999");
		esocial.setConsultaLoteEventos(cle);
		
		StringWriter swter = Util.convertObjectInXML(esocial) ;
		
		sb.append(swter.toString());
		sb.append(str.substring(str.indexOf("</soapenv:Body>")));
		
		String novostr = Util.formataNameSpaceEvento(sb.toString());
		
		System.out.println("SOAP ENVELOPE: "+ novostr);*/
		//String str = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?> <ns2:eSocial xmlns:ns2=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns3=\"http://www.esocial.gov.br/schema/evt/evtInfoEmpregador/v02_04_02\">     <ns3:evtInfoEmpregador Id=\"ID1103687170001442018041713182000000\">         <ns3:ideEvento>             <ns3:tpAmb>2</ns3:tpAmb>             <ns3:procEmi>0</ns3:procEmi>             <ns3:verProc>0</ns3:verProc>         </ns3:ideEvento>         <ns3:ideEmpregador>             <ns3:tpInsc>1</ns3:tpInsc>             <ns3:nrInsc>123456789</ns3:nrInsc>         </ns3:ideEmpregador>         <ns3:infoEmpregador>             <ns3:inclusao>                 <ns3:idePeriodo>                     <ns3:iniValid>2017-01</ns3:iniValid>                     <ns3:fimValid>2017-02</ns3:fimValid>                 </ns3:idePeriodo>                 <ns3:infoCadastro>                     <ns3:nmRazao>Teste 123</ns3:nmRazao>                     <ns3:classTrib>10</ns3:classTrib>                     <ns3:natJurid>0000</ns3:natJurid>                     <ns3:indCoop>0</ns3:indCoop>                     <ns3:indConstr>0</ns3:indConstr>                     <ns3:indDesFolha>0</ns3:indDesFolha>                     <ns3:indOptRegEletron>0</ns3:indOptRegEletron>                     <ns3:indEntEd>N</ns3:indEntEd>                     <ns3:indEtt>N</ns3:indEtt>                     <ns3:nrRegEtt>12345678910</ns3:nrRegEtt>                     <ns3:dadosIsencao>                         <ns3:ideMinLei>ok</ns3:ideMinLei>                         <ns3:nrCertif>numero</ns3:nrCertif>                         <ns3:dtEmisCertif>2018-05-15</ns3:dtEmisCertif>                         <ns3:dtVencCertif>2018-05-15</ns3:dtVencCertif>                         <ns3:nrProtRenov>numero</ns3:nrProtRenov>                         <ns3:dtProtRenov>2018-05-15</ns3:dtProtRenov>                         <ns3:dtDou>2018-05-15</ns3:dtDou>                         <ns3:pagDou>10</ns3:pagDou>                     </ns3:dadosIsencao>                     <ns3:contato>                         <ns3:nmCtt>testeContato</ns3:nmCtt>                         <ns3:cpfCtt>09063372752</ns3:cpfCtt>                         <ns3:foneFixo>2122222222</ns3:foneFixo>                         <ns3:foneCel>21999999999</ns3:foneCel>                         <ns3:email>thiago@thiago.com</ns3:email>                     </ns3:contato>                     <ns3:infoOP>                         <ns3:nrSiafi>789</ns3:nrSiafi>                         <ns3:infoEFR>                             <ns3:ideEFR>S</ns3:ideEFR>                             <ns3:cnpjEFR>12345678912345</ns3:cnpjEFR>                         </ns3:infoEFR>                         <ns3:infoEnte>                             <ns3:nmEnte>ENTE</ns3:nmEnte>                             <ns3:uf>RJ</ns3:uf>                             <ns3:codMunic>10</ns3:codMunic>                             <ns3:indRPPS>S</ns3:indRPPS>                             <ns3:subteto>0</ns3:subteto>                             <ns3:vrSubteto>10</ns3:vrSubteto>                         </ns3:infoEnte>                     </ns3:infoOP>                     <ns3:infoOrgInternacional>                         <ns3:indAcordoIsenMulta>0</ns3:indAcordoIsenMulta>                     </ns3:infoOrgInternacional>                     <ns3:infoComplementares>                         <ns3:situacaoPJ>                             <ns3:indSitPJ>0</ns3:indSitPJ>                         </ns3:situacaoPJ>                         <ns3:situacaoPF>                             <ns3:indSitPF>0</ns3:indSitPF>                         </ns3:situacaoPF>                     </ns3:infoComplementares>                 </ns3:infoCadastro>             </ns3:inclusao>         </ns3:infoEmpregador>     </ns3:evtInfoEmpregador> </ns2:eSocial>";
		
		//System.out.println(Util.formataNameSpace(str));
		//String xmlFormatado = Util.formataNameSpace(str);
		
		//NodeList nodeList = Util.getNodeList(convertStringToElement(xmlFormatado), "infoEmpregador");
		
		//System.out.println(nodeList);
		
		/*for(int i = 0; i < nodeList.getLength(); i++) {
			//Node nodeList.item(i);
		}*/
		
		/*MontaXmlConsulta xmlConsulta = new MontaXmlConsulta();
		System.out.println(xmlConsulta.montarXml("1.2.201805.0000000000003400999"));*/
	}
}