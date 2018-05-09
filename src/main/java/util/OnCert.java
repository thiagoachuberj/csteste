/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.demoiselle.signer.core.keystore.loader.factory.KeyStoreLoaderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import exception.BusinessException;
import message.SystemPropertiesMessage;

/**
 * Classe copiada em 13/06/2017 do seguinte endere�o: 
 * http://www.guj.com.br/t/resolvido-como-selecionar-o-certificado-digital-token-e-assinar-arquivos-no-formato-pkcs7/182257/4
 * 
 * <p>Fiz algumas adapta��es de acordo com nossas necessidades.</p>
 * 
 * @author onvaid@hotmail.com</br>
 * Soulslinux Onvaid Marques
 */
public class OnCert {
	//Procedimento que retorna o Keystore
	public static KeyStore funcKeyStore(String strAliasTokenCert) throws NoSuchProviderException, IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableEntryException 
	{
		String strResult = "";
		KeyStore ks = null;
		try 
		{
			ks = KeyStore.getInstance("Windows-MY", "SunMSCAPI");
			ks.load(null, null);
			Enumeration<String> aliasEnum = ks.aliases();
			while (aliasEnum.hasMoreElements()) 
			{
				String aliasKey = (String) aliasEnum.nextElement();
				if (ks.isKeyEntry(aliasKey)) 
				{
					strResult = aliasKey;
				}
				if (ks.getCertificateAlias(ks.getCertificate(strResult)) == strAliasTokenCert) 
				{
					break;
				}
			}
		} 
		catch (KeyStoreException ex) 
		{
			System.out.println("ERROR " + ex.getMessage());
		}
		return ks;
	}
	
	//Procedimento que retorna o Keystore
	public static KeyStore funcKeyStoreForLinux(String strAliasTokenCert) throws NoSuchProviderException, IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableEntryException 
	{
		String strResult = "";
		KeyStore ks = null;
		try 
		{
			ks = KeyStore.getInstance("Windows-MY", "SunMSCAPI");
			ks.load(null, null);
			Enumeration<String> aliasEnum = ks.aliases();
			while (aliasEnum.hasMoreElements()) 
			{
				String aliasKey = (String) aliasEnum.nextElement();
				if (ks.isKeyEntry(aliasKey)) 
				{
					strResult = aliasKey;
				}
				if (ks.getCertificateAlias(ks.getCertificate(strResult)) == strAliasTokenCert) 
				{
					break;
				}
			}
		} 
		catch (KeyStoreException ex) 
		{
			System.out.println("ERROR " + ex.getMessage());
		}
		return ks;
	}
	
