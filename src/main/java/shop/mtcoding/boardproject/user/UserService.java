package shop.mtcoding.boardproject.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shop.mtcoding.boardproject._core.error.ex.MyException;
import shop.mtcoding.boardproject._core.vo.MyPath;
import shop.mtcoding.boardproject.posting.Posting;
import shop.mtcoding.boardproject.posting.PostingQueryRepository;
import shop.mtcoding.boardproject.posting.PostingRepository;
import shop.mtcoding.boardproject.skill.Skill;
import shop.mtcoding.boardproject.user.UserRequest.LoginDTO;
import shop.mtcoding.boardproject.user.UserRequest.UpdateDTO;

import javax.transaction.Transactional;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService {

    /* DI */
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostingRepository postingRepository;

    @Autowired
    PostingQueryRepository postingQueryRepository;

    @Transactional
    public void 회원가입(UserRequest.JoinDTO joinDTO) {
        // 개인회원에 해당하는 필드값을 builder로 담는다.
        User user = User.builder()
                .role(joinDTO.getRole())
                .email(joinDTO.getEmail())
                .password(joinDTO.getPassword())
                .username(joinDTO.getUsername())
                .tel(joinDTO.getTel())
                .position(joinDTO.getPosition())
                .build();

        // JPA save
        userRepository.save(user);
    }

    public User 로그인(LoginDTO loginDTO) {
        // 아이디(이메일)를 찾는다.
        User user = userRepository.findByUserEmail(loginDTO.getEmail());
        if (user == null) {
            throw new MyException("없는 이메일 입니다.");
        }

        // 패스워드 검증
        if (!user.getPassword().equals(loginDTO.getPassword())) {
            throw new MyException("패스워드가 잘못되었습니다!");
        }
        // 위의 검증들을 통과하면 user 리턴
        return user;
    }

    @Transactional
    public User 회원수정(UpdateDTO updateDTO, Integer id) {
        UUID uuid = UUID.randomUUID(); // 랜덤한 해시값을 만들어줌
        String fileName = uuid + "_" + updateDTO.getPto().getOriginalFilename();

        // 프로젝트 실행 파일 변경 -> blogv2-1.0.jar
        // 해당 실행파일 경로에 images 폴더가 필요함
        Path filePath = Paths.get(MyPath.IMG_PATH + fileName);
        try {
            Files.write(filePath, updateDTO.getPto().getBytes());
        } catch (Exception e) {
            throw new MyException(e.getMessage());
        }

        // 수정 내용을 필드에 할당한다.
        User user = userRepository.findById(id).get();
        user.setUsername(updateDTO.getUsername());
        user.setTel(updateDTO.getTel());
        user.setAddress(updateDTO.getAddress());
        user.setBirth(updateDTO.getBirth());
        user.setPhoto(fileName);

        // 수정된 user 리턴
        return user;
    }

    public User 회원정보보기(Integer id) {
        // JPA로 id를 찾아서 해당하는 user 리턴
        return userRepository.findById(id).get();
    }

    public User 이메일중복체크(String email) {
        // 쿼리를 통해 이메일에 해당하는 user 리턴
        User user = userRepository.FindByemail(email);
        return user;
    }

    public List<Posting> 기업추천검색(UserRequest.SearchDTO searchDTO) {

        List<String> skillList = searchDTO.getSkillName();
        String position = searchDTO.getPosition();
        System.out.println("skillList : " + skillList);
        System.out.println("position : " + position);

        HashSet<Posting> postingSet = new HashSet<Posting>();
        List<Posting> listPositionPosting = postingRepository.findByPosition(position);
        List<Posting> listSkillPosting = postingQueryRepository.joinSkillPosting(skillList);

        if (position.equals("all")) {
            for (Posting posting : listSkillPosting) {
                postingSet.add(posting);
            }
        }
        if (!position.equals("all")) {
            for (Posting posting : listSkillPosting) {
                System.out.println("posting.getPosition() : " + posting.getPosition());
                System.out.println("position : " + position);
                if (posting.getPosition().equals(position)) {
                    postingSet.add(posting);
                }
            }
            System.out.println("postingSet size" + postingSet.size());
        }
        List<Posting> PostingList = new ArrayList<>();

        Iterator<Posting> iterator = postingSet.iterator();
        while (iterator.hasNext()) {
            Posting posting = iterator.next();
            PostingList.add(posting);
        }
        return PostingList;
    }
}
