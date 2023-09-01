package shop.mtcoding.boardproject.posting;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import shop.mtcoding.boardproject.skill.PostingSkill;
import shop.mtcoding.boardproject.user.User;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@Table(name = "posting_tb")
@Entity // ddl-auto가 create
public class Posting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String title;

    private String desc;

    private String photo;

    private String region;

    @Column(nullable = false)
    private String position;

    private String career;

    private String education;

    private Timestamp expiryDate;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private User user; // 1+N

    @CreationTimestamp
    private Timestamp createdAt;

    @Builder
    public Posting(Integer id, String title, String desc, String photo, String region, String position, String career,
            String education, Timestamp expiryDate, User user, Timestamp createdAt) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.photo = photo;
        this.region = region;
        this.position = position;
        this.career = career;
        this.education = education;
        this.expiryDate = expiryDate;
        this.user = user;
        this.createdAt = createdAt;
    }

}