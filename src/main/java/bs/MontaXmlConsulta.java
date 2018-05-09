package bs;

public class MontaXmlConsulta {

	public String montarXml(String protocolo) {
		StringBuffer sb = new StringBuffer();
		
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

}