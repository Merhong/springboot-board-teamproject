package shop.mtcoding.boardproject.user;

import javax.servlet.http.HttpSession;

import org.h2.tools.Script;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

     @Autowired // DI
    private UserService userService;

    @Autowired
    private HttpSession session;
     @GetMapping("/logout")
    public String logout() {
        session.invalidate();
        return "redirect:/";
    }

    // C - V
    @GetMapping("/joinForm")
    public String joinForm() {
        return "user/joinForm";
    }

    // M - V - C
    @PostMapping("/join")
    public String join(UserRequest.JoinDTO joinDTO) {
        // System.out.println(joinDTO.getPic().getOriginalFilename());
        // System.out.println(joinDTO.getPic().getSize());
        // System.out.println(joinDTO.getPic().getContentType());

        userService.회원가입(joinDTO);
        return "user/loginForm"; // persist 초기화
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "user/loginForm";
    }

    @PostMapping("/login")
    public @ResponseBody String login(UserRequest.LoginDTO loginDTO) {
        System.out.println("1");
        User sessionUser = userService.로그인(loginDTO);
        if (sessionUser == null) {
            return "redirect:/";
        }
        session.setAttribute("sessionUser", sessionUser);
        return "redirect:/";
    }

    @GetMapping("/user/updateForm")
    public String updateForm() {
         return "/";
    }
}
