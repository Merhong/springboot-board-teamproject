package shop.mtcoding.boardproject.reply;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Integer> {

    // JpaRepository에는 Entity만 들어갈수 있다 >> Reply객체가 들어가야함
    @Query
    List<Reply> findByBoardId(@Param("boardId") Integer boardId);
}
