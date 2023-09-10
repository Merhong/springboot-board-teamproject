package shop.mtcoding.boardproject.apply;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import shop.mtcoding.boardproject.posting.Posting;
import shop.mtcoding.boardproject.resume.Resume;
import shop.mtcoding.boardproject.user.User;

import javax.persistence.*;
import java.sql.Timestamp;

/* 지원하기 중간 테이블 */
// 이력서id, 공고id

@NoArgsConstructor
@Setter
@Getter
@Table(name = "apply_tb")
@Entity
public class Apply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 지원 상태
    private String statement = "대기";

    // Log용 타임스탬프
    private Timestamp createdAt;

    // 유저 테이블 ORM
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    // 이력서 테이블 ORM
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Resume resume;

    // 공고 테이블 ORM
    @ManyToOne(fetch = FetchType.LAZY)
    private Posting posting;

    @Builder
    public Apply(Resume resume, Posting posting, String statement, User user) {
        this.resume = resume;
        this.posting = posting;
        this.statement = statement;
        this.user = user;
    }
}
