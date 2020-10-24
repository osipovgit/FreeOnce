package osipov.evgeny.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Класс представления задачи, которую предлагает выполнить заказчик.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {
    /**
     * Поле уникального идентификатора задачи.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;
    /**
     * Поле уникального идентификатора заказчика.
     */
    @Getter @Setter
    private Long customer_id;
    /**
     * Поле статуса заказа. Всего насчитывается 4 варианта текущего состояния:
     * - published: задача в открытом доступе, ждет своего исполнителя;
     * - hidden: задача временно скрыта заказчиком, можно восстановить в личном кабинете ("История заказов");
     * - in_progress: исполнитель начал рабооту над задачей, заказчик может отказаться от его услуг или пометить как выполненную;
     * - done: задача выполненна и перемещена в архив ("История заказов").
     */
    @Getter @Setter
    private String status;
    /**
     * Поле категории задачи. Заказчик может сам описать категорию (для большей точности и понимания со стороны исполнителя).
     */
    @Getter @Setter
    private String category;
    /**
     * Поле названия задачи (краткое описание).
     */
    @Getter @Setter
    private String name;
    /**
     * Поле описания задачи.
     */
    @Getter @Setter
    private String description;
    /**
     * Поле для задания желаемого времени исполнения задачи.
     */
    @Getter @Setter
    private String time;
    /**
     * Поле для задания желаемой стоимости исполнения задачи.
     */
    @Getter @Setter
    private String price;
    /**
     * Поле уникального идентификатора исполнителя.
     */
    @Getter @Setter
    private Long freelancer_id;
    /**
     * Конструктор класса. Принимает все поля текущего класса, кроме id и freelancer_id.
     */
    public Order(Long customer_id, String status,
                 String category, String name,
                 String description, String time, String price) {
        this.customer_id = customer_id;
        this.status = status;
        this.category = category;
        this.name = name;
        this.description = description;
        this.time = time;
        this.price = price;
    }
    /**
     * Метод, преобразующий поля класса в строку в виде JSON.
     *
     * @return объект в виде JSON
     */
    public String toJSON () {
        return "{\"status\":\"" + this.getStatus() + "\",\"category\":\""
                + this.getCategory() + "\",\"name\":\"" + this.getName() + "\",\"description\":\""
                + this.getDescription() + "\",\"time\":\"" + this.getTime()
                + "\",\"price\":\"" + this.getPrice() + "\"}";
    }
    /**
     * Метод, преобразующий поля класса в строку в виде:
     *  id - "ключ", останьные поля - "значение".
     * PersonalView - информация выводится для заказчика в большем объеме.
     * @return объект в виде JSON
     */
    public String toJSONLineKeyIdValueOtherPersonalView () {
        return "\"" + this.getId().toString() + "\":\"" + "status: " + this.getStatus() + " category: "
                + this.getCategory() + " name: " + this.getName() + " description: " + this.getDescription()
                + " time: " + this.getTime() + " price: " + this.getPrice()
                + " freelancer id: " + this.getFreelancer_id() + "\"";
    }
    /**
     * Метод, преобразующий поля класса в строку в виде:
     *  id - "ключ", останьные поля - "значение".
     * ForAllAccess - информация выводится для исполнителей в сокращенном виде.
     * @return объект в виде JSON
     */
    public String toJSONLineKeyIdValueOtherForAllAccess () {
        return "\"" + this.getId().toString() + "\":\"" + " category: " + this.getCategory()
                + " name: " + this.getName() + " description: " + this.getDescription()
                + " time: " + this.getTime() + " price: " + this.getPrice() + "\"";
    }
}
