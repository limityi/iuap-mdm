
package com.yonyou.itf.mdm07.sharing;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.2.10
 * Fri Apr 20 11:40:56 CST 2018
 * Generated source version: 2.2.10
 * 
 */

public final class IMdSharingCenterServicePortType_IMdSharingCenterServiceSOAP11PortHttp_Client {

    private static final QName SERVICE_NAME = new QName("http://sharing.mdm07.itf.yonyou.com/IMdSharingCenterService", "IMdSharingCenterService");

    private IMdSharingCenterServicePortType_IMdSharingCenterServiceSOAP11PortHttp_Client() {
    }

    public static void main(String args[]) throws Exception {
        URL wsdlURL = IMdSharingCenterService.WSDL_LOCATION;
        if (args.length > 0) { 
            File wsdlFile = new File(args[0]);
            try {
                if (wsdlFile.exists()) {
                    wsdlURL = wsdlFile.toURI().toURL();
                } else {
                    wsdlURL = new URL(args[0]);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
      
        IMdSharingCenterService ss = new IMdSharingCenterService(wsdlURL, SERVICE_NAME);
        IMdSharingCenterServicePortType port = ss.getIMdSharingCenterServiceSOAP11PortHttp();  
        
        {
        System.out.println("Invoking subscribeMd...");
        String _subscribeMd_string = "";
        String _subscribeMd_string1 = "";
        Boolean _subscribeMd__boolean = null;
        String _subscribeMd__return = port.subscribeMd(_subscribeMd_string, _subscribeMd_string1, _subscribeMd__boolean);
        System.out.println("subscribeMd.result=" + _subscribeMd__return);


        }
        {
        System.out.println("Invoking queryXmlListMdByType...");
        String _queryXmlListMdByType_string = "";
        String _queryXmlListMdByType_string1 = "";
        Boolean _queryXmlListMdByType__boolean = null;
        com.yonyou.itf.mdm07.sharing.MdmRetVO _queryXmlListMdByType__return = port.queryXmlListMdByType(_queryXmlListMdByType_string, _queryXmlListMdByType_string1, _queryXmlListMdByType__boolean);
        System.out.println("queryXmlListMdByType.result=" + _queryXmlListMdByType__return);


        }
        {
        System.out.println("Invoking queryXmlMdById...");
        String _queryXmlMdById_string = "";
        String _queryXmlMdById_string1 = "";
        String _queryXmlMdById_string2 = "";
        Boolean _queryXmlMdById__boolean = null;
        com.yonyou.itf.mdm07.sharing.MdmRetVO _queryXmlMdById__return = port.queryXmlMdById(_queryXmlMdById_string, _queryXmlMdById_string1, _queryXmlMdById_string2, _queryXmlMdById__boolean);
        System.out.println("queryXmlMdById.result=" + _queryXmlMdById__return);


        }
        {
        System.out.println("Invoking queryMdById...");
        String _queryMdById_string = "";
        String _queryMdById_string1 = "";
        String _queryMdById_string2 = "";
        Boolean _queryMdById__boolean = null;
        com.yonyou.itf.mdm07.sharing.MdmRetVO _queryMdById__return = port.queryMdById(_queryMdById_string, _queryMdById_string1, _queryMdById_string2, _queryMdById__boolean);
        System.out.println("queryMdById.result=" + _queryMdById__return);


        }
        {
        System.out.println("Invoking deleteMdById...");
        String _deleteMdById_string = "";
        String _deleteMdById_string1 = "";
        String _deleteMdById_string2 = "";
        com.yonyou.itf.mdm07.sharing.MdmRetVO _deleteMdById__return = port.deleteMdById(_deleteMdById_string, _deleteMdById_string1, _deleteMdById_string2);
        System.out.println("deleteMdById.result=" + _deleteMdById__return);


        }
        {
        System.out.println("Invoking queryIdsSingle...");
        String _queryIdsSingle_string = "";
        String _queryIdsSingle_string1 = "";
        java.util.List<String> _queryIdsSingle_list = null;
        String _queryIdsSingle_string3 = "";
        java.util.List<com.yonyou.itf.mdm07.sharing.QueryIdsSingleResponse.Return> _queryIdsSingle__return = port.queryIdsSingle(_queryIdsSingle_string, _queryIdsSingle_string1, _queryIdsSingle_list, _queryIdsSingle_string3);
        System.out.println("queryIdsSingle.result=" + _queryIdsSingle__return);


        }
        {
        System.out.println("Invoking updateMd...");
        String _updateMd_string = "";
        String _updateMd_string1 = "";
        String _updateMd_string2 = "";
        com.yonyou.itf.mdm07.sharing.MdmRetVO _updateMd__return = port.updateMd(_updateMd_string, _updateMd_string1, _updateMd_string2);
        System.out.println("updateMd.result=" + _updateMd__return);


        }
        {
        System.out.println("Invoking queryMdByCode...");
        String _queryMdByCode_string = "";
        String _queryMdByCode_string1 = "";
        String _queryMdByCode_string2 = "";
        Boolean _queryMdByCode__boolean = null;
        com.yonyou.itf.mdm07.sharing.MdmRetVO _queryMdByCode__return = port.queryMdByCode(_queryMdByCode_string, _queryMdByCode_string1, _queryMdByCode_string2, _queryMdByCode__boolean);
        System.out.println("queryMdByCode.result=" + _queryMdByCode__return);


        }
        {
        System.out.println("Invoking insertMd...");
        String _insertMd_string = "";
        String _insertMd_string1 = "";
        String _insertMd_string2 = "";
        com.yonyou.itf.mdm07.sharing.MdmRetVO _insertMd__return = port.insertMd(_insertMd_string, _insertMd_string1, _insertMd_string2);
        System.out.println("insertMd.result=" + _insertMd__return);


        }
        {
        System.out.println("Invoking queryMdByCondition...");
        String _queryMdByCondition_string = "";
        String _queryMdByCondition_string1 = "";
        String _queryMdByCondition_string2 = "";
        Boolean _queryMdByCondition__boolean = null;
        String _queryMdByCondition_string4 = "";
        com.yonyou.itf.mdm07.sharing.MdmRetVO _queryMdByCondition__return = port.queryMdByCondition(_queryMdByCondition_string, _queryMdByCondition_string1, _queryMdByCondition_string2, _queryMdByCondition__boolean, _queryMdByCondition_string4);
        System.out.println("queryMdByCondition.result=" + _queryMdByCondition__return);


        }
        {
        System.out.println("Invoking queryMdByName...");
        String _queryMdByName_string = "";
        String _queryMdByName_string1 = "";
        String _queryMdByName_string2 = "";
        Boolean _queryMdByName__boolean = null;
        com.yonyou.itf.mdm07.sharing.MdmRetVO _queryMdByName__return = port.queryMdByName(_queryMdByName_string, _queryMdByName_string1, _queryMdByName_string2, _queryMdByName__boolean);
        System.out.println("queryMdByName.result=" + _queryMdByName__return);


        }
        {
        System.out.println("Invoking changeBackgroundTaskStatus...");
        String _changeBackgroundTaskStatus_string = "";
        Boolean _changeBackgroundTaskStatus__boolean = null;
        String _changeBackgroundTaskStatus__return = port.changeBackgroundTaskStatus(_changeBackgroundTaskStatus_string, _changeBackgroundTaskStatus__boolean);
        System.out.println("changeBackgroundTaskStatus.result=" + _changeBackgroundTaskStatus__return);


        }
        {
        System.out.println("Invoking queryListMdByType...");
        String _queryListMdByType_string = "";
        String _queryListMdByType_string1 = "";
        Boolean _queryListMdByType__boolean = null;
        com.yonyou.itf.mdm07.sharing.MdmRetVO _queryListMdByType__return = port.queryListMdByType(_queryListMdByType_string, _queryListMdByType_string1, _queryListMdByType__boolean);
        System.out.println("queryListMdByType.result=" + _queryListMdByType__return);


        }
        {
        System.out.println("Invoking queryNewMdAfter...");
        String _queryNewMdAfter_string = "";
        String _queryNewMdAfter_string1 = "";
        String _queryNewMdAfter_string2 = "";
        Boolean _queryNewMdAfter__boolean = null;
        com.yonyou.itf.mdm07.sharing.MdmRetVO _queryNewMdAfter__return = port.queryNewMdAfter(_queryNewMdAfter_string, _queryNewMdAfter_string1, _queryNewMdAfter_string2, _queryNewMdAfter__boolean);
        System.out.println("queryNewMdAfter.result=" + _queryNewMdAfter__return);


        }
        {
        System.out.println("Invoking queryMDMCodeByIdsMulti...");
        String _queryMDMCodeByIdsMulti_string = "";
        java.util.List<SourceIDBean> _queryMDMCodeByIdsMulti_list = null;
        java.util.List<IDMapping> _queryMDMCodeByIdsMulti__return = port.queryMDMCodeByIdsMulti(_queryMDMCodeByIdsMulti_string, _queryMDMCodeByIdsMulti_list);
        System.out.println("queryMDMCodeByIdsMulti.result=" + _queryMDMCodeByIdsMulti__return);


        }
        {
        System.out.println("Invoking deleteListMdByIds...");
        String _deleteListMdByIds_string = "";
        String _deleteListMdByIds_string1 = "";
        java.util.List<String> _deleteListMdByIds_list = null;
        com.yonyou.itf.mdm07.sharing.MdmRetVO _deleteListMdByIds__return = port.deleteListMdByIds(_deleteListMdByIds_string, _deleteListMdByIds_string1, _deleteListMdByIds_list);
        System.out.println("deleteListMdByIds.result=" + _deleteListMdByIds__return);


        }
        {
        System.out.println("Invoking queryIdsMulti...");
        String _queryIdsMulti_string = "";
        java.util.List<SourceIDBean> _queryIdsMulti_list = null;
        String _queryIdsMulti_string2 = "";
        java.util.List<IDMapping> _queryIdsMulti__return = port.queryIdsMulti(_queryIdsMulti_string, _queryIdsMulti_list, _queryIdsMulti_string2);
        System.out.println("queryIdsMulti.result=" + _queryIdsMulti__return);


        }
        {
        System.out.println("Invoking queryListMdByIds...");
        String _queryListMdByIds_string = "";
        String _queryListMdByIds_string1 = "";
        java.util.List<String> _queryListMdByIds_list = null;
        Boolean _queryListMdByIds__boolean = null;
        com.yonyou.itf.mdm07.sharing.MdmRetVO _queryListMdByIds__return = port.queryListMdByIds(_queryListMdByIds_string, _queryListMdByIds_string1, _queryListMdByIds_list, _queryListMdByIds__boolean);
        System.out.println("queryListMdByIds.result=" + _queryListMdByIds__return);


        }
        {
        System.out.println("Invoking queryMdmcodeByGdCode...");
        String _queryMdmcodeByGdCode_string = "";
        String _queryMdmcodeByGdCode_string1 = "";
        String _queryMdmcodeByGdCode_string2 = "";
        String _queryMdmcodeByGdCode_string3 = "";
        com.yonyou.itf.mdm07.sharing.MdmRetVO _queryMdmcodeByGdCode__return = port.queryMdmcodeByGdCode(_queryMdmcodeByGdCode_string, _queryMdmcodeByGdCode_string1, _queryMdmcodeByGdCode_string2, _queryMdmcodeByGdCode_string3);
        System.out.println("queryMdmcodeByGdCode.result=" + _queryMdmcodeByGdCode__return);


        }

        System.exit(0);
    }

}