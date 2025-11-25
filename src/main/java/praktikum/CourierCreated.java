package praktikum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Random;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourierCreated {
    private String login;
    private String password;
    private String firstName;

    // Тут мы генерируем курьера со случайными данными
    public static CourierCreated random() {
        var rnd = new Random();
        return new CourierCreated(
                "othr" + rnd.nextInt(999999),
                "P@ssw0rd1915",
                "Lala Land"
        );
    }
}
