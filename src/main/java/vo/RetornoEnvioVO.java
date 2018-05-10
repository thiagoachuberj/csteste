package vo;

/**
 * Classe responsável por retornar o resultado do envio do lote.
 * 
 * @author Thiago Silva
 *
 */
public class RetornoEnvioVO extends BaseVO { 

	private String protocolo;
	private String loteEnviado;
	private String retornoEnvio;
	private Object retornoEnvioObjeto;
	
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
	public void setRetornoEnvio(String retornoEnvio) {
		this.retornoEnvio = retornoEnvio;
	}
	public String getRetornoEnvio() {
		return retornoEnvio;
	}
	public void setRetornoEnvioObjeto(Object xmlToObject) {
		this.retornoEnvioObjeto = xmlToObject;
	}
	public Object getRetornoEnvioObjeto() {
		return retornoEnvioObjeto;
	}
}