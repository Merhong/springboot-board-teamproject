package shop.mtcoding.boardproject.skill;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.mtcoding.boardproject.user.User;

import javax.persistence.*;

@NoArgsConstructor
@Setter
@Getter
@Table(name = "userskill_tb")
@Entity
public class UserSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Skill skill;
}
