/**
 * 
 */
package bs;

import exception.BusinessException;
import vo.RetornoConsultaVO;

/**
 * @author Thiago Silva
 *
 */
public interface IConsultaProcessamentoLotes {
	
	public RetornoConsultaVO realizarConsultaProcessamento(String protocolo) throws BusinessException;
	
}
