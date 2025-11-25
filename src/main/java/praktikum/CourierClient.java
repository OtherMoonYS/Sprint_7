package praktikum;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;


import static io.restassured.RestAssured.given;
import static praktikum.Constants.*;

public class CourierClient {

    @Step("Создаем уникального курьера")
    public ValidatableResponse getNewCourier(CourierCreated courier) {
        return given ()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .body(courier)
                .when()
                .post(CREATE_COURIER)
                .then()
                .log()
                .all();
    }

    @Step("Логинимся курьером в систему")
    public ValidatableResponse logInCourier(CourierLogin courier) {
        return given()
                .log()
                .all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .body(courier)
                .when()
                .post(LOGIN_COURIER)
                .then().log().all();
    }

    @Step("Удаляем курьера из системы")
    public ValidatableResponse deleteCourier(String id) {
        return given()
                .log()
                .all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .when()
                .delete(DELETE_COURIER + id)
                .then();
    }
}
