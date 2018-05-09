package bs;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

import javax.xml.stream.XMLStreamException;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.util.AXIOMUtil;
import org.apache.axis2.AxisFault;

import util.EStatusEnvio;
import vo.RetornoConsultaVO;
import ws.consulta.ServicoConsultarLoteEventosStub;

public class EnviaConsultaProcessamento {
	
	private static String URL_CONSULTA = "https://webservices.producaorestrita.esocial.gov.br/servicos/empregador/consultarloteeventos/WsConsultarLoteEventos.svc"; 
	
	public RetornoConsultaVO consultarProcessamento(String xmlConsulta) {
		RetornoConsultaVO vo = new RetornoConsultaVO();
		
		try {
			URL urlCLE = new URL(URL_CONSULTA);
		
			OMElement omeCLE = AXIOMUtil.stringToOM(xmlConsulta);
			ServicoConsultarLoteEventosStub.Consulta_type0 dadosMsgTypeCLE = new ServicoConsultarLoteEventosStub.Consulta_type0();
			dadosMsgTypeCLE.setExtraElement(omeCLE);
	
			ServicoConsultarLoteEventosStub.ConsultarLoteEventos distEnvioESocial = new ServicoConsultarLoteEventosStub.ConsultarLoteEventos();
			distEnvioESocial.setConsulta(dadosMsgTypeCLE);
	
			ServicoConsultarLoteEventosStub stubCLE = new ServicoConsultarLoteEventosStub(urlCLE.toString());
			ServicoConsultarLoteEventosStub.ConsultarLoteEventosResponse resultCLE = stubCLE.consultarLoteEventos(distEnvioESocial); 
			resultCLE.getConsultarLoteEventosResult().getExtraElement().toString();
	
			System.out.println(resultCLE.getConsultarLoteEventosResult().getExtraElement().toString());
	
			vo.setXmlDaConsulta(xmlConsulta);
			vo.setStatus(EStatusEnvio.SUCESSO);
			vo.setResultadoConsulta(resultCLE.getConsultarLoteEventosResult().getExtraElement().toString());
		} 
		catch (MalformedURLException e) {
			e.printStackTrace();
			vo.setXmlDaConsulta(xmlConsulta);
			vo.setStatus(EStatusEnvio.FALHA);
			vo.setMensagem(e.toString());
			vo.setTipoErro(null);
		} 
		catch (XMLStreamException e) {
			e.printStackTrace();
			vo.setXmlDaConsulta(xmlConsulta);
			vo.setStatus(EStatusEnvio.FALHA);
			vo.setMensagem(e.toString());
			vo.setTipoErro(null);
		} 
		catch (AxisFault e) {
			e.printStackTrace();
			vo.setXmlDaConsulta(xmlConsulta);
			vo.setStatus(EStatusEnvio.FALHA);
			vo.setMensagem(e.toString());
			vo.setTipoErro(null);
		} 
		catch (RemoteException e) {
			e.printStackTrace();
			vo.setXmlDaConsulta(xmlConsulta);
			vo.setStatus(EStatusEnvio.FALHA);
			vo.setMensagem(e.toString());
			vo.setTipoErro(null);
		}
		
		return vo;
	}
}
