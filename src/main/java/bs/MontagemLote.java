package bs;

import java.util.List;

import org.apache.log4j.Logger;

import exception.BusinessException;

public class MontagemLote {

	private static Logger LOG = Logger.getLogger(MontagemLote.class);
	
	public MontagemLote() {
	}
	
	public String montaLote(List<String> lstEventosAssinados, String grupoLote, String numeroEmissor, String numeroTransmissor) throws BusinessException {
		StringBuilder sb = new StringBuilder();
		
		try {
			String tipoEmissor = numeroEmissor.length() > 11 ? "1" : "2";
			String tipoTransmissor = numeroTransmissor.length() > 11 ? "1" : "2";
			
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
			sb.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
			sb.append(" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"");
			sb.append(" xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\">");
			sb.append(" <soap:Header/>");
			sb.append(" <soap:Body>");
			sb.append(" <eSocial xmlns=\"http://www.esocial.gov.br/schema/lote/eventos/envio/v1_1_1\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
					+ "xsi:schemaLocation=\"http://www.esocial.gov.br/schema/lote/eventos/envio/v1_1_1 EnvioLoteEventos-v1_1_1.xsd \">");
			sb.append(" <envioLoteEventos grupo=\""+grupoLote+"\">");
			sb.append(" <ideEmpregador>");
			sb.append(" <tpInsc>" + tipoEmissor + "</tpInsc>");
			sb.append(" <nrInsc>" + numeroEmissor + "</nrInsc>");
			sb.append(" </ideEmpregador>");
			sb.append(" <ideTransmissor>");
			sb.append(" <tpInsc>" + tipoTransmissor + "</tpInsc>");
			sb.append(" <nrInsc>" + numeroTransmissor + "</nrInsc>");
			sb.append(" </ideTransmissor>");
			sb.append(" <eventos>");
			
			for (int i = 0; i < lstEventosAssinados.size(); i++) {
				String idEvento = lstEventosAssinados.get(i).substring(lstEventosAssinados.get(i).indexOf("Id=\"") + 4, lstEventosAssinados.get(i).indexOf("Id=\"") + 40 );
				System.out.println(":::: "+idEvento + " :::: ");
				sb.append(" <evento Id=\"" + idEvento + "\">");   // este Id � o mesmo do Id do evento que foi assinado e ser� envelopado
				//
				// envelopar o XML assinado
				//
				sb.append(lstEventosAssinados.get(i).replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", ""));
				sb.append(" </evento>");
				//
				// aqui termina o loop que envelopou o(s) evento(s)
				//
			}
			
			sb.append(" </eventos>");
			sb.append(" </envioLoteEventos>");
			sb.append(" </eSocial>");
			sb.append(" </soap:Body>");
			sb.append(" </soap:Envelope>");
			
			//System.out.println("XML SOAP a ser enviado : "+sb.toString());
		}
		catch (Exception e) {
			LOG.error("montaLoteEventos(List<String> lstEventosAssinados, String grupoLote, String numeroEmissor, String numeroTransmissor) ==>> ", e);
			throw new BusinessException(e);	
		}

		return sb.toString();
	}
	
}