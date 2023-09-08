package shop.mtcoding.boardproject.apply;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


    @Getter
    @Setter
    @ToString
    public class ApplyTestDTO {

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


