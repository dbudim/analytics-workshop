package com.dbudim.analytics.ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;

import static com.codeborne.selenide.Selenide.$;

/**
 * Created by dbudim on 12.07.2021
 */

public class GithubPage {

    public void open(){
        Selenide.open("https://github.com/");
        $("#user_email").shouldBe(Condition.appear);
    }

}
