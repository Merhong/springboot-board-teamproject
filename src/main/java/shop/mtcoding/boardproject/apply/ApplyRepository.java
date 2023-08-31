package shop.mtcoding.boardproject.apply;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ApplyRepository extends JpaRepository<Apply, Integer> {

    // @Query(value = "select * from Apply_tb at left outer join posting_tb pt on at.posting_id = pt.id left outer join resume_tb rt on rt.id = at.resume_id where rt.user_id = :userId;", nativeQuery = true)
    // public List<ApplyResponse.TestApplyListDTO> findApplyList(@Param("userId") Integer userId, @Param("postingId") Integer postingId);

}
