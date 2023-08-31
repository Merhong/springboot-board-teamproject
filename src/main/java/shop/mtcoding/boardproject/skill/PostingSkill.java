package shop.mtcoding.boardproject.skill;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.mtcoding.boardproject.posting.Posting;

import javax.persistence.*;

@NoArgsConstructor
@Setter
@Getter
@Table(name = "postingskill_tb")
@Entity
public class PostingSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Posting posting;

    @ManyToOne(fetch = FetchType.LAZY)
    private Skill skill;
}
