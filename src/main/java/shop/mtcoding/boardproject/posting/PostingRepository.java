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
    
    List<Posting> findByTitleContaining(@Param("title") String title);
    
}
