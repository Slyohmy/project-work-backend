package projectwork.backend.model;

import lombok.Getter;
import lombok.Setter;
import projectwork.backend.enums.ERole;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ERole role;
}
