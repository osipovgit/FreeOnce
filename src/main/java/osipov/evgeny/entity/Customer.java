package osipov.evgeny.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;
    @Getter @Setter
    private String fio;
    @Getter @Setter
    private String customer_name;
    @Getter @Setter
    private String password;
    @Getter @Setter
    private Long count_orders;
    @Getter @Setter
    private String description;
    @Getter @Setter
    private String phone_number;
    @Getter @Setter
    private String email;

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
}
