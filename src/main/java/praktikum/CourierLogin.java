package praktikum;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourierLogin {
    private String login;
    private String password;

    //Логиним созданного курьера в систему
    public static CourierLogin from(CourierCreated courierCreated) {
        return new CourierLogin(courierCreated.getLogin(), courierCreated.getPassword());
    }
}
