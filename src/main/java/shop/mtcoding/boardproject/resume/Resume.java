package shop.mtcoding.boardproject.resume;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.mtcoding.boardproject.user.User;

import javax.persistence.*;

/* 이력서 엔티티 클래스 */

@NoArgsConstructor
@Setter
@Getter
@Table(name = "resume_tb")
@Entity // ddl-auto가 create
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 이력서 제목
    private String title;

    // 이력서 경력
    private String career;

    // 이력서 학력
    private String grade;

    // 이력서 자기소개
    @Lob
    private String personalStatement;

    // 이력서 공개여부
    private Boolean disclosure;
    @JsonIgnore

    // 유저 테이블 ORM
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