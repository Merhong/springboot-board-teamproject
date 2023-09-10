package shop.mtcoding.boardproject.bookmark;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import shop.mtcoding.boardproject.resume.Resume;
import shop.mtcoding.boardproject.user.User;

import javax.persistence.*;

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
    private User user;

    // 이력서 테이블 ORM
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Resume resume;

    public CompBookmark(User user, Resume resume) {
        this.user = user;
        this.resume = resume;
    }

    // public CompBookmark(Integer user, Integer resume) {
    //     this.user = new User();
    //     this.user.setId(user);
    //     this.resume = new Resume();
    //     this.resume.setId(resume);
    // }

}