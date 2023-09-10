package shop.mtcoding.boardproject.skill;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserSkillRepository extends JpaRepository<UserSkill, Integer> {

    // 유저가 가진 스킬을 insert하는 쿼리문
    // ResumeService에서 사용됨
    @Modifying
    @Query(value = "insert into userSkill_tb(skill_id, user_id) values(:skillId,:userId)", nativeQuery = true)
    void insertUserSkill(@Param("skillId") Integer skillId, @Param("userId") Integer userId);

    @Query(value = "select * from userSkill_tb where user_id = :userId", nativeQuery = true)
    List<UserSkill> selectUserSkill(@Param("userId") Integer userId);

    @Modifying
    @Query(value = "delete from userSkill_tb where user_id = :userId", nativeQuery = true)
    void deleteAllByUserId(@Param("userId") Integer userId);

}
