package org.vincent.dcrawler;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

public class EpFlightProcessor implements IFlightProcessor{

    private final String LEG_TMPL = "from:%s,to:%s,departure:%sTANYT";

    private final SiteConfig siteConfig;

    public EpFlightProcessor(SiteConfig siteConfig) {
        this.siteConfig = siteConfig;
    }
    public String process(String date, String from, String to) {
        HttpClient hClient = HttpClients.createDefault();
        try  {

            //expedia.fr :departure:14/02/2016TANYT
            //https://www.ep.com.hk/Flights-Search?trip=oneway&leg1=from:HKG,to:TYO,departure:2016/02/14TANYT&passengers=children:0,adults:1,seniors:0,infantinlap:Y&mode=search
            final URI expediaUri = new URIBuilder().setScheme("https")
                    .setHost(siteConfig.getHost())
                    .setPath("/Flights-Search")
                    .setParameter("trip", "oneway")
                    .setParameter("leg1", String.format(LEG_TMPL, from, to, date))
                    .setParameter("passengers", "children:0,adults:1,seniors:0,infantinlap:Y")
                    .setParameter("mode", "search")
                    .setParameter("langid", "2057") //for english
                    .build();
            HttpGet expediaGet = new HttpGet(expediaUri);
            expediaGet.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.130 Safari/537.36");

            HttpResponse expediaRsp = hClient.execute(expediaGet);
            String expediaBody = EntityUtils.toString(expediaRsp.getEntity());
            Document doc = Jsoup.parse(expediaBody);
            String cVal = doc.getElementById("originalContinuationId").text();
            URI epFlightUri = new URIBuilder()
                    .setScheme("https")
                    .setHost(siteConfig.getHost())
                    .setPath("/Flight-Search-Outbound")
                    .setParameter("c", cVal)
                    .setParameter("_", String.valueOf(new Date().getTime()))
                    .build();
            HttpGet epFlightPost = new HttpGet(epFlightUri);
            epFlightPost.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
            epFlightPost.setHeader("Accept-Language", "en-US,en;q=0.8,zh-CN;q=0.6,zh;q=0.4");
            epFlightPost.setHeader("Referer", expediaGet.getURI().toASCIIString());
            epFlightPost.setHeader("X-Requested-With", "XMLHttpRequest");
            epFlightPost.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.130 Safari/537.36");

            HttpResponse epResult = hClient.execute(epFlightPost);
            String epJson = EntityUtils.toString(epResult.getEntity());
            return epJson;
        }catch (URISyntaxException | IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
