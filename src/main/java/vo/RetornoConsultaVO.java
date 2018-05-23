package vo;

import java.util.List;

import consulta.retornoEvento.ESocial;

public class RetornoConsultaVO extends BaseVO {

	private String xmlDaConsulta;
	private String resultadoConsulta;
	private Object resultadoConsultaObjeto;
	private List<ESocial> lstRetornoEvento; 
	
	public String getXmlDaConsulta() {
		return xmlDaConsulta;
	}
	public void setXmlDaConsulta(String xmlDaConsulta) {
		this.xmlDaConsulta = xmlDaConsulta;
	}
	public String getResultadoConsulta() {
		return resultadoConsulta;
	}
	public void setResultadoConsulta(String resultadoConsulta) {
		this.resultadoConsulta = resultadoConsulta;
	}
	public void setResultadoConsultaObjeto(Object resultadoConsultaObjeto) {
		this.resultadoConsultaObjeto = resultadoConsultaObjeto;
	}
	public Object getResultadoConsultaObjeto() {
		return resultadoConsultaObjeto;
	}
	public List<ESocial> getLstRetornoEvento() {
		return lstRetornoEvento;
	}
	public void setLstRetornoEvento(List<ESocial> lstRetornoEvento) {
		this.lstRetornoEvento = lstRetornoEvento;
	}
	
}
