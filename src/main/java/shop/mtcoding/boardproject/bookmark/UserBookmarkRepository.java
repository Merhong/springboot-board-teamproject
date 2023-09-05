package shop.mtcoding.boardproject.bookmark;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import shop.mtcoding.boardproject.posting.Posting;
import shop.mtcoding.boardproject.skill.PostingSkill;
import shop.mtcoding.boardproject.user.User;

import java.util.List;

@Repository
public interface UserBookmarkRepository extends JpaRepository<UserBookmark, Integer> {

    @Query(value = "select * from userbookmark_tb where user_Id = :userId", nativeQuery = true)
    List<UserBookmark> findAllByUserId(@Param("userId") Integer userId);

    List<UserBookmark> findByPostingId(@Param("postingId") Integer postingId);


    @Query(value = "select * from userbookmark_tb where posting_id = :postiongId and user_Id  = :userId", nativeQuery = true)
    List<UserBookmark> findByPostingIdAndUserId(@Param("postiongId") Integer postiongId,
            @Param("userId") Integer userId);

    @Modifying
    @Query(value = "delete from userbookmark_tb where posting_id = :postiongId and user_Id  = :userId", nativeQuery = true)
    Integer deleteByPostingAndUserId(@Param("postiongId") Integer postiongId,
            @Param("userId") Integer userId);

    @Modifying
    @Query(value = "insert into userbookmark_tb (posting_id,  user_id ) values (:postiongId, :userId)", nativeQuery = true)
    Integer saveByPostingAndUserId(@Param("postiongId") Integer postiongId, @Param("userId") Integer userId);

}
