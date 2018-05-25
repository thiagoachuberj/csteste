package bs;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;

import exception.BusinessException;
import exception.CertificadoException;
import util.OnCert;
import util.Util;
import vo.BaseVO;
import xml.XmlDocumentBuildFactory;

public class AssinaturaEventos {

	private static Logger LOG = Logger.getLogger(AssinaturaEventos.class);
	private XmlDocumentBuildFactory xmlFactory;
	
	public AssinaturaEventos() {	
	}
	
	public AssinaturaEventos(XmlDocumentBuildFactory xmlFactory) {
		this.xmlFactory = xmlFactory;
	}
	
	public List<String> assina(List<BaseVO> lstEventos) throws BusinessException {
		List<String> lstXml = new ArrayList<String>();
		
		try {
			OnCert.TAssinaXML dadosAssinatura = new OnCert.TAssinaXML();
			
			for(BaseVO vo : lstEventos) {
				//converter o evento em xml
				StringWriter stringWriterXml = Util.convertObjectInXML(vo);
				String xmlEventoFormatado = Util.formataNameSpace(stringWriterXml.toString());
				
				dadosAssinatura.strArquivoXML = xmlEventoFormatado;

				String eventoXMLAssinado = OnCert.assinarCertificado(dadosAssinatura.strArquivoXML.getBytes(StandardCharsets.UTF_8));
				
				Util.validarXml(xmlFactory, eventoXMLAssinado.getBytes(StandardCharsets.UTF_8));
				
				lstXml.add(eventoXMLAssinado);
			}
		}
		catch (CertificadoException e) {
			LOG.error("Erro no metodo \"OnCert.assinarCertificado\": ", e);
			throw new BusinessException("Erro no metodo \"OnCert.assinarCertificado\": ", e);
		} 
		catch (JAXBException e) {
			LOG.error("Erro no metodo \"Util.convertObjectInXML\": ", e);
			throw new BusinessException("Erro no metodo \"Util.convertObjectInXML\": ", e);
		} 
		catch (IOException e) {
			LOG.error("Erro no metodo \"Util.validarXml\": ", e);
			throw new BusinessException("Erro no metodo \"Util.validarXml\": ", e);
		}
				
		return lstXml;
	}

}