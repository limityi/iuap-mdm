
package com.yonyou.itf.mdm07.sharing;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SourceIDBean complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SourceIDBean">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="gdCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="srcIds" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SourceIDBean", namespace = "http://sharing.mdm07.itf.yonyou.com/SourceIDBean", propOrder = {
    "gdCode",
    "srcIds"
})
public class SourceIDBean {

    @XmlElementRef(name = "gdCode", namespace = "http://sharing.mdm07.itf.yonyou.com/SourceIDBean", type = JAXBElement.class)
    protected JAXBElement<String> gdCode;
    @XmlElement(nillable = true)
    protected List<String> srcIds;

    /**
     * Gets the value of the gdCode property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getGdCode() {
        return gdCode;
    }

    /**
     * Sets the value of the gdCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setGdCode(JAXBElement<String> value) {
        this.gdCode = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the srcIds property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the srcIds property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSrcIds().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getSrcIds() {
        if (srcIds == null) {
            srcIds = new ArrayList<String>();
        }
        return this.srcIds;
    }

}
