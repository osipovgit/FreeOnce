package osipov.evgeny.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Класс представления пользователя - заказчика.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer")
public class Customer {
    /**
     * Поле уникального идентификатора.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;
    /**
     * Поле фамилии, имени, отчества (опционально) заказчика.
     */
    @Getter
    @Setter
    private String fio;
    /**
     * Поле имени пользователя (уникальное, ввиду реализации).
     */
    @Getter
    @Setter
    private String customer_name;
    /**
     * Поле пароля.
     */
    @Getter
    @Setter
    private String password;
    /**
     * Поле количества завершенных заказов.
     */
    @Getter
    @Setter
    private Long count_orders;
    /**
     * Поле описания заказчика (компании, которую он представляет).
     */
    @Getter
    @Setter
    private String description;
    /**
     * Поле контактного телефона.
     */
    @Getter
    @Setter
    private String phone_number;
    /**
     * Поле для почты.
     */
    @Getter
    @Setter
    private String email;

    /**
     * Конструктор класса. Принимает все поля текущего класса, кроме id.
     */
    public Customer(String fio, String customer_name, String password,
                    Long count_orders, String description,
                    String phone_number, String email) {
        this.fio = fio;
        this.customer_name = customer_name;
        this.password = password;
        this.count_orders = count_orders;
        this.description = description;
        this.phone_number = phone_number;
        this.email = email;
    }

    /**
     * Метод, преобразующий поля класса в строку в виде JSON.
     *
     * @return объект в виде JSON
     */
    public String toJSON() {
        return "{\"fio\":\"" + this.getFio()
                + "\",\"username\":\"" + this.getCustomer_name() + "\",\"count deals\":\""
                + this.getCount_orders().toString() + "\",\"description\":\""
                + this.getDescription() + "\",\"phone number\":\"" + this.getPhone_number()
                + "\",\"email\":\"" + this.getEmail() + "\"}";
    }
}
