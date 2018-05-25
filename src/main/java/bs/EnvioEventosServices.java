package bs;

import java.io.IOException;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import org.xml.sax.SAXException;

import exception.BusinessException;
import message.SystemPropertiesMessage;
import vo.EntradaEnvioVO;
import vo.RetornoEnvioVO;
import xml.XmlDocumentBuildFactory;
import xml.XmlValidationResolver;

public class EnvioEventosServices implements IEnvioEventosService {

	private XmlDocumentBuildFactory xmlFactory;
	private SystemPropertiesMessage properties;
	
	public EnvioEventosServices() throws SAXException, IOException, XMLStreamException {
		properties = SystemPropertiesMessage.getInstance();
		System.setProperty("maxOccurs", "10000");
		xmlFactory = new XmlDocumentBuildFactory(new XmlValidationResolver(SystemPropertiesMessage.getSystemEnvOrProperty("arquivosXSD")));
	}
	
	public EnvioEventosServices(String pathXSD) throws SAXException, IOException, XMLStreamException {
		properties = SystemPropertiesMessage.getInstance();
		System.setProperty("maxOccurs", "10000");
		xmlFactory = new XmlDocumentBuildFactory(new XmlValidationResolver(SystemPropertiesMessage.getSystemEnvOrProperty("arquivosXSD")));		
	}
	
	public RetornoEnvioVO enviarLoteEventos(EntradaEnvioVO entrada) throws BusinessException {
		try {
			RetornoEnvioVO retornoEnvioVO = new RetornoEnvioVO();
			List<String> lstEventosAssinados = null;
			
			if(entrada != null) {
				AssinaturaEventos assinaturaEventos = new AssinaturaEventos(xmlFactory);
				lstEventosAssinados = assinaturaEventos.assina(entrada.getLstEventosVO());
				
				if(lstEventosAssinados != null && !lstEventosAssinados.isEmpty()) {
					String loteEventos = null;
					
					MontagemLote montagemLote = new MontagemLote();
					loteEventos = montagemLote.montaLote(lstEventosAssinados, entrada.getGrupoLote(), entrada.getNumeroEmissor(), entrada.getNumeroTransmissor());
					
					if(loteEventos != null) {
						ProcessoEnvioLote processaEnvioLotes = new ProcessoEnvioLote(properties);
						retornoEnvioVO = processaEnvioLotes.processa(loteEventos);					
					}
				}
			}
			
			return retornoEnvioVO;
		}
		catch(BusinessException ex) {
			throw ex;
		}
	}
	
	public static void main(String []args) throws SAXException, IOException, XMLStreamException {
		new XmlDocumentBuildFactory(new XmlValidationResolver(SystemPropertiesMessage.getInstance().getValueKey("arquivosXSD")));
		System.out.println("alive");
	}
	
}