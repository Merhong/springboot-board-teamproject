package shop.mtcoding.boardproject.resume;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ResumeRepository extends JpaRepository<Resume, Integer> {

    @Query("select r from Resume r where r.id = :id")
    Resume findByResume(@Param("id") Integer id);

    // select * from resume_tb r inner join user_tb u on r.user_id =u.id where
    // u.id=2;
    @Query("select r from Resume r inner join r.user u where u.id= :id")
    List<Resume> findByResumeUser(@Param("id") Integer id);

    // 이력서를 제목을 통해 검색하는 JPQL 쿼리 메서드
    @Query("select r from Resume r where r.title = :title")
    List<Resume> findByTitle(@Param("title") String title);
}