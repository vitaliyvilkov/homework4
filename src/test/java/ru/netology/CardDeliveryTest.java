package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {

    private String generateDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    public void testSuccessfulForm() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Саратов");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.CONTROL + "a"), Keys.DELETE);
        String date = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input").setValue(date);
        $("[data-test-id='name'] input").setValue("Вальдемар Пупкин");
        $("[data-test-id='phone'] input").setValue("+79995567977");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("[data-test-id='notification'] .notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + date));
    }
}