package shop.mtcoding.boardproject.user;

import java.sql.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

public class UserRequest {

    @Getter
    @Setter
    public static class LoginDTO {
        private String email;
        private String password;
    }

    @Getter
    @Setter
    public static class UpdateDTO {
        private String username;
        private String tel;
        private String address;
        private Date birth;
        private MultipartFile pto;
    }

}
