package shop.mtcoding.boardproject.skill;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Setter
@Getter
@Table(name = "skill_list_tb")
@Entity
public class SkillList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @Builder
    public SkillList(String name) {
        this.name = name;
    }

}
