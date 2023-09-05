package shop.mtcoding.boardproject.master;

import lombok.Getter;
import lombok.Setter;

public class MasterResponse {

    @Getter
    @Setter
    public static class MasterListDTO {

        private Integer id;
        private String title;

    }

}
