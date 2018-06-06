package bs;

import java.util.List;

import org.apache.log4j.Logger;

import exception.BusinessException;

public class MontagemLote {

	private static Logger LOGGER = Logger.getLogger(MontagemLote.class);
	
	public static String montaLote(List<String> lstEventosAssinados, String grupoLote, String numeroEmissor, String numeroTransmissor) throws BusinessException {
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
				System.out.println(lstEventosAssinados.get(i).substring(lstEventosAssinados.get(i).indexOf("Id=\"") + 4, lstEventosAssinados.get(i).indexOf("Id=\"") + 40 ));
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
			LOGGER.error("Erro no metodo montaLote(): ", e);
			throw new BusinessException("Erro no metodo montaLote(): ", e);
		}

		return sb.toString();
	}
	
	/*public String montaLote(List<String> lstEventosAssinados, String grupoLote, String numeroEmissor, String numeroTransmissor) throws BusinessException {
		try {
			ESocial loteEnvio = new ESocial();
			EnvioLoteEventos envioLoteEventos = new EnvioLoteEventos();
			envioLoteEventos.setGrupo(Integer.parseInt(grupoLote));
			TIdeEmpregador idEmpregador = new TIdeEmpregador();
			idEmpregador.setTpInsc(numeroEmissor.length() > 11 ? new Byte("1") : new Byte("2"));
			idEmpregador.setNrInsc(numeroEmissor);
			envioLoteEventos.setIdeEmpregador(idEmpregador);
			TIdeTransmissor ideTransmissor = new TIdeTransmissor();
			ideTransmissor.setTpInsc(numeroTransmissor.length() > 11 ? new Byte("1") : new Byte("2"));
			ideTransmissor.setNrInsc(numeroTransmissor);
			envioLoteEventos.setIdeTransmissor(ideTransmissor);
			
			Eventos eventos = new Eventos();
			for(String str : lstEventosAssinados) {
				String idEvento = str.substring(str.indexOf("Id=\"") + 4, str.indexOf("Id=\"") + 40 );
				TArquivoEsocial evento = new TArquivoEsocial();
				evento.setId(idEvento);
				evento.setAny(Util.convertStringToElement(str));
				
				eventos.getEvento().add(evento);
			}
			
			envioLoteEventos.setEventos(eventos);
			loteEnvio.setEnvioLoteEventos(envioLoteEventos);
			
			String templateSoap = Util.createTemplateSoapMessage();
			StringBuilder sb = new StringBuilder();
			StringWriter swriter = Util.convertObjectInXML(loteEnvio);
			
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
			
			return Util.formataNameSpaceEvento(sb.toString());
		}
		catch (SOAPException | IOException e) {
			LOGGER.error("Erro no metodo Util.createTemplateSoapMessage(): ", e);
			throw new BusinessException("Erro no metodo Util.createTemplateSoapMessage(): ", e);
		} 
		catch (JAXBException e) {
			LOGGER.error("Erro no metodo Util.convertObjectInXML(esocial): ", e);
			throw new BusinessException("Erro no metodo Util.createTemplateSoapMessage(): ", e);
		}
	}*/
	
}