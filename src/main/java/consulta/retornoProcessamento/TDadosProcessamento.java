//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-558 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.04.24 at 11:51:23 PM BRT 
//


package consulta.retornoProcessamento;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * Define os dados de processamento de um lote de eventos.
 * 
 * <p>Java class for TDadosProcessamento complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TDadosProcessamento">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="versaoAplicativoProcessamentoLote" minOccurs="0">
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
@XmlType(name = "TDadosProcessamento", propOrder = {
    "versaoAplicativoProcessamentoLote"
})
public class TDadosProcessamento {

    protected String versaoAplicativoProcessamentoLote;

    /**
     * Gets the value of the versaoAplicativoProcessamentoLote property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersaoAplicativoProcessamentoLote() {
        return versaoAplicativoProcessamentoLote;
    }

    /**
     * Sets the value of the versaoAplicativoProcessamentoLote property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersaoAplicativoProcessamentoLote(String value) {
        this.versaoAplicativoProcessamentoLote = value;
    }

}