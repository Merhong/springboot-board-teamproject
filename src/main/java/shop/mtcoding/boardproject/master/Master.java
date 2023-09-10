package shop.mtcoding.boardproject.master;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import shop.mtcoding.boardproject.user.User;

import javax.persistence.*;
import java.sql.Timestamp;

/* 고객센터 엔티티 클래스 */

@NoArgsConstructor
@Setter
@Getter
@Table(name = "master_tb")
@Entity // ddl-auto가 create
public class Master {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 문의 유형
    @Column(nullable = false, length = 100)
    private String category;

    // 문의 제목
    @Column(nullable = false, length = 100)
    private String title;

    // 문의 내용
    private String content;

    // Log용 타임스탬프
    @CreationTimestamp
    private Timestamp createdAt;

    // 유저 테이블 ORM
    // fetch EAGER가 디폴트. Lazy는 필요없을때 조회 안한다!!!
    @JsonIgnore // 게시글에 작성자의 정보는 없으니 JSON에 안나오게 만듬
    @ManyToOne(fetch = FetchType.LAZY)
    private User user; // 1+N

    @Builder
    public Master(Integer id, String category, String title, String content, User user) {
        this.id = id;
        this.category = category;
        this.title = title;
        this.content = content;
        this.user = user;
    }

}