package praktikum;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.net.HttpURLConnection;
import static org.hamcrest.Matchers.*;

public class LoginCourierTest {
    private CourierClient client;
    private CourierCreated testCourier;
    private String courierId;

    @BeforeEach
    public void setUp() {
        client = new CourierClient();
        testCourier = CourierCreated.random();

        client.getNewCourier(testCourier)
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_CREATED);

        courierId = client.logInCourier(CourierLogin.from(testCourier))
                .statusCode(HttpURLConnection.HTTP_OK)
                .extract()
                .path("id").toString();
    }


    @AfterEach
    void deleteCourier() {
        if (courierId != null) {
            client.deleteCourier(courierId);
        }
    }


    @Test
    @DisplayName("Тест успешной авторизации курьера в системе")
    public void checkLogInCourierInSystemBeSuccessfully() {

        client.logInCourier(CourierLogin.from(testCourier))
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("id", notNullValue());
    }


    @Test
    @DisplayName("Тест на авторизацию курьера без login'а")
    public void checkLogInCourierWithoutLogin() {
        client.logInCourier(new CourierLogin(null, testCourier.getPassword()))
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }


    @Test
    @DisplayName("Тест на авторизацию курьера без password'a")
    public void CheckLogInCourierWithoutPassword() {
        client.logInCourier(new CourierLogin(testCourier.getLogin(), null))
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }


    @Test
    @DisplayName("Тест на авторизацию курьера с некорректным логином")
    public void checkLoginCourierWithIncorrectLogin() {
        client.logInCourier(new CourierLogin(testCourier.getLogin() + 1, testCourier.getPassword()))
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }


    @Test
    @DisplayName("Тест на авторизацию курьера с некорректным паролем")
    public void checkLoginCourierWithIncorrectPassword() {
        client.logInCourier(new CourierLogin(testCourier.getLogin(), testCourier.getPassword() + 1))
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }
}
