//
// Este arquivo foi gerado pela Arquitetura JavaTM para Implementação de Referência (JAXB) de Bind XML, v2.2.8-b130911.1802 
// Consulte <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas as modificações neste arquivo serão perdidas após a recompilação do esquema de origem. 
// Gerado em: 2018.01.30 às 05:14:28 PM BRST 
//


package eventos.s1000;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Identificação do Período de Validade das Informações
 * 
 * <p>Classe Java de TIdePeriodo complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="TIdePeriodo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
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
@XmlType(name = "TIdePeriodo", namespace = "http://www.esocial.gov.br/schema/evt/evtInfoEmpregador/v02_04_01", propOrder = {
    "iniValid",
    "fimValid"
})
public class TIdePeriodo {

    @XmlElement(required = true)
    protected String iniValid;
    protected String fimValid;

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