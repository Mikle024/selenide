package ru.netology.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;

public class CardApplicationPositiveTest {
    static {
        Configuration.headless = true;
    }

    private String generateDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    public void successfulApplicationTest() {
        open("http://localhost:9999");

        $("[data-test-id='city'] input").setValue("Москва");
        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Александр");
        $("[data-test-id='phone'] input").setValue("+79999999999");
        $("[data-test-id='agreement']").click();
        $(".button__content").click();

        $("[data-test-id='success-notification'] .notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldBe(Condition.exactText("Встреча успешно запланирована на " + planningDate));
    }

    @Test
    public void successfulApplicationComplexTest() {
        open("http://localhost:9999");

        $("[data-test-id='city'] input").setValue("Мо");
        $$(".menu-item__control").findBy(Condition.text("Москва")).click();
        $(".input__icon [type='button']").click();
        if (!generateDate(7, "MM").equals(generateDate(0, "MM"))) {
            $(".calendar__title [class='calendar__arrow calendar__arrow_direction_right']").click();
        }
        String useDate = generateDate(7, "d");
        $$(".calendar__day").findBy(Condition.text(useDate)).click();
        $("[data-test-id='name'] input").setValue("Александр");
        $("[data-test-id='phone'] input").setValue("+79999999999");
        $("[data-test-id='agreement']").click();
        $(".button__content").click();
        String planningDate = generateDate(7, "dd.MM.yyyy");
        $("[data-test-id='success-notification'] .notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldBe(Condition.exactText("Встреча успешно запланирована на " + planningDate));
    }
}

