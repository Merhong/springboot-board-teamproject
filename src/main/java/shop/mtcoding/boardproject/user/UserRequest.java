package shop.mtcoding.boardproject.user;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

public class UserRequest {
 @Getter
    @Setter
    public static class JoinDTO {
        private String username;
        private String password;
        private String email;
        private MultipartFile pic;
    }

    @Getter
    @Setter
    public static class LoginDTO {
        private String username;
        private String password;
    }
   
}
