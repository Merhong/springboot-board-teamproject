package shop.mtcoding.boardproject.resume;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder.Default;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.web.multipart.MultipartFile;

public class ResumeRequest {

    @Getter
    @Setter
    public static class ResumeDTO {
        private Integer id;
        private MultipartFile pto;
        private String username;
        private String tel;
        private String address;
        private Date birth;

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

    @Getter
    @Setter
    public static class ResumeUpdateDTO {
        private Integer id;
        private MultipartFile pto;
        private String username;
        private String tel;
        private String address;
        private Date birth;

        private String title;
        private String grade;
        private String career;
        private String personalStatement;

    }

}
