package shop.mtcoding.boardproject.resume;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shop.mtcoding.boardproject.user.User;

import java.util.List;

public interface ResumeRepository extends JpaRepository<Resume, Integer> {

    // id를 통해 이력서를 찾는 쿼리
    @Query("select r from Resume r where r.id = :id")
    Resume findByResume(@Param("id") Integer id);

    // 유저id를 통해 이력서를 찾는 조인 쿼리
    // select * from resume_tb r inner join user_tb u on r.user_id =u.id where
    // u.id=2;
    @Query("select r from Resume r inner join r.user u where u.id= :id")
    List<Resume> findByResumeUser(@Param("id") Integer id);

    // 해당 스킬을 가지고 있는 이력서를 찾는 조인 쿼리
    @Query(value = "select resume_tb.* from skill_tb join userSkill_tb on skill_tb.id = userSkill_tb.skill_id join user_tb on userSkill_tb.user_id = user_tb.id join resume_tb on user_tb.id = resume_tb.user_id where skill_tb.skillname = :skillname", nativeQuery = true)
    List<Resume> findBySkillReturnResume(@Param("skillname") String skillname);

    // 해당 스킬을 가지고 있는 유저를 찾는 조인 쿼리
    @Query(value = "select user_tb.* from skill_tb join userSkill_tb on skill_tb.id = userSkill_tb.skill_id inner join user_tb on userSkill_tb.user_id = user_tb.id where skill_tb.skillname = :skillname", nativeQuery = true)
    List<User> findResumeByCompId(@Param("skillname") String skillname);

    // 유저id(기업)를 통해 기업이 북마크한 이력서를 찾는 조인 쿼리
    @Query(value = "select RT.* from compbookmark_tb CT join resume_tb RT on CT.resume_id = RT.id where CT.user_id = :compId", nativeQuery = true)
    List<Resume> findResumeByCompId(@Param("compId") Integer compId);

    List<Resume> findByTitleContaining(@Param("title") String keyword);

    @Query(value = "SELECT resume_tb.* " +
            "FROM resume_tb " +
            "JOIN user_tb ON resume_tb.user_id = user_tb.id " +
            "WHERE user_tb.username LIKE %:username% " +
            "UNION " +
            "SELECT resume_tb.* " +
            "FROM resume_tb " +
            "WHERE title LIKE %:title% ", nativeQuery = true)
    List<Resume> findResumeByTitleOrJoinUserUsername(@Param("username") String username, @Param("title") String title);

}