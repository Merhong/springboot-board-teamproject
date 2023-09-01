package shop.mtcoding.boardproject.apply;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.mtcoding.boardproject.posting.Posting;
import shop.mtcoding.boardproject.resume.Resume;

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
