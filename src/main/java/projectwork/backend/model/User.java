package projectwork.backend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import projectwork.backend.payload.UserInfoResponse;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Table(name = "users")
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username")
    private String username;

    @Email
    @Column(name = "email")
    private String email;

    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User(String username, String email, String password) {
        super();
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public UserInfoResponse userInfoResponse() {
        return new UserInfoResponse(
                id,
                username,
                email,
                roles.stream()
                        .map(role -> role.getRole().toString())
                        .collect(Collectors.toList()));
    }
}
