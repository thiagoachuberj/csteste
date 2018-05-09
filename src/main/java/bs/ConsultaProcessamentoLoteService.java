/**
 * 
 */
package bs;

import exception.BusinessException;
import vo.RetornoConsultaVO;

/**
 * Classe responsável por disponibilizar o servico para a consulta do processamento do lote. 
 * 
 * @author Thiago Silva
 * 
 */
public class ConsultaProcessamentoLoteService implements IConsultaProcessamentoLotes {

	/* (non-Javadoc)
	 * @see bs.IConsultaProcessamentoLotes#realizaConsultaProcessamento(java.lang.String)
	 */
	public RetornoConsultaVO realizarConsultaProcessamento(String protocolo) throws BusinessException {
		//String templateSoapMessage = Util.createTemplateSoapMessage();
		
		MontaXmlConsulta montaXmlConsulta = new MontaXmlConsulta();
		String xmlDaConsulta = montaXmlConsulta.montarXml(protocolo);
		
		ProcessaConsultaLote resultadoProcessamento = new ProcessaConsultaLote();
		RetornoConsultaVO retoronVO = resultadoProcessamento.consultarProcessamento(xmlDaConsulta);
		
		return retoronVO;
	}

	public static void main(String[] args) throws BusinessException {
		ConsultaProcessamentoLoteService service = new ConsultaProcessamentoLoteService();
		System.out.println(service.realizarConsultaProcessamento("1.2.201804.0000000000007369608"));
	}
	
}