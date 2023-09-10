package shop.mtcoding.boardproject.apply;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class ApplyResponse {

    @Getter
    @Setter
    public static class TestApplyListDTO {
        private String postingTitle;
        private String resumeTitle;
        private String position;
        private String statement;
    }


    @Getter
    @Setter
    @ToString
    public static class ApplyCompDTO {
        private Integer applyId;
        private Integer postingId;
        private Integer resumeId;
        private String postingTitle;
        private String resumeTitle;
        private String resumeCareer;
        private String resumeGrade;
        private String username;
        private String statement;
    }


}
