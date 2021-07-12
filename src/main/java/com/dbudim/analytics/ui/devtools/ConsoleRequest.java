package com.dbudim.analytics.ui.devtools;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dbudim on 15.06.2020
 */

public class ConsoleRequest {

    public Message message;

    public class Message {
        public String method;
        public Params params;
    }

    public class Params {
        public String requestId;
        public Request request;
    }

    public class Request {
        public String url;
        public String method;
        public Headers headers;
    }

    public class Headers {
        @SerializedName("uber-trace-id")
        public String uber_trace_id;
    }

    public String getHttpMethod() {
        return this.message.params.request.method;
    }

    public String getUrl() {
        return this.message.params.request.url;
    }

    public Headers getHeaders(){
        return this.message.params.request.headers;
    }
}
