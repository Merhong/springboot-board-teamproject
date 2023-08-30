package shop.mtcoding.boardproject.user;

import lombok.Getter;
import lombok.Setter;

public class UserRequest {

    @Getter
    @Setter
    public static class JoinDTO {
      
        private String email;
        private String password;
        private String username;
        private Integer tel;

    } 

}
