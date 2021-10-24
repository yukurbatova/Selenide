package ru.netology.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryCardTest {
    LocalDate minDate = LocalDate.now().plusDays(3);
    LocalDate date = LocalDate.now().plusDays(4);
    LocalDate incorrectDate = LocalDate.now().plusDays(2);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldTextWithSpaceTest() {
        $("[data-test-id=city] .input__control").setValue("Нижний Новгород");
        $("[data-test-id=date] [type=tel]").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] [type=tel]").setValue(formatter.format(minDate));
        $("[data-test-id=name] .input__control").setValue("Курбатова Юлия");
        $("[data-test-id=phone] .input__control").setValue("+79038190000");
        $("[data-test-id=agreement] .checkbox__box").click();
        $(byText("Забронировать")).click();
        $(".notification__title").shouldHave(exactText("Успешно!"), Duration.ofSeconds(15));
        $(".notification__content").shouldHave(exactText("Встреча успешно забронирована на " + formatter.format(minDate)));
    }

    @Test
    void shouldTextWithHyphenTest() {
        $("[data-test-id=city] .input__control").setValue("Ростов-на-Дону");
        $("[data-test-id=date] [type=tel]").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] [type=tel]").setValue(formatter.format(minDate));
        $("[data-test-id=name] .input__control").setValue("Курбатова-Минакова Юлия");
        $("[data-test-id=phone] .input__control").setValue("+79038190000");
        $("[data-test-id=agreement] .checkbox__box").click();
        $(byText("Забронировать")).click();
        $(".notification__title").shouldHave(exactText("Успешно!"), Duration.ofSeconds(15));
        $(".notification__content").shouldHave(exactText("Встреча успешно забронирована на " + formatter.format(minDate)));
    }

    @Test
    void shouldNotExistCityTest() {
        $("[data-test-id=city] .input__control").setValue("Нет такого");
        $("[data-test-id=date] [type=tel]").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] [type=tel]").setValue(formatter.format(minDate));
        $("[data-test-id=name] .input__control").setValue("Курбатова Юлия");
        $("[data-test-id=phone] .input__control").setValue("+79038190000");
        $("[data-test-id=agreement] .checkbox__box").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=city].input_invalid .input__sub").shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldEnglishCityTest() {
        $("[data-test-id=city] .input__control").setValue("London");
        $("[data-test-id=date] [type=tel]").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] [type=tel]").setValue(formatter.format(minDate));
        $("[data-test-id=name] .input__control").setValue("Курбатова Юлия");
        $("[data-test-id=phone] .input__control").setValue("+79038190000");
        $("[data-test-id=agreement] .checkbox__box").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=city].input_invalid .input__sub").shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldEmptyCityTest() {
        $("[data-test-id=date] [type=tel]").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] [type=tel]").setValue(formatter.format(minDate));
        $("[data-test-id=name] .input__control").setValue("Курбатова Юлия");
        $("[data-test-id=phone] .input__control").setValue("+79038190000");
        $("[data-test-id=agreement] .checkbox__box").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=city].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldIncorrectDateTest() {
        $("[data-test-id=city] .input__control").setValue("Брянск");
        $("[data-test-id=date] [type=tel]").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] [type=tel]").setValue(formatter.format(incorrectDate));
        $("[data-test-id=name] .input__control").setValue("Курбатова Юлия");
        $("[data-test-id=phone] .input__control").setValue("+79038190000");
        $("[data-test-id=agreement] .checkbox__box").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=date] .input_invalid .input__sub").shouldHave(exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldNotMinDateTest() {
        $("[data-test-id=city] .input__control").setValue("Брянск");
        $("[data-test-id=date] [type=tel]").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] [type=tel]").setValue(formatter.format(date));
        $("[data-test-id=name] .input__control").setValue("Курбатова Юлия");
        $("[data-test-id=phone] .input__control").setValue("+79038190000");
        $("[data-test-id=agreement] .checkbox__box").click();
        $(byText("Забронировать")).click();
        $(".notification__title").shouldHave(exactText("Успешно!"), Duration.ofSeconds(15));
        $(".notification__content").shouldHave(exactText("Встреча успешно забронирована на " + formatter.format(date)));
    }

    @Test
    void shouldEmptyDateTest() {
        $("[data-test-id=city] .input__control").setValue("Брянск");
        $("[data-test-id=date] [type=tel]").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=name] .input__control").setValue("Курбатова Юлия");
        $("[data-test-id=phone] .input__control").setValue("+79038190000");
        $("[data-test-id=agreement] .checkbox__box").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=date] .input_invalid .input__sub").shouldHave(exactText("Неверно введена дата"));
    }

    @Test
    void shouldEnglishNameTest() {
        $("[data-test-id=city] .input__control").setValue("Брянск");
        $("[data-test-id=date] [type=tel]").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] [type=tel]").setValue(formatter.format(minDate));
        $("[data-test-id=name] .input__control").setValue("Kurbatova Yuliya");
        $("[data-test-id=phone] .input__control").setValue("+79038190000");
        $("[data-test-id=agreement] .checkbox__box").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldNumbersInNameTest() {
        $("[data-test-id=city] .input__control").setValue("Брянск");
        $("[data-test-id=date] [type=tel]").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] [type=tel]").setValue(formatter.format(minDate));
        $("[data-test-id=name] .input__control").setValue("Юлия123");
        $("[data-test-id=phone] .input__control").setValue("+79038190000");
        $("[data-test-id=agreement] .checkbox__box").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldEmptyNameTest() {
        $("[data-test-id=city] .input__control").setValue("Брянск");
        $("[data-test-id=date] [type=tel]").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] [type=tel]").setValue(formatter.format(minDate));
        $("[data-test-id=phone] .input__control").setValue("+79038190000");
        $("[data-test-id=agreement] .checkbox__box").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldLettersInPhoneTest() {
        $("[data-test-id=city] .input__control").setValue("Брянск");
        $("[data-test-id=date] [type=tel]").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] [type=tel]").setValue(formatter.format(minDate));
        $("[data-test-id=name] .input__control").setValue("Курбатова Юлия");
        $("[data-test-id=phone] .input__control").setValue("+ааааааааааа");
        $("[data-test-id=agreement] .checkbox__box").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldOnlyPlusInPhoneTest() {
        $("[data-test-id=city] .input__control").setValue("Брянск");
        $("[data-test-id=date] [type=tel]").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] [type=tel]").setValue(formatter.format(minDate));
        $("[data-test-id=name] .input__control").setValue("Курбатова Юлия");
        $("[data-test-id=phone] .input__control").setValue("+");
        $("[data-test-id=agreement] .checkbox__box").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldTwoCharactersInPhoneTest() {
        $("[data-test-id=city] .input__control").setValue("Брянск");
        $("[data-test-id=date] [type=tel]").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] [type=tel]").setValue(formatter.format(minDate));
        $("[data-test-id=name] .input__control").setValue("Курбатова Юлия");
        $("[data-test-id=phone] .input__control").setValue("+7");
        $("[data-test-id=agreement] .checkbox__box").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldThirteenCharactersInPhoneTest() {
        $("[data-test-id=city] .input__control").setValue("Брянск");
        $("[data-test-id=date] [type=tel]").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] [type=tel]").setValue(formatter.format(minDate));
        $("[data-test-id=name] .input__control").setValue("Курбатова Юлия");
        $("[data-test-id=phone] .input__control").setValue("+790381900001");
        $("[data-test-id=agreement] .checkbox__box").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldWithoutPlusPhoneTest() {
        $("[data-test-id=city] .input__control").setValue("Брянск");
        $("[data-test-id=date] [type=tel]").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] [type=tel]").setValue(formatter.format(minDate));
        $("[data-test-id=name] .input__control").setValue("Курбатова Юлия");
        $("[data-test-id=phone] .input__control").setValue("79038190000");
        $("[data-test-id=agreement] .checkbox__box").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }


    @Test
    void shouldEmptyPhoneTest() {
        $("[data-test-id=city] .input__control").setValue("Брянск");
        $("[data-test-id=date] [type=tel]").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] [type=tel]").setValue(formatter.format(minDate));
        $("[data-test-id=name] .input__control").setValue("Курбатова Юлия");
        $("[data-test-id=agreement] .checkbox__box").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldEmptyAgreementTest() {
        $("[data-test-id=city] .input__control").setValue("Брянск");
        $("[data-test-id=date] [type=tel]").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] [type=tel]").setValue(formatter.format(minDate));
        $("[data-test-id=name] .input__control").setValue("Курбатова Юлия");
        $("[data-test-id=phone] .input__control").setValue("+79038190000");
        $(byText("Забронировать")).click();
        $("[data-test-id=agreement].input_invalid .checkbox__text").shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }
}
