
package com.baeldung.soap.ws.client.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="id1" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="id2" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
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
    "id1",
    "id2"
})
@XmlRootElement(name = "GetTablesField")
public class GetTablesField {

    protected int id1;
    protected int id2;

    /**
     * Gets the value of the id1 property.
     * 
     */
    public int getId1() {
        return id1;
    }

    /**
     * Sets the value of the id1 property.
     * 
     */
    public void setId1(int value) {
        this.id1 = value;
    }

    /**
     * Gets the value of the id2 property.
     * 
     */
    public int getId2() {
        return id2;
    }

    /**
     * Sets the value of the id2 property.
     * 
     */
    public void setId2(int value) {
        this.id2 = value;
    }

}
