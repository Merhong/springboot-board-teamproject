package shop.mtcoding.boardproject.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import shop.mtcoding.boardproject.posting.Posting;
import shop.mtcoding.boardproject.skill.UserSkill;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@Table(name = "user_tb")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer role; // 관리자0? 개인1 회사2

    private String username; // 회사X

    private Date birth; // 회사X

    private String compname; // 개인X

    private String compRegister; // 개인X

    private String homepage; // 개인X 

    private String email;

    private String password;

    private String tel;

    @Column(nullable = true)
    private String photo;

    private String address;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Posting> postingList = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserSkill> userSkillList = new ArrayList<>();

    @CreationTimestamp // Insert 할때 시간을 적어준다.
    private Timestamp createdAt;


    @Builder
    public User(Integer id, Integer role, String username, Date birth, String compname, String compRegister,
                String homepage, String email, String password, String tel, String photo, String address,
                List<Posting> postingList, List<UserSkill> userSkillList, Timestamp createdAt) {
        this.id = id;
        this.role = role;
        this.username = username;
        this.birth = birth;
        this.compname = compname;
        this.compRegister = compRegister;
        this.homepage = homepage;
        this.email = email;
        this.password = password;
        this.tel = tel;
        this.photo = photo;
        this.address = address;
        this.postingList = postingList;
        this.userSkillList = userSkillList;
        this.createdAt = createdAt;
    }


}
