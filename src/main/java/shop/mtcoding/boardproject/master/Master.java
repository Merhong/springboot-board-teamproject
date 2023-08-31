package shop.mtcoding.boardproject.master;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import shop.mtcoding.boardproject.user.User;

import javax.persistence.*;
import java.sql.Timestamp;

@NoArgsConstructor
@Setter
@Getter
@Table(name = "master_tb")
@Entity // ddl-auto가 create
public class Master {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String title;

    private String content;

    @CreationTimestamp
    private Timestamp createdAt;


    // fetch EAGER가 디폴트. Lazy는 필요없을때 조회 안한다!!!
    @JsonIgnore // 게시글에 작성자의 정보는 없으니 JSON에 안나오게 만듬
    @ManyToOne(fetch = FetchType.LAZY)
    private User user; // 1+N


}