package com.dbudim.analytics.ui.devtools;


/**
 * Created by dbudim on 15.06.2020
 */

public class ConsoleResponse {

    public Message message;

    public class Message {
        public String method;
        public Params params;
    }

    public class Params {
        public String requestId;
        public Response response;
    }

    public class Response {
        public String url;
        public Timing timing;
    }

    public class Timing {
        public Double receiveHeadersEnd;
    }

    public String getUrl(){
        return this.message.params.response.url;
    }

    public Double getReceiveHeadersEndTime() {
        return this.message.params.response.timing.receiveHeadersEnd;
    }
}
