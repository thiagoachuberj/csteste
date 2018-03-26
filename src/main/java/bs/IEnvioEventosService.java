package bs;

import exception.BusinessException;
import vo.EntradaEnvioVO;
import vo.RetornoEnvioVO;

public interface IEnvioEventosService {
	
	public RetornoEnvioVO enviarLoteEventos(EntradaEnvioVO entrada) throws BusinessException;
	
}
