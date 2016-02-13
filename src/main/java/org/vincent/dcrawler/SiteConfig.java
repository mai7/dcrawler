package org.vincent.dcrawler;


public class SiteConfig {

    public final static SiteConfig EP_HK = new SiteConfig("www.ep.com.hk", DateConst.YYYYMMDD);
    public final static SiteConfig EP_TW = new SiteConfig("www.ep.com.tw", DateConst.YYYYMMDD);

    public String getHost() {
        return host;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    private final String host;
    private final String dateFormat;


    private SiteConfig(String host, String dateFormat) {
        this.host = host;
        this.dateFormat = dateFormat;
    }

    private static class DateConst {
         static final String YYYYMMDD = "yyyy/mm/dd";
    }

}
