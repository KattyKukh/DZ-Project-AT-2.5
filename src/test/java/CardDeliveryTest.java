import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$$;

public class CardDeliveryTest {
    private FormData generator = DataGenerator.Registration.generate();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public String numberDay(String date) {
        String numberDay;
        if (date.substring(0, 1).contains("0")) {
            numberDay = date.substring(1, 2);
        } else {
            numberDay = date.substring(0, 2);
        }
        return numberDay;
    }

    @BeforeAll
    static void setUpAll() {
        Configuration.headless = true;
    }

    @BeforeEach
    void setupTest() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = generator.getValidName();
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.Registration.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.Registration.generateDate(daysToAddForSecondMeeting);
        var validCity = generator.getValidCity();
        var validPhone = generator.getValidPhone();
        // TODO: добавить логику теста в рамках которого будет выполнено планирование и перепланирование встречи.
        $("[data-test-id=city] .input__control").setValue(validCity);
        $$(".menu-item").first().click();
        $(".input__icon").click();
        String calendarDay = numberDay(formatter.format(firstMeetingDate));
        if (LocalDate.now().plusDays(daysToAddForFirstMeeting).getMonthValue() > LocalDate.now().getMonthValue()) {
            $("[data-step='1'].calendar__arrow_direction_right ").click();
            $$(".calendar__day").find(text(calendarDay)).click();
        } else {
            $$(".calendar__day").find(text(calendarDay)).click();
        }
        $("[data-test-id = name] .input__control").setValue(validUser);
        $("[data-test-id = phone] .input__control").setValue(validPhone);
        $("[data-test-id = agreement]").click();
        $(".button").click();
        $("[data-test-id = success-notification]")
                .shouldHave(text("Ура!"), Duration.ofSeconds(15))
                .shouldBe(visible);
        $("[data-test-id = success-notification] .notification__content")
                .shouldHave(text("Встреча успешно запланирована на " + formatter.format(firstMeetingDate)), Duration.ofSeconds(15))
                .shouldBe(visible);
        $(".input__icon").click();
        calendarDay = numberDay(formatter.format(secondMeetingDate));
        if (secondMeetingDate.getMonthValue() > LocalDate.now().getMonthValue()) {
            $("[data-step='1'].calendar__arrow_direction_right ").click();
            $$(".calendar__day").find(text(calendarDay)).click();
        } else {
            $$(".calendar__day").find(text(calendarDay)).click();
        }
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
                .shouldHave(text("Встреча успешно запланирована на " + formatter.format(secondMeetingDate)), Duration.ofSeconds(15))
                .shouldBe(visible);
    }
}
