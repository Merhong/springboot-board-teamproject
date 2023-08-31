package shop.mtcoding.boardproject.skill;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.lang.Nullable;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import shop.mtcoding.boardproject.user.User;

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
