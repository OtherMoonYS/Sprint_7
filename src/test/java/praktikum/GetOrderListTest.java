package praktikum;

import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class GetOrderListTest {
    private final CreateOrder createOrder = new CreateOrder();


    private void checkResponseStatus(ValidatableResponse response) {
        response.statusCode(HttpURLConnection.HTTP_OK);
    }

    private void checkResponseContainsOrdersList(ValidatableResponse response) {
        response.body("orders", notNullValue());

        List<Map<String, Object>> orders = response.extract().jsonPath().getList("orders");
        assertFalse(orders.isEmpty(), "Список заказов не должен быть пустым");
    }


    @Test
    @DisplayName("Тест на проверку возвращения валидного списка заказов с эндпоинта")
    public void shouldReturnValidOrdersList() {
        var response = createOrder.getOrderList();

        checkResponseStatus(response);
        checkResponseContainsOrdersList(response);
    }
}
