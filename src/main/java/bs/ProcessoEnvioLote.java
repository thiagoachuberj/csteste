package bs;

import java.net.URL;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.util.AXIOMUtil;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.log4j.Logger;

import exception.BusinessException;
import exception.CertificadoException;
import lote.retornoEnvio.ESocial;
import message.SystemPropertiesMessage;
import util.Aux_String;
import util.Constantes;
import util.OnCert;
import util.SocketFactoryDinamico;
import util.Util;
import vo.RetornoEnvioVO;
import ws.envio.ServicoEnviarLoteEventosStub;

public class ProcessoEnvioLote {
	private static Logger LOGGER = Logger.getLogger(ProcessoEnvioLote.class);
	public SystemPropertiesMessage properties;

	public ProcessoEnvioLote(SystemPropertiesMessage properties) {
		this.properties = properties;
	}
	
	public RetornoEnvioVO processa(String loteEventos) throws BusinessException {
		RetornoEnvioVO retornoEnvioVO = new RetornoEnvioVO();
		String path_cert_domain = null, port_ssl = null, signer_alias = null, signer_password = null;
		
		try {
			String url = SystemPropertiesMessage.getSystemEnvOrProperty(Constantes.URL_ENVIAR_LOTE_EVENTOS);
			URL urlELE = new URL(url);
			
			KeyStore keystore = OnCert.loadKeystore();
			
			signer_alias = OnCert.getAliasCertificate(keystore);
			X509Certificate certificate = (X509Certificate) keystore.getCertificate(signer_alias);
			
			signer_password = OnCert.getSenhaCertificado();
			PrivateKey privateKey = (PrivateKey) keystore.getKey(signer_alias, signer_password.toCharArray());
			SocketFactoryDinamico socketFactoryDinamico = new SocketFactoryDinamico(certificate, privateKey);
			
			path_cert_domain = SystemPropertiesMessage.getSystemEnvOrProperty(Constantes.PATH_CERT_DOMAIN);
			socketFactoryDinamico.setFileCacerts(path_cert_domain);

			port_ssl = SystemPropertiesMessage.getSystemEnvOrProperty(Constantes.PORT_SSL);
			Protocol protocol = new Protocol("https", socketFactoryDinamico, Integer.parseInt(port_ssl));
			Protocol.registerProtocol("https", protocol);
	
			OMElement ome = AXIOMUtil.stringToOM(loteEventos);
			ServicoEnviarLoteEventosStub.LoteEventos_type0 dadosMsgType0 = new ServicoEnviarLoteEventosStub.LoteEventos_type0();
			dadosMsgType0.setExtraElement(ome);

			ServicoEnviarLoteEventosStub.EnviarLoteEventos distEnvioEsocial = new ServicoEnviarLoteEventosStub.EnviarLoteEventos();
			distEnvioEsocial.setLoteEventos(dadosMsgType0);

			ServicoEnviarLoteEventosStub stub = new ServicoEnviarLoteEventosStub(urlELE.toString());
			ServicoEnviarLoteEventosStub.EnviarLoteEventosResponse result = stub.enviarLoteEventos(distEnvioEsocial);

			System.out.println("XML de Envio ::: "+loteEventos);
			System.out.println("XML de Retorno ::: "+result.getEnviarLoteEventosResult().getExtraElement().toString());
			
			retornoEnvioVO.setProtocolo(Aux_String.subStrIntoDelim(result.getEnviarLoteEventosResult().getExtraElement().toString(), "<protocoloEnvio>", "</protocoloEnvio>", true));
			retornoEnvioVO.setLoteEnviado(loteEventos);
			retornoEnvioVO.setRetornoEnvio(result.getEnviarLoteEventosResult().getExtraElement().toString());
			retornoEnvioVO.setRetornoEnvioObjeto(Util.xmlToObject(ESocial.class, retornoEnvioVO.getRetornoEnvio()));
			System.out.println(retornoEnvioVO.getProtocolo());
		}
		catch(CertificadoException ex) {
			throw new BusinessException(ex);
		}
		catch (Exception ex) {
			LOGGER.debug("::: Dados do certificado ::: path_cert_domain = "+path_cert_domain 
					+", port_ssl = "+ port_ssl +", signer_password = "+ signer_password 
					+", signer_alias = "+signer_alias +"\n ::: Lote para Envio ::: "+ loteEventos);
			throw new BusinessException("Erro no metodo processa(String loteEventos): ", ex);
		}
	
		return retornoEnvioVO;
	}

}