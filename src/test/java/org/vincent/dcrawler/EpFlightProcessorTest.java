package org.vincent.dcrawler;


import org.junit.Test;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EpFlightProcessorTest {

    @Test
    public void testProcess() throws Exception {

        ExecutorService es = Executors.newFixedThreadPool(10);
        IFlightProcessor flightProcessor = new EpFlightProcessor(SiteConfig.EP_HK);
        for ( int i = 0; i < 100; i++) {
            es.submit(()->{
                Random r = new Random();
                int d = 14+r.nextInt(8);
                System.out.println(flightProcessor.process("2016/02/"+d,"HKG","TYO"));
            });
        }
        es.shutdown();
    }
}