package shop.mtcoding.boardproject.posting;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostingRepository extends JpaRepository<Posting, Integer> {

    List<Posting> findByUserId(@Param("userId") Integer compId);
}
