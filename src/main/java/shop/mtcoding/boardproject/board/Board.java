package shop.mtcoding.boardproject.board;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import shop.mtcoding.boardproject.reply.Reply;
import shop.mtcoding.boardproject.user.User;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@Table(name = "resume_tb")
@Entity // ddl-auto가 create
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String title;

    private String career;

    private String grade;

    @Lob
    @Column(nullable = true)
    private String personalStatement;

    private boolean disclosure;

    // fetch EAGER가 디폴트. Lazy는 필요없을때 조회 안한다!!!
    @JsonIgnore // 게시글에 작성자의 정보는 없으니 JSON에 안나오게 만듬
    @ManyToOne(fetch = FetchType.LAZY)
    private User user; // 1+N

    // // 양방향 매핑 (반대방향에서 걸어준 것.)
    // // ManyToOne : Eager가 디폴트
    // // OneToMany : Lazy가 디폴트
    // @JsonIgnoreProperties({ "board" }) // 자기를 다시 참조 못하게 막음, 무한 참조 방지
    // // @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
    // @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade =
    // CascadeType.ALL) // 게시글 삭제시 참조하고 있는 댓글 모두 삭제됨
    // private List<Reply> replies = new ArrayList<>();

}