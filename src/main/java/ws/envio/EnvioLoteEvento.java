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
import eventos.s1010.ESocial.EvtTabRubrica;
import eventos.s1010.ESocial.EvtTabRubrica.InfoRubrica;
import eventos.s1010.TDadosRubrica;
import eventos.s1010.TIdeRubrica;
import vo.BaseVO;
import vo.EntradaEnvioVO;

/**
 * Num único processo, esta classe monta a mensagem <b><i>SOAP</i></b>, l� os <b>XML�s</b>, assina cada um deles, envelopa na mensagem, envia para o e-Social, 
 * recebe o recibo, monta mensagem SOAP para consultar o resultado do processamento e, finalmente, recebo o resultado do processamento do lote.
 *
 * @author Usuario
 * 
 * Modificações e/ou adaptações feitas por pfugazza
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
		List<BaseVO> lstEventos = null;
		
		//monta lista de evento EvtInfoEmpregador
		//lstEventos = montaDadosEventoCadastroEvtInfoEmpregador("1", "09063372752", 1);
		
		//monta lista de evento EvtTabRubrica
		lstEventos =  montaDadosEvtTabRubrica("1", "10368717000144", 5);

		EnvioEventosServices service = new EnvioEventosServices();
		EntradaEnvioVO vo = new EntradaEnvioVO();
		vo.setGrupoLote("1");
		vo.setLstEventosVO(lstEventos);
		vo.setNumeroEmissor(numeroEmissor);
		vo.setNumeroTransmissor(numeroEmissor);
		System.out.println(service.enviarLoteEventos(vo));
	}

	private static List<BaseVO> montaDadosEvtTabRubrica(String tpEmpregador, String nrEmpregador, int qtdEventos) {
		List<BaseVO> lstEventos = new ArrayList<>();
		for(int i = 1; i <= qtdEventos; i++) {
			eventos.s1010.ESocial esocial = new eventos.s1010.ESocial();

			//montando ID:
			StringBuilder sb = montaIDEvento(tpEmpregador, nrEmpregador, i);

			EvtTabRubrica evtTabRubrica = new EvtTabRubrica();
			evtTabRubrica.setId(sb.toString());
			eventos.s1010.TEmpregador empregador = new eventos.s1010.TEmpregador();
			empregador.setTpInsc(new Byte(tpEmpregador));
			empregador.setNrInsc(nrEmpregador);
			evtTabRubrica.setIdeEmpregador(empregador);
			
			eventos.s1010.TIdeCadastro cadastro = new eventos.s1010.TIdeCadastro();
			cadastro.setProcEmi(new Byte("0"));
			cadastro.setTpAmb(new Byte("2"));
			cadastro.setVerProc("0");
			evtTabRubrica.setIdeEvento(cadastro);
			
			InfoRubrica info = new InfoRubrica();
			eventos.s1010.ESocial.EvtTabRubrica.InfoRubrica.Inclusao inclusao = new eventos.s1010.ESocial.EvtTabRubrica.InfoRubrica.Inclusao();
			
			TIdeRubrica idRubrica = new TIdeRubrica();
			idRubrica.setCodRubr("000000000");
			idRubrica.setIdeTabRubr("11111111");
			idRubrica.setIniValid("2018-05");
			inclusao.setIdeRubrica(idRubrica);
			
			TDadosRubrica dadosRubrica = new TDadosRubrica();
			dadosRubrica.setTpRubr(new Byte("2"));
			dadosRubrica.setDscRubr("teste");
			dadosRubrica.setNatRubr(new BigInteger("1111"));
			dadosRubrica.setCodIncCP("11");
			dadosRubrica.setCodIncIRRF("11");
			dadosRubrica.setCodIncFGTS("11");
			dadosRubrica.setCodIncSIND("31");
			dadosRubrica.setObservacao("Teste");
			inclusao.setDadosRubrica(dadosRubrica);
			
			info.setInclusao(inclusao);
			
			evtTabRubrica.setInfoRubrica(info);
			esocial.setEvtTabRubrica(evtTabRubrica);
			lstEventos.add(esocial);
		}
		
		return lstEventos;
	}

	private static StringBuilder montaIDEvento(String tpEmpregador, String nrEmpregador, int i) {
		StringBuilder sb = new StringBuilder("ID");
		sb.append(tpEmpregador).append(nrEmpregador.length() == 11 ? nrEmpregador+"000" : nrEmpregador);
		SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMddhhmmss");
		sb.append(sdf.format(new Date())).append(String.valueOf(i).length() == 2 ? "000"+i : "0000"+i);
		return sb;
	}
	
	public static List<BaseVO> montaDadosEventoCadastroEvtInfoEmpregador(String tpEmpregador, String nrEmpregador, int qtdEventos) {
		List<BaseVO> lstEventos = new ArrayList<>();
		for(int i = 0; i < qtdEventos; i++) {
			ESocial esocial = new ESocial();
		
			EvtInfoEmpregador evtInfoEmpregador = new EvtInfoEmpregador();
			
			//IDLEGAL
			//evtInfoEmpregador.setId("ID1103687170001442018022708230500001");//"ID1103687170001442018022708230500001");//ID2168256594720002015102015484600001");
			//IDMARCUS
			//evtInfoEmpregador.setId("ID1103687170001442018041713182000000");
			
			//montando ID:
			StringBuilder sb = montaIDEvento(tpEmpregador, nrEmpregador, i);
			evtInfoEmpregador.setId(sb.toString());
			
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
			lstEventos.add(esocial);
		}
		
		return lstEventos;
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