package shop.mtcoding.boardproject.user;

import java.sql.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class CompRequest {


    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    public static class SessionCompDTO {
        private Integer userId;
        private String email;
        private String compname;
        private String compRegister;
        private String tel;
        private String photo;
        private String address;
        private String homepage;
        private Integer role;


        @Builder
        public SessionCompDTO(Integer userId, String email, String compname, String compRegister, String tel,
                String photo, String address, String homepage, Integer role) {
            this.userId = userId;
            this.email = email;
            this.compname = compname;
            this.compRegister = compRegister;
            this.tel = tel;
            this.photo = photo;
            this.address = address;
            this.homepage = homepage;
            this.role = role;
        }
    } 


    @Getter
    @Setter
    @ToString
    public static class PicDTO{
        private MultipartFile photo;
    }

    @Getter
    @Setter
    @ToString
    public static class JoinDTO extends PicDTO {
        private String email;
        private String password;
        private String compname;
        private String compRegister;
        private String tel;
        private String address;
        private String homepage;
        private Integer role;

        public JoinDTO() {
            this.role = 2;
        }
    } 

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public static class SaveDTO {
        private String title;
        private String desc;
        private String position;
        private String region;
        private String career;
        private String education;
        private Date expiryDate;

        private List<String> postingSkill;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public static class UpdateDTO {
        private String title;
        private String desc;
        private String position;
        private String region;
        private String career;
        private String education;
        private Date expiryDate;

        private List<String> postingSkill;
    } 

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public static class compUpdateDTO extends PicDTO {
        private String address;
        private String tel;
        private String homepage;
    } 


}
