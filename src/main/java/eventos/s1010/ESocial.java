//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-558 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.05.15 at 12:30:00 AM BRT 
//


package eventos.s1010;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import vo.BaseVO;


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
 *         &lt;element name="evtTabRubrica">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="ideEvento" type="{http://www.esocial.gov.br/schema/evt/evtTabRubrica/v02_04_02}TIdeCadastro"/>
 *                   &lt;element name="ideEmpregador" type="{http://www.esocial.gov.br/schema/evt/evtTabRubrica/v02_04_02}TEmpregador"/>
 *                   &lt;element name="infoRubrica">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;choice>
 *                               &lt;element name="inclusao">
 *                                 &lt;complexType>
 *                                   &lt;complexContent>
 *                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                       &lt;sequence>
 *                                         &lt;element name="ideRubrica" type="{http://www.esocial.gov.br/schema/evt/evtTabRubrica/v02_04_02}TIdeRubrica"/>
 *                                         &lt;element name="dadosRubrica" type="{http://www.esocial.gov.br/schema/evt/evtTabRubrica/v02_04_02}TDadosRubrica"/>
 *                                       &lt;/sequence>
 *                                     &lt;/restriction>
 *                                   &lt;/complexContent>
 *                                 &lt;/complexType>
 *                               &lt;/element>
 *                               &lt;element name="alteracao">
 *                                 &lt;complexType>
 *                                   &lt;complexContent>
 *                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                       &lt;sequence>
 *                                         &lt;element name="ideRubrica" type="{http://www.esocial.gov.br/schema/evt/evtTabRubrica/v02_04_02}TIdeRubrica"/>
 *                                         &lt;element name="dadosRubrica" type="{http://www.esocial.gov.br/schema/evt/evtTabRubrica/v02_04_02}TDadosRubrica"/>
 *                                         &lt;element name="novaValidade" type="{http://www.esocial.gov.br/schema/evt/evtTabRubrica/v02_04_02}TPeriodoValidade" minOccurs="0"/>
 *                                       &lt;/sequence>
 *                                     &lt;/restriction>
 *                                   &lt;/complexContent>
 *                                 &lt;/complexType>
 *                               &lt;/element>
 *                               &lt;element name="exclusao">
 *                                 &lt;complexType>
 *                                   &lt;complexContent>
 *                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                       &lt;sequence>
 *                                         &lt;element name="ideRubrica" type="{http://www.esocial.gov.br/schema/evt/evtTabRubrica/v02_04_02}TIdeRubrica"/>
 *                                       &lt;/sequence>
 *                                     &lt;/restriction>
 *                                   &lt;/complexContent>
 *                                 &lt;/complexType>
 *                               &lt;/element>
 *                             &lt;/choice>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *                 &lt;attribute name="Id" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element ref="{http://www.w3.org/2000/09/xmldsig#}Signature"/>
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
    "evtTabRubrica",
    "signature"
})
@XmlRootElement(name = "eSocial", namespace = "http://www.esocial.gov.br/schema/evt/evtTabRubrica/v02_04_02")
public class ESocial extends BaseVO {

    @XmlElement(namespace = "http://www.esocial.gov.br/schema/evt/evtTabRubrica/v02_04_02", required = true)
    protected ESocial.EvtTabRubrica evtTabRubrica;
    @XmlElement(name = "Signature", required = true)
    protected SignatureType signature;

    /**
     * Gets the value of the evtTabRubrica property.
     * 
     * @return
     *     possible object is
     *     {@link ESocial.EvtTabRubrica }
     *     
     */
    public ESocial.EvtTabRubrica getEvtTabRubrica() {
        return evtTabRubrica;
    }

    /**
     * Sets the value of the evtTabRubrica property.
     * 
     * @param value
     *     allowed object is
     *     {@link ESocial.EvtTabRubrica }
     *     
     */
    public void setEvtTabRubrica(ESocial.EvtTabRubrica value) {
        this.evtTabRubrica = value;
    }

    /**
     * Gets the value of the signature property.
     * 
     * @return
     *     possible object is
     *     {@link SignatureType }
     *     
     */
    public SignatureType getSignature() {
        return signature;
    }

