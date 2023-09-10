package shop.mtcoding.boardproject.recommend;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class RecommendResponse {


    @Getter
    @Setter
    @ToString
    public static class RecommendCompDTO {
        private Integer recommendId;
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
