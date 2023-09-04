package shop.mtcoding.boardproject.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.boardproject._core.util.ApiUtil;
import shop.mtcoding.boardproject.apply.Apply;
import shop.mtcoding.boardproject.apply.ApplyService;
import shop.mtcoding.boardproject.comp.CompRequest;
import shop.mtcoding.boardproject.comp.CompService;
import shop.mtcoding.boardproject.posting.Posting;
import shop.mtcoding.boardproject.resume.Resume;
import shop.mtcoding.boardproject.resume.ResumeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserController {

    @Autowired // DI
    private UserService userService;

    @Autowired // DI
    private ResumeService resumeService;

    @Autowired // DI
    private ApplyService applyService;

    @Autowired // DI
    private CompService compService;

    @Autowired
    private HttpSession session;

    // 17_개인기업추천 화면
    @GetMapping("/user/recommendForm")
    public String userRecommendForm() {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/user/loginForm";
        }
        return "user/recommendForm";
    }

    // 15_개인지원내역 화면
    @GetMapping("/user/applyList")
    public String userApplyList(HttpServletRequest request) {
        User user = (User) session.getAttribute("sessionUser");
        List<Apply> applyList = applyService.유저지원내역전체(user.getId());
        request.setAttribute("applyList", applyList);
        return "user/applyList";
    }

    // 14번 이력서 수정 버튼 POST

    // 14번 이력서 삭제 버튼 POST

    // 13번 사진수정 버튼 POST

    // 12번 수정하기 버튼 POST
    @PostMapping("/user/update")
    public String userUpdate(UserRequest.UpdateDTO updateDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/user/loginForm";
        }
        User user = userService.회원수정(updateDTO, sessionUser.getId());
        session.setAttribute("sessionUser", user);

        return "redirect:/";
    }

    // 12_마이페이지 화면
    @GetMapping("/user/updateForm")
    public String userMyPage(HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/user/loginForm";
        }
        User user = userService.회원정보보기(sessionUser.getId());
        request.setAttribute("user", user);
        return "user/updateForm";
    }

    // 11번 지원하기 버튼 POST
    @PostMapping("/user/apply")
    public String userApply() {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/user/loginForm";
        }

        return "redirect:/";
    }

    // 11_개인지원하기 화면

    @GetMapping("/user/applyForm")
    public String userApplyForm() {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/user/loginForm";
        }

    @GetMapping("/user/applyForm/{postingId}")
    public String userApplyForm(Model model, @PathVariable("postingId") Integer postingId) {
        Posting posting = compService.공고찾기(postingId);

        User sessionUser = (User) session.getAttribute("sessionUser");
        Integer user = sessionUser.getId();
        List<Resume> resumes = resumeService.이력서목록(user);

        model.addAttribute("resumes", resumes); // 목록 추가
        model.addAttribute("user", user); // 유저 아이디를 모델에 추가
        model.addAttribute("posting", posting); // 유저 아이디를 모델에 추가

        return "user/applyForm";
    }

    // 10_개인공고상세보기 화면
    @GetMapping("/user/postingDetail")
    public String userPostingDetail() {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/user/loginForm";
        }
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

        if (sessionUser.getRole() == 0) {
            session.setAttribute("sessionAdmin", sessionUser);
        }
        if (sessionUser.getRole() == 1) {
            session.setAttribute("sessionUser", sessionUser);
        }
        if (sessionUser.getRole() == 2) {
            session.setAttribute("CompSession", sessionUser);
        }
        if (sessionUser.getCompname() != null) {
            System.out.println("sessionComp 실행");
          
          
        if (sessionUser.getRole() != 1) {
            CompRequest.SessionCompDTO sessionComp = CompRequest.SessionCompDTO.builder()
                    .userId(sessionUser.getId())
                    .email(sessionUser.getEmail())
                    .compname(sessionUser.getCompname())
                    .compRegister(sessionUser.getCompRegister())
                    .tel(sessionUser.getTel())
                    .photo(sessionUser.getPhoto())
                    .address(sessionUser.getAddress())
                    .homepage(sessionUser.getHomepage())
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

        User sessionUser = (User) session.getAttribute("sessionUser");
        CompRequest.SessionCompDTO sessionComp = (CompRequest.SessionCompDTO) session.getAttribute("sessionComp");
        if (sessionUser == null && sessionComp == null) {
            return "redirect:/user/loginForm";
        }
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

    // 중복체크
    @GetMapping("/check")
    public @ResponseBody ApiUtil<String> check(String useremail) {
        User user = userService.유저네임중복체크(useremail);
        if (user != null) {
            return new ApiUtil<String>(false, "이메일이 중복 되었습니다.");
        }
        System.out.println("테스트 3");
        return new ApiUtil<String>(true, "이메일을 사용 할 수 있습니다.");
    }
}
