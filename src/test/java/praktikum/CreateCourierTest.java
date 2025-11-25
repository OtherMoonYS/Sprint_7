package praktikum;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.net.HttpURLConnection;
import java.util.Random;

import static org.hamcrest.Matchers.equalTo;

public class CreateCourierTest {
    final CourierClient client = new CourierClient();
    private String courierId;
    CourierCreated courier = CourierCreated.random();


    @AfterEach
    void deleteCourier() {
        if (courierId != null) {
            client.deleteCourier(courierId);
        }
    }


    @Test
    @DisplayName("Проверяем, что курьер успешно создаётся")
    public void createNewUniqueCourier() {
        client.getNewCourier(courier)
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_CREATED)
                .body("ok", equalTo(true));
    }


    @Test
    @DisplayName("Проверяем, что поле 'Login' является обязательным для создания нового курьера")
    public void checkIfLoginIsRequired() {
        courier.setLogin(null);

        client.getNewCourier(courier)
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }


    @Test
    @DisplayName("Проверяем, что поле 'Password' является обязательным для создания нового курьера")
    public void checkIfPasswordIsRequired() {
        courier.setPassword(null);

        client.getNewCourier(courier)
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }


    @Test
    @DisplayName("Проверяем, что нельзя создать двух полностью индентичных курьера")
    public void createDuplicatedCouriersCannotBe() {

        //Сначала создаем курьера с конкретными данными
        client.getNewCourier(courier)
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_CREATED)
                .body("ok", equalTo(true));

        //Теперь создаем курьера с точно такими же данными, как в предыдущем шаге
        client.getNewCourier(courier)
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_CONFLICT)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }


    @Test
    @DisplayName("Проверяем, что нельзя создать двух курьеров с одинаковыми логинами")
    public void createCouriersWithDuplicatedLoginCannotBe() {
        //Сначала создаем курьера с конкретными данными
        client.getNewCourier(courier)
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_CREATED)
                .body("ok", equalTo(true));

        //Теперь создаем курьера, у которого будет такой же логин, но отличаются остальные данные
        var random = new Random();
        var secondCourier = new CourierCreated(courier.getLogin(), "password_" + random.nextInt(10000), "name_" + random.nextInt(1000));
        client.getNewCourier(secondCourier)
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_CONFLICT)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }
}
