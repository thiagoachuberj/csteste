package bs;

import java.io.IOException;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import exception.BusinessException;
import message.SystemPropertiesMessage;
import util.ETipoErro;
import vo.EntradaEnvioVO;
import vo.RetornoEnvioVO;

public class EnvioEventosServices implements IEnvioEventosService {

	private static Logger LOG = Logger.getLogger(EnvioEventosServices.class);
	//private XmlDocumentBuildFactory xmlFactory;
	private SystemPropertiesMessage properties;
	
	public EnvioEventosServices() throws SAXException, IOException, XMLStreamException {
		properties = SystemPropertiesMessage.getInstance();
		//xmlFactory = new XmlDocumentBuildFactory(new XmlValidationResolver(properties.getValueKey("arquivosXSD")));
	}
	
	public EnvioEventosServices(String pathXSD) throws SAXException, IOException, XMLStreamException {
		properties = SystemPropertiesMessage.getInstance();
		//xmlFactory = new XmlDocumentBuildFactory(new XmlValidationResolver(properties.getValueKey("arquivosXSD")));		
	}
	
	public RetornoEnvioVO enviarLoteEventos(EntradaEnvioVO entrada) throws BusinessException { 
		RetornoEnvioVO retornoEnvioVO = new RetornoEnvioVO();
		List<String> lstEventosAssinados = null;

		if(entrada != null) {
			try {
				AssinaturaEventos assinaturaEventos = new AssinaturaEventos();//(xmlFactory);
				lstEventosAssinados = assinaturaEventos.assina(entrada.getLstEventosVO());
			}
			catch(BusinessException ex) {
				retornoEnvioVO.setTipoErro(ETipoErro.ASSINATURA);
				retornoEnvioVO.setMensagem(ex.toString());
			}
			
			if(lstEventosAssinados != null && !lstEventosAssinados.isEmpty()) {
				String loteEventos = null;
				MontagemLote montagemLote = new MontagemLote();
				
				try {
					loteEventos = montagemLote.montaLote(lstEventosAssinados, entrada.getGrupoLote(), entrada.getNumeroEmissor(), entrada.getNumeroTransmissor());
				}
				catch(BusinessException ex) {
					retornoEnvioVO.setTipoErro(ETipoErro.MONTAGEM_LOTE);
					retornoEnvioVO.setMensagem(ex.toString());
				}
				
				if(loteEventos != null) {
					ProcessoEnvioLote processaEnvioLotes = new ProcessoEnvioLote(properties);
					retornoEnvioVO = processaEnvioLotes.processa(loteEventos);					
				}
			}
		}
		
		return retornoEnvioVO;
	}
	
}