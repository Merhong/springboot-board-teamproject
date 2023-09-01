package shop.mtcoding.boardproject.skill;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface SkillRepository extends JpaRepository<Skill, Integer> {
}