	//Procedimento de listagem dos certificados digitais
	public static String[] funcListaCertificados(boolean booCertValido) throws NoSuchProviderException, IOException, NoSuchAlgorithmException, CertificateException 
	{
		//Estou setando a variavel para 20 dispositivos no maximo
		String strResult[] = new String[20];
		Integer intCnt = 0;
		try 
		{
            KeyStore ks = KeyStore.getInstance("Windows-MY", "SunMSCAPI");
			ks.load(null, null);
			Enumeration<String> aliasEnum = ks.aliases();
			while (aliasEnum.hasMoreElements()) 
			{
				String aliasKey = (String) aliasEnum.nextElement();
				if (booCertValido == false) 
				{
					strResult[intCnt] = aliasKey;
				} 
				else if (ks.isKeyEntry(aliasKey)) 
				{
					strResult[intCnt] = aliasKey;
				}
				if (strResult[intCnt] != null) 
				{
					intCnt = intCnt + 1;
				}
			}
		} 
		catch (KeyStoreException ex) 
		{
			System.out.println("ERROR " + ex.getMessage());
		}
		return strResult;
	}
	//Procedimento que retorna a chave privada de um certificado Digital
	public static PrivateKey funcChavePrivada(String strAliasTokenCert, String strAliasCertificado, String strArquivoCertificado, String strSenhaCertificado) throws Exception 
	{
		KeyStore ks = null;
		PrivateKey privateKey = null;
		if (strAliasTokenCert == null || strAliasTokenCert == "") 
		{
			ks = KeyStore.getInstance("PKCS12");
			FileInputStream fis = new FileInputStream(strArquivoCertificado);
			//Efetua o load do keystore
			ks.load(fis, strSenhaCertificado.toCharArray());
			//captura a chave privada para a assinatura
			privateKey = (PrivateKey) ks.getKey(strAliasCertificado, strSenhaCertificado.toCharArray());
		} 
		else 
		{
			if (strSenhaCertificado == null || strSenhaCertificado == "") 
			{
				strSenhaCertificado = "Senha";
			}
			//Procedimento para a captura da chave privada do token/cert
			privateKey = (PrivateKey) funcKeyStore(strAliasTokenCert).getKey(strAliasTokenCert, strSenhaCertificado.toCharArray());
		}
		return privateKey;
	}
	//Procedimento que retorna a chave publica de um certificado Digital
	public static PublicKey funcChavePublica(String strAliasTokenCert, String strAliasCertificado, String strArquivoCertificado, String strSenhaCertificado) throws Exception 
	{
		KeyStore ks = null;
		PublicKey chavePublica = null;
		if (strAliasTokenCert == null || strAliasTokenCert == "") 
		{
			ks = KeyStore.getInstance("PKCS12");
			FileInputStream fis = new FileInputStream(strArquivoCertificado);
			//InputStream entrada para o arquivo
			ks.load(fis, strSenhaCertificado.toCharArray());
			fis.close();
//			Key chave = (Key) ks.getKey(strAliasCertificado, strSenhaCertificado.toCharArray());
			//O tipo de dado � declarado desse modo por haver ambig�idade (Classes assinadas com o mesmo nome "Certificate")
			java.security.Certificate cert = (java.security.Certificate) ks.getCertificate(strAliasCertificado);
			chavePublica = cert.getPublicKey();
		} 
		else 
		{
			if (strSenhaCertificado == null || strSenhaCertificado == "") 
			{
				strSenhaCertificado = "Senha";
			}
			//Procedimento se for utilizar token para a captura de chave publica
			ks = funcKeyStore(strAliasTokenCert);
//			Key key = ks.getKey(strAliasTokenCert, strSenhaCertificado.toCharArray());
			java.security.cert.Certificate crtCert = ks.getCertificate(strAliasTokenCert);
			chavePublica = crtCert.getPublicKey();
		}
		return chavePublica;
	}
	//Procedimento que verifica a assinatura
	public static boolean funcAssinaturaValida(PublicKey pbKey, byte[] bteBuffer, byte[] bteAssinado, String strAlgorithmAssinatura) throws Exception 
	{
		if (strAlgorithmAssinatura == null) 
		{
			strAlgorithmAssinatura = "MD5withRSA";
		}
		Signature isdAssinatura = Signature.getInstance(strAlgorithmAssinatura);
		isdAssinatura.initVerify(pbKey);
		isdAssinatura.update(bteBuffer, 0, bteBuffer.length);
		return isdAssinatura.verify(bteAssinado);
	}
	//Procedimento que gera a assinatura
	public static byte[] funcGeraAssinatura(PrivateKey pbKey, byte[] bteBuffer, String strAlgorithmAssinatura) throws Exception 
	{
		if (strAlgorithmAssinatura == null) 
		{
			strAlgorithmAssinatura = "MD5withRSA";
		}
		Signature isdAssinatura = Signature.getInstance(strAlgorithmAssinatura);
		isdAssinatura.initSign(pbKey);
		isdAssinatura.update(bteBuffer, 0, bteBuffer.length);
		return isdAssinatura.sign();
	}
	//Procedimento que retorna o status do certificado
	public static String funcStatusCertificado(X509Certificate crtCertificado) 
	{
		try 
		{
			crtCertificado.checkValidity();
			return "Certificado v�lido!";
		} 
		catch (CertificateExpiredException E) 
		{
			return "Certificado expirado!";
		} 
		catch (CertificateNotYetValidException E) 
		{
			return "Certificado inv�lido!";
		}
	}
	//Procedimento que retorna o certificado selecionado
	public static X509Certificate funcCertificadoSelecionado(String strAliasTokenCert, String strAliasCertificado, String strArquivoCertificado, String strSenhaCertificado) throws NoSuchProviderException, IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableEntryException, KeyStoreException 
	{
		X509Certificate crtCertificado = null;
		KeyStore crtRepositorio = null;
	
		if (strAliasTokenCert == null || strAliasTokenCert == "") 
		{
			//Procedimento de captura do certificao arquivo passado como parametro
			InputStream dado = new ByteArrayInputStream(strArquivoCertificado.getBytes(StandardCharsets.UTF_8));//new FileInputStream(strArquivoCertificado);
			crtRepositorio = KeyStore.getInstance("PKCS12");
			crtRepositorio.load(dado, strSenhaCertificado.toCharArray());
			crtCertificado = (X509Certificate) crtRepositorio.getCertificate(strAliasCertificado);
		} 
		else 
		{
			if (strSenhaCertificado == null || strSenhaCertificado == "") 
			{
				//strSenhaCertificado = "Senha";
				strSenhaCertificado = "coma01";
			}
			//Procedimento de captura do certificao token passado como parametro
			KeyStore.PrivateKeyEntry keyEntry;
			try 
			{
				keyEntry = (KeyStore.PrivateKeyEntry) funcKeyStore(strAliasTokenCert).getEntry(strAliasTokenCert, new KeyStore.PasswordProtection(strSenhaCertificado.toCharArray()));
				crtCertificado = (X509Certificate) keyEntry.getCertificate();
			} 
			catch (KeyStoreException ex) 
			{
				Logger.getLogger(OnCert.class.getName()).log(Level.SEVERE, null, ex);
				//				ex.printStackTrace();
			}
		}
		return crtCertificado;
	}
	//Procedimento de Parametros de assinatura
	public static class TAssinaXML 
	{
		//MD2withRSA - MD5withRSA - SHA1withRSA - SHA224withRSA - SHA256withRSA - SHA1withDSA - DSA - RawDSA
		//public String strAlgorithmAssinatura = "MD5withRSA";
		public String strAliasTokenCert = null;
		public String strAliasCertificado = null;
		public String strArquivoCertificado = null;
		public String strSenhaCertificado = null;
		public String strArquivoXML = null;
		public String strArquivoSaveXML = null;
		public String C14N_TRANSFORM_METHOD = "http://www.w3.org/TR/2001/REC-xml-c14n-20010315";
		public boolean booNFS = true;
		public String xmlAssinado;
		public String idEvento = "";
	}
	
