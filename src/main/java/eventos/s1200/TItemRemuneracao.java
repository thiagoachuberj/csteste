//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-558 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.05.15 at 09:18:45 AM BRT 
//


package eventos.s1200;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Informações dos Itens (rubricas) da remuneração
 * 
 * <p>Java class for TItemRemuneracao complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TItemRemuneracao">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codRubr">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="1"/>
 *               &lt;maxLength value="30"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ideTabRubr">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="1"/>
 *               &lt;maxLength value="8"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="qtdRubr" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;totalDigits value="6"/>
 *               &lt;fractionDigits value="2"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="fatorRubr" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;totalDigits value="5"/>
 *               &lt;fractionDigits value="2"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="vrUnit" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;totalDigits value="14"/>
 *               &lt;fractionDigits value="2"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="vrRubr">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;totalDigits value="14"/>
 *               &lt;fractionDigits value="2"/>
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
@XmlType(name = "TItemRemuneracao", namespace = "http://www.esocial.gov.br/schema/evt/evtRemun/v02_04_02", propOrder = {
    "codRubr",
    "ideTabRubr",
    "qtdRubr",
    "fatorRubr",
    "vrUnit",
    "vrRubr"
})
public class TItemRemuneracao {

    @XmlElement(required = true)
    protected String codRubr;
    @XmlElement(required = true)
    protected String ideTabRubr;
    protected BigDecimal qtdRubr;
    protected BigDecimal fatorRubr;
    protected BigDecimal vrUnit;
    @XmlElement(required = true)
    protected BigDecimal vrRubr;

    /**
     * Gets the value of the codRubr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodRubr() {
        return codRubr;
    }

    /**
     * Sets the value of the codRubr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodRubr(String value) {
        this.codRubr = value;
    }

    /**
     * Gets the value of the ideTabRubr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdeTabRubr() {
        return ideTabRubr;
    }

    /**
     * Sets the value of the ideTabRubr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdeTabRubr(String value) {
        this.ideTabRubr = value;
    }

    /**
     * Gets the value of the qtdRubr property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getQtdRubr() {
        return qtdRubr;
    }

    /**
     * Sets the value of the qtdRubr property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setQtdRubr(BigDecimal value) {
        this.qtdRubr = value;
    }

    /**
     * Gets the value of the fatorRubr property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getFatorRubr() {
        return fatorRubr;
    }

    /**
     * Sets the value of the fatorRubr property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setFatorRubr(BigDecimal value) {
        this.fatorRubr = value;
    }

    /**
     * Gets the value of the vrUnit property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getVrUnit() {
        return vrUnit;
    }

    /**
     * Sets the value of the vrUnit property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setVrUnit(BigDecimal value) {
        this.vrUnit = value;
    }

    /**
     * Gets the value of the vrRubr property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getVrRubr() {
        return vrRubr;
    }

    /**
     * Sets the value of the vrRubr property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setVrRubr(BigDecimal value) {
        this.vrRubr = value;
    }

}
