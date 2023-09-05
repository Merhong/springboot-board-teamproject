package shop.mtcoding.boardproject.posting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PostingQueryRepository {

    @Autowired
    private EntityManager em;

    public List<Posting> joinSkillPosting(List<String> skillnameList) {
        String sql = "select posting_tb.* from skill_tb " +
                "inner join postingskill_tb on skill_tb.id = postingskill_tb.skill_id " +
                "inner join posting_tb on postingskill_tb.posting_id = posting_tb.id ";

        for (int i = 0; i < skillnameList.size(); i++) {
            if (i == 0) {
                sql += "where skill_tb.skillname = :skillname"+i+" ";
            } else {
                sql += "or skill_tb.skillname = :skillname"+i+" ";
            }

        }

        Query query = em.createNativeQuery(sql, Posting.class);
        for (int i = 0; i < skillnameList.size(); i++) {
            query.setParameter("skillname"+i, skillnameList.get(i));
        }
        

        return (List<Posting>) query.getResultList();
    }

    // public static void main(String[] args) {
    //     List<String> skillnameList = Arrays.asList("css", "java", "spring");

    //     String sql = "select posting_tb.* from skill_tb " +
    //             "inner join postingskill_tb on skill_tb.id = postingskill_tb.skill_id " +
    //             "inner join posting_tb on postingskill_tb.posting_id = posting_tb.id ";

    //     for (int i = 0; i < skillnameList.size(); i++) {
    //         if (i == 0) {
    //             sql += "where skill_tb.skillname = :skillname"+i+" ";
    //         } else {
    //             sql += "or skill_tb.skillname = :skillname"+i+" ";
    //         }
    //     }
    //     System.out.println(sql);
    // }
}
