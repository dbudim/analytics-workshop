package com.dbudim.analytics;

import java.lang.annotation.*;

/**
 * Created by dbudim on 03.07.2021
 */

@Inherited
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Owner {

    String name();
}
