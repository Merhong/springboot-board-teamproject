package shop.mtcoding.boardproject.recommend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecommendRepository extends JpaRepository<Recommend, Integer> {

    List<Recommend> findByPostingId(@Param("postingId") Integer postingId);

    List<Recommend> findByResumeId(@Param("resumeId") Integer resumeId);

    Recommend findByPostingIdAndResumeId(@Param("postingId") Integer postingId, @Param("resumeId") Integer resumeId);

    // 추천테이블과 공고ID와 유저ID가 같은 이력서 테이블을 조인하는 쿼리
    @Query(value = "SELECT recommend_tb.* FROM recommend_tb "+
                    "JOIN resume_tb ON recommend_tb.resume_id = resume_tb.id "+
                    "WHERE recommend_tb.posting_id = :postingId "+
                    "AND resume_tb.user_id = :userId", nativeQuery = true)
    List<Recommend> findByPostingIdAndResumeUserId(@Param("postingId") Integer postingId, @Param("userId") Integer userId);



}
