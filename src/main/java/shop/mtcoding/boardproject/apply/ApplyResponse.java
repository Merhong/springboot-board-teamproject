package shop.mtcoding.boardproject.apply;

import lombok.Getter;
import lombok.Setter;

public class ApplyResponse {

    @Getter
    @Setter
    public static class TestApplyListDTO {
        private String postingTitle;
        private String resumeTitle;
        private String position;
        private String statement;

    }
}
