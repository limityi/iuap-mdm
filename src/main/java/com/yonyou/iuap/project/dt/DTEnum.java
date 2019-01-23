package com.yonyou.iuap.project.dt;

public class DTEnum {

    public static enum MdmSys implements DT {
        MDM("MDM", "MDM装载"),
        RC("RC", "润辰中心平台"),
        NC65("NC65", "NC系统"),
        ZHKY("ZHKY", "智慧客运站务系统"),
        NYT("NYT", "南粤通站务系统"),
        XJKY("MDM_ORG", "小件快运系统");


        private String id;
        private String dtName;

        private MdmSys(String id, String dtName) {
            this.id = id;
            this.dtName = dtName;
        }

        @Override
        public String getId() {
            return id;
        }

        @Override
        public String getName() {
            return name();
        }

        @Override
        public String getDtName() {
            return dtName;
        }

        public static DT valueOfId(String id) {
            if (id == null)
                return null;
            switch (id) {
                case "MDM":
                    return MDM;
                case "RC":
                    return RC;
                case "NC65":
                    return NC65;
                case "ZHKY":
                    return ZHKY;
                case "NYT":
                    return NYT;
                case "XJKY":
                    return XJKY;
                default:
                    return null;
            }
        }
    }

    public static enum UserMenus implements DT {
        station("md_mdm_station", "站场"),
        merchants("md_mdm_merchants", "客商"),
        stores("md_mdm_strores", "便利店"),
        serviceArea("md_mdm_service_area", "服务区"),
        settlementmethod("md_mdm_settlementmethod", "结算方式"),
        org("md_mdm_org", "组织部门"),
        dot("md_mdm_dot", "网上飞网点"),
        costitem("md_mdm_costitem", "费用项目"),
        ticketsales("md_mdm_ticketsales", "客票代售网点"),
        lines("md_mdm_line", "客运线路"),
        busline("md_mdm_busline", "公交线路"),
        lisence("md_mdm_lisence", "线路牌"),
        bus("md_mdm_bus", "车辆"),
        person("md_mdm_person", "人员"),
        fleet("md_mdm_fleet", "车队");

        private String id;
        private String dtName;

        private UserMenus(String id, String dtName) {
            this.id = id;
            this.dtName = dtName;
        }

        @Override
        public String getId() {
            return id;
        }

        @Override
        public String getName() {
            return name();
        }

        @Override
        public String getDtName() {
            return dtName;
        }

        public static DT valueOfId(String id) {
            if (id == null)
                return null;
            switch (id) {
                case "md_mdm_station":
                    return station;
                case "md_mdm_merchants":
                    return merchants;
                case "md_mdm_strores":
                    return stores;
                case "md_mdm_service_area":
                    return serviceArea;
                case "md_mdm_settlementmethod":
                    return settlementmethod;
                case "md_mdm_org":
                    return org;
                case "md_mdm_dot":
                    return dot;
                case "md_mdm_costitem":
                    return costitem;
                case "md_mdm_ticketsales":
                    return ticketsales;
                case "md_mdm_line":
                    return lines;
                case "md_mdm_busline":
                    return busline;
                case "md_mdm_lisence":
                    return lisence;
                case "md_mdm_bus":
                    return bus;
                case "md_mdm_person":
                    return person;
                case "md_mdm_fleet":
                    return fleet;
                default:
                    return null;
            }
        }
    }

    public static enum ZhkyMenus implements DT {
        zhkystation1("md_zhky_station1", "智慧客运站场"),
        zhkybus("md_zhky_bus", "智慧客运车辆"),
        zhkyline("md_zhky_line", "智慧客运线路");

        private String id;
        private String dtName;

        private ZhkyMenus(String id, String dtName) {
            this.id = id;
            this.dtName = dtName;
        }

        @Override
        public String getId() {
            return id;
        }

        @Override
        public String getName() {
            return name();
        }

        @Override
        public String getDtName() {
            return dtName;
        }

        public static DT valueOfId(String id) {
            if (id == null)
                return null;
            switch (id) {
                case "md_zhky_station1":
                    return zhkystation1;
                case "md_zhky_bus":
                    return zhkybus;
                case "md_zhky_line":
                    return zhkyline;
                default:
                    return null;
            }
        }
    }

    public static enum NytMenus implements DT {
        nytstation("md_nyt_station", "南粤通站场"),
        nytbus("md_nyt_bus", "南粤通车辆"),
        nytline("md_nyt_line", "南粤通客运线路");

        private String id;
        private String dtName;

        private NytMenus(String id, String dtName) {
            this.id = id;
            this.dtName = dtName;
        }

        @Override
        public String getId() {
            return id;
        }

        @Override
        public String getName() {
            return name();
        }

        @Override
        public String getDtName() {
            return dtName;
        }

        public static DT valueOfId(String id) {
            if (id == null)
                return null;
            switch (id) {
                case "md_nyt_station":
                    return nytstation;
                case "md_nyt_bus":
                    return nytbus;
                case "md_nyt_line":
                    return nytline;
                default:
                    return null;
            }
        }
    }

    public static enum DotStationMenus implements DT {
        dotstation("md_dot_station", "网上飞-站场");

        private String id;
        private String dtName;

        private DotStationMenus(String id, String dtName) {
            this.id = id;
            this.dtName = dtName;
        }

        @Override
        public String getId() {
            return id;
        }

        @Override
        public String getName() {
            return name();
        }

        @Override
        public String getDtName() {
            return dtName;
        }

        public static DT valueOfId(String id) {
            if (id == null)
                return null;
            switch (id) {
                case "md_dot_station":
                    return dotstation;
                default:
                    return null;
            }
        }
    }
}