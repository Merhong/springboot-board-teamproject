package shop.mtcoding.boardproject.resume;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.util.List;

public class ResumeRequest {

    // 이력서 DTO
    @Getter
    @Setter
    public static class ResumeDTO {
        private Integer id;
        private MultipartFile pto;
        private String username;
        private String tel;
        private String address;
        private Date birth;
        private String position;

        private String title;
        private String grade;
        private String career;
        private String personalStatement;

        private List<Integer> skillList;
        private Boolean disclosure;

        public ResumeDTO() {
            this.disclosure = false;
        }

    }

    // 이력서 수정 DTO
    @Getter
    @Setter
    public static class ResumeUpdateDTO {
        private Integer id;
        private MultipartFile pto;
        private String username;
        private String tel;
        private String address;
        private Date birth;
        private String position;

        private String title;
        private String grade;
        private String career;
        private String personalStatement;

        private List<Integer> skillList;
        private Boolean disclosure;

        public ResumeUpdateDTO() {
            this.disclosure = false;
        }

    }

}
