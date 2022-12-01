package projectwork.backend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "users")
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "full_name")
    private String fullName;

    @Email
    @Column(name = "email")
    private String email;

    private String password;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Role> roles = new HashSet<>();

    public User(String fullName, String email, String password) {
        super();
        this.fullName = fullName;
        this.email = email;
        this.password = password;
    }
}
