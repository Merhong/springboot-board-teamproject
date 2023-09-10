package shop.mtcoding.boardproject.skill;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillService {

    /* DI */
    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private UserSkillRepository userSkillRepository;

    @Autowired
    private PostingSkillRepository postingSkillRepository;


    public List<Skill> 전체기술조회() {
        // JPA로 모두 찾아서 리턴
        List<Skill> skillList = skillRepository.findAll();
        return skillList;
    }

    public List<Skill> 스킬이름전부() {
        return skillRepository.findAll();
    }

    public List<UserSkill> 유저스킬조회(Integer id) {
        return userSkillRepository.selectUserSkill(id);
    }

    public List<PostingSkill> 공고별스킬조회(Integer id) {
        System.out.println("검색3: " + id);
        return postingSkillRepository.findByPostingId(id);

    }

}
