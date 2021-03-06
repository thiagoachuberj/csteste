package util;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.text.DecimalFormat;

/**
 * 
 * @author pfugazza e F�bio<br/>
 * 
 * Diversas fun��es especiais sobre String 
 *
 */
public class Aux_String 
{
	private static DecimalFormat decimal = new DecimalFormat( "###,###,##0.00" );  
	/**
	 * Dado um <i>texto</i>, retornar parte deste levando em conta <i>delimitadorInicial</i>
	 * e <i>delimitadorFinal</i>.	
	 * @param texto
	 * @param delimitadorInicial
	 * @param delimitadorFinal
	 * @param entreDelimitadores 
	 * @return se <code>entreDelimitadores = true</code> : retornar� os caracteres de <b>texto</b> entre <b>delimitadorInicial</b> e <b>delimitadorFinal</b>;</br>
	 *         se <code>entreDelimitadores = false</code> : retornar� os caracteres de <b>texto</b> externos aos <b>delimitadorInicial</b> e <b>delimitadorFinal</b>;</br>
	 *         se <code>texto == null</code> : retornar� vazio (<code>""</code>);</br> 
	 *         se <code>texto == ""</code> : retornar� vazio (<code>""</code>). 
	 */
	public static String subStrIntoDelim(String texto, String delimitadorInicial, String delimitadorFinal, boolean entreDelimitadores) {
		StringBuilder textoValido = new StringBuilder();
		boolean encontrouPrimeiroDelimitador = false;
		
		try	{
			for (int i=0; i < texto.length(); i++) {
				if ( encontrouPrimeiroDelimitador )	{
					if ( ! texto.substring(i).startsWith(delimitadorFinal) )
						textoValido.append(texto.subSequence(i,i+1));
					else
						i = texto.length();					
				}
				else {
					if ( texto.substring(i).startsWith(delimitadorInicial) ) {
						encontrouPrimeiroDelimitador = true;
						i+=delimitadorInicial.length()-1;
					}
				}
			}
			
			if ( entreDelimitadores )
				return textoValido.toString();
			else
				return texto.replace(delimitadorInicial+textoValido+delimitadorFinal, "");
		}
		catch (NullPointerException npe) {
			return "";
		}
	}
	
