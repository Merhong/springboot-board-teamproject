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
    @Query(value = "SELECT recommend_tb.* FROM recommend_tb " +
            "JOIN resume_tb ON recommend_tb.resume_id = resume_tb.id " +
            "WHERE recommend_tb.posting_id = :postingId " +
            "AND resume_tb.user_id = :userId", nativeQuery = true)
    List<Recommend> findByPostingIdAndResumeUserId(@Param("postingId") Integer postingId, @Param("userId") Integer userId);

    @Query(value = " SELECT recommend_tb.id recommend_id, recommend_tb.posting_id posting_id, recommend_tb.resume_id resume_id, posting_tb.title posting_title, " +
            " resume_tb.title resume_title, resume_tb.career resume_career, resume_tb.grade resume_grade, " +
            " user_tb.username, recommend_tb.statement FROM recommend_tb " +
            " JOIN posting_tb ON recommend_tb.posting_id = posting_tb.id " +
            " JOIN resume_tb ON recommend_tb.resume_id = resume_tb.id " +
            " JOIN user_tb ON resume_tb.user_id = user_tb.id " +
            " WHERE posting_tb.user_id = :compId", nativeQuery = true)
    List<Object[]> findByCompId(@Param("compId") Integer compId);


}
