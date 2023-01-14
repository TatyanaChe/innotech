package com.innotech.interview.test.ui;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

class YandexPageTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() throws InterruptedException {
	    
		System.out.println("starting the test ... ");
		// Зайти на сайт https://ya.ru/
		Selenide.open(YandexPage.URL);
		// В поиске набрать gismeteo
		$(By.id("text")).shouldBe(visible).setValue("gismeteo");
		$(By.xpath(YandexPage.SEARCH_BUTTON)).shouldBe(visible).submit();
		// Через найденный список перейти на сайт https://www.gismeteo.ru/
		$(By.xpath(YandexPage.GISMETEO_LINK)).shouldBe(visible).click();
		Selenide.sleep(1000);
		Selenide.switchTo().window(1);
		// Набрать в строке поиска город Сочи и выбрать его в выпадающем списке
		$(By.xpath(GismeteoPage.SEARCH_INPUT)).shouldBe(visible).setValue("Сочи");
		Selenide.switchTo().window(1);
		$(By.xpath(GismeteoWeatherSochiPage.CITY_TITLE_SOCHI)).shouldBe(visible).click();
		SelenideElement pageTitle = $(By.xpath(GismeteoWeatherSochiPage.PAGE_TITLE));
		// Проверить что на странице отображается фраза «Погода в Сочи сегодня»
		Assertions.assertThat(pageTitle.shouldBe(visible).getText()).isEqualTo("Погода в Сочи сегодня",
		        "Page title is wrong");
		System.out.println("Page title is 'Погода в Сочи сегодня' as expected");
	}

}
