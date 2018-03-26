package vo;

import java.util.List;

public class EntradaEnvioVO extends BaseVO {
	
	private List<BaseVO> lstEventosVO; 
	private String grupoLote;
	private String numeroEmissor;
	private String numeroTransmissor;
	
	public List<BaseVO> getLstEventosVO() {
		return lstEventosVO;
	}
	public void setLstEventosVO(List<BaseVO> lstEventosVO) {
		this.lstEventosVO = lstEventosVO;
	}
	public String getGrupoLote() {
		return grupoLote;
	}
	public void setGrupoLote(String grupoLote) {
		this.grupoLote = grupoLote;
	}
	public String getNumeroEmissor() {
		return numeroEmissor;
	}
	public void setNumeroEmissor(String numeroEmissor) {
		this.numeroEmissor = numeroEmissor;
	}
	public String getNumeroTransmissor() {
		return numeroTransmissor;
	}
	public void setNumeroTransmissor(String numeroTransmissor) {
		this.numeroTransmissor = numeroTransmissor;
	}

}