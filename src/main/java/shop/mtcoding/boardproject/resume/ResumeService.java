package shop.mtcoding.boardproject.resume;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shop.mtcoding.boardproject._core.error.ex.MyException;
import shop.mtcoding.boardproject._core.vo.MyPath;
import shop.mtcoding.boardproject.resume.ResumeRequest.ResumeDTO;
import shop.mtcoding.boardproject.resume.ResumeRequest.ResumeUpdateDTO;
import shop.mtcoding.boardproject.skill.UserSkillRepository;
import shop.mtcoding.boardproject.user.User;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

@Service
public class ResumeService {
    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private UserSkillRepository userSkillRepository;

    @Transactional
    public void 이력서등록(ResumeDTO resumeDTO, Integer id) {
        UUID uuid = UUID.randomUUID(); // 랜덤한 해시값을 만들어줌
        String fileName = uuid + "_" + resumeDTO.getPto().getOriginalFilename();

        // 프로젝트 실행 파일 변경 -> blogv2-1.0.jar
        // 해당 실행파일 경로에 images 폴더가 필요함
        Path filePath = Paths.get(MyPath.IMG_PATH + fileName);
        try {
            Files.write(filePath, resumeDTO.getPto().getBytes());
        } catch (Exception e) {
            throw new MyException(e.getMessage());
        }
        Resume resume = Resume.builder()
                .user(User.builder().id(id).build())
                .title(resumeDTO.getTitle())
                .grade(resumeDTO.getGrade())
                .career(resumeDTO.getCareer())
                .personalStatement(resumeDTO.getPersonalStatement())
                .disclosure(resumeDTO.getDisclosure())
                .build();
        resumeRepository.save(resume);

        List<Integer> skillList = resumeDTO.getSkillList();
        for (Integer integer : skillList) {
            userSkillRepository.insertUserSkill(integer, id);
        }
    }

    public Resume 이력서상세보기(Integer id) {
        return resumeRepository.findByResume(id);
    }

    public List<Resume> 이력서목록(Integer id) {
        return resumeRepository.findByResumeUser(id);
    }

    @Transactional
    public void 이력서수정(Integer id, ResumeUpdateDTO resumeUpdateDTO) {
        // 이력서 ID로 엔티티를 찾습니다.
        Optional<Resume> optionalResume = resumeRepository.findById(id);

        UUID uuid = UUID.randomUUID(); // 랜덤한 해시값을 만들어줌
        String fileName = uuid + "_" + resumeUpdateDTO.getPto().getOriginalFilename();

        // 프로젝트 실행 파일 변경 -> blogv2-1.0.jar
        // 해당 실행파일 경로에 images 폴더가 필요함
        Path filePath = Paths.get(MyPath.IMG_PATH + fileName);
        try {
            Files.write(filePath, resumeUpdateDTO.getPto().getBytes());
        } catch (Exception e) {
            throw new MyException(e.getMessage());
        }

        if (optionalResume.isPresent()) {
            Resume resume = optionalResume.get();

            // DTO에서 가져온 데이터로 이력서 엔티티를 수정
            User user = resume.getUser();
            user.setUsername(resumeUpdateDTO.getUsername());
            user.setTel(resumeUpdateDTO.getTel());
            user.setAddress(resumeUpdateDTO.getAddress());
            user.setBirth(resumeUpdateDTO.getBirth());
            user.setPhoto(fileName);

            resume.setUser(user);
            resume.setTitle(resumeUpdateDTO.getTitle());
            resume.setGrade(resumeUpdateDTO.getGrade());
            resume.setCareer(resumeUpdateDTO.getCareer());
            resume.setPersonalStatement(resumeUpdateDTO.getPersonalStatement());

            // 수정된 이력서를 저장합니다.
            resumeRepository.save(resume);
        }
    }

    @Transactional
    public void 이력서삭제(Integer resumeId) {
        resumeRepository.deleteById(resumeId);

    }

    @Transactional
    public Resume 이력서찾기(Integer resumeId) {
        Optional<Resume> resume = resumeRepository.findById(resumeId);
        // Optional에서 Resume 객체를 가져오거나, 없을 경우 null을 반환하도록 수정
        return resume.orElse(null);
    }
}