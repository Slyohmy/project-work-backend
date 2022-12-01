package projectwork.backend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import projectwork.backend.enums.ERole;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ERole role;

    public Role(ERole role) {
        this.role = role;
    }

/*    @ManyToMany(mappedBy = "roles")
    private Set<User> users;*/
}
