package osipov.evgeny.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Класс представления пользователя - исполнителя.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "freelancer")
public class Freelancer {
    /**
     * Поле уникального идентификатора.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;
    /**
     * Поле фамилии, имени, отчества (опционально) исполнителя.
     */
    @Getter
    @Setter
    private String fio;
    /**
     * Поле имени пользователя (уникальное, ввиду реализации).
     */
    @Getter
    @Setter
    private String username;
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
    private Long count_deals;
    /**
     * Поле описания исполнителя (компании, которую он представляет).
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
    public Freelancer(String fio, String username, String password,
                      Long count_deals, String description,
                      String phone_number, String email) {
        this.fio = fio;
        this.username = username;
        this.password = password;
        this.count_deals = count_deals;
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
                + "\",\"username\":\"" + this.getUsername() + "\",\"count deals\":\""
                + this.getCount_deals().toString() + "\",\"description\":\""
                + this.getDescription() + "\",\"phone number\":\"" + this.getPhone_number()
                + "\",\"email\":\"" + this.getEmail() + "\"}";
    }
}
