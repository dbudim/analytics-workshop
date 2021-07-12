package com.dbudim.analytics;

import com.dbudim.analytics.tools.ElasticApi;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.Map;

/**
 * Created by dbudim on 09.07.2021
 */

@Owner(name = "Alex")
public class PushDataTest {

    @Test
    public void pusDataTest() {
        Map<String, Object> data = Map.of(
                "name", "Ray",
                "position", "SDET",
                "timestamp", new Date()
        );
        new ElasticApi().pushData("aqa-push-test", data);
    }
}
