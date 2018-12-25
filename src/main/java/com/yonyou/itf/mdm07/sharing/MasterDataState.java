
package com.yonyou.itf.mdm07.sharing;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MasterDataState.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="MasterDataState">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="NONE"/>
 *     &lt;enumeration value="UNCHANGE"/>
 *     &lt;enumeration value="SEAL"/>
 *     &lt;enumeration value="UPDATED"/>
 *     &lt;enumeration value="DELETED"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "MasterDataState", namespace = "http://sharing.mdm07.itf.yonyou.com/MasterDataState")
@XmlEnum
public enum MasterDataState {

    NONE,
    UNCHANGE,
    SEAL,
    UPDATED,
    DELETED;

    public String value() {
        return name();
    }

    public static MasterDataState fromValue(String v) {
        return valueOf(v);
    }

}
