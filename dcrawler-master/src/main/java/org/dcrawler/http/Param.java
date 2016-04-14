package org.dcrawler.http;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Created by vincent on 4/14/16.
 */
@Target(ElementType.PARAMETER)
public @interface Param {
    String value();
}
