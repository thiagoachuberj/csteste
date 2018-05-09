/**
 * 
 */
package vo;

import util.EStatusEnvio;
import util.ETipoErro;

/**
 * @author Thiago Silva
 *
 */
public class BaseVO {

	private String tipoEvento;
	private EStatusEnvio status;
	private ETipoErro tipoErro;
	private String mensagem;
	
	public EStatusEnvio getStatus() {
		return status;
	}

	public void setStatus(EStatusEnvio status) {
		this.status = status;
	}

	public ETipoErro getTipoErro() {
		return tipoErro;
	}

	public void setTipoErro(ETipoErro tipoErro) {
		this.tipoErro = tipoErro;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public void setTipoEvento(String tipoEvento) {
		this.tipoEvento = tipoEvento;
	}
	
	public String getTipoEvento() {
		return tipoEvento;
	}
	
}
