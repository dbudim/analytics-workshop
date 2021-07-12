package com.dbudim.analytics.tools;

import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by dbudim on 08.07.2021
 */

public class ElasticApi {

    private static final String HOST = "0.0.0.0";
    private static final int PORT = 9200;
    private RestHighLevelClient client;

    public ElasticApi() {
        client = new RestHighLevelClient(RestClient.builder(new HttpHost(HOST, PORT)));
    }

    public void pushData(String index, Map<String, Object> data) {
        IndexRequest source = new IndexRequest(index).source(data);
        try {
            client.bulk(new BulkRequest().add(source), RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void pushDataBulk(String index, List<Map<String, ?>> data) {
        BulkRequest bulkRequest = new BulkRequest();
        data.stream().forEach(d -> bulkRequest.add(new IndexRequest(index).source(d)));
        try {
            client.bulk(bulkRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void pushDataBulk(List<IndexRequest> data) {
        BulkRequest bulkRequest = new BulkRequest();
        data.stream().forEach(d -> bulkRequest.add(d));
        try {
            client.bulk(bulkRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
