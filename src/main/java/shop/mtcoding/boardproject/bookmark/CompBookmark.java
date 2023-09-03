package shop.mtcoding.boardproject.bookmark;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.mtcoding.boardproject.posting.Posting;
import shop.mtcoding.boardproject.resume.Resume;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/* 기업의 북마크 중간 테이블 */
// 공고id, 이력서 id

@NoArgsConstructor
@Setter
@Getter
@Table(name = "compbookmark_tb")
@Entity
public class CompBookmark {

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

    public CompBookmark(Posting posting, Resume resume) {
        this.posting = posting;
        this.resume = resume;
    }

}