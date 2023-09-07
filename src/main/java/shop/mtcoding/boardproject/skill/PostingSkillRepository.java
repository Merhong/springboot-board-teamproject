package shop.mtcoding.boardproject.skill;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostingSkillRepository extends JpaRepository<PostingSkill, Integer> {

    List<PostingSkill> deleteByPostingId(@Param("postingId") Integer postingId);

    List<PostingSkill> findByPostingId(@Param("postingId") Integer postingId);

    List<PostingSkill> findBySkill(@Param("skill") String skill);

}
