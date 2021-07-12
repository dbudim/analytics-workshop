package com.dbudim.analytics.listeners;

import com.dbudim.analytics.Owner;
import com.dbudim.analytics.tools.ElasticApi;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.*;

/**
 * Created by dbudim on 10.07.2021
 */

public class GlobalMetricsListener implements ITestListener, ISuiteListener {

    private static Date suiteDate = new Date();
    private static List<Map<String, ?>> data = Collections.synchronizedList(new ArrayList<>());

    public void onTestSuccess(ITestResult result) {
        data.add(buildResult(result, "success"));
    }

    public void onTestFailure(ITestResult result) {
        data.add(buildResult(result, "fail"));
    }

    public void onTestSkipped(ITestResult result) {
        data.add(buildResult(result, "skip"));
    }

    public void onFinish(ISuite suite) {
        new ElasticApi().pushDataBulk("aqa-test", data);
    }


    private String getOwner(ITestResult result) {
        return result.getTestClass().getRealClass().isAnnotationPresent(Owner.class)
                ? result.getTestClass().getRealClass().getAnnotation(Owner.class).name()
                : "undefined";
    }

    private Map<String, ?> buildResult(ITestResult result, String status) {
        return Map.of(
                "suite", result.getTestContext().getSuite().getName(),
                "method", result.getName(),
                "is_test", result.getMethod().isTest(),
                "class", result.getTestClass().getRealClass().getName(),
                "duration", result.getEndMillis() - result.getStartMillis(),
                "result", status,
                "owner", getOwner(result),
                "timestamp", suiteDate,
                "run_date", new Date()
        );
    }

}
