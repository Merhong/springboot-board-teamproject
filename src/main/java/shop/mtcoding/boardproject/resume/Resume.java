package shop.mtcoding.boardproject.resume;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import shop.mtcoding.boardproject.reply.Reply;
import shop.mtcoding.boardproject.user.User;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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

    private boolean disclosure;
    @JsonIgnore

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Builder
    public Resume(String title, String career, String grade, String personalStatement, boolean disclosure, User user) {
        this.title = title;
        this.career = career;
        this.grade = grade;
        this.personalStatement = personalStatement;
        this.disclosure = disclosure;
        this.user = user;
    }
}