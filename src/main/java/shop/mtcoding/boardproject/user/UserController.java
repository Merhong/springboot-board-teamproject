package shop.mtcoding.boardproject.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired // DI
    private UserService userService;

    @Autowired
    private HttpSession session;

    // 17_개인기업추천 화면
    @GetMapping("/user/recommendForm")
    public String userRecommendForm() {
        return "user/recommendForm";
    }

    // 14번 이력서 수정 버튼 POST

    // 14번 이력서 삭제 버튼 POST

    // 13번 사진수정 버튼 POST

    // 12번 수정하기 버튼 POST
    @PostMapping("/user/update")
    public String userUpdate(UserRequest.UpdateDTO updateDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        System.out.println("Session user: " + sessionUser);

        User user = userService.회원수정(updateDTO, sessionUser.getId());
        session.setAttribute("sessionUser", user);

        return "redirect:/";
    }

    // 12_마이페이지 화면
    @GetMapping("/user/updateForm")
    public String userMyPage(HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        User user = userService.회원정보보기(sessionUser.getId());
        request.setAttribute("user", user);
        return "user/updateForm";
    }

    // 11번 지원하기 버튼 POST
    @PostMapping("/user/apply")
    public String userApply() {
        return "redirect:/";
    }

    // 11_개인지원하기 화면
    @GetMapping("/user/applyForm")
    public String userApplyForm() {
        return "user/applyForm";
    }

    // 10_개인공고상세보기 화면
    @GetMapping("/user/postingDetail")
    public String userPostingDetail() {
        return "user/postingDetail";
    }

    // 5_로그인 화면
    @GetMapping("/user/loginForm")
    public String loginForm() {
        return "user/loginForm"; // view 파일 호출 user/loginForm 파일 호출
    }

    // 로그인
    @PostMapping("/login")
    public String login(UserRequest.LoginDTO loginDTO, HttpSession session) {
        User sessionUser = userService.로그인(loginDTO);
        session.setAttribute("sessionUser", sessionUser);

        if(sessionUser.getCompname()!=null){
            CompRequest.SessionCompDTO sessionComp = CompRequest.SessionCompDTO.builder()
                .userId(sessionUser.getId())
                .email(sessionUser.getEmail())
                .compname(sessionUser.getCompname())
                .compRegister(sessionUser.getCompRegister())
                .tel(sessionUser.getTel())
                .photo(sessionUser.getPhoto())
                .address(sessionUser.getAddress())
                .role(sessionUser.getRole())
                .build();
            // System.out.println("테스트:"+sessionComp);
            session.setAttribute("sessionComp", sessionComp);
        }


        return "redirect:/";
    }

    // 로그아웃
    @GetMapping("/logout")
    public String logout() {
        session.invalidate(); // 세션 무효화(세션 전체를 비움 - 서랍 비우는 거)
        return "redirect:/";
    }

    // 3_개인회원가입 화면
    @GetMapping("/user/joinForm")
    public String userJoinForm() {
        return "user/joinForm"; // view 파일 호출 user/loginForm 파일 호출
    }

    // 2번 회원가입 버튼 POST
    @PostMapping("/join")
    public String join(UserRequest.JoinDTO joinDTO) {
        userService.회원가입(joinDTO);
        return "user/loginForm";
    }

    // 2_회원가입유형선택 화면
    @GetMapping("/selectJoinForm")
    public String selectJoinForm() {
        return "user/selectJoinForm";
    }

}
