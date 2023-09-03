package shop.mtcoding.boardproject.skill;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.mtcoding.boardproject.user.User;

import javax.persistence.*;

/* 개인 보유 스킬 중간 테이블 */

@NoArgsConstructor
@Setter
@Getter
@Table(name = "userskill_tb")
@Entity
public class UserSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // User 테이블 ORM
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    // Skill 테이블 ORM
    @ManyToOne(fetch = FetchType.LAZY)
    private Skill skill;
}
