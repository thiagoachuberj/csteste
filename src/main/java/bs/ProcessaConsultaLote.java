package bs;

import java.net.URL;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.util.AXIOMUtil;
import org.apache.commons.httpclient.protocol.Protocol;

import consulta.retornoProcessamento.ESocial;
import message.SystemPropertiesMessage;
import util.Constantes;
import util.EStatusEnvio;
import util.OnCert;
import util.SocketFactoryDinamico;
import util.Util;
import vo.RetornoConsultaVO;
import ws.consulta.ServicoConsultarLoteEventosStub;

public class ProcessaConsultaLote {
	
	private static String URL_CONSULTA = "https://webservices.producaorestrita.esocial.gov.br/servicos/empregador/consultarloteeventos/WsConsultarLoteEventos.svc"; 
	
	public RetornoConsultaVO consultarProcessamento(String xmlConsulta) {
		RetornoConsultaVO vo = new RetornoConsultaVO();
		
		try {
			URL urlCLE = new URL(URL_CONSULTA);
			
			KeyStore keystore = OnCert.loadKeystore();
			
			X509Certificate certificate = (X509Certificate) keystore.getCertificate(OnCert.getAliasCertificate(keystore));
			PrivateKey privateKey = (PrivateKey) keystore.getKey(OnCert.getAliasCertificate(keystore), OnCert.getSenhaCertificado().toCharArray());
			SocketFactoryDinamico socketFactoryDinamico = new SocketFactoryDinamico(certificate, privateKey);
			socketFactoryDinamico.setFileCacerts(SystemPropertiesMessage.getSystemEnvOrProperty(Constantes.PATH_CERT_DOMAIN));

			Protocol protocol = new Protocol("https", socketFactoryDinamico, Integer.parseInt(SystemPropertiesMessage.getSystemEnvOrProperty(Constantes.PORT_SSL)));
			Protocol.registerProtocol("https", protocol);
		
			OMElement omeCLE = AXIOMUtil.stringToOM(xmlConsulta);
			ServicoConsultarLoteEventosStub.Consulta_type0 dadosMsgTypeCLE = new ServicoConsultarLoteEventosStub.Consulta_type0();
			dadosMsgTypeCLE.setExtraElement(omeCLE);
	
			ServicoConsultarLoteEventosStub.ConsultarLoteEventos distEnvioESocial = new ServicoConsultarLoteEventosStub.ConsultarLoteEventos();
			distEnvioESocial.setConsulta(dadosMsgTypeCLE);
	
			ServicoConsultarLoteEventosStub stubCLE = new ServicoConsultarLoteEventosStub(urlCLE.toString());
			ServicoConsultarLoteEventosStub.ConsultarLoteEventosResponse resultCLE = stubCLE.consultarLoteEventos(distEnvioESocial); 
			resultCLE.getConsultarLoteEventosResult().getExtraElement().toString();
	
			System.out.println(resultCLE.getConsultarLoteEventosResult().getExtraElement().toString());
	
			vo.setXmlDaConsulta(xmlConsulta);
			vo.setStatus(EStatusEnvio.SUCESSO);
			vo.setResultadoConsulta(resultCLE.getConsultarLoteEventosResult().getExtraElement().toString());
			vo.setResultadoConsultaObjeto(Util.xmlToObject(ESocial.class, vo.getResultadoConsulta()));			
		} 
		catch (Exception e) {
			e.printStackTrace();
			vo.setXmlDaConsulta(xmlConsulta);
			vo.setStatus(EStatusEnvio.FALHA);
			vo.setMensagem(e.toString());
			vo.setTipoErro(null);
		} 
		
		return vo;
	}
}
