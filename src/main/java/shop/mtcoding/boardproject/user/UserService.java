package shop.mtcoding.boardproject.user;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shop.mtcoding.boardproject.user.UserRequest.JoinDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shop.mtcoding.boardproject._core.error.ex.MyException;
import shop.mtcoding.boardproject.user.UserRequest.LoginDTO;

@Service
public class UserService {
    @Autowired 
    private UserRepository userRepository;


    @Transactional
    public void 회원가입(UserRequest.JoinDTO joinDTO) {
        
    User user = User.builder()
            .email(joinDTO.getEmail())
            .password(joinDTO.getPassword())
            .username(joinDTO.getUsername())
            .tel(joinDTO.getTel())
            .build();
     userRepository.save(user);


    }


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

}
