package bs;

import java.net.URL;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.util.AXIOMUtil;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.log4j.Logger;

import consulta.retornoProcessamento.ESocial;
import consulta.retornoProcessamento.ESocial.RetornoProcessamentoLoteEventos.RetornoEventos.Evento;
import exception.BusinessException;
import message.SystemPropertiesMessage;
import util.Constantes;
import util.OnCert;
import util.SocketFactoryDinamico;
import util.Util;
import vo.RetornoConsultaVO;
import ws.consulta.ServicoConsultarLoteEventosStub;

public class ProcessaConsultaLote {
	
	private static final Logger LOGGER = Logger.getLogger(ProcessaConsultaLote.class);
	private final SystemPropertiesMessage properties;
	
	public ProcessaConsultaLote(SystemPropertiesMessage properties) {
		this.properties = properties;
	}
	
	public RetornoConsultaVO consultarProcessamento(String xmlConsulta) throws BusinessException {
		RetornoConsultaVO vo = new RetornoConsultaVO();
		
		try {
			URL urlCLE = new URL("https://webservices.producaorestrita.esocial.gov.br/servicos/empregador/consultarloteeventos/WsConsultarLoteEventos.svc"/*properties.getValueKey(Constantes.URL_CONSULTA_LOTE_EVENTOS)*/);
			
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
	
			System.out.println(resultCLE.getConsultarLoteEventosResult().getExtraElement().toString());
	
						vo.setXmlDaConsulta(xmlConsulta);
			vo.setResultadoConsulta(resultCLE.getConsultarLoteEventosResult().getExtraElement().toString());
			vo.setResultadoConsultaObjeto(Util.xmlToObject(ESocial.class, vo.getResultadoConsulta()));
		
			if(vo.getResultadoConsultaObjeto() != null && 
					((ESocial)vo.getResultadoConsultaObjeto()).getRetornoProcessamentoLoteEventos() != null &&
					((ESocial)vo.getResultadoConsultaObjeto()).getRetornoProcessamentoLoteEventos().getStatus() != null && 
					((ESocial)vo.getResultadoConsultaObjeto()).getRetornoProcessamentoLoteEventos().getStatus().getCdResposta() == 201) {
				
				if(((ESocial)vo.getResultadoConsultaObjeto()).getRetornoProcessamentoLoteEventos().getRetornoEventos() != null) {
					List<Evento> lstEvento = ((ESocial)vo.getResultadoConsultaObjeto()).getRetornoProcessamentoLoteEventos().getRetornoEventos().getEvento();

					if(lstEvento != null) {
						List<consulta.retornoEvento.ESocial> lstRetornoEventos = new ArrayList<>();
						for(int i = 0; i < lstEvento.size(); i++) {
							String retornoEvento = Util.nodeToString(lstEvento.get(i).getRetornoEvento().getAny(), true);
							consulta.retornoEvento.ESocial objRetornoEvento = 
									(consulta.retornoEvento.ESocial) Util.xmlToObject(consulta.retornoEvento.ESocial.class, retornoEvento);
							
							lstRetornoEventos.add(objRetornoEvento);
						}
					
						vo.setLstRetornoEvento(lstRetornoEventos);
					}
				}
			}
		}
		catch (Exception e) {
			LOGGER.error("Erro no metodo consultarProcessamento(String xmlConsulta): ", e);
			throw new BusinessException("Erro no metodo consultarProcessamento(String xmlConsulta): ", e);
		} 
		
		return vo;
	}
}
