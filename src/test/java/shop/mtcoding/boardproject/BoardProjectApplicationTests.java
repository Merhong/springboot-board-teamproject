package shop.mtcoding.boardproject;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import shop.mtcoding.boardproject.posting.PostingRepository;
import shop.mtcoding.boardproject.resume.ResumeRepository;
import shop.mtcoding.boardproject.skill.UserSkillRepository;

@SpringBootTest
class BoardProjectApplicationTests {

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private UserSkillRepository userSkillRepository;

    @Autowired
    private PostingRepository postingRepository;

    @Test
    void contextLoads() {
    }

    @Test
    public void selectAllResume() {
        System.out.println(resumeRepository.findAll());
    }

    @Test
    public void selectAllUserSkill() {
        System.out.println(userSkillRepository.findAll());
    }

    @Test
    public void selectAllPosting() {
        System.out.println(userSkillRepository.findAll());
    }
}
