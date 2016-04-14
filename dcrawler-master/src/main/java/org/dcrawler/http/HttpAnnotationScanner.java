package org.dcrawler.http;

import org.glassfish.grizzly.http.server.HttpServer;

/**
 * Created by vincent on 4/14/16.
 */
public class HttpAnnotationScanner {

    private HttpServer server = null;

    public HttpAnnotationScanner(HttpServer server) {
        this.server = server;
    }

    public void scan(String... classPaths){
        //TODO scan all class by given class paths then register it to the server.

        server.getServerConfiguration().addHttpHandler(null);
    }
}
