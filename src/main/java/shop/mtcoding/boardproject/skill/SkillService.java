package shop.mtcoding.boardproject.skill;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.boardproject.resume.ResumeRequest.ResumeDTO;

@Service
public class SkillService {

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private UserSkillRepository userSkillRepository;

    public List<Skill> 모든스킬찾기() {
        List<Skill> skillList = skillRepository.findAll();
        return skillList;
    }

    @Transactional
    public void 유저스킬등록(ResumeDTO resumeDTO, Integer userId) {

    }

}
