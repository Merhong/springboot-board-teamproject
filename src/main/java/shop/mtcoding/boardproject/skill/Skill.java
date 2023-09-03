package shop.mtcoding.boardproject.skill;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/* 스킬 엔티티 클래스 */

@NoArgsConstructor
@Setter
@Getter
@Table(name = "skill_tb")
@Entity
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 스킬이름
    private String skillname;

    @Builder
    public Skill(String skillname) {
        this.skillname = skillname;
    }

}
