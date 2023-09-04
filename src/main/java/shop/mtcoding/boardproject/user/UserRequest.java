package shop.mtcoding.boardproject.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.util.List;

public class UserRequest {

    @Getter
    @Setter
    public static class JoinDTO {

        private String email;
        private String password;
        private String username;
        private String tel;

    }

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
