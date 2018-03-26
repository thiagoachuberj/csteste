package bs;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import exception.BusinessException;
import util.OnCert;
import util.OnCert.TAssinaXML;
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
		String[] strCertAlias;
		
		try {
			strCertAlias = OnCert.funcListaCertificados(false);
		
			OnCert.TAssinaXML dadosAssinatura = new OnCert.TAssinaXML();
			
			for(BaseVO vo : lstEventos) {
				//converter o evento em xml
				StringWriter stringWriterXml = Util.convertObjectInXML(vo);
				String xmlEventoFormatado = Util.formataNameSpace(stringWriterXml.toString());
				
				dadosAssinatura.strArquivoXML = xmlEventoFormatado;
				//dadosAssinatura.strAliasTokenCert = strCertAlias[7];
				
				//TAssinaXML eventoXMLAssinado = OnCert.funcAssinaEventoXML(dadosAssinatura);
				String eventoXMLAssinado = OnCert.assinarCertificado(dadosAssinatura.strArquivoXML.getBytes(StandardCharsets.UTF_8));
				
				//System.out.println(":::: eventoXMLAssinado.xmlAssinado "+ eventoXMLAssinado.xmlAssinado);
				
				//Util.validarXml(xmlFactory, eventoXMLAssinado.xmlAssinado.getBytes(StandardCharsets.UTF_8));
				
				/*Validador validar = new Validador();
				validar.valida(), new File("C:\\Users\\Thiago Silva\\Documents\\Projetos\\git\\csesocial\\src\\main\\java\\br\\com\\csesocial\\evento\\v2401\\S1000\\evtInfoEmpregador.xsd"));*/
				
				//lstXml.add(eventoXMLAssinado.xmlAssinado);
				lstXml.add(eventoXMLAssinado);
			}
		}
		catch (NoSuchProviderException | NoSuchAlgorithmException | CertificateException | IOException e) {
			LOG.error("assinaEventos(List<BaseVO> lstEventos) ==>> ", e);
			throw new BusinessException(e);
		} 
		catch (Exception e) {
			LOG.error("assinaEventos(List<BaseVO> lstEventos) ==>> ", e);
			throw new BusinessException(e);
		}
				
		return lstXml;
	}

}