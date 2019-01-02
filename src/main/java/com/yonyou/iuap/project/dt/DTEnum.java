package com.yonyou.iuap.project.dt;

public class DTEnum {

    public static enum MdmTable implements DT {
        MDM_STATION("MDM_STATION", "站场"),
        MDM_BUS("MDM_BUS", "车辆"),
        MDM_LINE("MDM_LINE", "线路"),
        MDM_MERCHANTS("MDM_MERCHANTS", "客商");

        private String id;
        private String dtName;

        private MdmTable(String id, String dtName) {
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
                case "MDM_STATION":
                    return MDM_STATION;
                case "MDM_BUS":
                    return MDM_BUS;
                case "MDM_LINE":
                    return MDM_LINE;
                case "MDM_MERCHANTS":
                    return MDM_MERCHANTS;
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
        fleet("fleet", "车队");

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
                case "fleet":
                    return fleet;
                default:
                    return null;
            }
        }
    }
}