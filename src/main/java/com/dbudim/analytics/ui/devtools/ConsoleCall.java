package com.dbudim.analytics.ui.devtools;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Created by dbudim on 16.06.2020
 */

public class ConsoleCall {

    public String method;
    public String url;
    public Double duration;

    public ConsoleCall(String method, String url, Double duration) {
        this.method = method;
        this.url = url;
        this.duration = duration;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
