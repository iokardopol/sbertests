package stepdefs;

import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import io.restassured.RestAssured;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.api.SoftAssertions;
import ru.sberbank.models.Current;
import ru.sberbank.models.CurrentInfo;
import ru.sberbank.models.ErrorInfo;

import java.util.Random;

/**
 * Класс WeatherStepDefs предоставляет шаги получения информации о погоде в сценарии BDD.
 * Шаги включают в себя настройку действительного ключа API, запрос информации о погоде,
 * и проверка деталей ответа на информацию о погоде.
 */
public class WeatherStepDefs {
    private static final int API_KEY_LENGTH = 32;
    private String apiKey;
    private Response response;
    private CurrentInfo currentInfo;
    private ErrorInfo errorInfo;

    private int expectedTemperature;
    private int expectedWindSpeed;


    /**
     * Устанавливает действительный ключ API для доступа к <a href="http://api.weatherstack.com">weatherstack.com</a>.
     */
    @Дано("Пользователь с валидным ключом для weatherstack")
    public void setupValidApiKey() {
        apiKey = "db54481def349ffaae5d19f2c71745d8";
    }

    /**
     * Устанавливает недействительный ключ API для доступа к <a href="http://api.weatherstack.com">weatherstack.com</a>.
     */
    @Дано("Пользователь с невалидным ключом для weatherstack")
    public void setupInvalidApiKey() {
        apiKey = RandomStringUtils.random(API_KEY_LENGTH);
    }

    /**
     * Запрашивает информацию о погоде для данного города.
     *
     * @param city город, для которого запрашивается информация о погоде.
     */
    @Когда("Пользователь запрашивает информацию о текущей погоде в городе {string}")
    public void requestWeatherInformation(String city) {
        response = RestAssured.given()
                .baseUri("http://api.weatherstack.com")
                .queryParam("access_key", apiKey)
                .queryParam("query", city)
                .get("current");
    }

    /**
     * Запрашивает информацию о погоде для данного города в запрашиваемых единицах измерения.
     * @param city город
     * @param unit единица измерения
     */
    @Когда("Пользователь запрашивает информацию о погоде в городе {string} в единицах измерения {string}")
    public void requestWeatherInformationWithUnit(String city, String unit) {
        response = RestAssured.given()
                .baseUri("http://api.weatherstack.com")
                .queryParam("access_key", apiKey)
                .queryParam("query", city)
                .queryParam("units", unit)
                .get("current");
    }

    /**
     * Устанавливает ожидаемую температуру случайно в пределах указанного минимума и максимума.
     * @param min минимальное ожидаемое значение
     * @param max максимальное ожидаемое значение
     */
    @Когда("Пользователь предполагает, что температура в этом городе составляет от {int} до {int} градусов")
    public void generateTemperature(int min, int max) {
        expectedTemperature = min + new Random().nextInt(max - min + 1);
    }
    /**
     * Устанавливает ожидаемую скорость ветра случайно в пределах указанного минимума и максимума.
     * @param min минимальное ожидаемое значение
     * @param max максимальное ожидаемое значение
     */
    @Когда("Пользователь предполагает, что скорость ветра в этом городе составляет от {int} до {int} метров в секунду")
    public void generateWindSpeed(int min, int max) {
        expectedWindSpeed = min + new Random().nextInt(max - min + 1);
    }

    /**
     * Проверяет, содержит ли ответ ожидаемые значения.
     *
     * @param expectedCity      ожидаемый город
     * @param expectedLanguage  ожидаемый язык
     * @param expectedUtcOffset ожидаемое utc время
     * @param expectedCountry   ожидаемая страна
     * @param expectedTimezone  ожидаемая временная зона
     */
    @Тогда("Ответ должен содержать город: {string}, язык запроса: {string}, время по utc: {double}, страна: {string}, timezone: {string}")
    public void verifyLocalDetails(String expectedCity, String expectedLanguage, double expectedUtcOffset, String expectedCountry, String expectedTimezone) {
        if (currentInfo == null) {
            currentInfo = response.then().extract().body().as(CurrentInfo.class);
        }

        assertThat(currentInfo)
                .extracting("location.city", "request.language", "location.utcOffset", "location.country", "location.timezoneId")
                .containsExactly(expectedCity, expectedLanguage, expectedUtcOffset, expectedCountry, expectedTimezone);
    }

    /**
     * Проверяет, совпадает ли погода с ожидаемыми.
     */
    @Тогда("Ответ содержит ожидаемые пользователем погодные условия")
    public void verifyWeatherDetails() {
        if (currentInfo == null) {
            currentInfo = response.then().extract().body().as(CurrentInfo.class);
        }

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(currentInfo.getCurrent())
                .describedAs("Разница действительной и ожидаемой температур: " + Math.abs(expectedTemperature - currentInfo.getCurrent().getTemperature()))
                .returns(expectedTemperature, Current::getTemperature)
                .describedAs("Разница действительной и ожидаемой скорости ветра: " + Math.abs(expectedWindSpeed - currentInfo.getCurrent().getWindSpeed()))
                .returns(expectedWindSpeed, Current::getWindSpeed);

        softAssertions.assertAll();
    }

    /**
     * Проверка сообщений об ошибке.
     * @param code ожидаемый код
     * @param type ожидаемый тип
     */
    @Тогда("Ответ об ошибке должен содержать код {int} и тип {string} ошибки")
    public void verifyError(int code, String type) {
        if (errorInfo == null) {
            errorInfo = response.then().extract().body().as(ErrorInfo.class);
        }

        assertThat(errorInfo.getError())
                .extracting("code", "type")
                .containsExactly(code, type);
    }
}


