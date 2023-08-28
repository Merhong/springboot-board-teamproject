package shop.mtcoding.boardproject.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;


public interface BoardRepository extends JpaRepository<Board, Integer> {

}