	//Procedimento de assinar XML
	public static boolean funcAssinaXML(TAssinaXML tpAssinaXML) throws Exception 
	{
//		Signature sgi = null;
		XMLSignatureFactory sig = null;
		SignedInfo si = null;
		KeyInfo ki = null;
		String strTipoSign = "?";
		String strID = "Id";
		//
		// Ler o XML a fim de obter a tag do Id do evento e a inscri��o do empregador
		String linha;
		LerStream lerStream = new LerStream(tpAssinaXML.strArquivoXML);
		if ( lerStream.prontoParaLeitura() )
		{
			while ((linha = lerStream.proximaLinha()) != "{EOF}") 
			{
				if ( linha.contains(strID+"=") )
				{
					strTipoSign = Aux_String.subStrIntoDelim(linha, "<", " ", true);
					tpAssinaXML.idEvento = Aux_String.subStrIntoDelim(linha," ",">",true);
				}
			}
			lerStream.close();
		}
		//Capturo o certificado
		X509Certificate cert = funcCertificadoSelecionado(tpAssinaXML.strAliasTokenCert, tpAssinaXML.strAliasCertificado, tpAssinaXML.strArquivoCertificado, tpAssinaXML.strSenhaCertificado);
		//Inicializo o arquivo/carrego
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		Document doc = dbf.newDocumentBuilder().parse(new FileInputStream(tpAssinaXML.strArquivoXML));
		sig = XMLSignatureFactory.getInstance("DOM");
		ArrayList<Transform> transformList = new ArrayList<Transform>();
		Transform enveloped = sig.newTransform(Transform.ENVELOPED, (TransformParameterSpec) null);
		Transform c14n = sig.newTransform(tpAssinaXML.C14N_TRANSFORM_METHOD, (TransformParameterSpec) null);
		transformList.add(enveloped);
		transformList.add(c14n);
		NodeList elements = doc.getElementsByTagName(strTipoSign);
		org.w3c.dom.Element el = (org.w3c.dom.Element) elements.item(0);
//		String id = el.getAttribute(strID);
		String id = el.getAttribute("Id");
		el.setIdAttribute("Id", true);
																
		Reference r = sig.newReference("", sig.newDigestMethod("http://www.w3.org/2001/04/xmlenc#sha256", null),
				transformList,
				null, null);
/*		Reference r = sig.newReference("#".concat(id), sig.newDigestMethod("http://www.w3.org/2001/04/xmlenc#sha256", null),
				transformList,
				null, null);
*/
/*
        Reference r = sig.newReference("#".concat(id), sig.newDigestMethod(DigestMethod.SHA256, null),
                transformList,
                null, null);

        si = sig.newSignedInfo(
                sig.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE,
                (C14NMethodParameterSpec) null),
                sig.newSignatureMethod(SignatureMethod.RSA_SHA1, null),
                Collections.singletonList(r));
*/      
		si = sig.newSignedInfo(
				sig.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE,
						(C14NMethodParameterSpec) null),
				sig.newSignatureMethod("http://www.w3.org/2001/04/xmldsig-more#rsa-sha256", null ),
				Collections.singletonList(r));

