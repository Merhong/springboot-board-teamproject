package shop.mtcoding.boardproject.bookmark;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.mtcoding.boardproject.posting.Posting;
import shop.mtcoding.boardproject.user.User;

import javax.persistence.*;

/* 개인의 북마크 중간테이블 */
// 유저id, 공고id

@NoArgsConstructor
@Setter
@Getter
@Table(name = "userbookmark_tb")
@Entity
public class UserBookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 유저 테이블 ORM
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    // 공고 테이블 ORM
    @ManyToOne(fetch = FetchType.LAZY)
    private Posting posting;

    public UserBookmark(User user, Posting posting) {
        this.user = user;
        this.posting = posting;
    }
}
