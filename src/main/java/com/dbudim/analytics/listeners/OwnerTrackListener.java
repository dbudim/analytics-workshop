package com.dbudim.analytics.listeners;

import com.dbudim.analytics.Owner;
import com.dbudim.analytics.tools.ElasticApi;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dbudim on 12.07.2021
 */

public class OwnerTrackListener implements ITestListener, ISuiteListener {

    public static Map<String, Integer> writtenTests = Collections.synchronizedMap(new HashMap<>());

    public void onFinish(ITestContext context) {
        for (var method : context.getAllTestMethods()) {
            if (!method.isTest()) continue;

            var owner = method.getTestClass().getRealClass().isAnnotationPresent(Owner.class)
                    ? method.getTestClass().getRealClass().getAnnotation(Owner.class).name()
                    : "undefined";

            if (writtenTests.containsKey(owner)) {
                int count = writtenTests.get(owner);
                count++;
                writtenTests.put(owner, count);
            } else {
                writtenTests.put(owner, 1);
            }
        }
    }

    public void onFinish(ISuite suite) {
        for (var owner : writtenTests.entrySet()) {

            new ElasticApi().pushData("aqa-owner-activity",
                    Map.of(
                            "suite", suite.getName(),
                            "timestamp", new Date(),
                            "owner_name", owner.getKey(),
                            "tests_count", owner.getValue()));
        }
    }


}
