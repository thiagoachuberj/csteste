/**
 * 
 */
package vo;

import util.ETipoErro;

/**
 * @author Thiago Silva
 *
 */
public class BaseVO {

	private String tipoEvento;
	private ETipoErro tipoErro;
	private String mensagem;
	private String id;
	
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
