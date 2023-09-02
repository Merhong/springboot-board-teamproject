package shop.mtcoding.boardproject.apply;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.mtcoding.boardproject.posting.Posting;
import shop.mtcoding.boardproject.resume.Resume;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Resume resume;

    @ManyToOne(fetch = FetchType.LAZY)
    private Posting posting;

    private String statement;

    private Timestamp createdAt;

    public Apply(Resume resume, Posting posting, String statement) {
        this.resume = resume;
        this.posting = posting;
        this.statement = statement;
    }

}
