
package com.yonyou.itf.mdm07.sharing;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="string" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="string1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="string2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="boolean" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="string4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "string",
    "string1",
    "string2",
    "_boolean",
    "string4"
})
@XmlRootElement(name = "queryMdByCondition")
public class QueryMdByCondition {

    @XmlElementRef(name = "string", namespace = "http://sharing.mdm07.itf.yonyou.com/IMdSharingCenterService", type = JAXBElement.class)
    protected JAXBElement<String> string;
    @XmlElementRef(name = "string1", namespace = "http://sharing.mdm07.itf.yonyou.com/IMdSharingCenterService", type = JAXBElement.class)
    protected JAXBElement<String> string1;
    @XmlElementRef(name = "string2", namespace = "http://sharing.mdm07.itf.yonyou.com/IMdSharingCenterService", type = JAXBElement.class)
    protected JAXBElement<String> string2;
    @XmlElementRef(name = "boolean", namespace = "http://sharing.mdm07.itf.yonyou.com/IMdSharingCenterService", type = JAXBElement.class)
    protected JAXBElement<Boolean> _boolean;
    @XmlElementRef(name = "string4", namespace = "http://sharing.mdm07.itf.yonyou.com/IMdSharingCenterService", type = JAXBElement.class)
    protected JAXBElement<String> string4;

    /**
     * Gets the value of the string property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getString() {
        return string;
    }

    /**
     * Sets the value of the string property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setString(JAXBElement<String> value) {
        this.string = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the string1 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getString1() {
        return string1;
    }

    /**
     * Sets the value of the string1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setString1(JAXBElement<String> value) {
        this.string1 = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the string2 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getString2() {
        return string2;
    }

    /**
     * Sets the value of the string2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setString2(JAXBElement<String> value) {
        this.string2 = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the boolean property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public JAXBElement<Boolean> getBoolean() {
        return _boolean;
    }

    /**
     * Sets the value of the boolean property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public void setBoolean(JAXBElement<Boolean> value) {
        this._boolean = ((JAXBElement<Boolean> ) value);
    }

    /**
     * Gets the value of the string4 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getString4() {
        return string4;
    }

    /**
     * Sets the value of the string4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setString4(JAXBElement<String> value) {
        this.string4 = ((JAXBElement<String> ) value);
    }

}
