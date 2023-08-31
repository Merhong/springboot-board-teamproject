package shop.mtcoding.boardproject.bookmark;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookmarkRepository extends JpaRepository<UserBookmark, Integer> {

    @Query(value = "select * from user_bookmark_tb where user_Id = :userId", nativeQuery = true)
    List<UserBookmark> findAllByUserId(@Param("userId") Integer userId);

}
