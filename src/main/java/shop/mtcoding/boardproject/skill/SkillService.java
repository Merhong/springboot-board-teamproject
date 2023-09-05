package shop.mtcoding.boardproject.skill;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shop.mtcoding.boardproject.resume.ResumeRequest.ResumeDTO;
import shop.mtcoding.boardproject.resume.ResumeRequest.ResumeUpdateDTO;
import shop.mtcoding.boardproject.user.User;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SkillService {

    /* DI */
    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private UserSkillRepository userSkillRepository;

    public List<Skill> 전체기술조회() {
        // JPA로 모두 찾아서 리턴
        List<Skill> skillList = skillRepository.findAll();
        return skillList;
    }

    // TODO 위, 아래 메서드 어떤것 쓸지 정해야함!!!
    public List<Skill> 스킬이름전부() {
        return skillRepository.findAll();
    }

    public List<UserSkill> 유저스킬조회(Integer id) {
        return userSkillRepository.selectUserSkill(id);
    }

}
