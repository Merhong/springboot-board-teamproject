package shop.mtcoding.boardproject.skill;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shop.mtcoding.boardproject.resume.ResumeRequest.ResumeDTO;
import shop.mtcoding.boardproject.resume.ResumeRequest.ResumeUpdateDTO;
import shop.mtcoding.boardproject.user.User;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

@Service
public class SkillService {

    @Autowired
    private SkillRepository skillRepository;

    public List<Skill> 전체기술조회() {
        List<Skill> skillList = skillRepository.findAll();
        return skillList;
    }

}