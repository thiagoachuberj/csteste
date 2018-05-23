package bs;

import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import exception.BusinessException;
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
				
				lstXml.add(eventoXMLAssinado);
			}
		}
		catch (Exception e) {
			LOG.error("assinaEventos(List<BaseVO> lstEventos) ==>> ", e);
			throw new BusinessException(e);
		}
				
		return lstXml;
	}

}