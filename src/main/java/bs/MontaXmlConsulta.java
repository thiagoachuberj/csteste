package bs;

import java.io.IOException;
import java.io.StringWriter;

import javax.xml.bind.JAXBException;
import javax.xml.soap.SOAPException;

import org.apache.log4j.Logger;

import consulta.envioConsulta.ESocial;
import consulta.envioConsulta.ESocial.ConsultaLoteEventos;
import exception.BusinessException;
import util.Util;

public class MontaXmlConsulta {
	private static Logger LOGGER = Logger.getLogger(MontaXmlConsulta.class);
	
	/*public String montarXml(String protocolo) throws BusinessException {
		StringBuffer sb = new StringBuffer();
		
		try {
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		sb.append(" <soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
		sb.append(" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"");
		sb.append(" xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\">");
		sb.append(" <soap:Header/>");
		sb.append(" <soap:Body>");
		sb.append(" <eSocial xmlns=\"http://www.esocial.gov.br/schema/lote/eventos/envio/consulta/retornoProcessamento/v1_0_0\">");
		sb.append(" <consultaLoteEventos>");
		sb.append(" <protocoloEnvio>"+protocolo+"</protocoloEnvio>");
		sb.append(" </consultaLoteEventos>");
		sb.append(" </eSocial>");

		sb.append(" </soap:Body>");
		sb.append(" </soap:Envelope>");
		System.out.println("XML SOAP a ser enviado : "+sb.toString());

		return sb.toString();
		}
		catch(Exception ex) {
			LOGGER.error("Erro no metodo montarXml(String protocolo): ", ex);
			throw new BusinessException("Erro no metodo montarXml(String protocolo): ", ex);
		}
	}*/
	
	
	public String montarXml(String protocolo) throws BusinessException {
		try {
			StringBuilder sb = new StringBuilder();
			String templateSoap = Util.createTemplateSoapMessage();
			
			ESocial esocial = new ESocial();
			ConsultaLoteEventos cle = new ConsultaLoteEventos();
			cle.setProtocoloEnvio(protocolo);
			esocial.setConsultaLoteEventos(cle);
			
			StringWriter swriter = Util.convertObjectInXML(esocial);
			
			switch (templateSoap.indexOf("<soapenv:Body>")) { 
				case -1:
					sb.append(templateSoap.substring(0, templateSoap.indexOf("<soapenv:Body />")));
					sb.append("<soapenv:Body>");
					sb.append(swriter.toString());
					sb.append("</soapenv:Body>");
					sb.append("</soapenv:Envelope>");
					break;
				
				default:
					sb.append(templateSoap.substring(0, templateSoap.indexOf("<soapenv:Body>")+14));
					sb.append(swriter.toString());
					sb.append(templateSoap.substring(templateSoap.indexOf("</soapenv:Body>")));
					break;
			}			
			
			return Util.formataNameSpace(sb.toString());
		} 
		catch (SOAPException | IOException e) {
			LOGGER.error("Erro no metodo Util.createTemplateSoapMessage(): ", e);
			throw new BusinessException("Erro no metodo Util.createTemplateSoapMessage(): ", e);
		} 
		catch (JAXBException e) {
			LOGGER.error("Erro no metodo Util.convertObjectInXML(esocial): ", e);
			throw new BusinessException("Erro no metodo Util.createTemplateSoapMessage(): ", e);
		}
	}
}