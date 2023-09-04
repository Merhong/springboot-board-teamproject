package shop.mtcoding.boardproject.skill;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.mtcoding.boardproject.posting.Posting;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

/* 공고 요구스킬 중간 테이블 */
// 공고id, 스킬id

@NoArgsConstructor
@Setter
@Getter
@Table(name = "postingskill_tb")
@Entity
public class PostingSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private Posting posting;

    @ManyToOne(fetch = FetchType.LAZY)
    private Skill skill;
}
