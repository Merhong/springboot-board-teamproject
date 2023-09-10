package shop.mtcoding.boardproject.recommend;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import shop.mtcoding.boardproject.posting.Posting;
import shop.mtcoding.boardproject.resume.Resume;

import javax.persistence.*;
import java.sql.Timestamp;

/* 기업추천, 인재추천 테이블 - 중간테이블 */
// 유저id, 공고id, 이력서id

@NoArgsConstructor
@Setter
@Getter
@Table(name = "recommend_tb")
@Entity
public class Recommend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 공고 테이블 ORM
    @ManyToOne(fetch = FetchType.LAZY)
    private Posting posting;

    // 이력서 테이블 ORM
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Resume resume;

    // 대기/수락/거절
    private String statement;

    private Timestamp createdAt;

}
