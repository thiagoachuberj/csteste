package bs;

import java.io.IOException;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import exception.BusinessException;
import message.SystemPropertiesMessage;
import vo.EntradaEnvioVO;
import vo.RetornoEnvioVO;
import xml.XmlDocumentBuildFactory;
import xml.XmlValidationResolver;

public class EnvioEventosServices implements IEnvioEventosService {
	private static final Logger LOGGER = Logger.getLogger(EnvioEventosServices.class);
	private XmlDocumentBuildFactory xmlFactory;
	private SystemPropertiesMessage properties;
	
	public EnvioEventosServices() throws SAXException, IOException, XMLStreamException {
		properties = SystemPropertiesMessage.getInstance();
		System.setProperty("jdk.xml.maxOccurLimit", "10000");
		xmlFactory = new XmlDocumentBuildFactory(new XmlValidationResolver(SystemPropertiesMessage.getSystemEnvOrProperty("arquivosXSD")));
	}
	
	public EnvioEventosServices(String pathXSD) throws SAXException, IOException, XMLStreamException {
		properties = SystemPropertiesMessage.getInstance();
		System.setProperty("jdk.xml.maxOccurLimit", "10000");
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
					
					loteEventos = MontagemLote.montaLote(lstEventosAssinados, entrada.getGrupoLote(), entrada.getNumeroEmissor(), entrada.getNumeroTransmissor());
					
					if(loteEventos != null) {
						ProcessoEnvioLote processaEnvioLotes = new ProcessoEnvioLote(properties);
						retornoEnvioVO = processaEnvioLotes.processa(loteEventos);					
					}
					else {
						throw new BusinessException("Ocorreu um problema ao tentar criar o Lote.");	
					}
				}
				else {
					throw new BusinessException("Eventos não localizados para efetuar o envio.");
				}
			}
			else {
				throw new BusinessException("Objeto EntradaEnvioVO é vazio ou nulo");
			}
			
			return retornoEnvioVO;
		}
		catch(Exception ex) {
			LOGGER.error("Erro no metodo enviarLoteEventos(EntradaEnvioVO entrada): ", ex);
			throw new BusinessException("Erro no metodo enviarLoteEventos(EntradaEnvioVO entrada): ", ex);
		}
	}
	
	/*public RetornoEnvioVO enviarLoteEventos(EntradaEnvioVO entrada) throws BusinessException {
		try {
			RetornoEnvioVO retornoEnvioVO = new RetornoEnvioVO();
			List<String> lstEventosAssinados = null;
			
			if(entrada != null) {
				AssinaturaEventos assinaturaEventos = new AssinaturaEventos(xmlFactory);
				lstEventosAssinados = assinaturaEventos.assina(entrada.getLstEventosVO());
				
				if(lstEventosAssinados != null && !lstEventosAssinados.isEmpty()) {
//				if(entrada.getLstEventosVO() != null && !entrada.getLstEventosVO().isEmpty()) {
					String loteEventos = null;

					ESocial loteEnvio = new ESocial();
					EnvioLoteEventos envioLoteEventos = new EnvioLoteEventos();
					envioLoteEventos.setGrupo(Integer.parseInt(entrada.getGrupoLote()));
					TIdeEmpregador idEmpregador = new TIdeEmpregador();
					idEmpregador.setTpInsc(entrada.getNumeroEmissor().length() > 11 ? new Byte("1") : new Byte("2"));
					idEmpregador.setNrInsc(entrada.getNumeroEmissor());
					envioLoteEventos.setIdeEmpregador(idEmpregador);
					TIdeTransmissor ideTransmissor = new TIdeTransmissor();
					ideTransmissor.setTpInsc(entrada.getNumeroTransmissor().length() > 11 ? new Byte("1") : new Byte("2"));
					ideTransmissor.setNrInsc(entrada.getNumeroTransmissor());
					envioLoteEventos.setIdeTransmissor(ideTransmissor);
					
					Eventos eventos = new Eventos();
					for(String str : lstEventosAssinados) {
						String idEvento = str.substring(str.indexOf("Id=\"") + 4, str.indexOf("Id=\"") + 40 );
						TArquivoEsocial evento = new TArquivoEsocial();
						evento.setId(idEvento);
						evento.setAny(Util.convertStringToElement(str));
						eventos.getEvento().add(evento);
					}
					
					envioLoteEventos.setEventos(eventos);
					loteEnvio.setEnvioLoteEventos(envioLoteEventos);
					
					loteEventos = MontaXML.montaXML(loteEnvio);
					
					if(loteEventos != null) {
						ProcessoEnvioLote processaEnvioLotes = new ProcessoEnvioLote(properties);
						retornoEnvioVO = processaEnvioLotes.processa(loteEventos);					
					}
					else {
						throw new BusinessException("Ocorreu um problema ao tentar criar o Lote.");	
					}
				}
				else {
					throw new BusinessException("Eventos não localizados para efetuar o envio.");
				}
			}
			
			return retornoEnvioVO;
		}
		catch(IOException ex) {
			LOGGER.error("Erro no metodo enviarLoteEventos(EntradaEnvioVO entrada): ", ex);
			throw new BusinessException("Erro no metodo enviarLoteEventos(EntradaEnvioVO entrada): ", ex);
		}
	}*/
	
	public static void main(String []args) throws SAXException, IOException, XMLStreamException {
		new XmlDocumentBuildFactory(new XmlValidationResolver(SystemPropertiesMessage.getInstance().getValueKey("arquivosXSD")));
		System.out.println("alive");
	}
	
}