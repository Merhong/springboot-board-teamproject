package shop.mtcoding.boardproject.resume;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.mtcoding.boardproject.user.User;

import javax.persistence.*;

@NoArgsConstructor
@Setter
@Getter
@Table(name = "resume_tb")
@Entity // ddl-auto가 create
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String career;

    private String grade;

    @Lob
    private String personalStatement;

    private Boolean disclosure;
    @JsonIgnore

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Builder
    public Resume(String title, String career, String grade, String personalStatement, Boolean disclosure, User user) {
        this.title = title;
        this.career = career;
        this.grade = grade;
        this.personalStatement = personalStatement;
        this.disclosure = disclosure;
        this.user = user;
    }
}