	/**
	 * Dado um <i>texto</i>, retornar parte deste levando em conta <i>delimitadorInicial</i>
	 * e <i>delimitadorFinal</i>, a partir do �ltimo <i>delimitadorInicial</i>. 	
	 * @param texto
	 * @param delimitadorInicial Este par�metro ser� ignorado e substitu�do pelo caracter <i>[</i>
	 * @param delimitadorFinal
	 * @param entreDelimitadores 
	 * @param aPartirDoUltimoDelimitadorInicial 
	 * @return se <code>entreDelimitadores = true</code> : retornar� os caracteres de <b>texto</b> entre <b>delimitadorInicial</b> e <b>delimitadorFinal</b>;<br>
	 *         se <code>entreDelimitadores = false</code> : retornar� os caracteres de <b>texto</b> externos aos <b>delimitadorInicial</b> e <b>delimitadorFinal</b> 
	 */
	public static String subStrIntoDelim(String texto, String delimitadorInicial, String delimitadorFinal, boolean entreDelimitadores, boolean aPartirDoUltimoDelimitadorInicial)
	{
		return inverte(subStrIntoDelim("["+inverte(texto),"[",delimitadorFinal,entreDelimitadores));
	}
	/**
	 * Dado um <i>texto</i>, retornar parte deste levando em conta os delimitadores <i>default</i> <b>[</b> e <b>]</b>. 
	 * @param texto
	 * @return retornar� os caracteres de <b>texto</b> entre os delimitadores 
	 */
	public static String subStrIntoDelim(String texto)
	{
		return subStrIntoDelim(texto,"[","]",true);
	}
	/**
	 * @param string
	 * @param tamanho
	 * @return <code>string.substring(0,tamanho)</code></br>
	 * 
	 * Retornar <b>string</b> com a quantidade <b>tamanho</b> de caracteres. Ser� preenchido
	 * com espa�os a direita ou ser� truncado dependendo do <b>tamanho</b>.
	 */
	public static String StrNesteTamanho(String string, int tamanho)
	{
		return (string+replicate(" ",tamanho)).substring(0, tamanho);
	}
	/**
	 * Dado um <i>numero</i>, converter num <i>String</i> preenchendo com <i>zero(s)</i>
	 * a esquerda levando em conta <i>tamanho</i>
	 * @param numero -> <i>int</i>eiro a ser convertido
	 * @param tamanho -> tamanho do <i>String</i> a ser retornado 
	 * @return
	 */
	public static String StrZeroNesteTamanho(int numero, int tamanho)
	{
		String strNumero = Integer.toString(numero);
		String complemento = replicate("0",tamanho-strNumero.length());
		return (complemento+strNumero);
	}
	/**
	 * @param caracter
	 * @param quantos
	 * @return <code>replicate(caracter,quantos)</code><br>
	 * Retornar� um <i>string</i> de tamanho <b>quantos</b> composto da seq��ncia em <b>
	 * caracter</b>. 
	 */
	public static String replicate(String caracter, int quantos)
	{
		StringBuilder str=new StringBuilder();
		for (int i = 0; i < quantos; i++)
		{
			str.append(caracter);
		}
		return str.toString();
	}
	/**
	 * Similar � fun��o Transf(...) do Clipper.</br>
	 * Transformar um <i>texto</i> num formato definido por uma <i>mascara</i>
	 * @param texto -> texto a ser transformado
	 * @param mascara -> mascara de transforma��o
	 * @return Exemplos: </br>transforma("123456","99999-9") retornar� 12345-6</br>
	 *                   transforma("123456","99.999-9") retornar� 12.345-6</br>
	 *                   transforma("1234","99.999-9") retornar� 00.123-4 com zeros a esquerda</br>
	 *                   transforma("1234","XX.XXX-X") retornar�    123-4 com espa�os a esquerda
	 */
	public static String transforma(String texto, String mascara)
	{
		StringBuilder resultado=new StringBuilder();
		int tamMascara = mascara.length()-1;
		int tamTexto = texto.length()-1;
		while ( tamMascara > -1 )
		{
			if ( "() .-,/".contains(mascara.substring(tamMascara, tamMascara+1)) )
			{
				resultado.append(mascara.substring(tamMascara, tamMascara+1));
				tamMascara--;
			}
			else
			{
				if ( tamTexto > -1 )
				{
					resultado.append(texto.substring(tamTexto, tamTexto+1));
					tamTexto--;
				}
				else
				{
					resultado.append(mascara.substring(tamMascara, tamMascara+1).equals("9") ? "0":" ");
				}	
				tamMascara--;
			}
		}
		return inverte(resultado.toString());
	}
	/**
	 * Transformar um texto no seu inverso. Exemplo: <i>123456</i> ser� transformado em <i>654321</i>
	 * @param texto
	 * @return
	 */
	public static String inverte(String texto)
	{
		StringBuilder resultado=new StringBuilder();
		for (int i = texto.length()-1; i > -1; i--)
			resultado.append(texto.substring(i, i+1));

		return resultado.toString();
	}
	/**
	 * Similar � fun��o StrTran(...) do Clipper
	 * @param parametro -> texto original
	 * @param existe -> caracter a ser pesquisado e substituido caso encontrado no texto original
	 * @param sera -> caracter que substituir� o caracter pesquisado 
	 * @return
	 */
	public static String modificaChar(String parametro, char existe, char sera) 
	{
		StringBuilder resultado = new StringBuilder();
		for ( int i=0 ; i<parametro.length() ; i++)
		{
			if ( existe == parametro.charAt(i) )
				resultado.append(String.valueOf(sera));
			else
				resultado.append(String.valueOf(parametro.charAt(i)));
		}
		return resultado.toString();
	}
	/**
	 * Converter par�metro </i>String</i> recebido com a primeira letra em mai�scula e as demais em min�sculas 
	 * @param texto
	 * @return <i>texto</i> recebido como par�metro, totalmente min�scula com a 1� letra mai�scula
	 */
	public static String proper( String texto)
	{
		return texto.substring(0,1).toUpperCase().concat(texto.substring(1).toLowerCase());
	}
	/**
	 * Retornar de <i>meuTexto</i> somente os caracteres que fazem parte de <i>esteConjunto</i>
	 * @param meuTexto
	 * @param esteConjunto um conjunto de caracteres quaisquer e pode ser tamb�m:<li>
	 * <b>soDigitosNumericos</b> e ent�o ter� os d�gitos 0123456789;<li>
	 * <b>soLetrasMaiusculas</b> e ter� o conjunto ABCDEFGHIJKLMNOPQRSTUWXYZ;<li>
	 * <b>soLetrasMinusculas</b> e ter� o conjunto abcdefghijklmnopqrstuwxyz;<li>
	 * <b>nomeDeArquivo</b> e ter� o conjunto de car�cteres v�lidos para nome de arquivos;
	 * @return
	 */
	public static String soCaracteres(String meuTexto, String esteConjunto)
	{
		StringBuilder retorno = new StringBuilder(); 
		String ec = qualConjunto(esteConjunto);
		for (int i = 0; i < meuTexto.length(); i++)
		{
			if (ec.contains(meuTexto.substring(i,i+1)) )
				retorno.append(meuTexto.substring(i,i+1));
		}
		return retorno.toString();
	}

