package shop.mtcoding.boardproject.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

     @Autowired // DI
    private UserService userService;

    @Autowired
    private HttpSession session;

}
