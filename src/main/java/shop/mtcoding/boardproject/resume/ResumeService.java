package shop.mtcoding.boardproject.resume;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shop.mtcoding.boardproject.resume.ResumeRequest.ResumeDTO;
import shop.mtcoding.boardproject.user.User;
import shop.mtcoding.boardproject.user.UserRepository;

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

}

