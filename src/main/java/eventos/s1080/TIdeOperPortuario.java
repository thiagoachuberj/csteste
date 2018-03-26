//
// Este arquivo foi gerado pela Arquitetura JavaTM para Implementação de Referência (JAXB) de Bind XML, v2.2.8-b130911.1802 
// Consulte <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas as modificações neste arquivo serão perdidas após a recompilação do esquema de origem. 
// Gerado em: 2018.01.30 às 05:05:28 PM BRST 
//


package eventos.s1080;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Informações de identificação do Operador Portuário
 * 
 * <p>Classe Java de TIdeOperPortuario complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="TIdeOperPortuario">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cnpjOpPortuario">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;length value="14"/>
 *               &lt;pattern value="\d{8,14}"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="iniValid">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;pattern value="[2]{1}\d{3}-(1[0-2]|0[1-9])"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="fimValid" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;pattern value="[2]{1}\d{3}-(1[0-2]|0[1-9])"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TIdeOperPortuario", namespace = "http://www.esocial.gov.br/schema/evt/evtTabOperPort/v02_04_01", propOrder = {
    "cnpjOpPortuario",
    "iniValid",
    "fimValid"
})
public class TIdeOperPortuario {

    @XmlElement(required = true)
    protected String cnpjOpPortuario;
    @XmlElement(required = true)
    protected String iniValid;
    protected String fimValid;

    /**
     * Obtém o valor da propriedade cnpjOpPortuario.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCnpjOpPortuario() {
        return cnpjOpPortuario;
    }

    /**
     * Define o valor da propriedade cnpjOpPortuario.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCnpjOpPortuario(String value) {
        this.cnpjOpPortuario = value;
    }

    /**
     * Obtém o valor da propriedade iniValid.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIniValid() {
        return iniValid;
    }

    /**
     * Define o valor da propriedade iniValid.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIniValid(String value) {
        this.iniValid = value;
    }

    /**
     * Obtém o valor da propriedade fimValid.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFimValid() {
        return fimValid;
    }

    /**
     * Define o valor da propriedade fimValid.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFimValid(String value) {
        this.fimValid = value;
    }

}