	public static String qualConjunto(String esteConjunto)
	{
		String retorno;
		if (esteConjunto.equals("soDigitosNumericos"))
			retorno = "0123456789";
		else if (esteConjunto.equals("soLetrasMaiusculas"))
			retorno = "ABCDEFGHIJKLMNOPQRSTUWXYZ";
		else if (esteConjunto.equals("soLetrasMinusculas"))
			retorno = "abcdefghijklmnopqrstuwxyz";
		else if (esteConjunto.equals("nomeDeArquivo"))
			retorno = "0123456789ABCDEFGHIJKLMNOPQRSTUWXYZabcdefghijklmnopqrstuwxyz_.";
		else if (esteConjunto.equals("extensaoDeArquivo"))
			retorno = "0123456789ABCDEFGHIJKLMNOPQRSTUWXYZabcdefghijklmnopqrstuwxyz_";
		else
			retorno = esteConjunto;

		return retorno;
	}
	/**
	 * Transformar <i>String</i> em <i>int</i>, sem que retorne a exce��o, caso ocorra.
	 * @param estesCaracteres
	 * @return <i>estesCaracteres</i> em <i>int</i> ou, se ocorrer <i>NumberFormatException</i>, 0
	 */
	public static int toInt(String estesCaracteres)
	{
		int numero = 0;
		try
		{
			numero = Integer.parseInt(estesCaracteres);
		}
		catch (NumberFormatException nfe)
		{
			numero = 0;
		}
		return numero;
	}
	/**
	 * Compara um texto qualquer (nome de arquivo, por exemplo) com a m�scara fornecida.
	 * @param texto
	 * @param mascara no formato ????A?c??123?MA.???
	 * @return <i>true</i> se nome do arquivo est� de acordo com a m�scara; <i>false</i> caso contr�rio.
	 */
	public static boolean compara(String texto, String mascara) 
	{
		boolean ok = true;
		int indexador = 0;
		while (ok && indexador < mascara.length())
		{
			ok =( mascara.substring(indexador, indexador+1).equals("?") || mascara.substring(indexador, indexador+1).equals(texto.substring(indexador,indexador+1)) );
			indexador++;
		}
		return ok;
	}
	/**
	 * Compara um texto qualquer (nome de arquivo, por exemplo) com um conjunto de m�scara fornecido.
	 * @param texto
	 * @param mascaras no formato ????A?c??123?MA.???
	 * @return <i>true</i> se nome do arquivo est� de acordo com pelo menos uma das m�scaras; <i>false</i> caso contr�rio.
	 */
	public static boolean compara(String texto, String[] mascaras) 
	{
		boolean ok = false;
		int i = 0;
		do 
		{
			ok = compara(texto,mascaras[i]);
			i++;
		} while (! ok && (i < mascaras.length));
		return ok;
	}
	/**
	 * Converter uma cadeia de caracteres codificada em UTF-8 para outra em ISO-8859-1
	 * @param str cadeia de caracteres a ser convertida
	 * @return cadeia de caracteres convertida para ISO-8859-1
	 */
	public static String UTF8toISO(String str)
	{
		Charset utf8charset = Charset.forName("UTF-8");
		Charset iso88591charset = Charset.forName("ISO-8859-1");

		ByteBuffer inputBuffer = ByteBuffer.wrap(str.getBytes());

		// decode UTF-8
		CharBuffer data = utf8charset.decode(inputBuffer);

		// encode ISO-8559-1
		ByteBuffer outputBuffer = iso88591charset.encode(data);
		byte[] outputData = outputBuffer.array();

		return new String(outputData);
	}
	/**
	 * Converter um valor qualquer num formato ###,###,##0.00
	 * @param valor
	 * @return valor formatado
	 */
	public static String formatoDecimal(Double valor) 
	{
		return decimal.format(valor);
	}
	
