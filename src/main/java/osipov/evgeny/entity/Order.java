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
    @OneToOne
    @JoinColumn(name = "id")
    private Customer customer_id;
    @Getter @Setter
    private String status;
    @Getter @Setter
    private String category;
    @Getter @Setter
    private String name;
    @Getter @Setter
    private String description;
    @Getter @Setter
    private String price;
    @Getter @Setter
    @OneToOne
    @JoinColumn(name = "id")
    private Freelancer freelancer_id;

    public Order(Customer customer_id, String status,
                 String category, String name,
                 String description, String price,
                 Freelancer freelancer_id) {
        this.customer_id = customer_id;
        this.status = status;
        this.category = category;
        this.name = name;
        this.description = description;
        this.price = price;
        this.freelancer_id = freelancer_id;
    }
    public String toJSON () {
        return "{\"customer id\":\"" + this.getCustomer_id()
                + "\",\"status\":\"" + this.getStatus() + "\",\"category\":\""
                + this.getCategory() + "\",\"name\":\""
                + this.getName() + "\",\"description\":\"" + this.getDescription()
                + "\",\"price\":\"" + this.getPrice() + "\",\"freelancer id\":\"" + this.getFreelancer_id()+ "\"}";
    }

}
