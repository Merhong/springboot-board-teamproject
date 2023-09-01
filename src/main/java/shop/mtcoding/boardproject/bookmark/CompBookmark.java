package shop.mtcoding.boardproject.bookmark;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.mtcoding.boardproject.posting.Posting;
import shop.mtcoding.boardproject.resume.Resume;

import javax.persistence.*;

@NoArgsConstructor
@Setter
@Getter
@Table(name = "compbookmark_tb")
@Entity
public class CompBookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Posting posting;

    @ManyToOne(fetch = FetchType.LAZY)
    private Resume resume;

    public CompBookmark(Posting posting, Resume resume) {
        this.posting = posting;
        this.resume = resume;
    }

}