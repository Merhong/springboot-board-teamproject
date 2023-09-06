package shop.mtcoding.boardproject.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;

public class UserRequest {

    // 회원가입 DTO
    @Getter
    @Setter
    public static class JoinDTO {
        private Integer role;
        private String email;
        private String password;
        private String username;
        private String tel;
        private String position;
    }

    // 로그인 DTO
    @Getter
    @Setter
    public static class LoginDTO {
        private String email;
        private String password;
    }

    // 회원정보수정 DTO
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
