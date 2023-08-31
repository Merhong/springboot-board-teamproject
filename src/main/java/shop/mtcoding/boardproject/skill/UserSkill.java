package shop.mtcoding.boardproject.skill;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.mtcoding.boardproject.user.User;

import javax.persistence.*;

@NoArgsConstructor
@Setter
@Getter
@Table(name = "user_skill_tb")
@Entity
public class UserSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(nullable = true)
    private boolean java;

    @Column(nullable = true)
    private boolean db;

    @Column(nullable = true)
    private boolean htmlAndCss;

    @Column(nullable = true)
    private boolean python;

    @Column(nullable = true)
    private boolean javascript;

    @Builder
    public UserSkill(User user, boolean java, boolean db, boolean htmlAndCss, boolean python, boolean javascript) {
        this.user = user;
        this.java = java;
        this.db = db;
        this.htmlAndCss = htmlAndCss;
        this.python = python;
        this.javascript = javascript;
    }

}