    /**
     * Sets the value of the signature property.
     * 
     * @param value
     *     allowed object is
     *     {@link SignatureType }
     *     
     */
    public void setSignature(SignatureType value) {
        this.signature = value;
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
     *         &lt;element name="ideEvento" type="{http://www.esocial.gov.br/schema/evt/evtTabRubrica/v02_04_02}TIdeCadastro"/>
     *         &lt;element name="ideEmpregador" type="{http://www.esocial.gov.br/schema/evt/evtTabRubrica/v02_04_02}TEmpregador"/>
     *         &lt;element name="infoRubrica">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;choice>
     *                     &lt;element name="inclusao">
     *                       &lt;complexType>
     *                         &lt;complexContent>
     *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                             &lt;sequence>
     *                               &lt;element name="ideRubrica" type="{http://www.esocial.gov.br/schema/evt/evtTabRubrica/v02_04_02}TIdeRubrica"/>
     *                               &lt;element name="dadosRubrica" type="{http://www.esocial.gov.br/schema/evt/evtTabRubrica/v02_04_02}TDadosRubrica"/>
     *                             &lt;/sequence>
     *                           &lt;/restriction>
     *                         &lt;/complexContent>
     *                       &lt;/complexType>
     *                     &lt;/element>
     *                     &lt;element name="alteracao">
     *                       &lt;complexType>
     *                         &lt;complexContent>
     *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                             &lt;sequence>
     *                               &lt;element name="ideRubrica" type="{http://www.esocial.gov.br/schema/evt/evtTabRubrica/v02_04_02}TIdeRubrica"/>
     *                               &lt;element name="dadosRubrica" type="{http://www.esocial.gov.br/schema/evt/evtTabRubrica/v02_04_02}TDadosRubrica"/>
     *                               &lt;element name="novaValidade" type="{http://www.esocial.gov.br/schema/evt/evtTabRubrica/v02_04_02}TPeriodoValidade" minOccurs="0"/>
     *                             &lt;/sequence>
     *                           &lt;/restriction>
     *                         &lt;/complexContent>
     *                       &lt;/complexType>
     *                     &lt;/element>
     *                     &lt;element name="exclusao">
     *                       &lt;complexType>
     *                         &lt;complexContent>
     *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                             &lt;sequence>
     *                               &lt;element name="ideRubrica" type="{http://www.esocial.gov.br/schema/evt/evtTabRubrica/v02_04_02}TIdeRubrica"/>
     *                             &lt;/sequence>
     *                           &lt;/restriction>
     *                         &lt;/complexContent>
     *                       &lt;/complexType>
     *                     &lt;/element>
     *                   &lt;/choice>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *       &lt;attribute name="Id" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "ideEvento",
        "ideEmpregador",
        "infoRubrica"
    })
    public static class EvtTabRubrica {

        @XmlElement(namespace = "http://www.esocial.gov.br/schema/evt/evtTabRubrica/v02_04_02", required = true)
        protected TIdeCadastro ideEvento;
        @XmlElement(namespace = "http://www.esocial.gov.br/schema/evt/evtTabRubrica/v02_04_02", required = true)
        protected TEmpregador ideEmpregador;
        @XmlElement(namespace = "http://www.esocial.gov.br/schema/evt/evtTabRubrica/v02_04_02", required = true)
        protected ESocial.EvtTabRubrica.InfoRubrica infoRubrica;
        @XmlAttribute(name = "Id", required = true)
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlID
        @XmlSchemaType(name = "ID")
        protected String id;

        /**
         * Gets the value of the ideEvento property.
         * 
         * @return
         *     possible object is
         *     {@link TIdeCadastro }
         *     
         */
        public TIdeCadastro getIdeEvento() {
            return ideEvento;
        }

        /**
         * Sets the value of the ideEvento property.
         * 
         * @param value
         *     allowed object is
         *     {@link TIdeCadastro }
         *     
         */
        public void setIdeEvento(TIdeCadastro value) {
            this.ideEvento = value;
        }

        /**
         * Gets the value of the ideEmpregador property.
         * 
         * @return
         *     possible object is
         *     {@link TEmpregador }
         *     
         */
        public TEmpregador getIdeEmpregador() {
            return ideEmpregador;
        }

        /**
         * Sets the value of the ideEmpregador property.
         * 
         * @param value
         *     allowed object is
         *     {@link TEmpregador }
         *     
         */
        public void setIdeEmpregador(TEmpregador value) {
            this.ideEmpregador = value;
        }

        /**
         * Gets the value of the infoRubrica property.
         * 
         * @return
         *     possible object is
         *     {@link ESocial.EvtTabRubrica.InfoRubrica }
         *     
         */
        public ESocial.EvtTabRubrica.InfoRubrica getInfoRubrica() {
            return infoRubrica;
        }

        /**
         * Sets the value of the infoRubrica property.
         * 
         * @param value
         *     allowed object is
         *     {@link ESocial.EvtTabRubrica.InfoRubrica }
         *     
         */
        public void setInfoRubrica(ESocial.EvtTabRubrica.InfoRubrica value) {
            this.infoRubrica = value;
        }

        /**
         * Gets the value of the id property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getId() {
            return id;
        }

        /**
         * Sets the value of the id property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setId(String value) {
            this.id = value;
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
         *         &lt;choice>
         *           &lt;element name="inclusao">
         *             &lt;complexType>
         *               &lt;complexContent>
         *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                   &lt;sequence>
         *                     &lt;element name="ideRubrica" type="{http://www.esocial.gov.br/schema/evt/evtTabRubrica/v02_04_02}TIdeRubrica"/>
         *                     &lt;element name="dadosRubrica" type="{http://www.esocial.gov.br/schema/evt/evtTabRubrica/v02_04_02}TDadosRubrica"/>
         *                   &lt;/sequence>
         *                 &lt;/restriction>
         *               &lt;/complexContent>
         *             &lt;/complexType>
         *           &lt;/element>
         *           &lt;element name="alteracao">
         *             &lt;complexType>
         *               &lt;complexContent>
         *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                   &lt;sequence>
         *                     &lt;element name="ideRubrica" type="{http://www.esocial.gov.br/schema/evt/evtTabRubrica/v02_04_02}TIdeRubrica"/>
         *                     &lt;element name="dadosRubrica" type="{http://www.esocial.gov.br/schema/evt/evtTabRubrica/v02_04_02}TDadosRubrica"/>
         *                     &lt;element name="novaValidade" type="{http://www.esocial.gov.br/schema/evt/evtTabRubrica/v02_04_02}TPeriodoValidade" minOccurs="0"/>
         *                   &lt;/sequence>
         *                 &lt;/restriction>
         *               &lt;/complexContent>
         *             &lt;/complexType>
         *           &lt;/element>
         *           &lt;element name="exclusao">
         *             &lt;complexType>
         *               &lt;complexContent>
         *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                   &lt;sequence>
         *                     &lt;element name="ideRubrica" type="{http://www.esocial.gov.br/schema/evt/evtTabRubrica/v02_04_02}TIdeRubrica"/>
         *                   &lt;/sequence>
         *                 &lt;/restriction>
         *               &lt;/complexContent>
         *             &lt;/complexType>
         *           &lt;/element>
         *         &lt;/choice>
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
            "inclusao",
            "alteracao",
            "exclusao"
        })
        public static class InfoRubrica {

            @XmlElement(namespace = "http://www.esocial.gov.br/schema/evt/evtTabRubrica/v02_04_02")
            protected ESocial.EvtTabRubrica.InfoRubrica.Inclusao inclusao;
            @XmlElement(namespace = "http://www.esocial.gov.br/schema/evt/evtTabRubrica/v02_04_02")
            protected ESocial.EvtTabRubrica.InfoRubrica.Alteracao alteracao;
            @XmlElement(namespace = "http://www.esocial.gov.br/schema/evt/evtTabRubrica/v02_04_02")
            protected ESocial.EvtTabRubrica.InfoRubrica.Exclusao exclusao;

            /**
             * Gets the value of the inclusao property.
             * 
             * @return
             *     possible object is
             *     {@link ESocial.EvtTabRubrica.InfoRubrica.Inclusao }
             *     
             */
            public ESocial.EvtTabRubrica.InfoRubrica.Inclusao getInclusao() {
                return inclusao;
            }

            /**
             * Sets the value of the inclusao property.
             * 
             * @param value
             *     allowed object is
             *     {@link ESocial.EvtTabRubrica.InfoRubrica.Inclusao }
             *     
             */
            public void setInclusao(ESocial.EvtTabRubrica.InfoRubrica.Inclusao value) {
                this.inclusao = value;
            }

            /**
             * Gets the value of the alteracao property.
             * 
             * @return
             *     possible object is
             *     {@link ESocial.EvtTabRubrica.InfoRubrica.Alteracao }
             *     
             */
            public ESocial.EvtTabRubrica.InfoRubrica.Alteracao getAlteracao() {
                return alteracao;
            }

            /**
             * Sets the value of the alteracao property.
             * 
             * @param value
             *     allowed object is
             *     {@link ESocial.EvtTabRubrica.InfoRubrica.Alteracao }
             *     
             */
            public void setAlteracao(ESocial.EvtTabRubrica.InfoRubrica.Alteracao value) {
                this.alteracao = value;
            }

            /**
             * Gets the value of the exclusao property.
             * 
             * @return
             *     possible object is
             *     {@link ESocial.EvtTabRubrica.InfoRubrica.Exclusao }
             *     
             */
            public ESocial.EvtTabRubrica.InfoRubrica.Exclusao getExclusao() {
                return exclusao;
            }

            /**
             * Sets the value of the exclusao property.
             * 
             * @param value
             *     allowed object is
             *     {@link ESocial.EvtTabRubrica.InfoRubrica.Exclusao }
             *     
             */
            public void setExclusao(ESocial.EvtTabRubrica.InfoRubrica.Exclusao value) {
                this.exclusao = value;
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
             *         &lt;element name="ideRubrica" type="{http://www.esocial.gov.br/schema/evt/evtTabRubrica/v02_04_02}TIdeRubrica"/>
             *         &lt;element name="dadosRubrica" type="{http://www.esocial.gov.br/schema/evt/evtTabRubrica/v02_04_02}TDadosRubrica"/>
             *         &lt;element name="novaValidade" type="{http://www.esocial.gov.br/schema/evt/evtTabRubrica/v02_04_02}TPeriodoValidade" minOccurs="0"/>
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
                "ideRubrica",
                "dadosRubrica",
                "novaValidade"
            })
            public static class Alteracao {

                @XmlElement(namespace = "http://www.esocial.gov.br/schema/evt/evtTabRubrica/v02_04_02", required = true)
                protected TIdeRubrica ideRubrica;
                @XmlElement(namespace = "http://www.esocial.gov.br/schema/evt/evtTabRubrica/v02_04_02", required = true)
                protected TDadosRubrica dadosRubrica;
                @XmlElement(namespace = "http://www.esocial.gov.br/schema/evt/evtTabRubrica/v02_04_02")
                protected TPeriodoValidade novaValidade;

                /**
                 * Gets the value of the ideRubrica property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link TIdeRubrica }
                 *     
                 */
                public TIdeRubrica getIdeRubrica() {
                    return ideRubrica;
                }

                /**
                 * Sets the value of the ideRubrica property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link TIdeRubrica }
                 *     
                 */
                public void setIdeRubrica(TIdeRubrica value) {
                    this.ideRubrica = value;
                }

                /**
                 * Gets the value of the dadosRubrica property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link TDadosRubrica }
                 *     
                 */
                public TDadosRubrica getDadosRubrica() {
                    return dadosRubrica;
                }

                /**
                 * Sets the value of the dadosRubrica property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link TDadosRubrica }
                 *     
                 */
                public void setDadosRubrica(TDadosRubrica value) {
                    this.dadosRubrica = value;
                }

                /**
                 * Gets the value of the novaValidade property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link TPeriodoValidade }
                 *     
                 */
                public TPeriodoValidade getNovaValidade() {
                    return novaValidade;
                }

                /**
                 * Sets the value of the novaValidade property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link TPeriodoValidade }
                 *     
                 */
                public void setNovaValidade(TPeriodoValidade value) {
                    this.novaValidade = value;
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
             *         &lt;element name="ideRubrica" type="{http://www.esocial.gov.br/schema/evt/evtTabRubrica/v02_04_02}TIdeRubrica"/>
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
                "ideRubrica"
            })
            public static class Exclusao {

                @XmlElement(namespace = "http://www.esocial.gov.br/schema/evt/evtTabRubrica/v02_04_02", required = true)
                protected TIdeRubrica ideRubrica;

                /**
                 * Gets the value of the ideRubrica property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link TIdeRubrica }
                 *     
                 */
                public TIdeRubrica getIdeRubrica() {
                    return ideRubrica;
                }

                /**
                 * Sets the value of the ideRubrica property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link TIdeRubrica }
                 *     
                 */
                public void setIdeRubrica(TIdeRubrica value) {
                    this.ideRubrica = value;
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
             *         &lt;element name="ideRubrica" type="{http://www.esocial.gov.br/schema/evt/evtTabRubrica/v02_04_02}TIdeRubrica"/>
             *         &lt;element name="dadosRubrica" type="{http://www.esocial.gov.br/schema/evt/evtTabRubrica/v02_04_02}TDadosRubrica"/>
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
                "ideRubrica",
                "dadosRubrica"
            })
            public static class Inclusao {

                @XmlElement(namespace = "http://www.esocial.gov.br/schema/evt/evtTabRubrica/v02_04_02", required = true)
                protected TIdeRubrica ideRubrica;
                @XmlElement(namespace = "http://www.esocial.gov.br/schema/evt/evtTabRubrica/v02_04_02", required = true)
                protected TDadosRubrica dadosRubrica;

                /**
                 * Gets the value of the ideRubrica property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link TIdeRubrica }
                 *     
                 */
                public TIdeRubrica getIdeRubrica() {
                    return ideRubrica;
                }

                /**
                 * Sets the value of the ideRubrica property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link TIdeRubrica }
                 *     
                 */
                public void setIdeRubrica(TIdeRubrica value) {
                    this.ideRubrica = value;
                }

                /**
                 * Gets the value of the dadosRubrica property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link TDadosRubrica }
                 *     
                 */
                public TDadosRubrica getDadosRubrica() {
                    return dadosRubrica;
                }

                /**
                 * Sets the value of the dadosRubrica property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link TDadosRubrica }
                 *     
                 */
                public void setDadosRubrica(TDadosRubrica value) {
                    this.dadosRubrica = value;
                }

            }

        }

    }

}
