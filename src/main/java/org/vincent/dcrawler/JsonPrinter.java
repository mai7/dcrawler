package org.vincent.dcrawler;

import com.fasterxml.jackson.jr.ob.JSON;

import java.io.IOException;


public class JsonPrinter implements IDataRepository {
    @Override
    public void save(String s) {

        Object oJson = null;
        try {
            oJson = JSON.std.anyFrom(s);
            JSON j = JSON.std.with(JSON.Feature.PRETTY_PRINT_OUTPUT);
            System.out.println(j.asString(oJson));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

    }
}
