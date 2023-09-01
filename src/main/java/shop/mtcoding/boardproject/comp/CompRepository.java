package shop.mtcoding.boardproject.comp;


import org.springframework.data.jpa.repository.JpaRepository;
import shop.mtcoding.boardproject.user.User;

public interface CompRepository extends JpaRepository<User, Integer> {
}
