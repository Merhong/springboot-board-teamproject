package shop.mtcoding.boardproject.resume;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

public class ResumeRequest {

    @Getter
    @Setter
    public static class ResumeDTO {
        private Integer id;
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
