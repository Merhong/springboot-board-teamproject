package shop.mtcoding.boardproject.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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

    /* DI */
    @Autowired
    private UserService userService;

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private ApplyService applyService;

    @Autowired
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
    public String userApply(@RequestParam("selectedResume") Integer selectedResumeId,
            @RequestParam("postingId") Integer postingId) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        if (sessionUser == null) {
            return "redirect:/user/loginForm";
        }

        Posting posting = compService.공고찾기(postingId);
        Resume selectedResume = resumeService.이력서찾기(selectedResumeId); // 선택한 이력서를 ID로 조회
        Apply apply = new Apply();
        apply.setUser(sessionUser);
        apply.setResume(selectedResume);
        apply.setPosting(posting); // Apply 엔티티에 공고 설정
        applyService.지원(apply); // Apply 엔티티 저장
        return "redirect:/";
    }

    // 11_개인지원하기 화면
    @GetMapping("/user/applyForm")
    public String userApplyForm() {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/user/loginForm";
        }
        return "redirect:/";
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

    // 10_개인공고상세보기 화면 (사용안함)
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
        System.out.println("세션 " + sessionUser.getRole());
        // 로그인 사용자의 역할(role)에 따라 세션을 구분합니다.
        if (sessionUser != null) {
            if (sessionUser.getRole() == 0) {
                // 관리자의 경우 sessionAdmin 세션을 설정합니다.
                session.setAttribute("sessionAdmin", sessionUser);
            } else if (sessionUser.getRole() == 1) {
                // 개인 사용자의 경우 sessionUser 세션을 설정합니다.
                session.setAttribute("sessionUser", sessionUser);
                System.out.println("x : 유저 로그인");
            } else if (sessionUser.getRole() == 2) {
                // 기업 사용자의 경우 CompSession 세션을 설정합니다.
                session.setAttribute("CompSession", sessionUser);
                System.out.println("x : 기업 로그인");
            }

        if (sessionUser.getRole() == 0) {
            session.setAttribute("sessionAdmin", sessionUser);
        }
        if (sessionUser.getRole() == 1) {
            session.setAttribute("sessionUser", sessionUser);
        }
        if (sessionUser.getRole() == 2) {
            session.setAttribute("CompSession", sessionUser);
        }

        // 개인 및 기업 사용자의 경우 세부 정보를 SessionCompDTO에 저장하여 세션에 추가합니다.
        if (sessionUser.getRole() == 1 || sessionUser.getRole() == 2) {
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
                session.setAttribute("sessionComp", sessionComp);
            }

        }

        // 로그인 후 메인 페이지로 리다이렉트합니다.

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

    // 로그인 아이디(이메일) 중복체크
    @GetMapping("/check")
    public @ResponseBody ApiUtil<String> check(String useremail) {
        User user = userService.이메일중복체크(useremail);
        if (user != null) {
            return new ApiUtil<String>(false, "이메일이 중복 되었습니다.");
        }

        return new ApiUtil<String>(true, "이메일을 사용 할 수 있습니다.");
    }
}
