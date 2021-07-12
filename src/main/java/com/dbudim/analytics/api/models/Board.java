package com.dbudim.analytics.api.models;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Created by dbudim on 10.07.2021
 */

public class Board {

    public String id;
    public String name;
    public String prefs_permissionLevel;
    public Boolean closed;
    public String url;
    public String shortUrl;

    public Board(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

}
