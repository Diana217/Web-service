
package com.baeldung.soap.ws.client.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="AddColumnResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "addColumnResult"
})
@XmlRootElement(name = "AddColumnResponse")
public class AddColumnResponse {

    @XmlElement(name = "AddColumnResult")
    protected String addColumnResult;

    /**
     * Gets the value of the addColumnResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddColumnResult() {
        return addColumnResult;
    }

    /**
     * Sets the value of the addColumnResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddColumnResult(String value) {
        this.addColumnResult = value;
    }

}
