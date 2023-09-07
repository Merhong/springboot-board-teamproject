package shop.mtcoding.boardproject.user;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.boardproject.skill.Skill;

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

    @Getter
    @Setter
    public static class SearchDTO {

        private List<String> skillName;
        private String position;
    }

}
