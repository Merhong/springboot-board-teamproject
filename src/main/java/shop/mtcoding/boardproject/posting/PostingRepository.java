package shop.mtcoding.boardproject.posting;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostingRepository extends JpaRepository<Posting, Integer> {

    // 유저id에 해당하는 공고리스트
    List<Posting> findByUserId(@Param("userId") Integer compId);

    // 스킬을 가지고 있는 공고를 찾는 쿼리 (검색시에 사용)
    @Query(value = "select posting_tb.* from skill_tb inner join postingskill_tb on skill_tb.id = postingskill_tb.skill_id inner join posting_tb on postingskill_tb.posting_id = posting_tb.id where skill_tb.skillname = :skillname", nativeQuery = true)
    List<Posting> joinSkillPosting(@Param("skillname") String skillname);

    // @Query(value = "select posting_tb.* from skill_tb join postingskill_tb on
    // skill_tb.id = postingskill_tb.skill_id join posting_tb on
    // postingskill_tb.posting_id = posting_tb.id where skill_tb.skillname =
    // :skillname", nativeQuery = true)
    // List<Posting> findBykillResumeReturnComp(@Param("skillname") String
    // skillname);

    @Query(value = "select * from posting_tb where position = :position", nativeQuery = true)
    List<Posting> findByPosition(@Param("position") String position);

    List<Posting> findByTitleContaining(@Param("title") String title);

    @Query(value = "SELECT posting_tb.* " +
            "FROM posting_tb " +
            "JOIN user_tb ON posting_tb.user_id = user_tb.id " +
            "WHERE user_tb.compname LIKE %:compname% " +
            "UNION " +
            "SELECT posting_tb.* " +
            "FROM posting_tb " +
            "WHERE title LIKE %:title% ", nativeQuery = true)
    List<Posting> findPostingByTitleOrJoinUserCompname(@Param("compname") String compname, @Param("title") String title);

}
