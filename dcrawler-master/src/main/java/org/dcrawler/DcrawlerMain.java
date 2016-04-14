package org.dcrawler;

import org.glassfish.grizzly.http.server.HttpServer;

/**
 * The dcrawler main class, which works like a never ending automatic machine that can accept new tasks
 *
 */
public final class DcrawlerMain
{
    private static final HttpServer server = HttpServer.createSimpleServer();
    public static void main( String[] args )
    {

        try{
            server.start();
            System.out.println("Press any key to continue");
            System.in.read();
        }catch (Exception e){
            System.err.println(e);
        }
    }
}
