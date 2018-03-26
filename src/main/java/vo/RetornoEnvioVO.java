package vo;

import util.EStatusEnvio;
import util.ETipoErro;

/**
 * Classe responsável por retornar o resultado do envio do lote.
 * 
 * @author Thiago Silva
 *
 */
public class RetornoEnvioVO extends BaseVO { 

	private EStatusEnvio status;
	private ETipoErro tipoErro;
	private String mensagem;
	private String protocolo;
	private String loteEnviado;

	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public String getLoteEnviado() {
		return loteEnviado;
	}
	public void setLoteEnviado(String loteEnviado) {
		this.loteEnviado = loteEnviado;
	}
	public String getProtocolo() {
		return protocolo;
	}
	public void setProtocolo(String protocolo) {
		this.protocolo = protocolo;
	}
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

}
