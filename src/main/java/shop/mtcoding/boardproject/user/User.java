package shop.mtcoding.boardproject.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;

import java.sql.Date;
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

    @Column(nullable = true)
    private String photo;

    private String address;

    private Date birth;

    private Integer role;

    @CreationTimestamp // Insert 할때 시간을 적어준다.
    private Timestamp createdAt;

    @Builder
    public User(Integer id, String username, String compname, String compRegister, String password, String email,
            String tel, String photo, String address, Date birth, Integer role) {
        this.id = id;
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
