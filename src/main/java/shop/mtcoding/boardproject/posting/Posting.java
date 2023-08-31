package shop.mtcoding.boardproject.posting;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import shop.mtcoding.boardproject.user.User;

import javax.persistence.*;
import java.sql.Timestamp;

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

    private String homepage;

    private String position;

    private Timestamp expiryDate;

    @CreationTimestamp
    private Timestamp createdAt;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private User user; // 1+N

    @Builder
    public Posting(String title, String desc, String photo, String region, String homepage, String position, Timestamp expiryDate, Timestamp createdAt, User user) {
        this.title = title;
        this.desc = desc;
        this.photo = photo;
        this.region = region;
        this.homepage = homepage;
        this.position = position;
        this.expiryDate = expiryDate;
        this.user = user;
    }

}