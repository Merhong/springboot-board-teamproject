package shop.mtcoding.boardproject.reply;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {

    @Query(value = "select * from reply_tb where master_id = :id", nativeQuery = true)
    List<Reply> findByMasterId(@Param("id") Integer id);

}
