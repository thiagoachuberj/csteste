//
// Este arquivo foi gerado pela Arquitetura JavaTM para Implementação de Referência (JAXB) de Bind XML, v2.2.8-b130911.1802 
// Consulte <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas as modificações neste arquivo serão perdidas após a recompilação do esquema de origem. 
// Gerado em: 2018.02.05 às 08:50:21 PM BRST 
//


package lote;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Define a identificação do transmissor.
 * 
 * <p>Classe Java de TIdeTransmissor complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="TIdeTransmissor">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="tpInscricao" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="nrInscricao" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TIdeTransmissor", propOrder = {
    "tpInscricao",
    "nrInscricao"
})
public class TIdeTransmissor {

    protected int tpInscricao;
    @XmlElement(required = true)
    protected String nrInscricao;

    /**
     * Obtém o valor da propriedade tpInscricao.
     * 
     */
    public int getTpInscricao() {
        return tpInscricao;
    }

    /**
     * Define o valor da propriedade tpInscricao.
     * 
     */
    public void setTpInscricao(int value) {
        this.tpInscricao = value;
    }

    /**
     * Obtém o valor da propriedade nrInscricao.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNrInscricao() {
        return nrInscricao;
    }

    /**
     * Define o valor da propriedade nrInscricao.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNrInscricao(String value) {
        this.nrInscricao = value;
    }

}
