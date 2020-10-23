package osipov.evgeny.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;
    @Getter @Setter
    private Long customer_id;
    @Getter @Setter
    private String status;
    @Getter @Setter
    private String category;
    @Getter @Setter
    private String name;
    @Getter @Setter
    private String description;
    @Getter @Setter
    private String time;
    @Getter @Setter
    private String price;
    @Getter @Setter
    private Long freelancer_id;

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
    public String toJSONWithoutBrace () {
        return "\"id\":\"" + this.getId() + "\",\"customer id\":\"" + this.getCustomer_id()
                + "\",\"status\":\"" + this.getStatus() + "\",\"category\":\""
                + this.getCategory() + "\",\"name\":\""
                + this.getName() + "\",\"description\":\"" + this.getDescription() + "\",\"time\":\"" + this.getTime()
                + "\",\"price\":\"" + this.getPrice() + "\",\"freelancer id\":\"" + this.getFreelancer_id()+ "\"";
    }

    public String toJSONLineKeyIdValueOtherPersonalView () {
        return "\"" + this.getId().toString() + "\":\"" + "status: " + this.getStatus() + " category: "
                + this.getCategory() + " name: " + this.getName() + " description: " + this.getDescription()
                + " time: " + this.getTime() + " price: " + this.getPrice()
                + " freelancer id: " + this.getFreelancer_id() + "\"";
    }

    public String toJSONLineKeyIdValueOtherForAllAccess () {
        return "\"" + this.getId().toString() + "\":\"" + " category: " + this.getCategory()
                + " name: " + this.getName() + " description: " + this.getDescription()
                + " time: " + this.getTime() + " price: " + this.getPrice() + "\"";
    }
}
