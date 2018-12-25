
/*
 * 
 */

package com.yonyou.itf.mdm07.sharing;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This class was generated by Apache CXF 2.2.10
 * Fri Apr 20 11:40:56 CST 2018
 * Generated source version: 2.2.10
 * 
 */


@WebServiceClient(name = "IMdSharingCenterService", 
                  wsdlLocation = "http://128.14.1.55:809/uapws/service/com.yonyou.itf.mdm07.sharing.IMdSharingCenterService?wsdl",
                  targetNamespace = "http://sharing.mdm07.itf.yonyou.com/IMdSharingCenterService") 
public class IMdSharingCenterService extends Service {

    public final static URL WSDL_LOCATION;
    public final static QName SERVICE = new QName("http://sharing.mdm07.itf.yonyou.com/IMdSharingCenterService", "IMdSharingCenterService");
    public final static QName IMdSharingCenterServiceSOAP11PortHttp = new QName("http://sharing.mdm07.itf.yonyou.com/IMdSharingCenterService", "IMdSharingCenterServiceSOAP11port_http");
    static {
        URL url = null;
        try {
            url = new URL("http://128.14.1.55:809/uapws/service/com.yonyou.itf.mdm07.sharing.IMdSharingCenterService?wsdl");
        } catch (MalformedURLException e) {
            System.err.println("Can not initialize the default wsdl from http://128.14.1.55:809/uapws/service/com.yonyou.itf.mdm07.sharing.IMdSharingCenterService?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public IMdSharingCenterService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public IMdSharingCenterService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public IMdSharingCenterService() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    public IMdSharingCenterService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }
    public IMdSharingCenterService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public IMdSharingCenterService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns IMdSharingCenterServicePortType
     */
    @WebEndpoint(name = "IMdSharingCenterServiceSOAP11port_http")
    public IMdSharingCenterServicePortType getIMdSharingCenterServiceSOAP11PortHttp() {
        return super.getPort(IMdSharingCenterServiceSOAP11PortHttp, IMdSharingCenterServicePortType.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns IMdSharingCenterServicePortType
     */
    @WebEndpoint(name = "IMdSharingCenterServiceSOAP11port_http")
    public IMdSharingCenterServicePortType getIMdSharingCenterServiceSOAP11PortHttp(WebServiceFeature... features) {
        return super.getPort(IMdSharingCenterServiceSOAP11PortHttp, IMdSharingCenterServicePortType.class, features);
    }

}
