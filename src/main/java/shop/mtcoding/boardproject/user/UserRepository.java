package shop.mtcoding.boardproject.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "select*from user_tb where email = :email", nativeQuery = true)
    User FindByemail(@Param("email") String email);

    @Query("select u from User u where u.email = :email")
    User findByUserEmail(@Param("email") String email);

    List<User> findByRole(@Param("role") Integer role);

    @Query(value = "select user_tb.* from skill_tb join userSkill_tb on skill_tb.id = userSkill_tb.skill_id inner join user_tb on userSkill_tb.user_id = user_tb.id where skill_tb.skillname = :skillname", nativeQuery = true)
    List<User> findBykillResumeReturnUser(@Param("skillname") String skillname);

}