		KeyInfoFactory kif = sig.getKeyInfoFactory();
		List<X509Certificate> x509Content = new ArrayList<X509Certificate>();
		x509Content.add(cert);
		X509Data xd = kif.newX509Data(x509Content);
		ki = kif.newKeyInfo(Collections.singletonList(xd));
		DOMSignContext dsc = new DOMSignContext(funcChavePrivada(tpAssinaXML.strAliasTokenCert, tpAssinaXML.strAliasCertificado, tpAssinaXML.strArquivoCertificado, tpAssinaXML.strSenhaCertificado), doc.getDocumentElement());
		XMLSignature signature = sig.newXMLSignature(si, ki);
		signature.sign(dsc);		// neste momento � solicitada a senha do token
		
		OutputStream os = new FileOutputStream(tpAssinaXML.strArquivoSaveXML);    //Salvo o XML assinado num arquivo 
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer trans = tf.newTransformer();
		trans.transform(new DOMSource(doc), new StreamResult(os));
		
		StringWriter writer = new StringWriter();								  //Salvo o XML assinado numa propriedade String
		Transformer transformer = tf.newTransformer();
        transformer.transform(new DOMSource(doc), new StreamResult(writer));
        tpAssinaXML.xmlAssinado = writer.getBuffer().toString();
        
		return true;
	}
