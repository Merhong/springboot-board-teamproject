package shop.mtcoding.boardproject.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "select*from user_tb where email = :email", nativeQuery = true)
    User FindByemail(@Param("email")String email);

}