package shop.mtcoding.boardproject.bookmark;

import java.util.List;

import org.apache.tomcat.jni.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import shop.mtcoding.boardproject.posting.Posting;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Integer> {

    @Query(value = "select * from bookmark_tb where user_Id = :userId", nativeQuery = true)
    List<Bookmark> findAllByUserId(@Param("userId") Integer userId);

}
