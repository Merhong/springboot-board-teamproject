package shop.mtcoding.boardproject.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import shop.mtcoding.boardproject.posting.Posting;
import shop.mtcoding.boardproject.resume.Resume;
import shop.mtcoding.boardproject.skill.UserSkill;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/* 유저 엔티티 클래스 */

@NoArgsConstructor
@Setter
@Getter
@Table(name = "user_tb")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 회원유형
    private Integer role; // 관리자0? 개인1 회사2

    // 개인이름, 기업은 null
    private String username;

    // 생년월일, 기업은 null
    private Date birth;

    // (추가) 개인이 원하는 직무, 기업은 null
    private String position;

    // 기업이름, 개인은 null
    private String compname;

    // 사업자번호, 개인은 null
    private String compRegister;

    // 홈페이지, 개인은 null
    private String homepage;

    // 이메일, 로그인시 아이디로 사용
    private String email;

    // 비밀번호
    private String password;

    // 전화번호
    private String tel;

    // 프로필 사진
    @Column(nullable = true)
    private String photo;

    // 주소
    private String address;

    // 양방향 매핑 (공고 리스트)
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Posting> postingList = new ArrayList<>();

    // 양방향 매핑 (개인 스킬 리스트)
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserSkill> userSkillList = new ArrayList<>();

    // 이력서 목록
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)

    private List<Resume> resumeList = new ArrayList<>();

    // Log용 타임스탬프
    @CreationTimestamp // Insert 할때 시간을 적어준다.
    private Timestamp createdAt;

    @Column(columnDefinition = "boolean default false")
    private Boolean message;

    @Column(columnDefinition = "boolean default false")
    private Boolean stateChange;

    @PrePersist
    public void prePersist() {
        if (photo == null) {
            photo = "basic.jpg";
        }
    }

    @Builder
    public User(Integer id, Integer role, String username, Date birth, String position, String compname,
                String compRegister,
                String homepage, String email, String password, String tel, String photo, String address,
                List<Posting> postingList, List<UserSkill> userSkillList, List<Resume> resumeList, Timestamp createdAt) {
        this.id = id;
        this.role = role;
        this.username = username;
        this.birth = birth;
        this.position = position;
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
        this.resumeList = resumeList;
        this.createdAt = createdAt;
        this.message = false;
        this.stateChange = false;
    }
}
