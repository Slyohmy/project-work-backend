package projectwork.backend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
@Getter
@Setter
@Table(name = "users")
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "full_name")
    private String fullName;

    @Email
    @Column(name = "email")
    private String email;

    public User(String fullName, String email){
        super();
        this.fullName = fullName;
        this.email = email;
    }
}
