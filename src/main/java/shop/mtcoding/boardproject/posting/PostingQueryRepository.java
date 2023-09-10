package shop.mtcoding.boardproject.posting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class PostingQueryRepository {

    /* DI */
    @Autowired
    private EntityManager em;


    // SELECT DISTINCT pt.*
    // FROM ( SELECT DISTINCT posting_tb.*
    //     FROM skill_tb
    //     JOIN postingskill_tb ON skill_tb.id = postingskill_tb.skill_id
    //     JOIN posting_tb ON postingskill_tb.posting_id = posting_tb.id
    //     WHERE skill_tb.skillname = 'Spring'
    //     OR skill_tb.skillname = 'Git' 
    // ) pt
    // WHERE pt.position = '백엔드'
    // AND pt.region = '서울'
    public List<Posting> joinSkillPostingOneHitQuery(List<String> skillnameList, String position, String region) {
        String sql = " SELECT DISTINCT pt.* " +
                " FROM ( SELECT DISTINCT posting_tb.* " +
                " FROM skill_tb " +
                " JOIN postingskill_tb ON skill_tb.id = postingskill_tb.skill_id " +
                " JOIN posting_tb ON postingskill_tb.posting_id = posting_tb.id ";

        // 반복문을 통해 (스킬이름)체크박스에 체크된 횟수만큼 스킬이름을 or로 검색
        if (!(skillnameList.get(0).equals("all"))) {
            for (int i = 0; i < skillnameList.size(); i++) {
                if (i == 0) {
                    sql += " WHERE skill_tb.skillname = :skillname" + i + " ";
                } else {
                    sql += " OR skill_tb.skillname = :skillname" + i + " ";
                }
            }
        }

        sql += " ) pt ";

        if (position.equals("all") && region.equals("all")) {
            //
        } else if (!(region.equals("all")) && position.equals("all")) {
            sql += " WHERE pt.region = :region ";
        } else if (!(position.equals("all")) && region.equals("all")) {
            sql += " WHERE pt.position = :position ";
        } else {
            sql += " WHERE pt.position = :position " +
                    " AND pt.region = :region ";
        }

        System.out.println("테스트 메인페이지 sql : " + sql);


        // 매핑
        Query query = em.createNativeQuery(sql, Posting.class);
        if (!(skillnameList.get(0).equals("all"))) {
            for (int i = 0; i < skillnameList.size(); i++) {
                query.setParameter("skillname" + i, skillnameList.get(i));
            }
        }
        if (!(position.equals("all"))) {
            query.setParameter("position", position);
        }
        if (!(region.equals("all"))) {
            query.setParameter("region", region);
        }

        // 조회된 결과를 리스트에 담아서 리턴
        return (List<Posting>) query.getResultList();
    }


    // 공고에서 요구하는 스킬을 조인해서 조회하는 쿼리
    public List<Posting> joinSkillPosting(List<String> skillnameList) {
        String sql = "select DISTINCT posting_tb.* from skill_tb " +
                "inner join postingskill_tb on skill_tb.id = postingskill_tb.skill_id " +
                "inner join posting_tb on postingskill_tb.posting_id = posting_tb.id ";

        // 반복문을 통해 (스킬이름)체크박스에 체크된 횟수만큼 스킬이름을 or로 검색
        for (int i = 0; i < skillnameList.size(); i++) {
            if (i == 0) {
                sql += "where skill_tb.skillname = :skillname" + i + " ";
            } else {
                sql += "or skill_tb.skillname = :skillname" + i + " ";
            }
        }
        // 매핑
        Query query = em.createNativeQuery(sql, Posting.class);
        for (int i = 0; i < skillnameList.size(); i++) {
            query.setParameter("skillname" + i, skillnameList.get(i));
        }

        // 조회된 결과를 리스트에 담아서 리턴
        return (List<Posting>) query.getResultList();
    }


    // public List<Posting> joinSkillPositionPosting(UserRequest.SearchDTO searchDTO
    // ) {

    // String sql = "select DISTINCT posting_tb.* from skill_tb " +
    // "inner join postingskill_tb on skill_tb.id = postingskill_tb.skill_id " +
    // "inner join posting_tb on postingskill_tb.posting_id = posting_tb.id ";

    // List<Integer> skillId = searchDTO.getSkillId();
    // String position = searchDTO.getPosition();
    // if(position != null) {
    // sql += "where posting_tb.position = :position";
    // }
    // for (int i = 0; i < skillId.size(); i++) {
    // if (i == 0) {
    // sql += "where skill_tb.skillname = :skillname"+i+" ";
    // } else {
    // sql += "or skill_tb.skillname = :skillname"+i+" ";
    // }
    // }

    // public static void main(String[] args) {
    // List<String> skillnameList = Arrays.asList("css", "java", "spring");

    // String sql = "select posting_tb.* from skill_tb " +
    // "inner join postingskill_tb on skill_tb.id = postingskill_tb.skill_id " +
    // "inner join posting_tb on postingskill_tb.posting_id = posting_tb.id ";

    // for (int i = 0; i < skillnameList.size(); i++) {
    // if (i == 0) {
    // sql += "where skill_tb.skillname = :skillname"+i+" ";
    // } else {
    // sql += "or skill_tb.skillname = :skillname"+i+" ";
    // }
    // }
    // System.out.println(sql);
    // }
}
