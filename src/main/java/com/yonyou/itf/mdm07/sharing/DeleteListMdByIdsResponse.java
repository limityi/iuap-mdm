
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
 *         &lt;element name="return" type="{http://sharing.mdm07.itf.yonyou.com/MdmRetVO}MdmRetVO" minOccurs="0"/>
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
    "_return"
})
@XmlRootElement(name = "deleteListMdByIdsResponse")
public class DeleteListMdByIdsResponse {

    @XmlElementRef(name = "return", namespace = "http://sharing.mdm07.itf.yonyou.com/IMdSharingCenterService", type = JAXBElement.class)
    protected JAXBElement<MdmRetVO> _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link MdmRetVO }{@code >}
     *     
     */
    public JAXBElement<MdmRetVO> getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link MdmRetVO }{@code >}
     *     
     */
    public void setReturn(JAXBElement<MdmRetVO> value) {
        this._return = ((JAXBElement<MdmRetVO> ) value);
    }

}
