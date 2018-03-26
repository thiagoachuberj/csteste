package bs;

import java.net.URL;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.util.AXIOMUtil;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.log4j.Logger;
import org.demoiselle.signer.core.keystore.loader.factory.KeyStoreLoaderFactory;

import exception.BusinessException;
import javassist.bytecode.ConstantAttribute;
import message.SystemPropertiesMessage;
import util.Aux_String;
import util.Constantes;
import util.EStatusEnvio;
import util.ETipoErro;
import util.OnCert;
import util.SocketFactoryDinamico;
import vo.RetornoEnvioVO;
import ws.envio.ServicoEnviarLoteEventosStub;

public class ProcessoEnvioLote {
	private static Logger LOG = Logger.getLogger(ProcessoEnvioLote.class);
	public SystemPropertiesMessage properties;

	public ProcessoEnvioLote(SystemPropertiesMessage properties) {
		this.properties = properties;
	}
	
	public RetornoEnvioVO processa(String loteEventos) throws BusinessException {
		RetornoEnvioVO retornoEnvioVO = new RetornoEnvioVO();
		
		try {
			URL urlELE = new URL(SystemPropertiesMessage.getSystemEnvOrProperty(Constantes.URL_ENVIAR_LOTE_EVENTOS));
			String[] strCertAlias = null;
			
			/*strCertAlias = OnCert.funcListaCertificados(false);
			OnCert.TAssinaXML tpAssinaXML = new OnCert.TAssinaXML();
			tpAssinaXML.strAliasTokenCert = strCertAlias[7];*/
			
			//strCertAlias = OnCert.funcListaCertificadosDemoiselle();

			KeyStore ks = OnCert.funcListaCertificadosDemoiselle();//OnCert.funcKeyStore(strCertAlias[0]);
			//String arquivoCacerts = properties.getValueKey("certificado.caminho");
			String senhaDoCertificado = SystemPropertiesMessage.getSystemEnvOrProperty(Constantes.SIGNER_PASSWORD);//properties.getValueKey("certificado.senha");

			String alias = "";
			Enumeration<String> aliasesEnum = ks.aliases();
			
			while (aliasesEnum.hasMoreElements()) {
				alias = (String) aliasesEnum.nextElement();
				
				if (ks.isKeyEntry(alias)) {
					break;
				}
			}
			
			X509Certificate certificate = (X509Certificate) ks.getCertificate(alias);
			PrivateKey privateKey = (PrivateKey) ks.getKey(alias, senhaDoCertificado.toCharArray());
			SocketFactoryDinamico socketFactoryDinamico = new SocketFactoryDinamico(certificate, privateKey);
			socketFactoryDinamico.setFileCacerts(SystemPropertiesMessage.getSystemEnvOrProperty(Constantes.PATH_CERT_DOMAIN));//arquivoCacerts);

			Protocol protocol = new Protocol("https", socketFactoryDinamico, Integer.parseInt(SystemPropertiesMessage.getSystemEnvOrProperty(Constantes.PORT_SSL)));
			Protocol.registerProtocol("https", protocol);
	
			OMElement ome = AXIOMUtil.stringToOM(loteEventos);
			ServicoEnviarLoteEventosStub.LoteEventos_type0 dadosMsgType0 = new ServicoEnviarLoteEventosStub.LoteEventos_type0();
			dadosMsgType0.setExtraElement(ome);

			ServicoEnviarLoteEventosStub.EnviarLoteEventos distEnvioEsocial = new ServicoEnviarLoteEventosStub.EnviarLoteEventos();
			distEnvioEsocial.setLoteEventos(dadosMsgType0);

			ServicoEnviarLoteEventosStub stub = new ServicoEnviarLoteEventosStub(urlELE.toString());
			ServicoEnviarLoteEventosStub.EnviarLoteEventosResponse result = stub.enviarLoteEventos(distEnvioEsocial);
			result.getEnviarLoteEventosResult().getExtraElement().toString();

			System.out.println(result.getEnviarLoteEventosResult().getExtraElement().toString());
			
			retornoEnvioVO.setProtocolo(Aux_String.subStrIntoDelim(result.getEnviarLoteEventosResult().getExtraElement().toString(), "<protocoloEnvio>", "</protocoloEnvio>", true));
			retornoEnvioVO.setStatus(EStatusEnvio.SUCESSO);
			retornoEnvioVO.setMensagem("Envio realizado com sucesso.");
			retornoEnvioVO.setLoteEnviado(loteEventos);
			System.out.println(retornoEnvioVO.getProtocolo());
		} 
		catch (Exception ex) {
			LOG.error(" ERRO ==>> ", ex);
			//throw new BusinessException(ex);
			retornoEnvioVO.setStatus(EStatusEnvio.FALHA);
			retornoEnvioVO.setLoteEnviado(loteEventos);
			retornoEnvioVO.setTipoErro(ETipoErro.ENVIO);
		}
	
		return retornoEnvioVO;
	}

}