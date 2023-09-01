package shop.mtcoding.boardproject.resume;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shop.mtcoding.boardproject.resume.ResumeRequest.ResumeDTO;
import shop.mtcoding.boardproject.user.User;

import java.util.List;

import javax.transaction.Transactional;

@Service
public class ResumeService {
    @Autowired
    private ResumeRepository resumeRepository;

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
    }

    public Resume 이력서상세보기(Integer id) {
        return resumeRepository.findByResume(id);
    }

    public List<Resume> 이력서목록(Integer id) {
        return resumeRepository.findByResumeUser(id);
    }
}