package praktikum;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import praktikum.CreateOrder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CreateOrderTest {
    @ParameterizedTest
    @MethodSource("orderParam")
    @DisplayName("Тест на создание заказа с разными цветами самоката")
    public void checkCreateOrderWithNotSameColors(String firstName,
                                                  String lastName,
                                                  String address,
                                                  String metroStation,
                                                  String phone,
                                                  int rentTime,
                                                  String deliveryDate,
                                                  String comment,
                                                  List<String> color) {
        CreateOrder createOrder = new CreateOrder();
        int track = createOrder.createOrder(firstName,
                                            lastName,
                                            address,
                                            metroStation,
                                            phone,
                                            rentTime,
                                            deliveryDate,
                                            comment,
                                            color)
                .extract()
                .path("track");
        assertNotNull(track, "Трек номер заказа не должен быть null");
    }

    static Stream<Arguments> orderParam() {
        return Stream.of(
                Arguments.of("Pepe", "Kaef", "North Pole, 34", "2", "+7 987 654 32 10", 9, "2025-11-11", "MyBest delivery", Arrays.asList("BLACK")),
                Arguments.of("Pepe", "Kaef", "North Pole, 34", "2", "+7 987 654 32 10", 9, "2025-11-21", "MyBest delivery", Arrays.asList()),
                Arguments.of("Pepe", "Kaef", "North Pole, 34", "2", "+7 987 654 32 10", 9, "2025-11-11", "MyBest delivery", Arrays.asList("GREY")),
                Arguments.of("Pepe", "Kaef", "North Pole, 34", "2", "+7 987 654 32 10", 9, "2025-11-11", "MyBest delivery", Arrays.asList("BLACK", "GREY"))
        );
    }
}
