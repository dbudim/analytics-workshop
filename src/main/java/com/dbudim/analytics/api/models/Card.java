package com.dbudim.analytics.api.models;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Created by dbudim on 10.07.2021
 */

public class Card {

    public String id;
    public String idBoard;
    public String idList;
    public String name;
    public String desc;
    public String email;
    public String shortUrl;
    public String url;
    public Boolean closed;

    public Card(String idList, String name) {
        this.idList = idList;
        this.name = name;
    }

    public Card(String idList, String name, String desc) {
        this.idList = idList;
        this.name = name;
        this.desc = desc;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
