package shop.mtcoding.boardproject.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    // 이메일(계정아이디)을 통해 해당 튜플을 찾는 네이티브 쿼리
    @Query(value = "select*from user_tb where email = :email", nativeQuery = true)
    User FindByemail(@Param("email") String email);

    // JPQL 위의 쿼리와 동일
    @Query("select u from User u where u.email = :email")
    User findByUserEmail(@Param("email") String email);

    // 회원유형으로 회원리스트 찾는 쿼리
    List<User> findByRole(@Param("role") Integer role);

    // 스킬을 가진 유저를 찾는 네이티브 쿼리
    @Query(value = "select user_tb.* from skill_tb join userSkill_tb on skill_tb.id = userSkill_tb.skill_id inner join user_tb on userSkill_tb.user_id = user_tb.id where skill_tb.skillname = :skillname", nativeQuery = true)
    List<User> findBykillResumeReturnUser(@Param("skillname") String skillname);

    // List<User> findByCompname(@Param("compname") String compname);

    // List<User> findByUsername(@Param("username") String username);

    List<User> findByUsernameContainingOrCompnameContaining(@Param("username") String username,
                                                            @Param("compname") String compname);

    @Modifying
    @Query(value = "update user_tb set message = :boolean where id = :id", nativeQuery = true)
    Integer message(@Param("boolean") Boolean message, @Param("id") Integer id);

    @Modifying
    @Query(value = "update user_tb set state_change = :boolean where username = :username", nativeQuery = true)
    Integer stateChange(@Param("boolean") Boolean message, @Param("username") String username);
}