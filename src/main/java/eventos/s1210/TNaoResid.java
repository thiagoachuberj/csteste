//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-558 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.05.15 at 09:26:02 AM BRT 
//


package eventos.s1210;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Endereço no Exterior - Fiscal
 * 
 * <p>Java class for TNaoResid complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TNaoResid">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idePais">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="codPais">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;length value="3"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="indNIF">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}byte">
 *                         &lt;pattern value="\d"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="nifBenef" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;minLength value="1"/>
 *                         &lt;maxLength value="20"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="endExt">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="dscLograd">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;minLength value="1"/>
 *                         &lt;maxLength value="80"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="nrLograd" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;minLength value="1"/>
 *                         &lt;maxLength value="10"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="complem" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;maxLength value="30"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="bairro" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;minLength value="1"/>
 *                         &lt;maxLength value="60"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="nmCid">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;maxLength value="50"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="codPostal" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;whiteSpace value="preserve"/>
 *                         &lt;minLength value="4"/>
 *                         &lt;maxLength value="12"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
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
@XmlType(name = "TNaoResid", namespace = "http://www.esocial.gov.br/schema/evt/evtPgtos/v02_04_02", propOrder = {
    "idePais",
    "endExt"
})
public class TNaoResid {

    @XmlElement(required = true)
    protected TNaoResid.IdePais idePais;
    @XmlElement(required = true)
    protected TNaoResid.EndExt endExt;

    /**
     * Gets the value of the idePais property.
     * 
     * @return
     *     possible object is
     *     {@link TNaoResid.IdePais }
     *     
     */
    public TNaoResid.IdePais getIdePais() {
        return idePais;
    }

    /**
     * Sets the value of the idePais property.
     * 
     * @param value
     *     allowed object is
     *     {@link TNaoResid.IdePais }
     *     
     */
    public void setIdePais(TNaoResid.IdePais value) {
        this.idePais = value;
    }

    /**
     * Gets the value of the endExt property.
     * 
     * @return
     *     possible object is
     *     {@link TNaoResid.EndExt }
     *     
     */
    public TNaoResid.EndExt getEndExt() {
        return endExt;
    }

    /**
     * Sets the value of the endExt property.
     * 
     * @param value
     *     allowed object is
     *     {@link TNaoResid.EndExt }
     *     
     */
    public void setEndExt(TNaoResid.EndExt value) {
        this.endExt = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="dscLograd">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;minLength value="1"/>
     *               &lt;maxLength value="80"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="nrLograd" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;minLength value="1"/>
     *               &lt;maxLength value="10"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="complem" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;maxLength value="30"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="bairro" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;minLength value="1"/>
     *               &lt;maxLength value="60"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="nmCid">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;maxLength value="50"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="codPostal" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;whiteSpace value="preserve"/>
     *               &lt;minLength value="4"/>
     *               &lt;maxLength value="12"/>
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
    @XmlType(name = "", propOrder = {
        "dscLograd",
        "nrLograd",
        "complem",
        "bairro",
        "nmCid",
        "codPostal"
    })
    public static class EndExt {

        @XmlElement(namespace = "http://www.esocial.gov.br/schema/evt/evtPgtos/v02_04_02", required = true)
        protected String dscLograd;
        @XmlElement(namespace = "http://www.esocial.gov.br/schema/evt/evtPgtos/v02_04_02")
        protected String nrLograd;
        @XmlElement(namespace = "http://www.esocial.gov.br/schema/evt/evtPgtos/v02_04_02")
        protected String complem;
        @XmlElement(namespace = "http://www.esocial.gov.br/schema/evt/evtPgtos/v02_04_02")
        protected String bairro;
        @XmlElement(namespace = "http://www.esocial.gov.br/schema/evt/evtPgtos/v02_04_02", required = true)
        protected String nmCid;
        @XmlElement(namespace = "http://www.esocial.gov.br/schema/evt/evtPgtos/v02_04_02")
        protected String codPostal;

        /**
         * Gets the value of the dscLograd property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDscLograd() {
            return dscLograd;
        }

        /**
         * Sets the value of the dscLograd property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDscLograd(String value) {
            this.dscLograd = value;
        }

        /**
         * Gets the value of the nrLograd property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNrLograd() {
            return nrLograd;
        }

        /**
         * Sets the value of the nrLograd property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNrLograd(String value) {
            this.nrLograd = value;
        }

        /**
         * Gets the value of the complem property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getComplem() {
            return complem;
        }

        /**
         * Sets the value of the complem property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setComplem(String value) {
            this.complem = value;
        }

        /**
         * Gets the value of the bairro property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getBairro() {
            return bairro;
        }

        /**
         * Sets the value of the bairro property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setBairro(String value) {
            this.bairro = value;
        }

        /**
         * Gets the value of the nmCid property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNmCid() {
            return nmCid;
        }

        /**
         * Sets the value of the nmCid property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNmCid(String value) {
            this.nmCid = value;
        }

        /**
         * Gets the value of the codPostal property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCodPostal() {
            return codPostal;
        }

        /**
         * Sets the value of the codPostal property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCodPostal(String value) {
            this.codPostal = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="codPais">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;length value="3"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="indNIF">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}byte">
     *               &lt;pattern value="\d"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="nifBenef" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;minLength value="1"/>
     *               &lt;maxLength value="20"/>
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
    @XmlType(name = "", propOrder = {
        "codPais",
        "indNIF",
        "nifBenef"
    })
    public static class IdePais {

        @XmlElement(namespace = "http://www.esocial.gov.br/schema/evt/evtPgtos/v02_04_02", required = true)
        protected String codPais;
        @XmlElement(namespace = "http://www.esocial.gov.br/schema/evt/evtPgtos/v02_04_02")
        protected byte indNIF;
        @XmlElement(namespace = "http://www.esocial.gov.br/schema/evt/evtPgtos/v02_04_02")
        protected String nifBenef;

        /**
         * Gets the value of the codPais property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCodPais() {
            return codPais;
        }

        /**
         * Sets the value of the codPais property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCodPais(String value) {
            this.codPais = value;
        }

        /**
         * Gets the value of the indNIF property.
         * 
         */
        public byte getIndNIF() {
            return indNIF;
        }

        /**
         * Sets the value of the indNIF property.
         * 
         */
        public void setIndNIF(byte value) {
            this.indNIF = value;
        }

        /**
         * Gets the value of the nifBenef property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNifBenef() {
            return nifBenef;
        }

        /**
         * Sets the value of the nifBenef property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNifBenef(String value) {
            this.nifBenef = value;
        }

    }

}
