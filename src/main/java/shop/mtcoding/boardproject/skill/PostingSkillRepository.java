package shop.mtcoding.boardproject.skill;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface PostingSkillRepository extends JpaRepository<PostingSkill, Integer> {

    List<PostingSkill> deleteByPostingId(@Param("postingId") Integer postingId);
}
