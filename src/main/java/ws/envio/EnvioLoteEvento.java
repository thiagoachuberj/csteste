/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.envio;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import bs.EnvioEventosServices;
import eventos.s1000.ESocial;
import eventos.s1000.ESocial.EvtInfoEmpregador;
import eventos.s1000.ESocial.EvtInfoEmpregador.InfoEmpregador;
import eventos.s1000.ESocial.EvtInfoEmpregador.InfoEmpregador.Inclusao;
import eventos.s1000.TEmpregador;
import eventos.s1000.TIdeCadastro;
import eventos.s1000.TIdePeriodo;
import eventos.s1000.TInfoEmpregador;
import eventos.s1000.TInfoEmpregador.Contato;
import eventos.s1000.TInfoEmpregador.DadosIsencao;
import eventos.s1000.TInfoEmpregador.InfoComplementares;
import eventos.s1000.TInfoEmpregador.InfoComplementares.SituacaoPF;
import eventos.s1000.TInfoEmpregador.InfoComplementares.SituacaoPJ;
import eventos.s1000.TInfoEmpregador.InfoOP;
import eventos.s1000.TInfoEmpregador.InfoOP.InfoEFR;
import eventos.s1000.TInfoEmpregador.InfoOP.InfoEnte;
import eventos.s1000.TInfoEmpregador.InfoOrgInternacional;
import vo.BaseVO;
import vo.EntradaEnvioVO;

/**
 * Num �nico processo, esta classe monta a mensagem <b><i>SOAP</i></b>, l� os <b>XML�s</b>, assina cada um deles, envelopa na mensagem, envia para o e-Social, 
 * recebe o recibo, monta mensagem SOAP para consultar o resultado do processamento e, finalmente, recebo o resultado do processamento do lote.
 *
 * @author Usuario
 * 
 * Modifica��es e/ou adapta��es feitas por pfugazza
 */
public class EnvioLoteEvento 
{
	private static final int SSL_PORT = 443;
	private static String urlEnviarLoteEventos = "https://webservices.producaorestrita.esocial.gov.br/servicos/empregador/enviarloteeventos/WsEnviarLoteEventos.svc";
	private static String urlConsultarLoteEventos = "https://webservices.producaorestrita.esocial.gov.br/servicos/empregador/consultarloteeventos/WsConsultarLoteEventos.svc";
	private static String schemaLoteEventosEnvio = "\"http://www.esocial.gov.br/schema/lote/eventos/envio/v1_1_1\"";
	private static String schemaLoteEventosEnvioConsultaRetornoProcessamento = "\"http://www.esocial.gov.br/schema/lote/eventos/envio/consulta/retornoProcessamento/v1_0_0\"";
	private static String protocoloEsocial = "";
	private static Properties properties;
	private static String numeroEmissor = "10368717000144";
	
