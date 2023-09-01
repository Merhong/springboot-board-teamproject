package shop.mtcoding.boardproject.resume;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

import javax.sound.sampled.Line;

public class ResumeRequest {

    @Getter
    @Setter
    public static class ResumeDTO {
        private Integer id;
        private String username;
        private String tel;
        private String address;
        // private Date birth;

        private String title;
        private String grade;
        private String career;
        private String personalStatement;

        private List<Integer> skillList;

    }

}
