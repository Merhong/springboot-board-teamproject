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

    @Column(nullable = false, length = 20, unique = true)
    private String username;

    @Column(nullable = false, length = 60)
    private String password;

    @Column(nullable = false, length = 20)
    private String email;

    private String picUrl;

    @CreationTimestamp // Insert 할때 시간을 적어준다.
    private Timestamp createdAt;

    @Builder
    public User(Integer id, String username, String password, String email, String picUrl, Timestamp createdAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.picUrl = picUrl;
        this.createdAt = createdAt;
    }
}