package com.dbudim.analytics.ui;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.dbudim.analytics.Owner;
import com.dbudim.analytics.tools.ElasticApi;
import com.dbudim.analytics.ui.pages.ComingSoonPage;
import com.dbudim.analytics.ui.pages.MainPage;
import com.dbudim.analytics.ui.pages.OscarsPage;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.Map;

/**
 * Created by dbudim on 12.07.2021
 */

@Owner(name = "Jack")
public class PagesTimingTest {

    @BeforeClass
    public void prepareData() {
        Configuration.baseUrl = "https://www.imdb.com";
        Selenide.open();
    }

    @Test(dataProvider = "pageOpeners")
    public void open(String name, Runnable pageOpener) {
        long start = System.currentTimeMillis();
        pageOpener.run();
        long end = System.currentTimeMillis();
        new ElasticApi().pushData("aqa-pages", Map.of("page_name", name, "open_time", end - start, "timestamp", new Date()));
    }

    @DataProvider
    public Object[][] pageOpeners() {
        return new Object[][]{
                {"Main", (Runnable) () -> new MainPage().open()},
                {"Oscars", (Runnable) () -> new OscarsPage().open()},
                {"Coming Soon", (Runnable) () -> new ComingSoonPage().open()},
        };
    }
}
