package shop.mtcoding.boardproject.master;

import javax.persistence.Column;

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

}
