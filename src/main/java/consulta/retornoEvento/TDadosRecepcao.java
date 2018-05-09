//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-558 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.04.24 at 11:59:26 PM BRT 
//


package consulta.retornoEvento;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Define os dados de recepção do evento.
 * 
 * <p>Java class for TDadosRecepcao complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TDadosRecepcao">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="tpAmb" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/>
 *         &lt;element name="dhRecepcao" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="versaoAppRecepcao">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="30"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="protocoloEnvioLote" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="30"/>
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
@XmlType(name = "TDadosRecepcao", namespace = "http://www.esocial.gov.br/schema/evt/retornoEvento/v1_2_0", propOrder = {
    "tpAmb",
    "dhRecepcao",
    "versaoAppRecepcao",
    "protocoloEnvioLote"
})
public class TDadosRecepcao {

    @XmlSchemaType(name = "unsignedByte")
    protected short tpAmb;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dhRecepcao;
    @XmlElement(required = true)
    protected String versaoAppRecepcao;
    protected String protocoloEnvioLote;

    /**
     * Gets the value of the tpAmb property.
     * 
     */
    public short getTpAmb() {
        return tpAmb;
    }

    /**
     * Sets the value of the tpAmb property.
     * 
     */
    public void setTpAmb(short value) {
        this.tpAmb = value;
    }

    /**
     * Gets the value of the dhRecepcao property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDhRecepcao() {
        return dhRecepcao;
    }

    /**
     * Sets the value of the dhRecepcao property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDhRecepcao(XMLGregorianCalendar value) {
        this.dhRecepcao = value;
    }

    /**
     * Gets the value of the versaoAppRecepcao property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersaoAppRecepcao() {
        return versaoAppRecepcao;
    }

    /**
     * Sets the value of the versaoAppRecepcao property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersaoAppRecepcao(String value) {
        this.versaoAppRecepcao = value;
    }

    /**
     * Gets the value of the protocoloEnvioLote property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProtocoloEnvioLote() {
        return protocoloEnvioLote;
    }

    /**
     * Sets the value of the protocoloEnvioLote property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProtocoloEnvioLote(String value) {
        this.protocoloEnvioLote = value;
    }

}
