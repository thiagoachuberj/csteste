//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-558 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.05.15 at 08:57:26 AM BRT 
//


package eventos.s1030;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Informações do Cargo
 * 
 * <p>Java class for TDadosCargo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TDadosCargo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="nmCargo">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="1"/>
 *               &lt;maxLength value="100"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="codCBO">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;pattern value="\d{4,6}"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="cargoPublico" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="acumCargo">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}byte">
 *                         &lt;pattern value="\d"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="contagemEsp">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}byte">
 *                         &lt;pattern value="\d"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="dedicExcl">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;pattern value="[N|S]"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="leiCargo">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="nrLei">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;whiteSpace value="preserve"/>
 *                                   &lt;minLength value="1"/>
 *                                   &lt;maxLength value="12"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="dtLei">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}date">
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="sitCargo">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}byte">
 *                                   &lt;pattern value="\d"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
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
@XmlType(name = "TDadosCargo", namespace = "http://www.esocial.gov.br/schema/evt/evtTabCargo/v02_04_02", propOrder = {
    "nmCargo",
    "codCBO",
    "cargoPublico"
})
public class TDadosCargo {

    @XmlElement(required = true)
    protected String nmCargo;
    @XmlElement(required = true)
    protected String codCBO;
    protected TDadosCargo.CargoPublico cargoPublico;

    /**
     * Gets the value of the nmCargo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNmCargo() {
        return nmCargo;
    }

    /**
     * Sets the value of the nmCargo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNmCargo(String value) {
        this.nmCargo = value;
    }

    /**
     * Gets the value of the codCBO property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodCBO() {
        return codCBO;
    }

    /**
     * Sets the value of the codCBO property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodCBO(String value) {
        this.codCBO = value;
    }

    /**
     * Gets the value of the cargoPublico property.
     * 
     * @return
     *     possible object is
     *     {@link TDadosCargo.CargoPublico }
     *     
     */
    public TDadosCargo.CargoPublico getCargoPublico() {
        return cargoPublico;
    }

    /**
     * Sets the value of the cargoPublico property.
     * 
     * @param value
     *     allowed object is
     *     {@link TDadosCargo.CargoPublico }
     *     
     */
    public void setCargoPublico(TDadosCargo.CargoPublico value) {
        this.cargoPublico = value;
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
     *         &lt;element name="acumCargo">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}byte">
     *               &lt;pattern value="\d"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="contagemEsp">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}byte">
     *               &lt;pattern value="\d"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="dedicExcl">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;pattern value="[N|S]"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="leiCargo">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="nrLei">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         &lt;whiteSpace value="preserve"/>
     *                         &lt;minLength value="1"/>
     *                         &lt;maxLength value="12"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="dtLei">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}date">
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="sitCargo">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}byte">
     *                         &lt;pattern value="\d"/>
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
    @XmlType(name = "", propOrder = {
        "acumCargo",
        "contagemEsp",
        "dedicExcl",
        "leiCargo"
    })
    public static class CargoPublico {

        @XmlElement(namespace = "http://www.esocial.gov.br/schema/evt/evtTabCargo/v02_04_02")
        protected byte acumCargo;
        @XmlElement(namespace = "http://www.esocial.gov.br/schema/evt/evtTabCargo/v02_04_02")
        protected byte contagemEsp;
        @XmlElement(namespace = "http://www.esocial.gov.br/schema/evt/evtTabCargo/v02_04_02", required = true)
        protected String dedicExcl;
        @XmlElement(namespace = "http://www.esocial.gov.br/schema/evt/evtTabCargo/v02_04_02", required = true)
        protected TDadosCargo.CargoPublico.LeiCargo leiCargo;

        /**
         * Gets the value of the acumCargo property.
         * 
         */
        public byte getAcumCargo() {
            return acumCargo;
        }

        /**
         * Sets the value of the acumCargo property.
         * 
         */
        public void setAcumCargo(byte value) {
            this.acumCargo = value;
        }

        /**
         * Gets the value of the contagemEsp property.
         * 
         */
        public byte getContagemEsp() {
            return contagemEsp;
        }

        /**
         * Sets the value of the contagemEsp property.
         * 
         */
        public void setContagemEsp(byte value) {
            this.contagemEsp = value;
        }

        /**
         * Gets the value of the dedicExcl property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDedicExcl() {
            return dedicExcl;
        }

        /**
         * Sets the value of the dedicExcl property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDedicExcl(String value) {
            this.dedicExcl = value;
        }

        /**
         * Gets the value of the leiCargo property.
         * 
         * @return
         *     possible object is
         *     {@link TDadosCargo.CargoPublico.LeiCargo }
         *     
         */
        public TDadosCargo.CargoPublico.LeiCargo getLeiCargo() {
            return leiCargo;
        }

        /**
         * Sets the value of the leiCargo property.
         * 
         * @param value
         *     allowed object is
         *     {@link TDadosCargo.CargoPublico.LeiCargo }
         *     
         */
        public void setLeiCargo(TDadosCargo.CargoPublico.LeiCargo value) {
            this.leiCargo = value;
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
         *         &lt;element name="nrLei">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               &lt;whiteSpace value="preserve"/>
         *               &lt;minLength value="1"/>
         *               &lt;maxLength value="12"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="dtLei">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}date">
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="sitCargo">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}byte">
         *               &lt;pattern value="\d"/>
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
            "nrLei",
            "dtLei",
            "sitCargo"
        })
        public static class LeiCargo {

            @XmlElement(namespace = "http://www.esocial.gov.br/schema/evt/evtTabCargo/v02_04_02", required = true)
            protected String nrLei;
            @XmlElement(namespace = "http://www.esocial.gov.br/schema/evt/evtTabCargo/v02_04_02", required = true)
            protected XMLGregorianCalendar dtLei;
            @XmlElement(namespace = "http://www.esocial.gov.br/schema/evt/evtTabCargo/v02_04_02")
            protected byte sitCargo;

            /**
             * Gets the value of the nrLei property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getNrLei() {
                return nrLei;
            }

            /**
             * Sets the value of the nrLei property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setNrLei(String value) {
                this.nrLei = value;
            }

            /**
             * Gets the value of the dtLei property.
             * 
             * @return
             *     possible object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public XMLGregorianCalendar getDtLei() {
                return dtLei;
            }

            /**
             * Sets the value of the dtLei property.
             * 
             * @param value
             *     allowed object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public void setDtLei(XMLGregorianCalendar value) {
                this.dtLei = value;
            }

            /**
             * Gets the value of the sitCargo property.
             * 
             */
            public byte getSitCargo() {
                return sitCargo;
            }

            /**
             * Sets the value of the sitCargo property.
             * 
             */
            public void setSitCargo(byte value) {
                this.sitCargo = value;
            }

        }

    }

}
