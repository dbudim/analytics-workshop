package com.dbudim.analytics.ui;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.dbudim.analytics.tools.ElasticApi;
import com.dbudim.analytics.ui.devtools.ConsoleCall;
import com.dbudim.analytics.ui.devtools.ConsoleRequest;
import com.dbudim.analytics.ui.devtools.ConsoleResponse;
import com.dbudim.analytics.ui.pages.GithubPage;
import com.dbudim.analytics.ui.pages.MainPage;
import com.google.gson.Gson;
import org.elasticsearch.action.index.IndexRequest;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;

/**
 * Created by dbudim on 12.07.2021
 */

public class PageRequestsTest {

    @BeforeClass
    public void prepareData() {
        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
        options.setCapability("goog:loggingPrefs", logPrefs);
        Configuration.browserCapabilities = options;
        Selenide.open();
    }

    @Test
    public void mainPageRequests() {
        new GithubPage().open();
        writeConsoleCalls("Github", getConsoleCalls(), new Date());
    }

    public static List<ConsoleCall> getConsoleCalls() {
        List<LogEntry> logEntries = WebDriverRunner.getWebDriver().manage().logs().get(LogType.PERFORMANCE).getAll();

        Map<String, ConsoleRequest> requestWillBeSent = logEntries.stream()
                .filter(l -> l.getMessage().contains("Network.requestWillBeSent\""))
                .map(l -> new Gson().fromJson(l.getMessage(), ConsoleRequest.class))
                .filter(r -> r.message.params.request.url.startsWith("http") | r.message.params.request.url.startsWith("www"))
                .collect(Collectors.toMap(r -> r.message.params.requestId, r -> r, (r1, r2) -> r1));

        Map<String, ConsoleResponse> responseReceived = logEntries.stream()
                .filter(l -> l.getMessage().contains("Network.responseReceived\""))
                .map(l -> new Gson().fromJson(l.getMessage(), ConsoleResponse.class))
                .filter(r -> r.message.params.response.url.startsWith("http") | r.message.params.response.url.startsWith("www"))
                .collect(Collectors.toMap(r -> r.message.params.requestId, r -> r));

        List<ConsoleCall> result = new ArrayList<>();

        for (Map.Entry<String, ConsoleResponse> entry : responseReceived.entrySet()) {
            Optional.ofNullable(requestWillBeSent.get(entry.getKey()))
                    .ifPresent(s -> result.add(new ConsoleCall(s.getHttpMethod(), entry.getValue().getUrl(), entry.getValue().getReceiveHeadersEndTime())));
        }
        return result;
    }

    public static void writeConsoleCalls(String page, List<ConsoleCall> consoleCalls, Date timestamp) {
        List<IndexRequest> indexRequests = new ArrayList<>();

        for (ConsoleCall call : consoleCalls) {
            Map<String, Object> data = Map.of(
                    "page", page,
                    "timestamp", timestamp,
                    "duration", call.duration,
                    "url", call.url
            );
            IndexRequest request = new IndexRequest().index("aqa-page-request").source(data);
            indexRequests.add(request);
        }
        new ElasticApi().pushDataBulk(indexRequests);
    }
}
