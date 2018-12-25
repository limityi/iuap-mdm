package com.yonyou.iuap.project.json;

import com.google.gson.FieldNamingStrategy;

import java.lang.reflect.Field;

/**
 * gson 转换网上飞网点
 * Created by XiongYi on 2018/12/24.
 */
public class SiteFieldNamingStrategy implements FieldNamingStrategy {

    @Override
    public String translateName(Field field) {
        if(field.getName().equals("code")){
            return "siteCode";
        }else if(field.getName().equals("name")){
            return "siteName";
        }else if(field.getName().equals("charge_person")){
            return "head";
        }else if(field.getName().equals("phone")){
            return "siteTel";
        }else if(field.getName().equals("county")){
            return "area";
        }else if(field.getName().equals("sjt_code")){
            return "standardCode";
        }else if(field.getName().equals("sjt_name")){
            return "standardName";
        }
        return field.getName();
    }

}