	public static String textoFormatado(String textoAFormatar, String texto, String delimitadorInicial, String delimitadorFinal){
		String textoFormatadoFinal = "";
		if(textoAFormatar == null) {
			return textoFormatadoFinal;
		}
		if (texto == null) {
			return textoFormatadoFinal;
		}
		
		if(textoAFormatar.contains(delimitadorInicial)) {
			int inicio = textoAFormatar.indexOf(delimitadorInicial);
			int fim = textoAFormatar.indexOf(delimitadorFinal);
			String strI = textoAFormatar.substring(0, inicio+14);
			String strCont = strI.concat(texto);
			textoFormatadoFinal = strCont.concat(textoAFormatar.substring(fim));
		}
		
		return textoFormatadoFinal;
	}

	public static void main(String[] args) {
		String str = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><soapenv:Body></soapenv:Body></soapenv:Envelope>";
		String texto = "<evento Id=\"ID1591047600000002017091910250700001\"> 				        <eSocial xmlns=\"http://www.esocial.gov.br/schema/evt/evtTabRubrica/v02_01_00\" 							xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" 							xsi:schemaLocation=\"http://www.esocial.gov.br/schema/evt/evtTabRubrica/v02_01_00 evtTabRubrica-v02_01_00.xsd \"> 							<evtTabRubrica Id=\"ID1591047600000002017091910250700001\"> 								<ideEvento> 									<tpAmb>7</tpAmb> 									<procEmi>1</procEmi> 									<verProc>V1.0</verProc> 								</ideEvento> 								<ideEmpregador> 									<tpInsc>1</tpInsc> 									<nrInsc>59104760</nrInsc> 								</ideEmpregador> 								<infoRubrica> 									<inclusao> 										<ideRubrica> 											<codRubr>TREZEMAN</codRubr> 											<ideTabRubr>FTH</ideTabRubr> 											<iniValid>2017-07</iniValid> 										</ideRubrica> 										<dadosRubrica> 											<dscRubr>Adiantamento 13º Salário Manual</dscRubr> 											<natRubr>1000</natRubr> 											<tpRubr>3</tpRubr> 											<codIncCP>00</codIncCP> 											<codIncIRRF>00</codIncIRRF> 											<codIncFGTS>00</codIncFGTS> 											<codIncSIND>00</codIncSIND> 											<repDSR>N</repDSR> 											<rep13>N</rep13> 											<repFerias>N</repFerias> 											<repAviso>N</repAviso> 										</dadosRubrica> 									</inclusao> 								</infoRubrica> 							</evtTabRubrica> 							<Signature /> 							<Signature xmlns=\"http://www.w3.org/2000/09/xmldsig#\"> 								<SignedInfo> 									<CanonicalizationMethod 										Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\" /> 									<SignatureMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#rsa-sha1\" /> 									<Reference URI=\"\"> 										<Transforms> 											<Transform Algorithm=\"http://www.w3.org/2000/09/xmldsig#enveloped-signature\" /> 										</Transforms> 										<DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\" /> 										<DigestValue>7YkOWUVh4XJIh15jQgQSVwpZp2U=</DigestValue> 									</Reference> 								</SignedInfo> 								<SignatureValue>haBshj0JGBk0jZo1IO54AK/vNW3nqIRla4gcNr9qG60kGE7Dm3L35KkyVKeoB5VgIG4Nc8a/ocgz 									yHcu0rp0skUthQOPhHcEiorgoeJDPAJ1ozpTqpNlH10s23zuu07sAObL31RBY/eT+mFJYV6Wntnj 									gd+15nu6Mvc05CXKTsMoP8V+h4rwzDJImhgf0W9ECqP6Iplv1xAVvQTCGYHJQKhjenF38nT5MJxk 									KYM1boZOJIOSTJY4SlFpZ+StOhi1avS2xYC8mxwiKtLrVwf50H3SDeEtFovkah9bftEE0r12MxAX 									agVIM/IaX9nx4QrFPlt649pOVoX9wqKuVXHu7A== 								</SignatureValue> 								<KeyInfo> 									<X509Data> 										<X509SubjectName>CN=AMALIA MOREIRA DE MELO:52715329334,OU=AC 											Icptestes Sub PF v2,OU=AC Icptestes Sub v2,O=ICP-Icptestes,C=BR 										</X509SubjectName> 										<X509Certificate>MIIFxzCCBK+gAwIBAgIIIFWTlarq808wDQYJKoZIhvcNAQELBQAwXTELMAkGA1UEBhMCQlIxFjAU 											BgNVBAoMDUlDUC1JY3B0ZXN0ZXMxFTATBgNVBAsMDEFDIEljcHRlc3RlczEfMB0GA1UEAwwWQUMg 											SWNwdGVzdGVzIFN1YiBQRiB2MjAeFw0xNjEyMDYxNjE2MjZaFw0xNzEyMDYxNjE2MjZaMIGRMQsw 											CQYDVQQGEwJCUjEWMBQGA1UECgwNSUNQLUljcHRlc3RlczEcMBoGA1UECwwTQUMgSWNwdGVzdGVz 											IFN1YiB2MjEfMB0GA1UECwwWQUMgSWNwdGVzdGVzIFN1YiBQRiB2MjErMCkGA1UEAwwiQU1BTElB 											IE1PUkVJUkEgREUgTUVMTzo1MjcxNTMyOTMzNDCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoC 											ggEBALhbOo5f6COyljv5Ee9ghoxZ1FqMYHjzIRRsd//6Qi2Wcd3dUuAHc7fvHiUlLbsJvbQCCuD2 											NgCjsUlTLfobPLTPn3ZOfkOWPPWioWhQeLGh4PV62x+RKLdNged9ggKK3q6dFCUGZ4kqpwB68jbR 											Ne0i+n/5KmB27NTHAWfCN7TCy7FRwYpFPf9NAq6uGoK2zVgB6XJH7GRCQ813guK4tNALUhXlyio0 											GlqhmqZW0U+Qe77d/Br/XeP+ZiSYUNdZWu8uPgxbhUK91Y90EgohF+FwObb8BZ6wvxYauLdbXkqh 											ugSXCr2gYfDnMRm2xyugh4IM1TnFlA/xrKsQbVoh/1cCAwEAAaOCAlQwggJQMA4GA1UdDwEB/wQE 											AwIF4DApBgNVHSUEIjAgBggrBgEFBQcDAgYIKwYBBQUHAwQGCisGAQQBgjcUAgIwHQYDVR0OBBYE 											FAmr8Y3nNZVMeHcJPbDVJ8FzMDkNMB8GA1UdIwQYMBaAFA7qqq1u4Rcovy3RYZgtpHmufON4MIGV 											BgNVHREEgY0wgYqBEHh4eEBjYWl4YS5nb3YuYnKgFwYFYEwBAwagDgQMMDAwMDAwMDAwMDAwoB4G 											BWBMAQMFoBUEEzAwMDAwMDAwMDAwMDAwMDAwMDCgPQYFYEwBAwGgNAQyMDcxMTE5NjE1MjcxNTMy 											OTMzNDEyNTgwOTQzNDgxMDAwMDAwMDUyNTkyNTY2U1NQUkowcAYDVR0gBGkwZzBlBgZgTAECAQgw 											WzBZBggrBgEFBQcCARZNaHR0cDovL3d3dy5yZWljcC5jb3JlZGYuY2FpeGEvcmVwb3NpdG9yaW8v 											Y2FkZWlhdjIvZG9jdW1lbnRvcy9kcGNhYy1zdWJwZi5wZGYwXgYDVR0fBFcwVTBToFGgT4ZNaHR0 											cDovL3d3dy5yZWljcC5jb3JlZGYuY2FpeGEvcmVwb3NpdG9yaW8vY2FkZWlhdjIvbGNyL2FjaWNw 											dGVzdGVzc3VicGZ2Mi5jcmwwaQYIKwYBBQUHAQEEXTBbMFkGCCsGAQUFBzAChk1odHRwOi8vd3d3 											LnJlaWNwLmNvcmVkZi5jYWl4YS9yZXBvc2l0b3Jpby9jYWRlaWF2Mi9haWEvYWNpY3B0ZXN0ZXNz 											dWJwZnYyLnA3YjANBgkqhkiG9w0BAQsFAAOCAQEAQVXTXzgN/hmL5IZ/3WgNU6DivLn10HQ82CaB 											GbKitoeZIFi/sVMyr1BQZ3KLQpgdBIj6moPFgi8JCE0C7zSjscmvc0hj4eoF10ZvUPQL6ReCNJvK 											eXR52jEmQTZlfItsc/xlZIVzsDISzwX4hR+Wos+37kNfgGXibic0PXgVUqv8igIUYlZZOGUpwTvr 											PegzrFpWjy2y5x9ROBuvwvof3KMGNA+au639FjMFp72BcmZDrF5EMAjDDWfd+PTEWQsw5inIO1Vy 											ia2PA/xM9rOKIurok8Dqvi9DfEJmXWs671PR3ptsc9AbYJVZZdYEyHT7X2PtV0GGc8452RhQ5bOb 											ZQ== 										</X509Certificate> 									</X509Data> 								</KeyInfo> 							</Signature> 						</eSocial> 				      </evento>";
		Aux_String.textoFormatado(str, texto, "<soapenv:Body>", "</soapenv:Body>");
	}
}
