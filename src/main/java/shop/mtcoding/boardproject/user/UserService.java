package shop.mtcoding.boardproject.user;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shop.mtcoding.boardproject._core.error.ex.MyException;
import shop.mtcoding.boardproject._core.vo.MyPath;
import shop.mtcoding.boardproject.user.UserRequest.LoginDTO;
import shop.mtcoding.boardproject.user.UserRequest.UpdateDTO;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User 로그인(LoginDTO loginDTO) {
        User user = userRepository.findByUserEmail(loginDTO.getEmail());

        if (user == null) {
            throw new MyException("없는 이메일 입니다.");
        }

        // 패스워드 검증
        if (!user.getPassword().equals(loginDTO.getPassword())) {
            throw new MyException("패스워드가 잘못되었습니다!");
        }
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
        User user = userRepository.findById(id).get();

        user.setUsername(updateDTO.getUsername());
        user.setTel(updateDTO.getTel());
        user.setAddress(updateDTO.getAddress());
        user.setBirth(updateDTO.getBirth());
        user.setPhoto(fileName);

        return user;

    }

    public User 회원정보보기(Integer id) {
        return userRepository.findById(id).get();
    }

}
