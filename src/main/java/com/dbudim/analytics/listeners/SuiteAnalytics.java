package com.dbudim.analytics.listeners;

import com.dbudim.analytics.tools.ElasticApi;
import org.testng.ISuite;
import org.testng.ISuiteListener;

import java.util.Date;
import java.util.Map;

/**
 * Created by dbudim on 07.07.2021
 */

public class SuiteAnalytics implements ISuiteListener {

    private static long total;
    private static long passed;
    private static long failed;
    private static long skipped;

    @Override
    public void onFinish(ISuite suite) {
        suite.getResults()
                .entrySet()
                .stream()
                .forEach(result -> {
                    passed += result.getValue().getTestContext().getPassedTests().size();
                    failed += result.getValue().getTestContext().getFailedTests().size();
                    skipped += result.getValue().getTestContext().getSkippedTests().size();
                });
        total = passed + skipped + failed;

        Map<String, Object> data = Map.of(
                "suite", suite.getName(),
                "total", total,
                "passed", passed,
                "failed", failed,
                "skipped", skipped,
                "rate", (double) passed / total * 100,
                "timestamp", new Date());
        pushMetrics("aqa-suite-rate", data);
    }


    private void pushMetrics(String index, Map<String, Object> data) {
        try {
            new ElasticApi().pushData(index, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