/*
	O m�todo abaixo foi retirado deste endere�o : https://stackoverflow.com/questions/12528667/xml-digital-signature-java
	para futuros testes.
	
	private static Document sign(Document doc) throws InstantiationException, IllegalAccessException, ClassNotFoundException,
	NoSuchAlgorithmException, InvalidAlgorithmParameterException, KeyException, MarshalException, XMLSignatureException,
	FileNotFoundException, TransformerException 
	{

		String providerName = System.getProperty("jsr105Provider", "org.jcp.xml.dsig.internal.dom.XMLDSigRI");

		XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM", (Provider) Class.forName(providerName).newInstance());

		DigestMethod digestMethod = fac.newDigestMethod(DigestMethod.SHA256, null);
		Transform transform = fac.newTransform(ENVELOPED, (TransformParameterSpec) null);
		Reference reference = fac.newReference("", digestMethod, singletonList(transform), null, null);
		SignatureMethod signatureMethod = fac.newSignatureMethod("http://www.w3.org/2001/04/xmldsig-more#rsa-sha256", null);
		CanonicalizationMethod canonicalizationMethod = fac.newCanonicalizationMethod(EXCLUSIVE, (C14NMethodParameterSpec) null);

		// Create the SignedInfo
		SignedInfo si = fac.newSignedInfo(canonicalizationMethod, signatureMethod, singletonList(reference));


		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		kpg.initialize(2048);

		KeyPair kp = kpg.generateKeyPair();

		KeyInfoFactory kif = fac.getKeyInfoFactory();
		KeyValue kv = kif.newKeyValue(kp.getPublic());

		// Create a KeyInfo and add the KeyValue to it
		KeyInfo ki = kif.newKeyInfo(Collections.singletonList(kv));
		DOMSignContext dsc = new DOMSignContext(kp.getPrivate(), doc.getDocumentElement());

		XMLSignature signature = fac.newXMLSignature(si, ki);
		signature.sign(dsc);

		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer trans = tf.newTransformer();

		// output the resulting document
		OutputStream os;

		os = new FileOutputStream("xmlOut.xml");

		trans.transform(new DOMSource(doc), new StreamResult(os));
		return doc;
	}    
*/
	
	@SuppressWarnings("restriction")
	public static TAssinaXML funcAssinaEventoXML(TAssinaXML tpAssinaXML) throws Exception 
	{
//		Signature sgi = null;
		XMLSignatureFactory sig = null;
		SignedInfo si = null;
		KeyInfo ki = null;
		String strTipoSign = "Signature";//"?";
		String strID = "Id";
		String valorID = null;

		if(tpAssinaXML.strArquivoXML != null ) {
			if(tpAssinaXML.strArquivoXML.contains("Id=")) {
				valorID = tpAssinaXML.strArquivoXML.substring(tpAssinaXML.strArquivoXML.indexOf("Id="));
				valorID += valorID.substring(valorID.indexOf("Id=")+4, valorID.indexOf("\">"));
				System.out.println(valorID);
			}
		}
		
		//Capturo o certificado
		X509Certificate cert = funcCertificadoSelecionado(tpAssinaXML.strAliasTokenCert, tpAssinaXML.strAliasCertificado, tpAssinaXML.strArquivoCertificado, tpAssinaXML.strSenhaCertificado);

		//Inicializo o arquivo/carrego
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		//Document doc = dbf.newDocumentBuilder().parse(tpAssinaXML.strArquivoXML);
		Document doc = dbf.newDocumentBuilder().parse(new ByteArrayInputStream((tpAssinaXML.strArquivoXML).getBytes(StandardCharsets.UTF_8)));
		sig = XMLSignatureFactory.getInstance("DOM");
		ArrayList<Transform> transformList = new ArrayList<Transform>();
		Transform enveloped = sig.newTransform(Transform.ENVELOPED, (TransformParameterSpec) null);
		Transform c14n = sig.newTransform(tpAssinaXML.C14N_TRANSFORM_METHOD, (TransformParameterSpec) null);
		transformList.add(enveloped);
		transformList.add(c14n);
		// NodeList elements = doc.getFirstChild().getChildNodes();//doc.getElementsByTagName(strTipoSign);
		//org.w3c.dom.Element el = (org.w3c.dom.Element) elements.item(0);
		// Node el = elements.item(0).getNextSibling().getAttributes().getNamedItem("Id");
//		String id = el.getAttribute(strID);
		/*String id = el.getAttribute("Id");
		el.setIdAttribute("Id", true);*/
																
		Reference r = sig.newReference("", sig.newDigestMethod("http://www.w3.org/2001/04/xmlenc#sha256", null),
				transformList,
				null, null);

		si = sig.newSignedInfo(
				sig.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE,
						(C14NMethodParameterSpec) null),
				sig.newSignatureMethod("http://www.w3.org/2001/04/xmldsig-more#rsa-sha256", null ),
				Collections.singletonList(r));

		KeyInfoFactory kif = sig.getKeyInfoFactory();
		List<X509Certificate> x509Content = new ArrayList<X509Certificate>();
		x509Content.add(cert);
		X509Data xd = kif.newX509Data(x509Content);
		ki = kif.newKeyInfo(Collections.singletonList(xd));
		DOMSignContext dsc = new DOMSignContext(funcChavePrivada(tpAssinaXML.strAliasTokenCert, tpAssinaXML.strAliasCertificado, tpAssinaXML.strArquivoCertificado, tpAssinaXML.strSenhaCertificado), doc.getDocumentElement());
		XMLSignature signature = sig.newXMLSignature(si, ki);
		signature.sign(dsc);		// neste momento � solicitada a senha do token
		
		TransformerFactory tf = TransformerFactory.newInstance();
		/*OutputStream os = new FileOutputStream(tpAssinaXML.strArquivoSaveXML);    //Salvo o XML assinado num arquivo 
		Transformer trans = tf.newTransformer();
		trans.transform(new DOMSource(doc), new StreamResult(os));*/
		
		StringWriter writer = new StringWriter();								  //Salvo o XML assinado numa propriedade String
		Transformer transformer = tf.newTransformer();
        transformer.transform(new DOMSource(doc), new StreamResult(writer));
        tpAssinaXML.xmlAssinado = writer.getBuffer().toString();
        
		//return true;
        return tpAssinaXML;
	}
	
	/**.
     * Objetivo: metodo responsavel por assinar digitalmente o xml de retorno
     * @param byte[]
     * @author CTMONSI
     * @since 09/02/2015
     * @version 1.0
	 * @throws CertificadoException 
     */
	public static String assinarCertificado(byte[] xml) throws BusinessException {
		XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");
		try {
			final Reference ref = fac.newReference("", fac.newDigestMethod(DigestMethod.SHA1, null),
					Collections.singletonList(fac.newTransform(Transform.ENVELOPED, (TransformParameterSpec) null)), null, null);

			SignedInfo si = fac.newSignedInfo(fac.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE, (C14NMethodParameterSpec) null),
					fac.newSignatureMethod(SignatureMethod.RSA_SHA1, null), Collections.singletonList(ref));

			final KeyStore ks = KeyStore.getInstance(SystemPropertiesMessage.getSystemEnvOrProperty(Constantes.SIGNER_TYPE));
			
			final File signerFile = new File(SystemPropertiesMessage.getSystemEnvOrProperty(Constantes.SIGNER_FILE));
			final FileInputStream fis = new FileInputStream(signerFile);
			ks.load(fis, SystemPropertiesMessage.getSystemEnvOrProperty(Constantes.SIGNER_PASSWORD).toCharArray());
			fis.close();
			 
			final KeyStore.PrivateKeyEntry keyEntry = (KeyStore.PrivateKeyEntry) ks.getEntry(SystemPropertiesMessage.getSystemEnvOrProperty(Constantes.SIGNER_ALIAS),
					new KeyStore.PasswordProtection(SystemPropertiesMessage.getSystemEnvOrProperty(Constantes.SIGNER_PASSWORD).toCharArray()));
			final X509Certificate cert = (X509Certificate) keyEntry.getCertificate();

			final KeyInfoFactory kif = fac.getKeyInfoFactory();
			final List<Object> x509Content = new ArrayList<Object>();
			x509Content.add(cert.getSubjectX500Principal().getName());
			x509Content.add(cert);
			final X509Data xd = kif.newX509Data(x509Content);
			final KeyInfo ki = kif.newKeyInfo(Collections.singletonList(xd));

			final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);
			final Document doc = dbf.newDocumentBuilder().parse(new ByteArrayInputStream(xml));

			final DOMSignContext dsc = new DOMSignContext(keyEntry.getPrivateKey(), doc.getDocumentElement());
			final XMLSignature signature = fac.newXMLSignature(si, ki);
			signature.sign(dsc);
			
		    StringWriter sw = new StringWriter();

			final TransformerFactory tf = TransformerFactory.newInstance();
			final Transformer trans = tf.newTransformer();
			trans.transform(new DOMSource(doc), new StreamResult(sw));
			return sw.toString();
		} 
		catch (Exception e) {
			throw new BusinessException("Assinatura Inválida", e);
		}
	}
	
	/**
	 * Carrega os certificados com PKCS11 cadastradas no Windows
	 * 
	 * @return
	 */
	public static KeyStore funcListaCertificadosDemoiselle() {
		//String strResult[] = new String[20];
		Integer intCnt = 0;
		//KeyStore ks = null;
		
		KeyStore ks = KeyStoreLoaderFactory.factoryKeyStoreLoader().getKeyStore();
		/*jkspkcs11.load(null);
		Enumeration<String> aliasEnum = jkspkcs11.aliases();
		
		while (aliasEnum.hasMoreElements()) {
			String aliasKey = (String) aliasEnum.nextElement();
			
			if (jkspkcs11.isKeyEntry(aliasKey)) {
				//strResult[intCnt] = aliasKey;
				ks = 
			}
			
			if (strResult[intCnt] != null) {
				intCnt = intCnt + 1;
			}
		}*/
		
		return ks;
	}

	public static KeyStore loadKeystore() throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException {
		KeyStore ks = KeyStore.getInstance(SystemPropertiesMessage.getSystemEnvOrProperty(Constantes.SIGNER_TYPE));
		
		File signerFile = new File(SystemPropertiesMessage.getSystemEnvOrProperty(Constantes.SIGNER_FILE));
		FileInputStream fis = new FileInputStream(signerFile);
		ks.load(fis, SystemPropertiesMessage.getSystemEnvOrProperty(Constantes.SIGNER_PASSWORD).toCharArray());
		fis.close();
		
		return ks;
	}

	public static String getAliasCertificate(KeyStore keystore) throws KeyStoreException {
		String alias = "";
		Enumeration<String> aliasesEnum = keystore.aliases();
		
		while (aliasesEnum.hasMoreElements()) {
			alias = (String) aliasesEnum.nextElement();
			System.out.println("Alias encontrado: "+ alias);
			
			if (keystore.isKeyEntry(alias)) {
				break;
			}
		}
		
		return alias;
	}
	
	public static String getSenhaCertificado() {
		String senhaDoCertificado = SystemPropertiesMessage.getSystemEnvOrProperty(Constantes.SIGNER_PASSWORD);
		return senhaDoCertificado;
	}
}