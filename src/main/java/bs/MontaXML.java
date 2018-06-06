package bs;

import java.io.IOException;
import java.io.StringWriter;

import javax.xml.bind.JAXBException;
import javax.xml.soap.SOAPException;

import org.apache.log4j.Logger;

import exception.BusinessException;
import util.Util;

public final class MontaXML {
	private static Logger LOGGER = Logger.getLogger(MontaXML.class);
	
	public static String montaXML(Object objeto) throws BusinessException {
		String templateSoap = null;
		StringWriter swriter = null;
		
		try {
			templateSoap = Util.createTemplateSoapMessage();
			StringBuilder sb = new StringBuilder();
			swriter = Util.convertObjectInXML(objeto);
			
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
			
			return Util.formataNameSpaceLoteCompleto(sb.toString());//Util.formataNameSpaceEvento(sb.toString());
		}
		catch (SOAPException | IOException e) {
			LOGGER.debug("::: templateSoap = Util.createTemplateSoapMessage() ::: " + templateSoap);
			throw new BusinessException("Erro no metodo Util.createTemplateSoapMessage(): ", e);
		} 
		catch (JAXBException e) {
			LOGGER.debug("::: swriter = Util.convertObjectInXML() ::: " + objeto.toString());
			throw new BusinessException("Erro no metodo Util.convertObjectInXML(): ", e);
		}
	}
	
}
