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

/* 공고 엔티티 클래스 */

@NoArgsConstructor
@Setter
@Getter
@Table(name = "posting_tb")
@Entity // ddl-auto가 create
public class Posting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 공고 제목
    @Column(nullable = false, length = 100)
    private String title;

    // 공고 내용
    private String desc;

    // 공고 사진
    private String photo;

    // 공고 관할 지역
    private String region;

    // 공고 요구 직무
    @Column(nullable = false)
    private String position;

    // 공고 요구 경력
    private String career;

    // 공고 요구 학력
    private String education;

    // 공고 만료일
    private Timestamp expiryDate;

    @OneToMany(mappedBy = "posting", fetch = FetchType.LAZY)
    private List<PostingSkill> postingSkill = new ArrayList<>();

    // 유저 테이블 ORM
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private User user; // 1+N

    // 로그용 타임스탬프
    @CreationTimestamp
    private Timestamp createdAt;

    @Builder
    public Posting(Integer id, String title, String desc, String photo, String region, String position, String career,
                   String education, Timestamp expiryDate, List<PostingSkill> postingSkill, User user, Timestamp createdAt) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.photo = photo;
        this.region = region;
        this.position = position;
        this.career = career;
        this.education = education;
        this.expiryDate = expiryDate;
        this.postingSkill = postingSkill;
        this.user = user;
        this.createdAt = createdAt;
    }
}