/**
 * 
 */
package bs;

import consulta.envioConsulta.ESocial;
import consulta.envioConsulta.ESocial.ConsultaLoteEventos;
import exception.BusinessException;
import message.SystemPropertiesMessage;
import vo.RetornoConsultaVO;

/**
 * Classe responsável por disponibilizar o servico para a consulta do processamento do lote. 
 * 
 * @author Thiago Silva
 * 
 */
public class ConsultaProcessamentoLoteService implements IConsultaProcessamentoLotes {

	private final SystemPropertiesMessage properties;

	public ConsultaProcessamentoLoteService() {
		properties = SystemPropertiesMessage.getInstance();
	}
	
	/* (non-Javadoc)
	 * @see bs.IConsultaProcessamentoLotes#realizaConsultaProcessamento(java.lang.String)
	 */
	public RetornoConsultaVO realizarConsultaProcessamento(String protocolo) throws BusinessException {
		//MontaXmlConsulta montaXmlConsulta = new MontaXmlConsulta();
		ESocial esocial = new ESocial();
		ConsultaLoteEventos cle = new ConsultaLoteEventos();
		cle.setProtocoloEnvio(protocolo);
		esocial.setConsultaLoteEventos(cle);

		String xmlDaConsulta = MontaXML.montaXML(esocial);
		
		ProcessaConsultaLote resultadoProcessamento = new ProcessaConsultaLote(properties);
		RetornoConsultaVO retoronVO = resultadoProcessamento.consultarProcessamento(xmlDaConsulta);
		
		return retoronVO;
	}

	public static void main(String[] args) throws BusinessException {
		ConsultaProcessamentoLoteService service = new ConsultaProcessamentoLoteService();
		System.out.println(service.realizarConsultaProcessamento("1.2.201806.0000000000008500163"));//"1.2.201805.0000000000002934749"));
	}
	
}