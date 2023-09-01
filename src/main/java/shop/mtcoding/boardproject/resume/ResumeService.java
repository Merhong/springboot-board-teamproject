package shop.mtcoding.boardproject.resume;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shop.mtcoding.boardproject.resume.ResumeRequest.ResumeDTO;
import shop.mtcoding.boardproject.skill.UserSkillRepository;
import shop.mtcoding.boardproject.user.User;

import java.util.List;

import javax.transaction.Transactional;

@Service
public class ResumeService {

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private UserSkillRepository userSkillRepository;

    @Transactional
    public void 이력서등록(ResumeDTO resumeDTO, Integer id) {
        Resume resume = Resume.builder()
                .user(User.builder().id(id).build())
                .title(resumeDTO.getTitle())
                .grade(resumeDTO.getGrade())
                .career(resumeDTO.getCareer())
                .personalStatement(resumeDTO.getPersonalStatement())
                .build();

        resumeRepository.save(resume);

        System.out.println("테스트 유저스킬 등록실행 userId" + id);
        List<Integer> skillList = resumeDTO.getSkillList();
        System.out.println("테스트 유저스킬 리스트 대입");
        for (Integer integer : skillList) {
            userSkillRepository.insertUserSkill(integer, id);
            System.out.println(integer);
        }
    }

}
