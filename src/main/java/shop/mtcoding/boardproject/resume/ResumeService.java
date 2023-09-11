package shop.mtcoding.boardproject.resume;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import shop.mtcoding.boardproject._core.error.ex.MyException;
import shop.mtcoding.boardproject._core.vo.MyPath;
import shop.mtcoding.boardproject.resume.ResumeRequest.ResumeDTO;
import shop.mtcoding.boardproject.skill.UserSkillRepository;
import shop.mtcoding.boardproject.user.User;

import javax.transaction.Transactional;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ResumeService {
    /* DI */
    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private UserSkillRepository userSkillRepository;


    @Transactional
    public void 이력서등록(ResumeDTO resumeDTO, Integer id) {

        userSkillRepository.deleteAllByUserId(id);


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
        // 이력서에 해당하는 필드값을 builder로 담는다.
        Resume resume = Resume.builder()
                .user(User.builder().id(id).build())

                .title(resumeDTO.getTitle())
                .grade(resumeDTO.getGrade())
                .career(resumeDTO.getCareer())
                .personalStatement(resumeDTO.getPersonalStatement())
                .disclosure(resumeDTO.getDisclosure())
                .build();

        // JPA save
        resumeRepository.save(resume);

        // 이력서(개인) 보유 기술 리스트
        List<Integer> skillList = resumeDTO.getSkillList();
        // foreach문을 사용해서 개인이 보유중인 기술을 insert한다.
        for (Integer integer : skillList) {
            userSkillRepository.insertUserSkill(integer, id);
        }

    }

    public Resume 이력서상세보기(Integer id) {
        // id에 해당하는 이력서를 찾는다.
        return resumeRepository.findByResume(id);
    }

    @Transactional
    public Resume 이력서찾기(Integer resumeId) { // 이력서 비공개라도 봐진다 밑에꺼 쓰면 못봄
        Optional<Resume> resume = resumeRepository.findById(resumeId);
        // Optional에서 Resume 객체를 가져오거나, 없을 경우 null을 반환하도록 수정
        return resume.orElse(null);
    }

    @Transactional
    public Resume 이력서찾기(Integer resumeId, Integer userId) { // 비공개 이력서는 못보게
        Optional<Resume> resumeOp = resumeRepository.findById(resumeId);
        if (resumeOp.isPresent()) {
            Resume resume = resumeOp.get();

            if (resume.getDisclosure() == false) {
                if (resume.getUser().getId() != userId) {
                    throw new MyException(resumeId + "비공개임");
                }
            }

            return resume;
        } else {
            throw new MyException(resumeId + " 없음");
        }
    }

    public List<Resume> 이력서목록(Integer id) {
        return resumeRepository.findByResumeUser(id);
    }

    @Transactional
    public void 이력서수정(Integer id, ResumeRequest.ResumeUpdateDTO resumeUpdateDTO) {
        // 이력서 ID로 엔티티를 찾습니다.
        Optional<Resume> optionalResume = resumeRepository.findById(id);

        if (optionalResume.isPresent()) {
            Resume resume = optionalResume.get();

            // 이력서의 현재 사진 파일 이름을 가져옵니다.
            String currentPhotoFileName = resume.getUser().getPhoto();

            // DTO에서 가져온 데이터로 이력서 엔티티를 수정
            User user = resume.getUser();
            user.setUsername(resumeUpdateDTO.getUsername());
            user.setTel(resumeUpdateDTO.getTel());
            user.setAddress(resumeUpdateDTO.getAddress());
            user.setBirth(resumeUpdateDTO.getBirth());
            user.setPosition(resumeUpdateDTO.getPosition());

            // 사진 파일 업로드를 확인합니다.
            MultipartFile newPhoto = resumeUpdateDTO.getPto();
            if (newPhoto != null && !newPhoto.isEmpty()) {
                // 새로운 사진 파일이 업로드되었을 경우
                UUID uuid = UUID.randomUUID();
                String fileName = uuid + "_" + newPhoto.getOriginalFilename();
                Path filePath = Paths.get(MyPath.IMG_PATH + fileName);
                try {
                    Files.write(filePath, newPhoto.getBytes());

                    // 새로운 사진 파일 이름을 사용합니다.
                    user.setPhoto(fileName);

                    // 이전 사진 파일을 삭제합니다.
                    if (currentPhotoFileName != null) {
                        Path currentPhotoPath = Paths.get(MyPath.IMG_PATH + currentPhotoFileName);
                        Files.deleteIfExists(currentPhotoPath);
                    }
                } catch (Exception e) {
                    throw new MyException(e.getMessage());
                }
            }

            resume.setUser(user);
            resume.setTitle(resumeUpdateDTO.getTitle());
            resume.setGrade(resumeUpdateDTO.getGrade());
            resume.setCareer(resumeUpdateDTO.getCareer());
            resume.setPersonalStatement(resumeUpdateDTO.getPersonalStatement());
            resume.setDisclosure(resumeUpdateDTO.getDisclosure());

            // 수정된 이력서를 저장합니다.
            resumeRepository.save(resume);

            // 기존 스킬 목록을 삭제
            userSkillRepository.deleteAllByUserId(user.getId());

            // 새로 체크한 스킬 목록을 추가
            List<Integer> skillList = resumeUpdateDTO.getSkillList();
            for (Integer integer : skillList) {
                userSkillRepository.insertUserSkill(integer, user.getId());
            }
        }
    }

    @Transactional
    public void 이력서삭제(Integer resumeId) {
        resumeRepository.deleteById(resumeId);
    }
}