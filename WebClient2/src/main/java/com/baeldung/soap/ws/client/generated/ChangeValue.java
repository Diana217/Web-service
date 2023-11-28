
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
 *         &lt;element name="newValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="tableId" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="columnId" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="rIndex" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
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
    "newValue",
    "tableId",
    "columnId",
    "rIndex"
})
@XmlRootElement(name = "ChangeValue")
public class ChangeValue {

    protected String newValue;
    protected int tableId;
    protected int columnId;
    protected int rIndex;

    /**
     * Gets the value of the newValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewValue() {
        return newValue;
    }

    /**
     * Sets the value of the newValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewValue(String value) {
        this.newValue = value;
    }

    /**
     * Gets the value of the tableId property.
     * 
     */
    public int getTableId() {
        return tableId;
    }

    /**
     * Sets the value of the tableId property.
     * 
     */
    public void setTableId(int value) {
        this.tableId = value;
    }

    /**
     * Gets the value of the columnId property.
     * 
     */
    public int getColumnId() {
        return columnId;
    }

    /**
     * Sets the value of the columnId property.
     * 
     */
    public void setColumnId(int value) {
        this.columnId = value;
    }

    /**
     * Gets the value of the rIndex property.
     * 
     */
    public int getRIndex() {
        return rIndex;
    }

    /**
     * Sets the value of the rIndex property.
     * 
     */
    public void setRIndex(int value) {
        this.rIndex = value;
    }

}
