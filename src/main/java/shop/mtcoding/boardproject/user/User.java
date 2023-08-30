package shop.mtcoding.boardproject.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@NoArgsConstructor
@Setter
@Getter
@Table(name = "user_tb")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String compname;

    private String compRegister;

    private String email;
    private String password;

    private String tel;
    private String photo;

    private String address;

    private Timestamp birth;

    private Integer role;

    @CreationTimestamp // Insert 할때 시간을 적어준다.
    private Timestamp createdAt;

    @Builder
    public User(String username, String compname, String compRegister, String password, String email, String tel,
            String photo, String address, Timestamp birth, Integer role) {
        this.username = username;
        this.compname = compname;
        this.compRegister = compRegister;
        this.password = password;
        this.email = email;
        this.tel = tel;
        this.photo = photo;
        this.address = address;
        this.birth = birth;
        this.role = role;
    }

}
