package shop.mtcoding.boardproject.skill;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Setter
@Getter
@Table(name = "skill_tb")
@Entity
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String skillname;

    @Builder
    public Skill(String skillname) {
        this.skillname = skillname;
    }

}