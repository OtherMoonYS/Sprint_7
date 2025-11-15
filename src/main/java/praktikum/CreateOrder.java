package praktikum;
import static org.hamcrest.Matchers.notNullValue;
import static praktikum.Constants.*;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import java.net.HttpURLConnection;
import java.util.List;

import static io.restassured.RestAssured.given;

public class CreateOrder {

    @Step("Создаём новый заказ")
    public ValidatableResponse createOrder(String firstName,
                                           String lastName,
                                           String address,
                                           String metroStation,
                                           String phone,
                                           int rentTime,
                                           String deliveryDate,
                                           String comment,
                                           List<String> color) {
        OrderCreated order = new OrderCreated(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .body(order)
                .when()
                .post(ORDER_CREATED)
                .then()
                .statusCode(HttpURLConnection.HTTP_CREATED)
                .body("track", notNullValue());
    }

    @Step("Получаем список заказов")
    public ValidatableResponse getOrderList() {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .when()
                .get(ORDERS_LIST)
                .then()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("orders", notNullValue());
    }
}
