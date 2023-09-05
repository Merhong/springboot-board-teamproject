package shop.mtcoding.boardproject.recommend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecommendRepository extends JpaRepository<Recommend, Integer> {

    List<Recommend> findByPostingId(@Param("postingId") Integer postingId);   

}
