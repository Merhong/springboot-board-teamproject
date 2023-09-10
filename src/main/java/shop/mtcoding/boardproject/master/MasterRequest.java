package shop.mtcoding.boardproject.master;

import lombok.Getter;
import lombok.Setter;

public class MasterRequest {

    @Getter
    @Setter
    public static class MasterDTO {
        private String category;
        private String title;
        private String content;
    }

    @Getter
    @Setter
    public static class ReplyDTO {
        private Integer userId;
        private String content;
    }

}
