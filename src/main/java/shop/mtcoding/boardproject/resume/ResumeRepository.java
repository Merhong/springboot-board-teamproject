package shop.mtcoding.boardproject.resume;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import shop.mtcoding.boardproject.user.User;

public interface ResumeRepository extends JpaRepository<Resume, Integer> {

    @Query("select r from Resume r where r.id = :id")
    Resume findByResume(@Param("id") Integer id);

    // select * from resume_tb r inner join user_tb u on r.user_id =u.id where
    // u.id=2;
    @Query("select r from Resume r inner join r.user u where u.id= :id")
    List<Resume> findByResumeUser(@Param("id") Integer id);

    @Query(value = "select resume_tb.* from skill_tb join userSkill_tb on skill_tb.id = userSkill_tb.skill_id join user_tb on userSkill_tb.user_id = user_tb.id join resume_tb on user_tb.id = resume_tb.user_id where skill_tb.skillname = :skillname", nativeQuery = true)
    List<Resume> findBySkillReturnResume(@Param("skillname") String skillname);

}