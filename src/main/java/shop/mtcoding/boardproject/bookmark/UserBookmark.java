package shop.mtcoding.boardproject.bookmark;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.mtcoding.boardproject.posting.Posting;
import shop.mtcoding.boardproject.user.User;

import javax.persistence.*;

@NoArgsConstructor
@Setter
@Getter
@Table(name = "userbookmark_tb")
@Entity
public class UserBookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Posting posting;

    public UserBookmark(User user, Posting posting) {
        this.user = user;
        this.posting = posting;
    }
}
