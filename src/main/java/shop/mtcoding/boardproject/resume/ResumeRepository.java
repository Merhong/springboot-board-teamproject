package shop.mtcoding.boardproject.resume;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ResumeRepository extends JpaRepository<Resume, Integer> {


    @Query("select r from Resume r where r.id = :id")
    Resume findByResumeAll(@Param("id") Integer id);
}
