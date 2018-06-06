package bs;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

import org.apache.log4j.Logger;

import exception.BusinessException;
import exception.CertificadoException;
import message.SystemPropertiesMessage;
import util.OnCert;
import util.Util;
import vo.BaseVO;
import ws.envio.EnvioLoteEvento;
import xml.XmlDocumentBuildFactory;
import xml.XmlValidationResolver;

public class AssinaturaEventos {

	private static final Logger LOGGER = Logger.getLogger(AssinaturaEventos.class);
	private XmlDocumentBuildFactory xmlFactory;
	
	public AssinaturaEventos(XmlDocumentBuildFactory xmlFactory) {
		this.xmlFactory = xmlFactory;
	}
	
	public List<String> assina(List<BaseVO> lstEventos) throws BusinessException {
		List<String> lstXml = new ArrayList<String>();
		OnCert.TAssinaXML dadosAssinatura = new OnCert.TAssinaXML();
		String eventoXMLAssinado = null;
		
		try {
			for(BaseVO vo : lstEventos) {
				//converter o evento em xml
				String xmlEventoFormatado = null;
				
				try {
					StringWriter stringWriterXml = Util.convertObjectInXML(vo);
					xmlEventoFormatado = Util.formataNameSpaceEvento(stringWriterXml.toString()); //xmlns="http://www.w3.org/2000/09/xmldsig#"
				}
				catch (JAXBException e) {
					LOGGER.error("::: Util.convertObjectInXML(vo) :::" + vo.toString());
					throw new BusinessException("Erro no metodo Util.convertObjectInXML: ", e);
				} 
				
				dadosAssinatura.strArquivoXML = xmlEventoFormatado;

				eventoXMLAssinado = OnCert.assinarCertificado(dadosAssinatura.strArquivoXML.getBytes(StandardCharsets.UTF_8));
				
				Util.validarXml(xmlFactory, eventoXMLAssinado.getBytes(StandardCharsets.UTF_8));
				
				lstXml.add(eventoXMLAssinado);
			}
		}
		catch (CertificadoException e) {
			throw new BusinessException("Erro no metodo OnCert.assinarCertificado: ", e);
		} 
		catch (IOException e) {
			LOGGER.error("::: Util.validarXml() :::" + eventoXMLAssinado);
			throw new BusinessException("Erro no metodo Util.validarXml: ", e);
		}
				
		return lstXml;
	}

	public static void main(String[] args) throws Exception, IOException, XMLStreamException {
		//monta lista de evento
		List<BaseVO> lstEventos = EnvioLoteEvento.montaDadosEventoCadastroEvtInfoEmpregador("1", "09063372752", 1);
		
		System.setProperty("jdk.xml.maxOccurLimit", "10000");
		AssinaturaEventos assinaturaEventos = new AssinaturaEventos(new XmlDocumentBuildFactory(new XmlValidationResolver(SystemPropertiesMessage.getSystemEnvOrProperty("arquivosXSD"))));
		System.out.println(assinaturaEventos.assina(lstEventos));
	}
	
}