	/**
	 * @param args the command line arguments
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception 
	{
		//properties = ArquivosProperties.obterProperties("C:/Users/Thiago Silva/Documents/Projetos/git/eSocial/src/eSocialScript.properties");
		//moonta os dados do evento Cadastro
		ESocial esocial = montaDadosEventoCadastro();
		
		//monta lista de evento
		List<BaseVO> lstEventos = new ArrayList<BaseVO>();
		lstEventos.add(esocial);

		EnvioEventosServices service = new EnvioEventosServices();
		EntradaEnvioVO vo = new EntradaEnvioVO();
		vo.setGrupoLote("1");
		vo.setLstEventosVO(lstEventos);
		vo.setNumeroEmissor(numeroEmissor);
		vo.setNumeroTransmissor(numeroEmissor);
		System.out.println(service.enviarLoteEventos(vo));
		
		/*List<String> lstXml = new ArrayList<String>();
		for(BaseVO vo : lstEventos) {
			//converter o evento em xml
			StringWriter stringWriterXml = Util.convertObjectInXML(vo);
			
			//System.out.println(stringWriterXml.toString());
			
			String st = stringWriterXml.toString().replaceAll("\"http://www.w3.org/2000/09/xmldsig#\"", "");
			st = st.replaceAll("\"http://www.esocial.gov.br/schema/evt/evtInfoEmpregador/v02_04_01\"", "");
			st = st.replaceAll("xmlns:ns3=", "").replaceAll("xmlns:ns2=", "");
			st = st.replaceAll("ns2:", "").replaceAll("ns3:", "");
			
			st = st.replace("<eSocial  >", "<eSocial xmlns=\"http://www.esocial.gov.br/schema/evt/evtInfoEmpregador/v02_04_01\">");
			st = st.replaceAll("ns2:", "").replaceAll("ns3:", "");
			
			//System.out.println(":::: st "+st+" :::: ");
			
			//adicionar o xml assinado na lista de retorno
			lstXml.add(st);
		}

		List<String> lstXmlAssinado = new ArrayList<String>();
		for(String str : lstXml) {
			//assinar o xml
			String[] strCertAlias = null;
			strCertAlias = OnCert.funcListaCertificados(false);
			
			OnCert.TAssinaXML dadosAssinatura = new OnCert.TAssinaXML();
			dadosAssinatura.strArquivoXML = str.toString();
			dadosAssinatura.strAliasTokenCert = strCertAlias[7];
			
			TAssinaXML eventoXMLAssinado = OnCert.funcAssinaEventoXML(dadosAssinatura);
			
			//System.out.println(":::: eventoXMLAssinado.xmlAssinado "+ eventoXMLAssinado.xmlAssinado);
			
			Validador validar = new Validador();
			validar.valida(new ByteArrayInputStream(eventoXMLAssinado.xmlAssinado.getBytes(StandardCharsets.UTF_8)), new File("C:\\Users\\Thiago Silva\\Documents\\Projetos\\git\\csesocial\\src\\main\\java\\br\\com\\csesocial\\evento\\v2401\\S1000\\evtInfoEmpregador.xsd"));
			
			lstXmlAssinado.add(eventoXMLAssinado.xmlAssinado);
		}
		
		String loteXml = montaLoteParaEnvio(lstXmlAssinado);
		
		System.out.println(":::: loteXml "+ loteXml +" ::::");
		
		try 
		{
			URL urlELE = new URL(urlEnviarLoteEventos);
			String[] strCertAlias = null;
			strCertAlias = OnCert.funcListaCertificados(false);
			OnCert.TAssinaXML tpAssinaXML = new OnCert.TAssinaXML();
			tpAssinaXML.strAliasTokenCert = strCertAlias[7];

			KeyStore ks = OnCert.funcKeyStore(strCertAlias[0]);
			String senhaDoCertificado = "coma01";//properties.getProperty("Senha");
			String arquivoCacerts = "C:/Users/Thiago Silva/Documents/Projetos/git/csesocial/certificados/cacerts"; //eSocial/certificados/eSocialCacerts";//properties.getProperty("ArquivoDosCertificadosEsocial");

			String alias = "";
			Enumeration<String> aliasesEnum = ks.aliases();
			while (aliasesEnum.hasMoreElements()) 
			{
				alias = (String) aliasesEnum.nextElement();
				if (ks.isKeyEntry(alias)) 
				{
					break;
				}
			}
			X509Certificate certificate = (X509Certificate) ks.getCertificate(alias);
			PrivateKey privateKey = (PrivateKey) ks.getKey(alias, senhaDoCertificado.toCharArray());
			SocketFactoryDinamico socketFactoryDinamico = new SocketFactoryDinamico(certificate, privateKey);
			socketFactoryDinamico.setFileCacerts(arquivoCacerts);

			Protocol protocol = new Protocol("https", socketFactoryDinamico, SSL_PORT);
			Protocol.registerProtocol("https", protocol);
	
			OMElement ome = AXIOMUtil.stringToOM(loteXml);
			ServicoEnviarLoteEventosStub.LoteEventos_type0 dadosMsgType0 = new ServicoEnviarLoteEventosStub.LoteEventos_type0();
			dadosMsgType0.setExtraElement(ome);

			ServicoEnviarLoteEventosStub.EnviarLoteEventos distEnvioEsocial = new ServicoEnviarLoteEventosStub.EnviarLoteEventos();
			distEnvioEsocial.setLoteEventos(dadosMsgType0);

			ServicoEnviarLoteEventosStub stub = new ServicoEnviarLoteEventosStub(urlELE.toString());
			ServicoEnviarLoteEventosStub.EnviarLoteEventosResponse result = stub.enviarLoteEventos(distEnvioEsocial);  // neste momento � solicitada a senha do token
			result.getEnviarLoteEventosResult().getExtraElement().toString();

			System.out.println(result.getEnviarLoteEventosResult().getExtraElement().toString());
			protocoloEsocial = Aux_String.subStrIntoDelim(result.getEnviarLoteEventosResult().getExtraElement().toString(), "<protocoloEnvio>", "</protocoloEnvio>", true);
			System.out.println(protocoloEsocial);
		} 
		catch (Exception ex) {
			ex.printStackTrace();
//			Logger.getLogger(EnvioXml.class.getName()).log(Level.SEVERE, null, ex);
		}*/
	}
	
	public static ESocial montaDadosEventoCadastro() throws DatatypeConfigurationException {
		ESocial esocial = new ESocial();
		EvtInfoEmpregador evtInfoEmpregador = new EvtInfoEmpregador();
		
		evtInfoEmpregador.setId("ID1103687170001442018022708230500001");//ID2168256594720002015102015484600001");
		
		TEmpregador empregador = new TEmpregador();
		empregador.setNrInsc("123456789");
		empregador.setTpInsc(new Byte("1"));
		evtInfoEmpregador.setIdeEmpregador(empregador);
		
		TIdeCadastro value = new TIdeCadastro();
		value.setProcEmi(new Byte("0"));
		value.setTpAmb(new Byte("2"));
		value.setVerProc("0");
		evtInfoEmpregador.setIdeEvento(value);
		
		InfoEmpregador empregador2 = new InfoEmpregador();
		Inclusao inclusao = new Inclusao();
		TIdePeriodo idePeriodo = new TIdePeriodo();
		idePeriodo.setIniValid("2017-01");
		idePeriodo.setFimValid("2017-02");
		inclusao.setIdePeriodo(idePeriodo);
		
		// preencher os dados obrigatorios
		TInfoEmpregador tinfoEmpregador = new TInfoEmpregador();
		tinfoEmpregador.setClassTrib("10");
		tinfoEmpregador.setNmRazao("Teste 123");
		tinfoEmpregador.setIndConstr(new Byte("0"));
		tinfoEmpregador.setIndCoop(new Byte("0"));
		tinfoEmpregador.setIndDesFolha(new Byte("0"));
		tinfoEmpregador.setIndEntEd("N");
		tinfoEmpregador.setIndEtt("N");
		tinfoEmpregador.setIndOptRegEletron(new Byte("0"));
		tinfoEmpregador.setNatJurid("0000");
		tinfoEmpregador.setNrRegEtt("12345678910");
		
		Contato contato = new Contato();
		contato.setCpfCtt("09063372752");
		contato.setEmail("thiago@thiago.com");
		contato.setFoneCel("21999999999");
		contato.setNmCtt("testeContato");
		contato.setFoneFixo("2122222222");
		tinfoEmpregador.setContato(contato);
		
		DadosIsencao dadosIsencao = new DadosIsencao();
		GregorianCalendar gc = new GregorianCalendar();
		//gc.setTime(new Date());
		
		dadosIsencao.setDtDou(toXMLGregorianCalendar(gc.getTime()));
		dadosIsencao.setDtEmisCertif(toXMLGregorianCalendar(gc.getTime()));
		dadosIsencao.setDtProtRenov(toXMLGregorianCalendar(gc.getTime()));
		dadosIsencao.setDtVencCertif(toXMLGregorianCalendar(gc.getTime()));
		dadosIsencao.setIdeMinLei("ok");
		dadosIsencao.setNrCertif("numero");
		dadosIsencao.setNrProtRenov("numero");
		dadosIsencao.setPagDou(new BigInteger("10"));
		tinfoEmpregador.setDadosIsencao(dadosIsencao);
		
		InfoComplementares ip = new InfoComplementares();
		SituacaoPF sitPF = new SituacaoPF();
		sitPF.setIndSitPF(new Byte("0"));
		ip.setSituacaoPF(sitPF);
		SituacaoPJ pj = new SituacaoPJ();
		pj.setIndSitPJ(new Byte("0"));
		ip.setSituacaoPJ(pj);
		tinfoEmpregador.setInfoComplementares(ip);
		
		InfoOP info = new InfoOP();
		InfoEFR efr = new InfoEFR();
		efr.setCnpjEFR("12345678912345");
		efr.setIdeEFR("S");
		info.setInfoEFR(efr);
		InfoEnte ente = new InfoEnte();
		ente.setCodMunic(new BigInteger("10"));
		ente.setIndRPPS("S");
		ente.setNmEnte("ENTE");
		ente.setUf("RJ");
		ente.setVrSubteto(new BigDecimal("10"));
		ente.setSubteto(new Byte("0"));
		info.setInfoEnte(ente);
		info.setNrSiafi("789");
		info.setInfoEnte(ente);
		tinfoEmpregador.setInfoOP(info);
		
		InfoOrgInternacional infoOrg = new InfoOrgInternacional();
		infoOrg.setIndAcordoIsenMulta(new Byte("0"));
		tinfoEmpregador.setInfoOrgInternacional(infoOrg);
		
		
		inclusao.setInfoCadastro(tinfoEmpregador);
		empregador2.setInclusao(inclusao);
		evtInfoEmpregador.setInfoEmpregador(empregador2);
		
		esocial.setEvtInfoEmpregador(evtInfoEmpregador);
		return esocial;
	}
	
	private static String montaLoteParaEnvio(List<String> lstXmlAssinado) {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		sb.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
		sb.append(" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"");
		sb.append(" xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\">");
		sb.append(" <soap:Header/>");
		sb.append(" <soap:Body>");
		sb.append(" <eSocial xmlns=\"http://www.esocial.gov.br/schema/lote/eventos/envio/v1_1_1\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
					+ "xsi:schemaLocation=\"http://www.esocial.gov.br/schema/lote/eventos/envio/v1_1_1 EnvioLoteEventos-v1_1_1.xsd \">");
		sb.append(" <envioLoteEventos grupo=\"1\">");
		sb.append(" <ideEmpregador>");
		sb.append(" <tpInsc>1</tpInsc>");
		sb.append(" <nrInsc>"+numeroEmissor+"</nrInsc>");
		sb.append(" </ideEmpregador>");
		sb.append(" <ideTransmissor>");
		sb.append(" <tpInsc>1</tpInsc>");
		sb.append(" <nrInsc>"+numeroEmissor+"</nrInsc>");
		sb.append(" </ideTransmissor>");
		sb.append(" <eventos>");
		
		for (int i = 0; i < lstXmlAssinado.size(); i++) {
			String idEvento = lstXmlAssinado.get(i).substring(lstXmlAssinado.get(i).indexOf("Id=\"") + 4, lstXmlAssinado.get(i).indexOf("Id=\"") + 40 );
			System.out.println(":::: "+idEvento + " :::: ");
			sb.append(" <evento Id=\"ID1103687170001442018022708230500001\">");   // este Id � o mesmo do Id do evento que foi assinado e ser� envelopado
			//
			// envelopar o XML assinado
			//
			sb.append(lstXmlAssinado.get(i).replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", ""));
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

		return sb.toString(); 
	}

	public static XMLGregorianCalendar toXMLGregorianCalendar(Date date) { 
        GregorianCalendar gCalendar = new GregorianCalendar();
        gCalendar.setTime(date);
        XMLGregorianCalendar xmlCalendar = null;
        try {
            //xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gCalendar);
            Date dob=null;
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            String str = df.format( date );
            dob = df.parse(str);
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(dob);
            xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendarDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH), DatatypeConstants.FIELD_UNDEFINED);

        } catch (DatatypeConfigurationException ex) {
        	ex.printStackTrace();
        } catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return xmlCalendar;
    }

}