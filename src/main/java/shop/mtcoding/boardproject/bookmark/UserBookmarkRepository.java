package shop.mtcoding.boardproject.bookmark;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserBookmarkRepository extends JpaRepository<UserBookmark, Integer> {

    @Query(value = "select * from userbookmark_tb where user_Id = :userId", nativeQuery = true)
    List<UserBookmark> findAllByUserId(@Param("userId") Integer userId);

    List<UserBookmark> findByPostingId(@Param("postingId") Integer postingId);

    @Query(value = "select * from userbookmark_tb where posting_id = :postingId and user_Id  = :userId", nativeQuery = true)
    UserBookmark findByUserIdAndPostingId(@Param("postingId") Integer postingId, @Param("userId") Integer userId);

    @Query(value = "select * from userbookmark_tb where posting_id = :postingId and user_Id  = :userId", nativeQuery = true)
    List<UserBookmark> findByPostingIdAndUserId(@Param("postingId") Integer postingId,
                                                @Param("userId") Integer userId);

    @Modifying
    @Query(value = "delete from userbookmark_tb where posting_id = :postingId and user_Id  = :userId", nativeQuery = true)
    Integer deleteByPostingAndUserId(@Param("postingId") Integer postingId,
                                     @Param("userId") Integer userId);

    @Modifying
    @Query(value = "insert into userbookmark_tb (posting_id,  user_id ) values (:postingId, :userId)", nativeQuery = true)
    Integer saveByPostingAndUserId(@Param("postingId") Integer postingId, @Param("userId") Integer userId);

}
