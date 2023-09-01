package shop.mtcoding.boardproject.posting;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface PostingRepository extends JpaRepository<Posting, Integer> {

    List<Posting> findByUserId(@Param("userId") Integer compId);
}
