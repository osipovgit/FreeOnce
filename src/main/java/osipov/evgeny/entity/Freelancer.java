package osipov.evgeny.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "freelancer")
public class Freelancer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;
    @Getter @Setter
    private String fio;
    @Getter @Setter
    private String username;
    @Getter @Setter
    private Long count_deals;
    @Getter @Setter
    private String description;
    @Getter @Setter
    private String phone_number;
    @Getter @Setter
    private String email;
}
