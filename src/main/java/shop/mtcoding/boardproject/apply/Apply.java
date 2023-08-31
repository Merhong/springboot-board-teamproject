package shop.mtcoding.boardproject.apply;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import shop.mtcoding.boardproject.posting.Posting;
import shop.mtcoding.boardproject.user.User;

import javax.persistence.*;
import java.sql.Timestamp;

@NoArgsConstructor
@Setter
@Getter
@Table(name = "apply_tb")
@Entity
public class Apply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 직책
    private String position;

    // 상태
    private String statement;

    @CreationTimestamp
    private Timestamp createdAt;

    // 유저 테이블 ORM
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    // 공고 테이블 ORM
    @ManyToOne(fetch = FetchType.LAZY)
    private Posting posting;

    @Builder
    public Apply(Integer id, String position, String statement, Timestamp createdAt, User user, Posting posting) {
        this.id = id;
        this.position = position;
        this.statement = statement;
        this.createdAt = createdAt;
        this.user = user;
        this.posting = posting;
    }
}
