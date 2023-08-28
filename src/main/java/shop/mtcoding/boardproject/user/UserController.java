package shop.mtcoding.boardproject.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

     @Autowired // DI
    private UserService userService;

    @Autowired
    private HttpSession session;

    // Browser URL : IP주소:포트번호/login 입력시 호출
    @GetMapping("/loginForm")
    public String loginForm() {
        return "user/loginForm"; // view 파일 호출 user/loginForm 파일 호출
    }

    // Browser URL : IP주소:포트번호/userJoinForm 입력시 호출
    @GetMapping("/userJoinForm")
    public String userJoinForm() {
        return "user/userJoinForm"; // view 파일 호출 user/loginForm 파일 호출
    }

    // Browser URL : IP주소:포트번호/companyJoinForm 입력시 호출
    @GetMapping("/companyJoinForm")
    public String companyJoinForm() {
        return "user/companyJoinForm"; // view 파일 호출 user/loginForm 파일 호출
    }
}
