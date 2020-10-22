package osipov.evgeny.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order")
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
}
