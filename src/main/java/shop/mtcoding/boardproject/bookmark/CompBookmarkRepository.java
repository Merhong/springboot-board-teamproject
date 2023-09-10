package shop.mtcoding.boardproject.bookmark;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompBookmarkRepository extends JpaRepository<CompBookmark, Integer> {

    @Query(value = "SELECT * FROM COMPBOOKMARK_TB ct left outer join posting_tb pt on pt.id = ct.posting_id where pt.user_id = :userId", nativeQuery = true)
    List<CompBookmark> findAllByUserId(@Param("userId") Integer userId);


    @Query(value = "select RT.* from compbookmark_tb CT join resume_tb RT on CT.resume_id = RT.id where CT.user_id= :compId", nativeQuery = true)
    List<CompBookmark> findByUserId(Integer compId);

    // @Modifying // db 변화 있으면 적어야함
    // @Query(value = "delete from compbookmark_tb where user_id = :userId and resume_id = :resumeId", nativeQuery = true)
    void deleteByUserIdAndResumeId(@Param("userId") Integer userId, @Param("resumeId") Integer resumeId);

    CompBookmark findByUserIdAndResumeId(@Param("userId") Integer userId, @Param("resumeId") Integer resumeId);

}
