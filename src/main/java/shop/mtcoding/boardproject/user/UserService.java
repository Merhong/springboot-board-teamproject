package shop.mtcoding.boardproject.user;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shop.mtcoding.boardproject.user.UserRequest.JoinDTO;

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

}
