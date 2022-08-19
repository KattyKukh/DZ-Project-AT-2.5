package ru.netology.delivery.test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.delivery.data.DataGenerator;
import ru.netology.delivery.data.FormData;

import java.time.Duration;
import java.time.LocalDate;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$$;

public class CardDeliveryTest {
    private FormData generator = DataGenerator.Registration.generate();

    @BeforeEach
    void setupTest() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = generator.getValidName();
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.Registration.generateDate(daysToAddForFirstMeeting, "dd.MM.yyyy");
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.Registration.generateDate(daysToAddForSecondMeeting, "dd.MM.yyyy");
        var firstMeetingDateDay = DataGenerator.Registration.generateDate(daysToAddForFirstMeeting, "dd");
        var secondMeetingDateDay = DataGenerator.Registration.generateDate(daysToAddForSecondMeeting, "dd");
        var validCity = generator.getValidCity();
        var validPhone = generator.getValidPhone();
        $("[data-test-id=city] .input__control").setValue(validCity);
        $$(".menu-item").first().click();
        $(".input__icon").click();
        if (LocalDate.now().plusDays(daysToAddForFirstMeeting).getMonthValue() > LocalDate.now().getMonthValue()) {
            $("[data-step='1'].calendar__arrow_direction_right ").click();
        }
        $$(".calendar__day").find(text(firstMeetingDateDay)).click();
        $("[data-test-id = name] .input__control").setValue(validUser);
        $("[data-test-id = phone] .input__control").setValue(validPhone);
        $("[data-test-id = agreement]").click();
        $(".button").click();
        $("[data-test-id = success-notification]")
                .shouldHave(text("Успешно!"))
                .shouldBe(visible);
        $("[data-test-id = success-notification] .notification__content")
                .shouldHave(text("Встреча успешно запланирована на " + firstMeetingDate), Duration.ofSeconds(15))
                .shouldBe(visible);
        $(".input__icon").click();
        if (LocalDate.now().plusDays(daysToAddForSecondMeeting).getMonthValue() > LocalDate.now().getMonthValue()) {
            $("[data-step='1'].calendar__arrow_direction_right ").click();
        }
        $$(".calendar__day").find(text(secondMeetingDateDay)).click();
        $(".button").click();
        $("[data-test-id = replan-notification]")
                .shouldHave(text("Необходимо подтверждение"), Duration.ofSeconds(15))
                .shouldBe(visible);
        $("[data-test-id = replan-notification] .notification__content")
                .shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"), Duration.ofSeconds(15))
                .shouldBe(visible);
        $("[data-test-id = replan-notification] .button")
                .shouldHave(text("Перепланировать"), Duration.ofSeconds(15))
                .shouldBe(visible);
        $("[data-test-id = replan-notification] .button").click();
        $("[data-test-id = success-notification]")
                .shouldHave(text("Успешно!"), Duration.ofSeconds(15))
                .shouldBe(visible);
        $("[data-test-id = success-notification] .notification__content")
                .shouldHave(text("Встреча успешно запланирована на " + secondMeetingDate))
                .shouldBe(visible);
    }
}
