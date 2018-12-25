package com.yonyou.iuap.project.express;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yonyou.itf.mdm07.sharing.IMdSharingCenterService;
import com.yonyou.itf.mdm07.sharing.IMdSharingCenterServicePortType;
import com.yonyou.iuap.project.express.vo.WsfSite;
import com.yonyou.iuap.project.json.SiteFieldNamingStrategy;
import com.yonyou.iuap.project.util.HttpHelper;
import com.yonyou.iuap.project.util.MD5Util;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.NameValuePair;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 网上飞接口查询处理类
 * Created by XiongYi on 2018/12/24.
 */
public class WsfSiteServicce {

    public void excute(){

        //数据格式类型_JSON或XML
        String format = "JSON";

        //签名密钥
        String key = "74F8A0743B18F6CB";

        //接口网关URL
        String url = "http://jk.fly-e.cn:22220/wsf-ifd-platform/site/siteAction!getSite.action";

        String charset = "UTF-8";

        Map<String,String> request=new HashMap<>();

        request.put("province","");
        request.put("city","");
        request.put("area","");
        request.put("key","");

        Gson gson= new GsonBuilder().enableComplexMapKeySerialization().setFieldNamingStrategy(new SiteFieldNamingStrategy()).create();
        Gson gson2=new Gson();


        //生成请求报文
        String params = gson.toJson(request);

        //生成签名摘要
        String digest = null;

        try {
            digest = new String(Base64.encodeBase64(MD5Util.code32((params + key), charset).getBytes(charset)));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //参数赋值
        String identify = "NC";
        NameValuePair agrs[] = {
                new NameValuePair("params", params),
                new NameValuePair("digest", digest),
                new NameValuePair("format", format),
                new NameValuePair("identify", identify),
        };
        //查询结果_请参考返回结果说明
        String result = HttpHelper.HttpPost("query", url, agrs, charset);

        Map<String,Object> resultMap=gson.fromJson(result,HashMap.class);

        if(resultMap.get("result").equals("true")&&resultMap.get("resultCode").equals("1000")){
            List<Map<String,String>> sites= (List<Map<String,String>>) resultMap.get("sites");


            if(!sites.isEmpty() && sites.size()>0){
                for(Map<String,String> map : sites){
                    WsfSite site=gson.fromJson(gson.toJson(map),WsfSite.class);

                    site.setId(site.getCode());

                    if(site.getEnableLogo().equals("1")){
                        IMdSharingCenterService client = new IMdSharingCenterService();
                        IMdSharingCenterServicePortType createsv;

                        createsv = client.getIMdSharingCenterServiceSOAP11PortHttp();

                        createsv.insertMd("XJKY","mdm_dot","["+gson2.toJson(site)+"]");
                    }
                }
            }
        }
    }
    public static void main(String[] args){
        WsfSiteServicce service=new WsfSiteServicce();
        service.excute();
    }
}
